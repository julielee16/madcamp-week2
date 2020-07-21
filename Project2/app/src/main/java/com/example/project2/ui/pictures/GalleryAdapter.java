package com.example.project2.ui.pictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {


    private Context context;
    private int[] images;
    private ArrayList<Bitmap> gallery;

    public GalleryAdapter(Context c, ArrayList<Bitmap> gallery) {
        context = c;
        this.gallery = gallery;
    }

    // returns the number of images
    public int getCount() {
        return gallery.size();
    }

    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }

    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }

    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView==null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(gallery.get(position));
        } else { imageView = (ImageView) convertView; }
        return imageView;
    }

}
