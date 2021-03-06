package com.widget.tag;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.sunnybear.library.widget.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 流式标签布局
 * Created by chenkai.gu on 16/07/11.
 */
public class FlowTagLayout<T> extends ViewGroup {

    private static final String TAG = FlowTagLayout.class.getSimpleName();
    private Context mContext;

    /**
     * FlowLayout not support checked
     */
    public static final int FLOW_TAG_CHECKED_NONE = 0;
    /**
     * FlowLayout support single-select
     */
    public static final int FLOW_TAG_CHECKED_SINGLE = 1;
    /**
     * FlowLayout support multi-select
     */
    public static final int FLOW_TAG_CHECKED_MULTI = 2;

    /**
     * Should be used by subclasses to listen to changes in the dataset
     */
    AdapterDataSetObserver mDataSetObserver;

    /**
     * The adapter containing the data to be displayed by this view
     */
    ListAdapter mAdapter;

    /**
     * the tag click event callback
     */
    OnTagClickListener mOnTagClickListener;

    /**
     * the tag select event callback
     */
    OnTagSelectListener mOnTagSelectListener;

    /**
     * 标签流式布局选中模式，默认是不支持选中的
     */
    private int mTagCheckMode = FLOW_TAG_CHECKED_SINGLE;

    private int background_style;//标签背景
    private ColorStateList text_color;//标签字体颜色

    /**
     * 存储选中的tag
     */
    private SparseBooleanArray mCheckedTagArray = new SparseBooleanArray();

    private TagAdapter<T> mTagAdapter;

    public FlowTagLayout(Context context) {
        this(context, null, 0);
    }

    public FlowTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initStyleable(context, attrs);
        setTagCheckedMode(mTagCheckMode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowTagLayout);
        mTagCheckMode = array.getInteger(R.styleable.FlowTagLayout_check_mode, FLOW_TAG_CHECKED_SINGLE);
        background_style = array.getResourceId(R.styleable.FlowTagLayout_background_style, R.drawable.round_rectangle_bg);
        text_color = array.getColorStateList(R.styleable.FlowTagLayout_text_color);
        if (text_color == null)
            text_color = context.getColorStateList(R.color.normal_text_color);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取Padding
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //FlowLayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;

        //测量时每一行的宽度
        int lineWidth = 0;
        //测量时每一行的高度，加起来就是FlowLayout的高度
        int lineHeight = 0;

        //遍历每个子元素
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            //测量每一个子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.leftMargin + mlp.rightMargin;
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;

