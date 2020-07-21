package com.example.project2.ui.present.friend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

import java.util.List;

public class FriendAddDialogFragment extends DialogFragment {
    private FriendViewModel friendViewModel;
    private FriendAdapter friendAdapter;

    public interface FriendAddDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    FriendAddDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (FriendAddDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement FriendAddDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_add_friend, null);

        friendViewModel =
                ViewModelProviders.of(this).get(FriendViewModel.class);

        RecyclerView searchFriendList = root.findViewById(R.id.search_friend_list);
        searchFriendList.setLayoutManager(new LinearLayoutManager(getActivity()));

        final FriendAdapter friendAdapter = new FriendAdapter();
        friendAdapter.setLayout(4);
        searchFriendList.setAdapter(friendAdapter);

        final androidx.lifecycle.Observer<List<Friend>> friendObserver =
                new androidx.lifecycle.Observer<List<Friend>>() {
                    @Override
                    public void onChanged(List<Friend> friendList) {
                        friendAdapter.setFriends(friendList);
                    }
                };

        friendViewModel.getAllFriendsList().observe(this.getActivity(),
                friendObserver);

        SearchView filterFriends = root.findViewById(R.id.search_friend_name);
        filterFriends.setQueryHint("친구 아이디");
        filterFriends.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                friendAdapter.getFilter().filter(s);
                return true;
            }
        });

        builder.setView(root).setPositiveButton(R.string.add_friend,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FriendAddDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}

