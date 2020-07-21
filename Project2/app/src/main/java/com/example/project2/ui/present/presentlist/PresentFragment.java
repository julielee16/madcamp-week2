package com.example.project2.ui.present.presentlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class PresentFragment extends Fragment {
    private Context context;
    private PresentViewModel presentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_present, container, false);
        context = getActivity();

        RecyclerView presentRecyclerView = root.findViewById(R.id.present_list);
        presentRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        final PresentAdapter presentAdapter = new PresentAdapter(this);
        presentRecyclerView.setAdapter(presentAdapter);

        final androidx.lifecycle.Observer<List<Present>> presentObserver =
                new androidx.lifecycle.Observer<List<Present>>() {
            @Override
            public void onChanged(List<Present> presentList) {
                presentAdapter.setPresents(presentList);
            }
        };

        presentViewModel.getPresentsList().observe(getViewLifecycleOwner(), presentObserver);
        return root;
    }
}