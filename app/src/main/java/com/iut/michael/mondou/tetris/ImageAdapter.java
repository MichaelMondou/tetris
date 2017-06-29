package com.iut.michael.mondou.tetris;

import android.widget.ImageView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

class ImageAdapter extends BaseAdapter {
    private Context m_context;
    private ArrayList<Integer> m_arrayList;

    private int m_nbColumns = 10;
    private int m_nbLines = 20;
    private int[][] m_grid;

    ImageAdapter(Context c) {
        m_context = c;
        initGrid();
    }

    private void initGrid() {
        m_arrayList = new ArrayList<>();
        m_grid = new int[m_nbLines][m_nbColumns];
        resetGrid();
    }

    void resetGrid() {
        m_arrayList.clear();
        for (int i = 0; i < m_nbLines; i++) {
            for (int j = 0; j < m_nbColumns; j++) {
                m_arrayList.add(R.drawable.square);
                m_grid[i][j] = R.drawable.square;
            }
        }
    }

    public int getCount() {
        return m_arrayList.size();
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
            int iImageSize = iDisplayWidth / m_nbColumns;
            while (iImageSize >= parent.getHeight() / m_nbLines) {
                iImageSize = iImageSize - 1;
            }
            imageView = new ImageView(m_context);
            imageView.setLayoutParams(new GridView.LayoutParams(iImageSize, iImageSize));
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(m_arrayList.get(position));
        return imageView;
    }

    void updateResults(ArrayList<Integer> arrayList) {
        m_arrayList = arrayList;
        notifyDataSetChanged();
    }

    ArrayList<Integer> getArrayList() {
        return m_arrayList;
    }
    int[][] getGrid() {
        return m_grid;
    }
    Integer getNbLines() {
        return m_nbLines;
    }
    Integer getNbColumns() {
        return m_nbColumns;
    }
}