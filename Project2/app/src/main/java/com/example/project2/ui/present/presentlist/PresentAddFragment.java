package com.example.project2.ui.present.presentlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.project2.MainActivity;
import com.example.project2.R;
import com.example.project2.ui.present.friend.Friend;
import com.example.project2.ui.present.friend.FriendListFragment;

import java.util.ArrayList;
import java.util.List;

public class PresentAddFragment extends Fragment {
    private Context context;
    private PresentViewModel presentViewModel;

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

        final View root = inflater.inflate(R.layout.fragment_present_add, container, false);
        context = getActivity();

        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);

        final NumberPicker numNeeded = root.findViewById(R.id.present_add_num_needed);
        numNeeded.setMinValue(1);
        numNeeded.setMaxValue(20);

        Button addButton = root.findViewById(R.id.button_to_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = root.findViewById(R.id.present_add_name);
                EditText price = root.findViewById(R.id.present_add_price);
                EditText link = root.findViewById(R.id.present_add_link);

                String presentTitle = title.getText().toString();
                int presentPrice = Integer.parseInt(price.getText().toString());
                String presentLink = link.getText().toString();
                int presentNumNeeded = numNeeded.getValue();

                List<Friend> emptySponsors = new ArrayList<>();
                List<String> emptyMessages = new ArrayList<>();
                Present newPresent = new Present(MainActivity.totalPresents + 1, MainActivity.myID, presentTitle,
                        presentPrice, 0,
                        presentNumNeeded, emptySponsors, emptyMessages);
                MainActivity.totalPresents = MainActivity.totalPresents + 1;
                presentViewModel.insert(newPresent);
                getParentFragmentManager().popBackStack();
            }
        });


        return root;
    }
}
