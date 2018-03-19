package tech.takenoko.swipegridview.listener;

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
