package com.example.project2.ui.present.presentlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PresentViewModel extends ViewModel {
    private PresentRepository presentRepository;
    private MutableLiveData<List<Present>> allPresents;

    public PresentViewModel() {
        presentRepository = new PresentRepository();
        allPresents = presentRepository.getAllPresents();
    }

    public void insert(Present present) {
        presentRepository.insert(present);
    }

    public MutableLiveData<List<Present>> getPresentsList() {
        return allPresents;
    }
}