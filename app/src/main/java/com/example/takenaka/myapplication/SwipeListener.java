package com.example.takenaka.myapplication;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by takenaka on 2018/03/14.
 */
public class SwipeListener implements View.OnTouchListener {

    /**
     * リスナー状態
     */
    public enum SwipeMode {
        /** 未選択 */
        NONE,
        /** シングル選択（未選択状態でタッチ終了） */
        SINGLE_TOUCH,
        /** 選択モード（横スワイプ） */
        SELECTION_MODE,
        /** 選択モード終了 */
        SELECTION_MODE_END,
        /** スライドモード（縦スワイプ） */
        SLIDE_MODE,
        /** スライドモード終了 */
        SLIDE_MODE_END,
    }

    /**
     * 後続のリスナー登録用のインターフェース
     */
    interface Listener {
        boolean onTouch(View view, MotionEvent motionEvent, SwipeMode mode);
    }

    /** 現在のスワイプ状態 */
    private SwipeMode mode = SwipeMode.NONE;

    /** 後続リスナー */
    private Listener listener;

    /** フリック判定時の遊び */
    private final static float DEFAULT_PLAY = 80f;

    /** 閾値判定の座標 */
    private PointF lastPoint = new PointF(0, 0);

    /**
     * コンストラクタ
     * @param nextListener 後続のリスナー
     */
    SwipeListener(@NonNull Listener nextListener) {
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
                touchStart(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touching(event);
                break;
            case MotionEvent.ACTION_UP:
                touchEnd(event);
                break;
            default:
                break;
        }
        MainActivity.debugView.update(lastPoint, new PointF(event.getX(), event.getY()), mode);
        return listener.onTouch(view, event, mode);
    }

    /**
     * タップ開始時にタップの閾値判定で使う座標を取得する。
     * @param event リスナー状態
     */
    private void touchStart(MotionEvent event) {
        lastPoint = new PointF(event.getX(), event.getY());
        mode = SwipeMode.NONE;
        touching(event);
    }

    /**
     * タップ終了時はリスナー状態の終了判定を行う。
     * @param event リスナー状態
     */
    private void touchEnd(MotionEvent event) {
        switch (mode) {
            case SELECTION_MODE:
                mode = SwipeMode.SELECTION_MODE_END;
                break;
            case SLIDE_MODE:
                mode = SwipeMode.SLIDE_MODE_END;
                break;
            case NONE:
                mode = SwipeMode.SINGLE_TOUCH;
                break;
            default:
                mode = SwipeMode.NONE;
                break;
        }
    }

    /**
     * タップ中は未選択状態の場合は適宜閾値判定を行う。
     * @param event リスナー状態
     */
    private void touching(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();

        if (currentX + DEFAULT_PLAY < lastPoint.x) {
            if(mode == SwipeMode.NONE) mode = SwipeMode.SELECTION_MODE;

        } else if (lastPoint.x < currentX - DEFAULT_PLAY) {
            if(mode == SwipeMode.NONE) mode = SwipeMode.SELECTION_MODE;

        } else if (currentY + DEFAULT_PLAY < lastPoint.y) {
            if(mode == SwipeMode.NONE) mode = SwipeMode.SLIDE_MODE;

        } else if (lastPoint.y < currentY - DEFAULT_PLAY) {
            if(mode == SwipeMode.NONE) mode = SwipeMode.SLIDE_MODE;
        }
    }
}
