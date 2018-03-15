package com.example.takenaka.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takenaka on 2018/03/14.
 *
 * デバッグ用の画面
 *  - 未選択状態は黒
 *  - 選択モードは赤
 *  - スワイプモードは青
 */
public class CustomView extends View {

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();

    /** 閾値判定の座標 */
    private PointF point = new PointF(0, 0);

    /** タップ判定した座標 */
    private List<PointF> decisionPointList = new ArrayList<>();

    /** リスナー状態 */
    private SwipeListener.SwipeMode mode = SwipeListener.SwipeMode.NONE;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint2.setColor(Color.YELLOW);
    }

    /**
     * 画面の再描画
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.argb(0, 0, 0, 0));
        if(point.x == 0 && point.y == 0) return;

        switch (mode) {
            case NONE:
                mPaint.setColor(Color.BLACK);
                break;

            case SELECTION_MODE:
            case SELECTION_MODE_END:
                mPaint.setColor(Color.RED);
                break;

            case SLIDE_MODE:
            case SLIDE_MODE_END:
                mPaint.setColor(Color.BLUE);
                break;
        }
        canvas.drawCircle(  point.x, point.y, 80f, mPaint);

        for(PointF p : decisionPointList) {
            canvas.drawCircle(  p.x, p.y, 10f, mPaint2);
        }
    }

    /**
     * 表示情報の更新
     * @param point 閾値判定の座標
     * @param decisionPoint タップ判定が行われた座標
     * @param mode リスナーの状態
     */
    void update (PointF point, PointF decisionPoint, SwipeListener.SwipeMode mode) {
        this.point = point;
        this.mode = mode;

        if(mode == SwipeListener.SwipeMode.SELECTION_MODE) {
            decisionPointList.add(decisionPoint);
        } else {
            decisionPointList = new ArrayList<>();
        }

        invalidate();
    }

}
