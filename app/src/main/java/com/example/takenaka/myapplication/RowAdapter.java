package com.example.takenaka.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by takenaka on 2018/03/14.
 */

public class RowAdapter extends BaseAdapter {

    GridView gridView;
    private LayoutInflater mLayoutInflater;
    public Map<Integer, ViewHolder> viewHolderList = new HashMap<>();

    public ArrayList<Integer> mColorArray = new ArrayList(Arrays.asList(Color.GREEN));
    public ArrayList<String> mArray = new ArrayList(Arrays.asList(
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"
    ));

    public RowAdapter(Context mContext, final CustomGridView gridView) {
        mLayoutInflater = LayoutInflater.from(mContext);
        this.gridView = gridView;
    }

    @Override
    public View getView(final int position, View mConvertView, ViewGroup viewGroup) {

        // Holderを初期化
        if(viewHolderList.get(position) == null) {
            viewHolderList.put(position, new ViewHolder());
        }

        //
        mConvertView = mLayoutInflater.inflate(R.layout.row_view, viewGroup, false);
        ViewHolder viewHolder = viewHolderList.get(position);
        viewHolder.rowView = mConvertView.findViewById(R.id.row_view);
        viewHolder.rowText = mConvertView.findViewById(R.id.row_text);
        mConvertView.setTag(position);

        // テキスト
        viewHolder.rowText.setText(mArray.get(position));

        // 色を描画
        if(viewHolder.onTouch) {
            viewHolder.rowView.setBackgroundColor(Color.GRAY);
        } else  {
            viewHolder.rowView.setBackgroundColor(mColorArray.get(position % mColorArray.size()));
        }

        return mConvertView;
    }

    @Override public int getCount() {
        return mArray.size();
    }
    @Override public Object getItem(int position) { return viewHolderList.get(position); }
    @Override public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView rowText;
        View rowView;
        boolean onTouch = false;
    }
}
