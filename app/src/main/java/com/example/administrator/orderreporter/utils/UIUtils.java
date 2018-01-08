package com.example.administrator.orderreporter.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by JINAN on 2017/10/16.
 */

public class UIUtils {
    /**
     * 简化ViewHolder
     * @param view 父控件
     * @param id  资源ID
     * @param <T> 继承自View的控件
     * @return 控件
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T getViewByHolder(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}