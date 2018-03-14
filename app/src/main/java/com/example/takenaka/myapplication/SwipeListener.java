package com.example.takenaka.myapplication;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by takenaka on 2018/03/14.
 */

public class SwipeListener implements View.OnTouchListener {

    enum SwipeState {
        NONE,
        SELECTING_MODE, /** 選択モード */
        SLIDE_MODE,     /** スライドモード */
    }

    private Listener listener;
    static SwipeState state = SwipeState.NONE;

    /** フリック判定時の遊び */
    private float DEFAULT_PLAY = 80f;
    private float lastX = 0;
    private float lastY = 0;

    SwipeListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
//        void onSwipeToLeft();
//        void onSwipeToRight();
//        void onSwipeToUp();
//        void onSwipeToDown();
        boolean onNextListener(MotionEvent motionEvent, SwipeState mode);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        MainActivity.selectMode = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: touchStart(event); break;
            case MotionEvent.ACTION_UP:   touchEnd(event); break;
            case MotionEvent.ACTION_MOVE: touching(event); break;
        }
        return listener.onNextListener(event, state);
    }

    private void touchStart(MotionEvent event) {
        lastX = event.getX();
        lastY = event.getY();
        MainActivity.customView.update(new PointF( lastX, lastY));
        touching(event);
    }

    private void touchEnd(MotionEvent event) {
        MainActivity.customView.update(new PointF( 0, 0));
        state = SwipeState.NONE;
    }

    private void touching(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();

        if (currentX + DEFAULT_PLAY < lastX) {
            MainActivity.customView.update(new PointF( lastX, lastY));
            if(state == SwipeState.NONE) state = SwipeState.SELECTING_MODE;
//            listener.onSwipeToLeft();

        } else if (lastX < currentX - DEFAULT_PLAY) {
            MainActivity.customView.update(new PointF( lastX, lastY));
            if(state == SwipeState.NONE) state = SwipeState.SELECTING_MODE;
//            listener.onSwipeToRight();

        } else if (currentY + DEFAULT_PLAY < lastY) {
            MainActivity.customView.update(new PointF( lastX, lastY));
            if(state == SwipeState.NONE) state = SwipeState.SLIDE_MODE;
//            listener.onSwipeToUp();

        } else if (lastY < currentY - DEFAULT_PLAY) {
            MainActivity.customView.update(new PointF( lastX, lastY));
            if(state == SwipeState.NONE) state = SwipeState.SLIDE_MODE;
//            listener.onSwipeToDown();
        }
    }
}
