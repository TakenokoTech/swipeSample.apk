package com.example.takenaka.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by takenaka on 2018/03/16.
 */

public class SwipeGridView extends GridView {

    /** タッチ開始時にタップしていたGridView内のView要素のTag */
    private static int startIndex = -1;

    /** 前回タッチしたGridView内のView要素のTag */
    private static int prevIndex = -1;

    /** GridViewのアダプター */
    private RowAdapter rowAdapter;

    /**
     * コンストラクタ
     */
    public SwipeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // GridViewにAdapterをセットする。
        rowAdapter = new RowAdapter(context);
        setAdapter(rowAdapter);

        // GridViewのリスナー登録
        setOnTouchListener(createSwipeListener());
    }

    /**
     * リスナーを生成する
     */
    private SwipeListener createSwipeListener() {
        return new SwipeListener(new SwipeListener.Listener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent, SwipeListener.SwipeMode mode) {

                // タッチしている座標からグリッド番号を取得
                int position = getTouchChildIndex((int) motionEvent.getX(), (int) motionEvent.getY());

                // シングル選択の判定（タップ開始からタップ終了まで同じセル内のみタップしていた。）
                boolean singleTouching = (mode == SwipeListener.SwipeMode.SINGLE_TOUCH) && (startIndex == position);

                // スワイプ選択の判定（選択モードで前回タップしたセル以外をタップしている。）
                boolean swipeTouching = (mode == SwipeListener.SwipeMode.SELECTION_MODE) && (prevIndex != position);

                // シングル選択またはスワイプ選択ならば、viewの選択状態を反転する。
                if (singleTouching || swipeTouching) {
                    rowAdapter.invertSelected(position);
                    invalidateViews();
                    prevIndex = position;
                }

                // タップ開始の時は、開始時のview番号を取得する。
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startIndex = position;
                }

                // タップ終了の時は、リセットする。
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startIndex = -1;
                    prevIndex = -1;
                }

                // スライドモードの場合はグリッドをスライド可能にする。
                return mode != SwipeListener.SwipeMode.SLIDE_MODE;
            }
        });
    }


    /**
     * 座標からGridView内のView要素のTagを取得する。
     * @param x 対象のx座標
     * @param y 対象のy座標
     * @return position
     */
    private int getTouchChildIndex(final int x, final int y) {
        int childCount = getChildCount();
        Rect rect = new Rect();

        for (int index = 0; index < childCount; index++) {
            getChildAt(index).getHitRect(rect);
            if (rect.contains(x, y)) {
                return (int)getChildAt(index).getTag();
            }
        }
        return -1;
    }
}
