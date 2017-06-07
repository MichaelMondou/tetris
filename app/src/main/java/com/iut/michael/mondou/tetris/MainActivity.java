package com.iut.michael.mondou.tetris;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int iDisplayWidth = gridview.getWidth();
                int iDisplayHeight = gridview.getHeight();
                int iImageSize = iDisplayWidth / 10 - 10;
                //while (iImageSize >= (gridview.getHeight()) / 20) {
                //    iImageSize = iImageSize - 1;
                //}
                Log.d("width", String.valueOf(iDisplayWidth));
                Log.d("height", String.valueOf(gridview.getHeight()));
                Log.d("taille", String.valueOf(iImageSize));
                gridview.setColumnWidth( iImageSize );
                //gridview.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, iImageSize));
                gridview.setStretchMode( GridView.NO_STRETCH ) ;
            }
        });
    }
}