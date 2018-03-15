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

    /**
     * View要素
     */
    static class ViewHolder {
        /** 大枠 */
        View rowView;
        /** テキスト */
        TextView rowText;
        /** 選択状態 */
        boolean isSelected = false;
    }

    private final static ArrayList<String> mArray = new ArrayList(Arrays.asList(
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
            "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79"
    ));

    private LayoutInflater mLayoutInflater;

    /** セル毎のView要素 */
    private Map<Integer, ViewHolder> viewHolderList = new HashMap<>();

    /**
     * コンストラクタ
     * @param context
     */
    RowAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 表示更新
     * @param position
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        // Holderを初期化
        if(viewHolderList.get(position) == null) {
            viewHolderList.put(position, new ViewHolder());
        }

        // RecyclerView実装出来てないです。
        convertView = mLayoutInflater.inflate(R.layout.row_view, viewGroup, false);
        ViewHolder viewHolder = viewHolderList.get(position);
        viewHolder.rowView = convertView.findViewById(R.id.row_view);
        viewHolder.rowText = convertView.findViewById(R.id.row_text);
        convertView.setTag(position);

        // テキスト変更
        viewHolder.rowText.setText(mArray.get(position));

        // 選択状態かで色を変更
        if(viewHolder.isSelected) {
            viewHolder.rowView.setBackgroundColor(Color.GRAY);
        } else  {
            viewHolder.rowView.setBackgroundColor(Color.GREEN);
        }

        return convertView;
    }

    /**
     * 選択状態を反転させる。
     * @param position
     */
    public void invertSelected(final int position) {
        if(viewHolderList.get(position) != null) {
            viewHolderList.get(position).isSelected = !viewHolderList.get(position).isSelected;
            notifyDataSetChanged();
        }
    }

    @Override public int getCount() {
        return mArray.size();
    }

    @Override public Object getItem(int position) {
        return viewHolderList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}
