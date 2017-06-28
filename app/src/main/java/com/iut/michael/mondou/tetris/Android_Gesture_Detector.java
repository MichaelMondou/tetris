package com.iut.michael.mondou.tetris;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static android.content.Context.WINDOW_SERVICE;

class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    GameActivity activity;
    int widthPixels;
    int heightPixels;

    Android_Gesture_Detector(Context context) {
        this.activity = (GameActivity) context;
        DisplayMetrics metrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.widthPixels = metrics.widthPixels;
        this.heightPixels = metrics.heightPixels;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (motionEvent.getX() < 200) {
            this.activity.moveLastPiece("left");
            this.activity.refresh();
        }
        else if (motionEvent.getX() > this.widthPixels - 200) {
            this.activity.moveLastPiece("right");
            this.activity.refresh();
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1.getX() < e2.getX() && Math.abs(e1.getX() - e2.getX()) > 300) {
            this.activity.moveLastPiece("rotate");
            this.activity.refresh();
        }

        if (e1.getY() < e2.getY() && Math.abs(e2.getY() - e1.getY()) > 300) {
            this.activity.moveLastPiece("down");
            this.activity.refresh();
        }

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
}
