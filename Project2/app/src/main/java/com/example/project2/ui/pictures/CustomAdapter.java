package com.example.project2.ui.pictures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.project2.R;

import java.util.ArrayList;

//public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> {
//    Context context;
//    ArrayList<String> arrayList;
//    public CustomAdapter(Context context, ArrayList<String> arrayList){
//        this.context= context;
//        this.arrayList=arrayList;
//    }
//
//
//
//    @NonNull
//    @Override
//    public CustomAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
//        return new viewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CustomAdapter.viewHolder holder, int position) {
//        Glide.with(context)
//                .load(arrayList.get(position))
//                .into(holder.image);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class viewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            image=(ImageView) itemView.findViewById(R.id.image);
//        }
//    }
//}
public class CustomAdapter extends BaseAdapter{
    Context context;
    ArrayList<String> arrayList;
    public CustomAdapter(Context context, ArrayList<String> arrayList){
        this.context= context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup parent) {
        ImageView picturesView;
        if(convertview==null){
            picturesView= new ImageView(context);
            picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            picturesView.setLayoutParams(new GridView.LayoutParams(270,270));
            //convertview=View.inflate(R.layout.user_item, parent, false);
        }else{
            picturesView=(ImageView) convertview;
        }
        System.out.println("check"+arrayList);
        Glide.with(context).load(arrayList.get(i)).into(picturesView);

        return convertview;
    }
}