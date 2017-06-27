package com.iut.michael.mondou.tetris;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {

    public GameActivity activity;
    public GestureDetector gestureDetector;

    public OnSwipeListener(Context context){
        this.activity = (GameActivity) context;
        Android_Gesture_Detector android_gesture_detector  =  new Android_Gesture_Detector(context);
        gestureDetector = new GestureDetector(context, android_gesture_detector);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
}