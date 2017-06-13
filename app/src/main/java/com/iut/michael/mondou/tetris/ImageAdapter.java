package com.iut.michael.mondou.tetris;

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

    private int[][] grid;

    public Integer getNbLines() {
        return nbLines;
    }

    public void setNbLines(Integer nbLines) {
        this.nbLines = nbLines;
    }

    public Integer getNbColumns() {
        return nbColumns;
    }

    public void setNbColumns(Integer nbColumns) {
        this.nbColumns = nbColumns;
    }

    private Integer nbLines = 20;
    private Integer nbColumns = 10;

    ImageAdapter(Context c) {
        mContext = c;
        initGrid();
    }

    private void initGrid() {
        this.mArrayList = new ArrayList<>();
        this.grid = new int[this.nbLines][this.nbColumns];

        for (int i = 0; i < this.nbLines; i++) {
            for (int j = 0; j < this.nbColumns; j++) {
                this.mArrayList.add(R.drawable.square);
                this.grid[i][j] = R.drawable.square;
            }
        }
    }

    void resetGrid() {
        for (int i = 0; i < this.nbLines; i++) {
            for (int j = 0; j < this.nbColumns; j++) {
                this.grid[i][j] = R.drawable.square;
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

    public void updateResults(ArrayList<Integer> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getmArrayList() {
        return mArrayList;
    }

    public void setmArrayList(ArrayList<Integer> mArrayList) {
        this.mArrayList = mArrayList;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

}