package tech.takenoko.swipegridview.io;

import android.view.MotionEvent;
import android.view.View;

/**
 * 後続のリスナー登録用のインターフェース
 */
public interface NextListener {

    /**
     * 後続処理
     */
    boolean onTouch(View view, MotionEvent motionEvent, SwipeMode mode);
}
