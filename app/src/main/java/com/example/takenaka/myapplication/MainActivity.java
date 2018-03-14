package com.example.takenaka.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private CustomGridView gridView;
    static CustomView customView;

    static boolean selectMode = false;
    static int startIndex = -1;
    static int prevIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGridView();
    }

    // グリッドセットアップ
    private void setupGridView() {
        customView = findViewById(R.id.custom_view);
        gridView = findViewById(R.id.grid_view);
        final RowAdapter rowView = new RowAdapter(this, gridView);
        gridView.setAdapter(rowView);

        gridView.setOnTouchListener(new SwipeListener(new SwipeListener.Listener() {
//            @Override public void onSwipeToLeft() {}
//            @Override public void onSwipeToRight() {}
//            @Override public void onSwipeToUp() {}
//            @Override public void onSwipeToDown() {}

            @Override public boolean onNextListener(MotionEvent motionEvent, SwipeListener.SwipeState mode) {
                int index = gridView.getTouchChildIndex((int)motionEvent.getX(), (int)motionEvent.getY());
                Log.i("^^^", "index = " + index + ", prev = " + prevIndex);
                boolean isTouching = (motionEvent.getAction() == MotionEvent.ACTION_UP) && (startIndex == index);
                boolean swipeTouching = (mode == SwipeListener.SwipeState.SELECTING_MODE) && (prevIndex != index);
                if(isTouching || swipeTouching) {
                    if(rowView.viewHolderList.get(index) != null ) {
                        rowView.viewHolderList.get(index).onTouch = !rowView.viewHolderList.get(index).onTouch;
                        rowView.notifyDataSetChanged();
                        gridView.invalidateViews();
                    }
                    prevIndex = index;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    startIndex = index;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    prevIndex = -1;
                }
                return SwipeListener.state != SwipeListener.SwipeState.SLIDE_MODE;
            }
        }));
    }
}
