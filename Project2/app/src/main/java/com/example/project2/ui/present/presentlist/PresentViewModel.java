package com.example.project2.ui.present.presentlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project2.ui.present.friend.Friend;

import java.util.List;

public class PresentViewModel extends ViewModel {
    private PresentRepository presentRepository;
    private MutableLiveData<List<Present>> allPresents;
    private MutableLiveData<List<Present>> myPresents;
    private MutableLiveData<List<Present>> selectedPresents;

    public PresentViewModel() {
        presentRepository = new PresentRepository();
        allPresents = presentRepository.getAllPresents();
        myPresents = presentRepository.getMyPresents();
        selectedPresents = presentRepository.getSelectedPresents();
    }

    public void insert(Present present) {
        presentRepository.insert(present);
    }

    public void update(Present present) {
        presentRepository.update(present);
    }

    public void delete(Present present) {
        presentRepository.delete(present);
    }

    public void getSelected(Present present) {
        presentRepository.getSelected(present);
    }

    public MutableLiveData<List<Present>> getAllPresentsList() {
        return allPresents;
    }

    public MutableLiveData<List<Present>> getMyPresentsList() {
        return myPresents;
    }

    public MutableLiveData<List<Present>> getSelectedPresentsList() {
        return selectedPresents;
    }
}