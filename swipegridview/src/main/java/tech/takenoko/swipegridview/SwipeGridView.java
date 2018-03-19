package tech.takenoko.swipegridview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import tech.takenoko.swipegridview.listener.NextListener;
import tech.takenoko.swipegridview.listener.OnSingleTouchListener;
import tech.takenoko.swipegridview.listener.OnSwipeTouchListener;
import tech.takenoko.swipegridview.listener.SwipeMode;

/**
 * Created by takenaka on 2018/03/16.
 */

public class SwipeGridView extends GridView {

    /** タッチ開始時にタップしていたGridView内のView要素のTag */
    private static Object startTag = new Object();

    /** 前回タッチしたGridView内のView要素のTag */
    private static Object prevTag = new Object();

    /** シングル選択のリスナー */
    OnSingleTouchListener singleTouchListener = null;

    /** スワイプ選択のリスナー */
    OnSwipeTouchListener swipeTouchListener = null;

    /**
     * コンストラクタ
     */
    public SwipeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // GridViewのリスナー登録
        setOnTouchListener(createSwipeListener());
    }

    /**
     * リスナーを生成する
     */
    private SwipeListener createSwipeListener() {
        return new SwipeListener(new NextListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent, SwipeMode mode) {

                // タッチしている座標からグリッド番号を取得
                Object tag = getTouchChildIndex((int) motionEvent.getX(), (int) motionEvent.getY());

                // シングル選択の判定（タップ開始からタップ終了まで同じセル内のみタップしていた。）
                boolean singleTouching = (mode == SwipeMode.SINGLE_TOUCH) && checkTag(startTag, tag);

                // スワイプ選択の判定（選択モードで前回タップしたセル以外をタップしている。）
                boolean swipeTouching = (mode == SwipeMode.SELECTION_MODE) && !checkTag(prevTag, tag);

                // シングル選択
                if (singleTouching) {
                    if(singleTouchListener != null) singleTouchListener.onTouch(view, motionEvent, tag);
                    invalidateViews();
                    prevTag = tag;
                }

                // スワイプ選択
                if (swipeTouching) {
                    if(swipeTouchListener != null) swipeTouchListener.onTouch(view, motionEvent, tag);
                    invalidateViews();
                    prevTag = tag;
                }

                // タップ開始の時は、開始時のview番号を取得する。
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startTag = tag;
                }

                // タップ終了の時は、リセットする。
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    prevTag = null;
                    startTag = null;
                }

                // スライドモードの場合はグリッドをスライド可能にする。
                return mode != SwipeMode.SLIDE_MODE;
            }
        });
    }

    /**
     * シングル選択のリスナー登録
     * @param l リスナー
     */
    public void setOnSingleTouching(@NonNull OnSingleTouchListener l) {
        singleTouchListener = l;
    }

    /**
     * スワイプ選択のリスナー登録
     * @param l
     */
    public void setOnSwipeTouching(@NonNull OnSwipeTouchListener l) {
        swipeTouchListener = l;
    }

    /**
     * 座標からGridView内のView要素のTagを取得する。
     * @param x 対象のx座標
     * @param y 対象のy座標
     * @return tag
     */
    @Nullable
    private Object getTouchChildIndex(final int x, final int y) {
        int childCount = getChildCount();
        Rect rect = new Rect();

        for (int index = 0; index < childCount; index++) {
            getChildAt(index).getHitRect(rect);
            if (rect.contains(x, y)) {
                return getChildAt(index).getTag();
            }
        }
        return null;
    }

    /**
     * タグが異なるか
     * @param target
     * @param tag
     * @return
     */
    private boolean checkTag(Object target, Object tag) {
        if (target != null) {
            return target.equals(tag);
        } else {
            return false;
        }
    }
}
