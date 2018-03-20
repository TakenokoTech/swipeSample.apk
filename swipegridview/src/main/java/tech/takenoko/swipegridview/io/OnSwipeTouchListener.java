package tech.takenoko.swipegridview.io;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by takenaka on 2018/03/19.
 */

public interface OnSwipeTouchListener {

    /**
     * 後続処理
     */
    void onTouch(View view, MotionEvent motionEvent, @Nullable Object tag);
}