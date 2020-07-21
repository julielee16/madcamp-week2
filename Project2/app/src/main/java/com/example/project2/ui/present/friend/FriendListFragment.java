package com.example.project2.ui.present.friend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FriendListFragment extends Fragment implements FriendAddDialogFragment.FriendAddDialogListener {
    private Context context;
    private FriendViewModel friendViewModel;
    private FriendAdapter friendAdapter;

    public FriendListFragment() {
        // Empty Constructor
    }

    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_friend_list, container, false);
        friendViewModel =
                ViewModelProviders.of(this).get(FriendViewModel.class);
        context = getActivity();

        final RecyclerView friend_list_recyclerview = root.findViewById(R.id.friend_list_recyclerview);
        friend_list_recyclerview.setLayoutManager(new LinearLayoutManager(context));

        friendAdapter = new FriendAdapter();
        friend_list_recyclerview.setAdapter(friendAdapter);

        final androidx.lifecycle.Observer<List<Friend>> friendObserver =
                new androidx.lifecycle.Observer<List<Friend>>() {
                    @Override
                    public void onChanged(List<Friend> friendList) {
                        friendAdapter.setFriends(friendList);
                    }
                };

        friendViewModel.getMyFriendsList().observe(getViewLifecycleOwner(),
                friendObserver);

        FloatingActionButton addFriend = root.findViewById(R.id.add_new_friend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFriendAddDialog();
            }
        });

        SearchView findFriend = root.findViewById(R.id.large_friend_list_search);
        findFriend.setQueryHint("친구 아이디");
        findFriend.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                friendViewModel.delete(friendAdapter.getFriendAt(viewHolder.getAdapterPosition
                 ()));
                Toast.makeText(getContext(), "친구 지워짐", Toast.LENGTH_SHORT).show();
                friendAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(friend_list_recyclerview);

        return root;
    }

    public void showFriendAddDialog() {
        DialogFragment dialog = new FriendAddDialogFragment();
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "FriendAddDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //friendViewModel.update();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
