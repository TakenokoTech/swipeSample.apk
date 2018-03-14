package com.example.takenaka.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by takenaka on 2018/03/14.
 */

public class CustomGridView extends GridView {

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getTouchChildIndex(final int x, final int y) {
        int childCount = getChildCount();
        Rect rect = new Rect();

        for (int index = 0; index < childCount; index++) {
            getChildAt(index).getHitRect(rect);
            if (rect.contains(x, y)) {
                return index;
            }
        }
        return -1;
    }
}
