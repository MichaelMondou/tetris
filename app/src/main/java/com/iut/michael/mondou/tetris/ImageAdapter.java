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

    private Integer nbLines = 20;
    private Integer nbColumns = 10;

    ImageAdapter(Context c) {
        mContext = c;
        initGrid();
    }

    private void initGrid() {
        this.mArrayList = new ArrayList<>();

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

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            int iDisplayWidth = parent.getWidth();
            int iImageSize = iDisplayWidth / this.nbColumns;
            while (iImageSize >= parent.getHeight() / this.nbLines) {
                iImageSize = iImageSize - 1;
            }
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(iImageSize, iImageSize));
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(this.mArrayList.get(position));
        return imageView;
    }


}