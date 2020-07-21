package com.example.project2.ui.pictures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.R;

import java.util.ArrayList;

public class UserAdpater extends RecyclerView.Adapter<UserAdpater.UserViewHolder> { ArrayList<String> mDataset;
    Context context;
    ArrayList<String> arrayList;

    public UserAdpater(ArrayList<String> arrayList, Context context){
        this.context=context;
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(v);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position)).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public UserViewHolder(View v) {
            super(v);
            image=(ImageView) v.findViewById(R.id.image);
        }
    }
}
