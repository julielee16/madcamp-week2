package com.example.project2.ui.present.presentlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project2.R;

public class PresentAddFragment extends Fragment {
    private Context context;

    public PresentAddFragment() {
        // Empty Constructor
    }

    public static PresentAddFragment newInstance() {
        PresentAddFragment fragment = new PresentAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_present_add, container, false);
        context = getActivity();

        NumberPicker numNeeded = root.findViewById(R.id.present_add_num_needed);
        numNeeded.setMinValue(1);
        numNeeded.setMaxValue(20);

        return root;
    }
}
