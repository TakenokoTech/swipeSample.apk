package tech.takenoko.swipegridview.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tech.takenoko.swipegridview.SwipeListener;
import tech.takenoko.swipegridview.listener.SwipeMode;

/**
 * Created by takenaka on 2018/03/14.
 *
 * デバッグ用の画面
 *  - 未選択状態は黒
 *  - 選択モードは赤
 *  - スワイプモードは青
 */
public class DebugView extends View {

    // Model
    DebugModel debugModel = DebugModel.getInstance();

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();

    public DebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint2.setColor(Color.YELLOW);

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(invalidateTask(), 0L, 100L, TimeUnit.MILLISECONDS);
    }

    private Runnable invalidateTask() {
        return new Runnable() {
            @Override public void run() {
                invalidate();
            }
        };
    }

    /**
     * 画面の再描画
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("^^^^", "sizwe = " +DebugModel.getInstance().getDecisionPointList().size());

        canvas.drawColor(Color.argb(0, 0, 0, 0));
        if(DebugModel.getInstance().getPoint().x == 0 && DebugModel.getInstance().getPoint().y == 0) return;

        switch (DebugModel.getInstance().getMode()) {
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
        canvas.drawCircle(  DebugModel.getInstance().getPoint().x, DebugModel.getInstance().getPoint().y, SwipeListener.DEFAULT_PLAY, mPaint);

        for(PointF p : DebugModel.getInstance().getDecisionPointList()) {
            canvas.drawCircle(  p.x, p.y, 10f, mPaint2);
        }
    }
}
