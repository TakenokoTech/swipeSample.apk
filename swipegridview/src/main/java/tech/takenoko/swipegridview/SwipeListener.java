package tech.takenoko.swipegridview;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import tech.takenoko.swipegridview.debug.DebugModel;
import tech.takenoko.swipegridview.io.NextListener;
import tech.takenoko.swipegridview.io.SwipeMode;

/**
 * Created by takenaka on 2018/03/14.
 */
public class SwipeListener implements View.OnTouchListener {

    /** フリック判定時の遊び */
    public final static float DEFAULT_PLAY = 50f;

    /** 現在のスワイプ状態 */
    private SwipeMode mode = SwipeMode.NONE;

    /** 後続リスナー */
    private NextListener listener;

    /** 閾値判定の座標 */
    private PointF lastPoint = new PointF(0, 0);

    /**
     * コンストラクタ
     * @param nextListener 後続のリスナー
     */
    SwipeListener(@NonNull NextListener nextListener) {
        this.listener = nextListener;
    }

    /**
     * タップイベント時の処理を振りわける。
     * @param view
     * @param event
     * @return 後続リスナーのonTouchの返り値
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mode = touchStart(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mode = touching(event);
                break;
            case MotionEvent.ACTION_UP:
                mode = touchEnd(event);
                break;
            default:
                break;
        }
        DebugModel.getInstance().update(lastPoint, new PointF(event.getX(), event.getY()), mode);
        return listener.onTouch(view, event, mode);
    }

    /**
     * タップ開始時にタップの閾値判定で使う座標を取得する。
     * @param event タップイベント
     * @return リスナー状態
     */
    private SwipeMode touchStart(MotionEvent event) {
        lastPoint = new PointF(event.getX(), event.getY());
        return SwipeMode.NONE;
    }

    /**
     * タップ終了時はリスナー状態の終了判定を行う。
     * @param event タップイベント
     * @return リスナー状態
     */
    private SwipeMode touchEnd(MotionEvent event) {
        switch (mode) {
            // スワイプ選択開始
            case SWIPE_MODE:    return SwipeMode.SWIPE_MODE_END;
            case SLIDE_MODE:    return SwipeMode.SLIDE_MODE_END;
            case NONE:          return SwipeMode.SINGLE_TOUCH;
            default:            return SwipeMode.NONE;
        }
    }

    /**
     * タップ中は未選択状態の場合は適宜閾値判定を行う。
     * @param event タップイベント
     * @return リスナー状態
     */
    private SwipeMode touching(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();

        if (currentX + DEFAULT_PLAY < lastPoint.x) {
            // スワイプ選択開始
            if(mode == SwipeMode.NONE) return SwipeMode.SWIPE_MODE_START;
            // スワイプ選択中
            else if(mode == SwipeMode.SWIPE_MODE_START) return SwipeMode.SWIPE_MODE;
        } else if (lastPoint.x < currentX - DEFAULT_PLAY) {
            // スワイプ選択開始
            if(mode == SwipeMode.NONE) return SwipeMode.SWIPE_MODE_START;
            // スワイプ選択中
            else if(mode == SwipeMode.SWIPE_MODE_START) return SwipeMode.SWIPE_MODE;
        } else if (currentY + DEFAULT_PLAY < lastPoint.y) {
            // スライド開始
            if(mode == SwipeMode.NONE) return SwipeMode.SLIDE_MODE_START;
            // スライド中
            else if(mode == SwipeMode.SLIDE_MODE_START) return SwipeMode.SLIDE_MODE;
        } else if (lastPoint.y < currentY - DEFAULT_PLAY) {
            // スライド開始
            if(mode == SwipeMode.NONE) return SwipeMode.SLIDE_MODE_START;
            // スライド中
            else if(mode == SwipeMode.SLIDE_MODE_START) return SwipeMode.SLIDE_MODE;
        }
        return mode;
    }
}
