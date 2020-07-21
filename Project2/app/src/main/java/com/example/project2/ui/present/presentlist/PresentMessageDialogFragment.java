package com.example.project2.ui.present.presentlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project2.R;

public class PresentMessageDialogFragment extends DialogFragment {
    private PresentViewModel presentViewModel;
    private PresentAdapter friendAdapter;

    public interface PresentMessageDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    PresentMessageDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (PresentMessageDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement PresentAddDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_present_message, null);

        builder.setView(root).setPositiveButton(R.string.set_message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PresentMessageDialogFragment.this.getDialog().cancel();
            }
        });

        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);

        /*
        RecyclerView searchFriendList = root.findViewById(R.id.search_friend_list);
        searchFriendList.setLayoutManager(new LinearLayoutManager(getActivity()));

        final PresentAdapter presentAdapter = new PresentAdapter(this);
        searchFriendList.setAdapter(presentAdapter);

        final androidx.lifecycle.Observer<List<Present>> presentObserver =
                new androidx.lifecycle.Observer<List<Present>>() {
                    @Override
                    public void onChanged(List<Present> presentList) {
                        presentAdapter.setPresents(presentList);
                    }
                };

        presentViewModel.getPresentsList().observe(this.getActivity(),
                presentObserver);

         */

        EditText writeMessage = root.findViewById(R.id.write_message);
        writeMessage.setHint("친구를 위한 메시지");

        return builder.create();
    }
}

