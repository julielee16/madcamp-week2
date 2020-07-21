package com.example.project2.ui.present.presentlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

import org.w3c.dom.Text;

import java.util.List;

public class PresentDetailFragment extends Fragment {
    private Context context;
    private PresentViewModel presentViewModel;
    private static Present present;

    public PresentDetailFragment() {
    }

    public static PresentDetailFragment newInstance(Present currPresent) {
        PresentDetailFragment fragment = new PresentDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        present = currPresent;
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_present_detail, container, false);
        context = getActivity();

        TextView detailName = root.findViewById(R.id.present_detail_name);
        TextView detailPrice = root.findViewById(R.id.present_detail_price);
        TextView detailNumFilled = root.findViewById(R.id.present_detail_numFilled);
        TextView detailMessages = root.findViewById(R.id.present_detail_messages);
        String messages = "";

        detailName.setText(present.getTitle());
        detailPrice.setText("" + present.getPrice());
        detailNumFilled.setText(present.getNumFilled() + " / " + present.getNumNeeded());
        for(String s: present.getMessages()) {
            messages += s + "/n";
        }

        detailMessages.setText(messages);
        return root;
    }

}
