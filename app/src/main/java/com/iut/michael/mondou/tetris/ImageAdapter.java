package com.iut.michael.mondou.tetris;

import android.util.Log;
import android.widget.ImageView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> mArrayList;

    private Integer nbLines = 9;
    private Integer nbColumns = 9;

    ImageAdapter(Context c) {
        mContext = c;
        initGrid();
    }

    private void initGrid() {
        this.mArrayList = new ArrayList<Integer>();

        for (int i = 0; i < this.nbLines; i++) {
            for (int j = 0; j < this.nbColumns; j++) {
                this.mArrayList.add(R.drawable.square);
            }
        }
    }

    public int getCount() {
        return mArrayList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(this.mArrayList.get(position));
        return imageView;
    }
}