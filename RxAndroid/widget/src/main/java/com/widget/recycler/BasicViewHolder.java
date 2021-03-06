package com.widget.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * 重新封装ViewHolder
 * Created by guchenkai on 2015/11/9.
 */
public abstract class BasicViewHolder<Item extends Serializable> extends RecyclerView.ViewHolder {
    protected Context mContext;
    private List<View> mBindViews;
    private Map<String, Object> mTagMap;

    public BasicViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void setBindViews(List<View> bindViews) {
        mBindViews = bindViews;
    }

    public void setTagMap(Map<String, Object> tagMap) {
        mTagMap = tagMap;
    }

    public BasicViewHolder(View itemView) {
        this(null, itemView);
    }

    /**
     * 向itemView上绑定数据
     *
     * @param item     item数据
     * @param position 该条数据的位置
     */
    public abstract void onBindItem(Item item, int position);

    /**
     * 是否已经绑定tag
     *
     * @param item item
     */
    private boolean hasBindingTag(View view, Item item) {
        return mTagMap.containsKey(view.getId() + "_" + item.hashCode());
    }

    /**
     * 绑定tag
     *
     * @param view 目标View
     * @param item item
     */
    public void bindingTag(View view, Item item, Object value) {
        if (!mBindViews.contains(view)) mBindViews.add(view);
        mTagMap.put(view.getId() + "_" + item.hashCode(), value);
    }

    /**
     * 获取绑定tag中的值
     *
     * @param view     目标View
     * @param item     item
     * @param defValue 默认值
     */
    public <T> T getTagValue(View view, Item item, T defValue) {
        if (hasBindingTag(view, item))
            return (T) mTagMap.get(view.getId() + "_" + item.hashCode());
        return defValue;
    }
}
