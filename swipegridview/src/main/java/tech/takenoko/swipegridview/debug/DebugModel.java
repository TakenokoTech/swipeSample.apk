package tech.takenoko.swipegridview.debug;

import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tech.takenoko.swipegridview.listener.SwipeMode;

/**
 * Created by takenaka on 2018/03/19.
 */
public class DebugModel {

    /** インスタンス */
    private static DebugModel inst = null;

    /** 閾値判定の座標 */
    private PointF point = new PointF(0, 0);

    /** タップ判定した座標 */
    private List<PointF> decisionPointList = new ArrayList<>();

    /** リスナー状態 */
    private SwipeMode mode = SwipeMode.NONE;

    /** シングルトン */
    public static DebugModel getInstance() {
        if(inst == null) {
            inst = new DebugModel();
        }
        return inst;
    }

    /**
     * 表示情報の更新
     * @param p 閾値判定の座標
     * @param decisionPoint タップ判定が行われた座標
     * @param m リスナーの状態
     */
     public void update (PointF p, PointF decisionPoint, SwipeMode m) {

         Log.d("^^^", m.toString());
         getInstance().point = p;
         getInstance().mode = m;

         if(getInstance().decisionPointList == null) {
             getInstance().decisionPointList = new ArrayList<>();
         }
         if(mode == SwipeMode.SELECTION_MODE) {
            getInstance().decisionPointList.add(decisionPoint);
         } else {
            getInstance().decisionPointList = new ArrayList<>();
         }
    }


    public PointF getPoint() {
        return getInstance().point;
    }

    public List<PointF> getDecisionPointList() {
        return getInstance().decisionPointList;
    }

    public SwipeMode getMode() {
        return getInstance().mode;
    }
}
