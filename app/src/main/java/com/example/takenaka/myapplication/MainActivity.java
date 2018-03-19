package com.example.takenaka.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import tech.takenoko.swipegridview.SwipeGridView;
import tech.takenoko.swipegridview.listener.OnSingleTouchListener;
import tech.takenoko.swipegridview.listener.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity {

    /** スワイプ選択機能付きGridView */
    private SwipeGridView swipeGridView;

    /** GridViewのアダプター */
    private RowAdapter rowAdapter;

    /** サンプル */
    public final static ArrayList<String> msampleArray = new ArrayList(Arrays.asList(
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
            "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79"
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // viewの初期化
        swipeGridView = findViewById(R.id.grid_view);

        // GridViewにAdapterをセットする。
        rowAdapter = new RowAdapter(this);
        swipeGridView.setAdapter(rowAdapter);
        swipeGridView.setOnSingleTouching(new OnSingleTouchListener() {
            @Override
            public void onTouch(View view, MotionEvent motionEvent, Object tag) {
                rowAdapter.invertSelected(tag);
            }
        });
        swipeGridView.setOnSwipeTouching(new OnSwipeTouchListener() {
            @Override
            public void onTouch(View view, MotionEvent motionEvent, Object tag) {
                rowAdapter.invertSelected(tag);
            }
        });

    }
}
