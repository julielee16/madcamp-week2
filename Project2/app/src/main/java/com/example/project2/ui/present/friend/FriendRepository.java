package com.example.project2.ui.present.friend;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class FriendRepository {
    private MutableLiveData<List<Friend>> allFriends;
    private MutableLiveData<List<Friend>> myFriends;
    private MutableLiveData<List<Friend>> selectedFriends;

    public FriendRepository(){

        // Temp Data
        // For testing (IMPLEMENT DB LATER)
        List<Friend> friendList = new ArrayList<>();
        List<Friend> secondFriendList = new ArrayList<>();
        allFriends = new MutableLiveData<>();
        myFriends = new MutableLiveData<>();
        selectedFriends = new MutableLiveData<>();
        Friend testFriend = new Friend(1, "홍길동", friendList);
        Friend testFriend2 = new Friend(2, "이주훈", friendList);
        Friend testFriend3 = new Friend(3, "가나다", friendList);
        Friend testFriend4 = new Friend(4, "마바사", friendList);
        Friend testFriend5 = new Friend(5, "아자차", friendList);
        Friend testFriend6 = new Friend(6, "타파하", friendList);
        Friend testFriend7 = new Friend(7, "John Doe", friendList);

        friendList.add(testFriend);
        friendList.add(testFriend3);
        testFriend2.setFriends(friendList);

        secondFriendList.add(testFriend4);
        secondFriendList.add(testFriend5);
        testFriend.setFriends(secondFriendList);

        List<Friend> testFriendList = new ArrayList<>();
        testFriendList.add(testFriend);
        testFriendList.add(testFriend2);
        testFriendList.add(testFriend3);
        testFriendList.add(testFriend4);
        testFriendList.add(testFriend5);
        testFriendList.add(testFriend6);
        testFriendList.add(testFriend7);
        allFriends.setValue(testFriendList);
        myFriends.setValue(secondFriendList);
        selectedFriends.setValue(friendList);

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
        }

        @Override
        protected Void doInBackground(Friend... friend) {
            return null;
        }

    }

    private static class DeleteFriendAsyncTask extends AsyncTask<Friend, Void, Void> {

        private DeleteFriendAsyncTask() {
        }

        @Override
        protected Void doInBackground(Friend... friend) {
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
