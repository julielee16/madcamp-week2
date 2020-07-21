package com.example.project2.ui.present;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.ui.present.friend.Friend;
import com.example.project2.ui.present.friend.FriendAdapter;
import com.example.project2.ui.present.friend.FriendDetailFragment;
import com.example.project2.ui.present.friend.FriendListFragment;
import com.example.project2.ui.present.friend.FriendViewModel;
import com.example.project2.ui.present.presentlist.Present;
import com.example.project2.ui.present.presentlist.PresentAdapter;
import com.example.project2.ui.present.presentlist.PresentViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartPageFragment extends Fragment {

    public static final String FRIEND_LIST = "com.example.project2.ui.FRIEND_LIST";
    private Context context;
    private FriendViewModel friendViewModel;
    private PresentViewModel presentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        friendViewModel =
                ViewModelProviders.of(this).get(FriendViewModel.class);
        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_start_page, container, false);
        context = getActivity();

        // Ranking of the most popular presents
        final RecyclerView rankRecyclerView = root.findViewById(R.id.rank_recyclerview);
        rankRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        final PresentAdapter presentAdapter = new PresentAdapter(this);
        presentAdapter.setLayout(0);
        rankRecyclerView.setAdapter(presentAdapter);

        runLayoutAnimation(rankRecyclerView);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runLayoutAnimation(rankRecyclerView);
                handler.postDelayed(this, 5000);
            }
        }, 5000);


        final androidx.lifecycle.Observer<List<Present>> presentObserver =
                new androidx.lifecycle.Observer<List<Present>>() {
                    @Override
                    public void onChanged(List<Present> presentList) {
                        presentAdapter.setPresents(presentList);
                    }
                };

        presentViewModel.getPresentsList().observe(getViewLifecycleOwner(), presentObserver);


        // List of friends you have
        RecyclerView friendListRecyclerView = root.findViewById(R.id.friends_recyclerview);
        friendListRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));

        final FriendAdapter friendAdapter = new FriendAdapter();
        friendListRecyclerView.setAdapter(friendAdapter);

        final androidx.lifecycle.Observer<List<Friend>> friendObserver =
                new androidx.lifecycle.Observer<List<Friend>>() {
                    @Override
                    public void onChanged(List<Friend> friendList) {
                        friendAdapter.setFriends(friendList);
                    }
                };

        friendViewModel.getMyFriendsList().observe(getViewLifecycleOwner(), friendObserver);

        TextView rankTitle = root.findViewById(R.id.rank_title);
        rankTitle.setText("오늘의 인기 선물");

        TextView toFriendList = root.findViewById(R.id.to_friend_list);
        toFriendList.setText("내 친구들                                    >");
        toFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendList(view);
            }
        });
        return root;
    }

    public void openFriendList(View view) {
        FriendListFragment fragment =
                FriendListFragment.newInstance();
        FragmentManager fragmentManager =
                ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_open_exit);
        transaction.replace(R.id.fragment_friend_detail_container, fragment,
                "FRIEND_LIST_FRAGMENT").commit();
        transaction.addToBackStack(null);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
