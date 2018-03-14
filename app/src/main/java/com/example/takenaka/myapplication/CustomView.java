package com.example.takenaka.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by takenaka on 2018/03/14.
 */

public class CustomView extends View {

    private Paint mPaint = new Paint();
    private PointF point = new PointF(0, 0);

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(0, 0, 0, 0));
        if(point.x == 0 && point.y == 0) return;

        switch (SwipeListener.state) {
            case NONE: mPaint.setColor(Color.BLACK); break;
            case SELECTING_MODE: mPaint.setColor(Color.RED); break;
            case SLIDE_MODE: mPaint.setColor(Color.BLUE); break;
        }
        canvas.drawCircle(  point.x, point.y, 80f, mPaint);
    }

    void update (PointF point) {
        this.point = point;
        invalidate();
    }

}
