package tech.takenoko.swipegridview.io;

/**
 * リスナー状態
 */
public enum SwipeMode {
    /** 未選択 */
    NONE,
    /** シングル選択（未選択状態でタッチ終了） */
    SINGLE_TOUCH,
    /** 選択モード開始（横スワイプ） */
    SWIPE_MODE_START,
    /** 選択モード（横スワイプ） */
    SWIPE_MODE,
    /** 選択モード終了 */
    SWIPE_MODE_END,
    /** スライドモード開始 */
    SLIDE_MODE_START,
    /** スライドモード（縦スワイプ） */
    SLIDE_MODE,
    /** スライドモード終了 */
    SLIDE_MODE_END,
}
