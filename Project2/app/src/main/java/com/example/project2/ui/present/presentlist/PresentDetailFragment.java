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

import java.util.List;

public class PresentDetailFragment extends Fragment {
    private Context context;
    private PresentViewModel presentViewModel;

    public PresentDetailFragment() {
        // Empty Constructor
    }

    public static PresentDetailFragment newInstance() {
        PresentDetailFragment fragment = new PresentDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_present_detail, container, false);
        context = getActivity();

        /*
        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);

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
        */

        return root;
    }

}
