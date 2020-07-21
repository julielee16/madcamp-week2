package com.example.project2.ui.present.presentlist;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.project2.MainActivity;
import com.example.project2.ui.present.RetroInterface;
import com.example.project2.ui.present.friend.Friend;
import com.example.project2.ui.present.friend.FriendRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PresentRepository {
    private static MutableLiveData<List<Present>> allPresents;
    private static MutableLiveData<List<Present>> myPresents;
    private static MutableLiveData<List<Present>> selectedPresents;

    private static Retrofit retrofit;
    private static RetroInterface retroInterface;
    private static String BASE_URL="http://192.249.19.243:0680/present/upload/";

    public PresentRepository(){
        allPresents = new MutableLiveData<>();
        myPresents = new MutableLiveData<>();
        selectedPresents = new MutableLiveData<>();

        retrofit =
                new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        retroInterface = retrofit.create(RetroInterface.class);

        Call<String> getPresent = retroInterface.executeGetPresent();
        getPresent.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<Present> parsedAllPresents = null;
                try {
                    parsedAllPresents = parsePresentJSON(response.body());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<Present> mPresents = new ArrayList<>();
                List<Present> sPresents = new ArrayList<>();
                for(Present p: parsedAllPresents) {
                    if(p.getOwnerId() == MainActivity.myID) {
                        mPresents.add(p);
                    }
                    if(p.getOwnerId() == MainActivity.selectedID) {
                        sPresents.add(p);
                    }
                }

                myPresents.setValue(mPresents);
                selectedPresents.setValue(sPresents);
                allPresents.setValue(parsedAllPresents);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public List<Present> parsePresentJSON(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Present> allPresents = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // Get present ownerId, id, title, price, numFilled, and numNeeded
            int id = Integer.parseInt(jsonObject.getString("id"));
            int ownerId = Integer.parseInt(jsonObject.getString("ownerId"));
            String title = jsonObject.getString("title");
            int price = Integer.parseInt(jsonObject.getString("price"));
            int numFilled = Integer.parseInt(jsonObject.getString("numFilled"));
            int numNeeded = Integer.parseInt(jsonObject.getString("numNeeded"));

            // Get Present sponsors
            String presents = jsonObject.getString("sponsors");
            String[] listOfSponsors = presents.split(",");
            List<Friend> presentSponsors = new ArrayList<>();
            List<Present> emptyPresents = new ArrayList<>();
            List<Friend> emptyFriends = new ArrayList<>();
            List<String> emptyMessages = new ArrayList<>();
            for (String s: listOfSponsors) {
                Friend addFriend = new Friend(Integer.parseInt(s), "",  emptyFriends,
                        emptyPresents);
                presentSponsors.add(addFriend);
            }

            Present newPresent = new Present(id, ownerId, title, price, numFilled, numNeeded,
                    presentSponsors, emptyMessages);
            allPresents.add(newPresent);
        }

        return allPresents;
    }

    public void insert(Present present) {
        new PresentRepository.InsertPresentAsyncTask().execute(present);
    }

    public void update(Present present) {
        new PresentRepository.UpdatePresentAsyncTask().execute(present);
    }

    public void delete(Present present) {
        new PresentRepository.DeletePresentAsyncTask().execute(present);
    }

    public void getSelected(Present present) {
        new PresentRepository.SelectedPresentAsyncTask().execute(present);
    }

    public MutableLiveData<List<Present>> getAllPresents() {
        return allPresents;
    }

    public MutableLiveData<List<Present>> getMyPresents() {
        return myPresents;
    }

    public MutableLiveData<List<Present>> getSelectedPresents() {
        return selectedPresents;
    }

    private static class InsertPresentAsyncTask extends AsyncTask<Present, Void, Void> {

        private InsertPresentAsyncTask() {
            retrofit =
                    new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
            retroInterface = retrofit.create(RetroInterface.class);
        }

        @Override
        protected Void doInBackground(Present... present) {
            JSONObject jsonFriend = new JSONObject();
            try {
                jsonFriend.accumulate("ownerId", present[0].getOwnerId());
                jsonFriend.accumulate("id", present[0].getId());
                jsonFriend.accumulate("title", present[0].getTitle());
                jsonFriend.accumulate("price", present[0].getPrice());
                jsonFriend.accumulate("numFilled", present[0].getNumFilled());
                jsonFriend.accumulate("numNeeded", present[0].getNumNeeded());

                String sponsorString = "";
                for(Friend f: present[0].getSponsors()) {
                    sponsorString += f.getId() + ",";
                }

                String messageString = "";
                for(String s: present[0].getMessages()) {
                    messageString += s + ",";
                }

                jsonFriend.accumulate("sponsors", "1");
                jsonFriend.accumulate("messages", "null");
                Call<String> sendNewFriend =
                        retroInterface.executeInsertPresent(jsonFriend.toString());
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

    private static class UpdatePresentAsyncTask extends AsyncTask<Present, Void, Void> {

        private UpdatePresentAsyncTask() {
            retrofit =
                    new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
            retroInterface = retrofit.create(RetroInterface.class);
        }

        @Override
        protected Void doInBackground(Present... present) {
            JSONObject jsonFriend = new JSONObject();
            try {
                jsonFriend.accumulate("ownerId", present[0].getOwnerId());
                jsonFriend.accumulate("id", present[0].getId());
                jsonFriend.accumulate("title", present[0].getTitle());
                jsonFriend.accumulate("price", present[0].getPrice());
                jsonFriend.accumulate("numFilled", present[0].getNumFilled());
                jsonFriend.accumulate("numNeeded", present[0].getNumNeeded());

                String sponsorString = "";
                for(Friend f: present[0].getSponsors()) {
                    sponsorString += f.getId() + ",";
                }

                String messageString = "";
                for(String s: present[0].getMessages()) {
                    messageString += s + ",";
                }

                jsonFriend.accumulate("sponsors", sponsorString);
                jsonFriend.accumulate("messages", messageString);
                Call<String> sendNewFriend = retroInterface.executeUpdatePresent(jsonFriend.toString());
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

    private static class DeletePresentAsyncTask extends AsyncTask<Present, Void, Void> {

        private DeletePresentAsyncTask() {
        }

        @Override
        protected Void doInBackground(Present... present) {
            return null;
        }

    }

    private static class SelectedPresentAsyncTask extends AsyncTask<Present, Void, Void> {

        private SelectedPresentAsyncTask() {
        }

        @Override
        protected Void doInBackground(Present... present) {
            return null;
        }

    }
}
