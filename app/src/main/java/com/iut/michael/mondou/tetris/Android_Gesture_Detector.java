package com.iut.michael.mondou.tetris;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;

class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    static int LEFT = 1;
    static int DOWN = 2;
    static int RIGHT = 3;
    static int ROTATE = 4;

    private GameActivity m_activity;
    private int m_widthPixels;

    Android_Gesture_Detector(Context context) {
        m_activity = (GameActivity) context;
        DisplayMetrics metrics = new DisplayMetrics();
        m_activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        m_widthPixels = metrics.widthPixels;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        if (motionEvent.getX() < 200) {
            m_activity.moveLastPiece(LEFT);
            m_activity.refresh();
        }
        else if (motionEvent.getX() > m_widthPixels - 200) {
            m_activity.moveLastPiece(RIGHT);
            m_activity.refresh();
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
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
            m_activity.moveLastPiece(ROTATE);
            m_activity.refresh();
        }

        if (e1.getY() < e2.getY() && Math.abs(e2.getY() - e1.getY()) > 300) {
            m_activity.moveLastPiece(DOWN);
            m_activity.refresh();
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