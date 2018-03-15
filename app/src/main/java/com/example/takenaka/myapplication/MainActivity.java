package com.example.takenaka.myapplication;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    /** 検証用の画面 */
    public static DebugView debugView;

    /** */
    private GridView gridView;

    /** タッチ開始時にタップしていたGridView内のView要素のTag */
    private static int startIndex = -1;

    /** 前回タッチしたGridView内のView要素のTag */
    private static int prevIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // viewの初期化
        debugView = findViewById(R.id.debug_view);
        gridView = findViewById(R.id.grid_view);
        setupGridView();
    }

    /**
     * GridViewのセットアップ
     */
    private void setupGridView() {

        // GridViewにAdapterをセットする。
        final RowAdapter rowView = new RowAdapter(this);
        gridView.setAdapter(rowView);

        // GridViewのリスナー登録
        gridView.setOnTouchListener(new SwipeListener(new SwipeListener.Listener() {

            @Override public boolean onTouch(View view, MotionEvent motionEvent, SwipeListener.SwipeMode mode) {

                // タッチしている座標からグリッド番号を取得
                int position = getTouchChildIndex(gridView, (int)motionEvent.getX(), (int)motionEvent.getY());

                // シングル選択の判定（タップ開始からタップ終了まで同じセル内のみタップしていた。）
                boolean singleTouching = (mode == SwipeListener.SwipeMode.SINGLE_TOUCH) && (startIndex == position);

                // スワイプ選択の判定（選択モードで前回タップしたセル以外をタップしている。）
                boolean swipeTouching = (mode == SwipeListener.SwipeMode.SELECTION_MODE) && (prevIndex != position);

                // シングル選択またはスワイプ選択ならば、viewの選択状態を反転する。
                if(singleTouching || swipeTouching) {
                    rowView.invertSelected(position);
                    gridView.invalidateViews();
                    prevIndex = position;
                }

                // タップ開始の時は、開始時のview番号を取得する。
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    startIndex = position;
                }

                // タップ終了の時は、リセットする。
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startIndex = -1;
                    prevIndex = -1;
                }

                // スライドモードの場合はグリッドをスライド可能にする。
                return mode != SwipeListener.SwipeMode.SLIDE_MODE;
            }
        }));
    }

    /**
     * 座標からGridView内のView要素のTagを取得する。
     * @param x 対象のx座標
     * @param y 対象のy座標
     * @return position
     */
    public int getTouchChildIndex(GridView mGridview, final int x, final int y) {
        int childCount = mGridview.getChildCount();
        Rect rect = new Rect();

        for (int index = 0; index < childCount; index++) {
            mGridview.getChildAt(index).getHitRect(rect);
            if (rect.contains(x, y)) {
                return (int)mGridview.getChildAt(index).getTag();
            }
        }
        return -1;
    }
}
