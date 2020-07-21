package com.example.project2.ui.present.friend;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class FriendViewModel extends ViewModel {
    private FriendRepository friendRepository;
    private MutableLiveData<List<Friend>> allFriends;
    private MutableLiveData<List<Friend>> myFriends;
    private MutableLiveData<List<Friend>> selectedFriends;

    public FriendViewModel() {
        friendRepository = new FriendRepository();
        allFriends = friendRepository.getAllFriends();
        myFriends = friendRepository.getMyFriends();
        selectedFriends = friendRepository.getSelectedFriends();
    }

    public void insert(Friend friend) {
        friendRepository.insert(friend);
    }

    public void update(Friend friend) {
        friendRepository.update(friend);
    }

    public void delete(Friend friend) {
        friendRepository.delete(friend);
    }

    public void getSelected(Friend friend) {
        friendRepository.getSelected(friend);
    }

    public MutableLiveData<List<Friend>> getAllFriendsList() {
        return allFriends;
    }

    public MutableLiveData<List<Friend>> getMyFriendsList() {
        return myFriends;
    }

    public MutableLiveData<List<Friend>> getSelectedFriendsList() {
        return selectedFriends;
    }
}