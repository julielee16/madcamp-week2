package com.example.project2.ui.present.friend;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.project2.MainActivity;
import com.example.project2.ui.present.RetroInterface;
import com.example.project2.ui.present.presentlist.Present;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FriendRepository {
    private static MutableLiveData<List<Friend>> allFriends;
    private static MutableLiveData<List<Friend>> myFriends;
    private static MutableLiveData<List<Friend>> selectedFriends;

    private static Retrofit retrofit;
    private static RetroInterface retroInterface;
    private static String BASE_URL="http://192.249.19.243:0680/present/upload/";

    public FriendRepository() {
        allFriends = new MutableLiveData<>();
        myFriends = new MutableLiveData<>();
        selectedFriends = new MutableLiveData<>();

        retrofit =
                new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        retroInterface = retrofit.create(RetroInterface.class);

        Call<String> getFriend = retroInterface.executeGetFriend();
        getFriend.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<Friend> parsedAllUsers = null;
                try {
                    parsedAllUsers = parseFriendJSON(response.body());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<Friend> mFriends = new ArrayList<>();
                List<Friend> sFriends = new ArrayList<>();
                for(Friend f: parsedAllUsers) {
                    if(f.getId() == MainActivity.myID) {
                        mFriends = f.getFriends();
                    }
                    if(f.getId() == MainActivity.selectedID) {
                        sFriends = f.getFriends();
                    }
                }

                allFriends.setValue(parsedAllUsers);
                myFriends.setValue(mFriends);
                selectedFriends.setValue(sFriends);

                for(Friend findMe: parsedAllUsers) {
                    if(findMe.getId() == MainActivity.myID) {
                        MainActivity.myself = findMe;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("d", "failed to connect nooo :(!");
            }
        });
    }

    public static List<Friend> parseFriendJSON(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Friend> allUsers = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // User ID and Name
            int id = Integer.parseInt(jsonObject.getString("id"));
            String name = jsonObject.getString("name");

            // Adding the list of presents under the user
            String presents = jsonObject.getString("presents");
            String[] listOfPresents = presents.split(",");
            List<Present> userPresents = new ArrayList<>();
            List<Friend> emptySponsors = new ArrayList<>();
            List<String> emptyMessages = new ArrayList<>();
            for (String s: listOfPresents) {
                Present addPresent = new Present(id, Integer.parseInt(s), "", 0, 0,0,
                        emptySponsors, emptyMessages);
                userPresents.add(addPresent);
            }

            // Adding the list of friends under the user
            String friends = jsonObject.getString("friends");
            String[] listOfFriends = friends.split(",");
            List<Friend> userFriends = new ArrayList<>();
            List<Friend> emptyFriendsList = new ArrayList<>();
            List<Present> emptyPresents = new ArrayList<>();
            for (String f: listOfFriends) {
                int friendId = Integer.parseInt(f);
                Friend addFriend = new Friend(friendId,
                        jsonArray.getJSONObject(friendId-1).getString(
                                "name"),
                        emptyFriendsList,
                        emptyPresents);
                userFriends.add(addFriend);
            }

            Friend newUser = new Friend(id, name, userFriends, userPresents);
            allUsers.add(newUser);
        }

        return allUsers;
    }

    public void insert(Friend friend) {
        new InsertFriendAsyncTask().execute(friend);
    }

    public void update(Friend friend) {
        new UpdateFriendAsyncTask().execute(friend);
    }

    public void delete(Friend friend) {
        new DeleteFriendAsyncTask().execute(friend);
    }

    public void getSelected(Friend friend) {
        new SelectedFriendAsyncTask().execute(friend);
    }

    public MutableLiveData<List<Friend>> getAllFriends() {
        return allFriends;
    }

    public MutableLiveData<List<Friend>> getMyFriends() {
        return myFriends;
    }

    public MutableLiveData<List<Friend>> getSelectedFriends() {
        return selectedFriends;
    }

    private static class InsertFriendAsyncTask extends AsyncTask<Friend, Void, Void> {

        private InsertFriendAsyncTask() {
        }

        @Override
        protected Void doInBackground(Friend... friend) {
            return null;
        }

    }

    private static class UpdateFriendAsyncTask extends AsyncTask<Friend, Void, Void> {

        private UpdateFriendAsyncTask() {
            retrofit =
                    new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
            retroInterface = retrofit.create(RetroInterface.class);
        }

        @Override
        protected Void doInBackground(Friend... friend) {
            JSONObject jsonFriend = new JSONObject();
            try {
                jsonFriend.accumulate("id", friend[0].getId());
                jsonFriend.accumulate("name", friend[0].getName());

                String friendString = "";
                for(Friend f: friend[0].getFriends()) {
                    friendString += f.getId() + ",";
                }

                String presentString = "";
                for(Present p: friend[0].getPresents()) {
                    presentString += p.getId() + ",";
                }

                jsonFriend.accumulate("friends", friendString);
                jsonFriend.accumulate("presents", presentString);
                Call<String> sendNewFriend = retroInterface.executeUploadFriend(jsonFriend.toString());
                sendNewFriend.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    private static class DeleteFriendAsyncTask extends AsyncTask<Friend, Void, Void> {

        private DeleteFriendAsyncTask() {
            retrofit =
                    new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
            retroInterface = retrofit.create(RetroInterface.class);
        }

        @Override
        protected Void doInBackground(Friend... friend) {
            JSONObject jsonFriend = new JSONObject();
            try {
                jsonFriend.accumulate("id", friend[0].getId());
                jsonFriend.accumulate("name", friend[0].getName());

                String friendString = "";
                for(Friend f: friend[0].getFriends()) {
                    friendString += f.getId() + ",";
                }

                String presentString = "";
                for(Present p: friend[0].getPresents()) {
                    presentString += p.getId() + ",";
                }

                jsonFriend.accumulate("friends", friendString);
                jsonFriend.accumulate("presents", presentString);
                Call<String> deleteFriend =
                        retroInterface.executeDeleteFriend(jsonFriend.toString());
                deleteFriend.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private static class SelectedFriendAsyncTask extends AsyncTask<Friend, Void, Void> {

        private SelectedFriendAsyncTask() {
        }

        @Override
        protected Void doInBackground(Friend... friend) {
            return null;
        }

    }
}