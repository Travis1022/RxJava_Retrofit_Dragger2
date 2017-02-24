package com.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 单选选择器
 * Created by chenkai.gu on 2016/10/11.
 */
public class SingleOptionGroup extends RadioGroup implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = SingleOptionGroup.class.getSimpleName();
    private static final String[] titles = {"A.", "B.", "C.", "D.", "E.", "F.", "G.", "H.", "I.", "J.", "K.", "L.", "M.", "N."
            , "O.", "P.", "Q.", "R.", "S.", "T.", "U.", "V.", "W.", "X.", "Y.", "Z."};
    private Context mContext;
    /*选项之间的间距*/
    private int margin;
    /*字体大小*/
    private float textSize;
    /*字体颜色*/
    private int textColor;
    /*选项*/
    private List<String> options;
    private List<RadioButton> mRadioButtons;
    /*选择器贴图*/
    private Drawable selectDrawable;
    /*选项选择监听器*/
    private OnSingleSelectedListener mOnSingleSelectedListener;

    public String option = "";

    public void setOnSingleSelectedListener(OnSingleSelectedListener onSelectedListener) {
        mOnSingleSelectedListener = onSelectedListener;
    }

    public SingleOptionGroup(Context context) {
        this(context, null);
    }

    public SingleOptionGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initStyleable(context, attrs);
        mRadioButtons = new ArrayList<>();

        setOnCheckedChangeListener(this);
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SingleOptionGroup);
        margin = array.getDimensionPixelSize(R.styleable.SingleOptionGroup_sg_margin, 0);
        textSize = array.getDimension(R.styleable.SingleOptionGroup_sg_textSize, DensityUtil.sp2px(getContext(), 12));
        textColor = array.getColor(R.styleable.SingleOptionGroup_sg_textColor, -1);
        selectDrawable = array.getDrawable(R.styleable.SingleOptionGroup_sg_selectDrawable);
        array.recycle();
    }

    /**
     * 设置选项
     *
     * @param options 选项集合
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setOptions(List<String> options) {
        this.options = options;
        for (int i = 0; i < options.size(); i++) {
            RadioButton button = new RadioButton(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT
                    , LayoutParams.WRAP_CONTENT);
            if (i != options.size() - 1)
                switch (getOrientation()) {
                    case HORIZONTAL:
                        params.setMargins(0, 0, margin, 0);
                        break;
                    case VERTICAL:
                        params.setMargins(0, 0, 0, margin);
                        break;
                }
            button.setId(i);
            button.setText(titles[i] + options.get(i));
            button.setTextSize(textSize);
            if (textColor != -1)
                button.setTextColor(textColor);
            if (selectDrawable != null)
                button.setButtonDrawable(selectDrawable);
            addView(button, params);
            mRadioButtons.add(button);
        }
    }

    public void setOptions(String... options) {
        List<String> ops = Arrays.asList(options);
        setOptions(ops);
    }

    public void setDefaultOptions(int options) {
        RadioButton button = mRadioButtons.get(options);
        button.setChecked(true);
//        postInvalidate();
    }

    private List<Integer> mCanBeChooseOptionsList = new ArrayList<>();

    public void addCanNotBeChooseOptions(Integer... options) {
        List<Integer> canBeChooseOptionsList = Arrays.asList(options);
        for (int option : options) {
            mRadioButtons.get(option).setEnabled(false);
            if (!mCanBeChooseOptionsList.contains(options))
                mCanBeChooseOptionsList.add(option);
        }
    }

    public void setCanNotBeChoose() {
        for (int i = 0; i < mRadioButtons.size(); i++) {
            mRadioButtons.get(i).setEnabled(false);
            if (!mCanBeChooseOptionsList.contains(i))
                mCanBeChooseOptionsList.add(i);
        }
    }

    public void removeCanNotBeChooseOptions(int... options) {
        for (int option : options) {
            if (mCanBeChooseOptionsList.contains(options)) {
                mRadioButtons.get(option).setEnabled(true);
                mCanBeChooseOptionsList.remove(option);
            }
        }
    }

    public void removeAllCanNotBeChooseOptions() {
        for (int option : mCanBeChooseOptionsList)
            mRadioButtons.get(option).setEnabled(false);
        mCanBeChooseOptionsList.clear();
    }

    @Override
    public final void onCheckedChanged(RadioGroup group, int checkedId) {
        option = options.get(checkedId);
        Logger.d(TAG, "选择的选项:" + option);
        if (mOnSingleSelectedListener != null && !StringUtils.isEmpty(option))
            mOnSingleSelectedListener.onSingleSelected(checkedId, option);
    }

    /**
     * 选项选择监听器
     */
    public interface OnSingleSelectedListener {

        void onSingleSelected(int position, String option);
    }
}
