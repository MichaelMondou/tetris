package com.iut.michael.mondou.tetris;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {

    private GestureDetector m_gestureDetector;

    OnSwipeListener(Context context){
        Android_Gesture_Detector android_gesture_detector  =  new Android_Gesture_Detector(context);
        m_gestureDetector = new GestureDetector(context, android_gesture_detector);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return m_gestureDetector.onTouchEvent(motionEvent);
    }
}