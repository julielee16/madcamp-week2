package com.example.project2.ui.present.friend;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.MainActivity;
import com.example.project2.R;
import com.example.project2.ui.present.presentlist.Present;
import com.example.project2.ui.present.presentlist.PresentAdapter;
import com.example.project2.ui.present.presentlist.PresentAddFragment;
import com.example.project2.ui.present.presentlist.PresentMessageDialogFragment;
import com.example.project2.ui.present.presentlist.PresentViewModel;

import java.util.List;

public class FriendDetailFragment extends Fragment implements PresentMessageDialogFragment.PresentMessageDialogListener {
    private static final String FRIEND = "friend";

    private Context context;
    private FriendViewModel friendViewModel;
    private PresentViewModel presentViewModel;
    private Friend mFriend;

    public FriendDetailFragment() {
        // Empty Constructor
    }

    public static FriendDetailFragment newInstance(Friend friend) {
        FriendDetailFragment fragment = new FriendDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(FRIEND, friend);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFriend = (Friend) getArguments().getSerializable(FRIEND);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel =
                ViewModelProviders.of(this).get(FriendViewModel.class);
        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_detail, container, false);
        context = getActivity();

        // List of friends you have
        RecyclerView friendListRecyclerView =
                root.findViewById(R.id.fragment_detail_friends_recyclerview);
        friendListRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));

        final FriendAdapter friendAdapter = new FriendAdapter();
        friendAdapter.setLayout(2);
        friendListRecyclerView.setAdapter(friendAdapter);

        final androidx.lifecycle.Observer<List<Friend>> friendObserver =
                new androidx.lifecycle.Observer<List<Friend>>() {
                    @Override
                    public void onChanged(List<Friend> friendList) {
                        friendAdapter.setFriends(friendList);
                    }
                };

        if(mFriend.getName().equals(MainActivity.myName)) {
            friendViewModel.getMyFriendsList().observe(getViewLifecycleOwner(), friendObserver);
        }
        else {
            MainActivity.selectedID = mFriend.getId();
            friendViewModel.getSelected(mFriend);
            friendViewModel.getSelectedFriendsList().observe(getViewLifecycleOwner(),
                    friendObserver);
        }

        // Wish list of presents
        RecyclerView presentListRecyclerView =
                root.findViewById(R.id.fragment_detail_presents_recyclerview);
        presentListRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        final PresentAdapter presentAdapter = new PresentAdapter(this);
        presentAdapter.setLayout(2);
        presentListRecyclerView.setAdapter(presentAdapter);

        final androidx.lifecycle.Observer<List<Present>> presentObserver =
                new androidx.lifecycle.Observer<List<Present>>() {
                    @Override
                    public void onChanged(List<Present> presentList) {
                        presentAdapter.setPresents(presentList);
                    }
                };

        if(mFriend.getName().equals(MainActivity.myName)) {
            presentViewModel.getMyPresentsList().observe(getViewLifecycleOwner(), presentObserver);
        }
        else {
            MainActivity.selectedID = mFriend.getId();
            //presentViewModel.getSelected(mFriend);
            presentViewModel.getSelectedPresentsList().observe(getViewLifecycleOwner(),
                    presentObserver);
        }

        TextView friendName = root.findViewById(R.id.friend_detail_name);
        friendName.setText(mFriend.getName());

        String mName = mFriend.getName();

        if (mFriend.getName().equals(MainActivity.myName)) {
            mName = "나";
            root.findViewById(R.id.friend_detail_background).setBackgroundColor(this.getResources().getColor(R.color.my_background));
        }

        TextView theirFriend = root.findViewById(R.id.their_friends);
        String myFriends = mName + "의 친구들";
        Spannable spannable = new SpannableString(myFriends);
        spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, mName.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        theirFriend.setText(spannable, TextView.BufferType.SPANNABLE);

        TextView theirWishList = root.findViewById(R.id.their_wish_list);
        String myWishList = mName + "의 위시리스트";
        Spannable spannableWish = new SpannableString(myWishList);
        spannableWish.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, mName.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        theirWishList.setText(spannableWish, TextView.BufferType.SPANNABLE);

        final Button addPresent = root.findViewById(R.id.add_present_button);
        if (mFriend.getName().equals(MainActivity.myName)) {
            addPresent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPresent.setVisibility(View.GONE);
                    openPresentAddFragment();
                }
            });
        }
        else {
            addPresent.setVisibility(View.GONE);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //presentViewModel.delete(presentAdapter.getPresentAt(viewHolder.getAdapterPosition
                // ()));
                Toast.makeText(getContext(), "선물 지워짐", Toast.LENGTH_SHORT).show();
                presentAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(presentListRecyclerView);

        return root;
    }

    public void openPresentAddFragment() {
        PresentAddFragment fragment =
                PresentAddFragment.newInstance();
        FragmentManager fragmentManager =
                ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_open_exit);
        transaction.replace(R.id.fragment_present_add_container, fragment,
                "PRESENT_ADD_FRAGMENT").commit();
        transaction.addToBackStack(null);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}