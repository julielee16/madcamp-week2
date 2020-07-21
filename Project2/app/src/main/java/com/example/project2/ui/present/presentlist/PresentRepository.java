package com.example.project2.ui.present.presentlist;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class PresentRepository {
    private MutableLiveData<List<Present>> allPresents;


    public PresentRepository(){
        // Temp Data
        // For testing (IMPLEMENT DB LATER)
        List<Present> presentList = new ArrayList<>();
        allPresents = new MutableLiveData<>();
        Present testPresent = new Present("Present 1", 10000, 3, 20, presentList);
        Present testPresent2 = new Present("Present 2", 11000, 8, 15, presentList);
        Present testPresent3 = new Present("Present 3", 120000, 3, 5, presentList);
        Present testPresent4 = new Present("Present 4", 1300000, 7, 19, presentList);
        Present testPresent5 = new Present("Present 5", 14000, 9, 21, presentList);
        Present testPresent6 = new Present("Present 6", 15000, 3, 15, presentList);
        List<Present> testPresentList = new ArrayList<>();
        testPresentList.add(testPresent);
        testPresentList.add(testPresent2);
        testPresentList.add(testPresent3);
        testPresentList.add(testPresent4);
        testPresentList.add(testPresent5);
        testPresentList.add(testPresent6);
        allPresents.setValue(testPresentList);

    }

    public void insert(Present present) {

    }

    public void update() {

    }

    public void delete() {

    }

    public void deleteAllPresents() {

    }

    public MutableLiveData<List<Present>> getAllPresents() {
        return allPresents;
    }

    private static class InsertPresentAsyncTask extends AsyncTask<Present, Void, Void> {

        private InsertPresentAsyncTask() {
        }

        @Override
        protected Void doInBackground(Present... present) {
            return null;
        }

    }
}
