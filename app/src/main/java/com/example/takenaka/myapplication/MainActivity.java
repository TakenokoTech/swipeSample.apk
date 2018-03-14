package com.example.takenaka.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private CustomGridView gridView;
    static CustomView customView;

    static boolean selectMode = false;
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
                Log.i("TOUCHING", "position = " + index);
                if(rowView.viewHolderArray.get(index) != null && mode == SwipeListener.SwipeState.SELECTING_MODE && prevIndex != index) {
                    rowView.viewHolderArray.get(index).isTouch = !rowView.viewHolderArray.get(index).isTouch;
                    prevIndex = index;
                }
                if(mode == SwipeListener.SwipeState.NONE) {
                    prevIndex = -1;
                }
                rowView.notifyDataSetChanged();
                return SwipeListener.state != SwipeListener.SwipeState.SLIDE_MODE;
            }
        }));
    }
}