            //如果当前一行的宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            if ((lineWidth + realChildWidth) > sizeWidth) {
                //换行
                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;
                //换行了，lineWidth和lineHeight重新算
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行的高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            //遍历到最后一个的时候，肯定走的是不换行
            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }

            setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                    modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int flowWidth = getWidth();

        int childLeft = 0;
        int childTop = 0;

        //遍历子控件，记录每个子view的位置
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            //跳过View.GONE的子View
            if (childView.getVisibility() == View.GONE)
                continue;

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            if (childLeft + mlp.leftMargin + childWidth + mlp.rightMargin > flowWidth) {
                //换行处理
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
            }
            //布局
            int left = childLeft + mlp.leftMargin;
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.leftMargin + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;
            childView.layout(left, top, right, bottom);

            childLeft += (mlp.leftMargin + childWidth + mlp.rightMargin);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

    /**
     * 重新加载刷新数据
     */
    private void reloadData() {
        removeAllViews();

        boolean isSetted = false;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            mCheckedTagArray.put(i, false);
            final View childView = mAdapter.getView(i, null, this);
            addView(childView, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            if (mAdapter instanceof OnInitSelectedPosition) {
                boolean isSelected = ((OnInitSelectedPosition) mAdapter).isSelectedPosition(i);
                //判断一下模式
                if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                    //单选只有第一个起作用
                    if (isSelected && !isSetted) {
                        mCheckedTagArray.put(i, true);
                        childView.setSelected(true);
                        isSetted = true;
                    }
                } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                    if (isSelected) {
                        mCheckedTagArray.put(i, true);
                        childView.setSelected(true);
                    }
                }
            }

            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTagCheckMode == FLOW_TAG_CHECKED_NONE) {
                        if (mOnTagClickListener != null)
                            mOnTagClickListener.onItemClick(FlowTagLayout.this, childView, j);
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                        //判断状态
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                            if (mOnTagSelectListener != null)
                                mOnTagSelectListener.onItemSelect(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE, j, null);
                            return;
                        }

                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            mCheckedTagArray.put(k, false);
                            getChildAt(k).setSelected(false);
                        }
                        mCheckedTagArray.put(j, true);
                        childView.setSelected(true);

                        if (mOnTagSelectListener != null) {
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE, j, null);
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                        } else {
                            mCheckedTagArray.put(j, true);
                            childView.setSelected(true);
                        }
                        //回调
                        if (mOnTagSelectListener != null) {
                            List<Integer> list = new ArrayList<>();
                            for (int k = 0; k < mAdapter.getCount(); k++) {
                                if (mCheckedTagArray.get(k))
                                    list.add(k);
                            }
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.FLOW_TAG_CHECKED_MULTI, null, list);
                        }
                    }
                }
            });
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener onTagSelectListener) {
        this.mOnTagSelectListener = onTagSelectListener;
    }

    /**
     * 像ListView、GridView一样使用FlowLayout
     *
     * @param adapter
     */
    private void setAdapter(ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null)
            mAdapter.unregisterDataSetObserver(mDataSetObserver);

        //清除现有的数据
        removeAllViews();
        mAdapter = adapter;

        if (mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    /**
     * 获取标签模式
     *
     * @return
     */
    public int getTagCheckMode() {
        return mTagCheckMode;
    }

    /**
     * 设置标签选中模式
     *
     * @param tagMode
     */
    public void setTagCheckedMode(int tagMode) {
        this.mTagCheckMode = tagMode;
    }

    /**
     * 添加标签
     *
     * @param tags 标签组
     */
    public void addTags(final Callback<T> callback, List<T> tags) {
        mTagAdapter = new TagAdapter<T>(mContext, background_style, text_color) {
            @Override
            public String getTagText(T t) {
                return callback.getTagText(t);
            }
        };
        setAdapter(mTagAdapter);
        List<T> tagList = new ArrayList<>();
        for (T tag : tags) {
            tagList.add(tag);
        }
        mTagAdapter.onlyAddAll(tagList);
    }

    /**
     * 添加标签
     *
     * @param tags     标签组
     * @param selected 默认选中项
     */
    public void addTags(final Callback<T> callback, int selected, List<T> tags) {
        mTagAdapter = new TagAdapter<T>(mContext, background_style, text_color, selected) {
            @Override
            public String getTagText(T t) {
                return callback.getTagText(t);
            }
        };
        setAdapter(mTagAdapter);
        List<T> tagList = new ArrayList<>();
        for (T tag : tags) {
            tagList.add(tag);
        }
        mTagAdapter.onlyAddAll(tagList);
    }

    /**
     * 添加标签
     *
     * @param tags 标签组
     */
    public void addTags(final Callback callback, T... tags) {
        addTags(callback, Arrays.asList(tags));
    }

    /**
     * 添加标签
     *
     * @param selected 默认选中项
     * @param tags     标签组
     */
    public void addTags(final Callback callback, int selected, T... tags) {
        addTags(callback, selected, Arrays.asList(tags));
    }

    /**
     * 获得添加的标签对象集合
     *
     * @return 标签对象集合
     */
    public List<T> getData() {
        return mTagAdapter.getData();
    }

    /**
     * 获得标签内容的回调
     *
     * @param <T>
     */
    public interface Callback<T> {

        String getTagText(T t);
    }
}
