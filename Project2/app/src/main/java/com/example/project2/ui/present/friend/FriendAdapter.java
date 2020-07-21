package com.example.project2.ui.present.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Friend> friends = new ArrayList<>();
    private List<Friend> filteredFriends = new ArrayList<>();
    private Context context;
    private int layout;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        switch(layout) {
            default:
                View itemView = LayoutInflater.from(context).inflate(R.layout.friend_item,
                        parent, false);
                return new FriendViewHolder(itemView);
            case 2:
                View horizontalItemView =
                        LayoutInflater.from(context).inflate(R.layout.friend_horizontal_item,
                        parent, false);
                return new HorizontalFriendViewHolder(horizontalItemView);
            case 4:
                View addItemView = LayoutInflater.from(context).inflate(R.layout.friend_add_item,
                        parent, false);
                return new AddFriendViewHolder(addItemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Friend currentFriend = friends.get(position);
        if(filteredFriends.size() > position) {
            currentFriend = filteredFriends.get(position);
        }

        switch(layout) {
            default:
                FriendViewHolder friendViewHolder = (FriendViewHolder)holder;
                friendViewHolder.friendId = currentFriend.getId();
                friendViewHolder.friendName.setText(currentFriend.getName());
                break;
            case 2:
                HorizontalFriendViewHolder horizontalFriendViewHolder =
                        (HorizontalFriendViewHolder)holder;
                horizontalFriendViewHolder.horizontalFriendName.setText(currentFriend.getName());
                break;
            case 4:
                AddFriendViewHolder addFriendViewHolder = (AddFriendViewHolder)holder;
                addFriendViewHolder.addFriendName.setText(currentFriend.getName());
                addFriendViewHolder.addRadioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = ((RadioButton) view).isChecked();
                        }
                    }
                );
                break;
        }

        final Friend finalCurrentFriend = currentFriend;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendDetailFragment(finalCurrentFriend);
            }
        } );
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
        this.filteredFriends = friends;
        notifyDataSetChanged();
    }


    public Friend getFriendAt(int position) {
        return filteredFriends.get(position);
    }

    public Filter getFilter() {
        return new Filter() {
            private List<Friend> filtered = new ArrayList<>();

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                filtered.clear();
                if(charString.isEmpty()) {
                    filtered = friends;
                }
                else {
                    for(Friend f : friends) {
                        if(f.getName().toLowerCase().contains(charString)) {
                            filtered.add(f);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredFriends = (List<Friend>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void openFriendDetailFragment(Friend friend) {
        FriendDetailFragment fragment =
                FriendDetailFragment.newInstance(friend);
        FragmentManager fragmentManager =
                ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_open_exit);
        transaction.replace(R.id.fragment_friend_detail_container, fragment,
                "FRIEND_DETAIL_FRAGMENT").commit();
        transaction.addToBackStack(null);
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private int friendId;
        private TextView friendName;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friend_name);
        }
    }

    class HorizontalFriendViewHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView horizontalFriendName;

        public HorizontalFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalFriendName = itemView.findViewById(R.id.horizontal_name);
        }
    }

    class AddFriendViewHolder extends RecyclerView.ViewHolder {
        private ImageView addProfilePic;
        private TextView addFriendName;
        private RadioButton addRadioButton;

        public AddFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            addFriendName = itemView.findViewById(R.id.friend_add_name);
            addRadioButton = itemView.findViewById(R.id.friend_add_radio_button);
        }
    }
}

