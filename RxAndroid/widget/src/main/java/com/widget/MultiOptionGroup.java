package com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.sunnybear.library.util.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 多选选择器
 * Created by chenkai.gu on 2016/11/2.
 */
public class MultiOptionGroup extends LinearLayout {
    private static final String TAG = MultiOptionGroup.class.getSimpleName();
    private static final String[] titles = {"A.", "B.", "C.", "D.", "E.", "F.", "G.", "H.", "I.", "J.", "K.", "L.", "M.", "N."
            , "O.", "P.", "Q.", "R.", "S.", "T.", "U.", "V.", "W.", "X.", "Y.", "Z."};
    /*选项之间的间距*/
    private int margin;
    /*字体大小*/
    private float textSize;
    /*字体颜色*/
    private int textColor;
    /*选择器贴图*/
    private Drawable selectDrawable;
    /*已选择的选项*/
    private List<MultiOption> selMultiOption;
    private List<CheckBox> mCheckBoxes;
    /*选项选择监听器*/
    private OnMultiSelectedListener mOnMultiSelectedListener;

    public void setOnMultiSelectedListener(OnMultiSelectedListener onMultiSelectedListener) {
        mOnMultiSelectedListener = onMultiSelectedListener;
    }

    public MultiOptionGroup(Context context) {
        this(context, null);
    }

    public MultiOptionGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyleable(context, attrs);

        mCheckBoxes = new ArrayList<>();
        selMultiOption = new ArrayList<>();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultiOptionGroup);
        margin = array.getDimensionPixelSize(R.styleable.MultiOptionGroup_mg_margin, 0);
        textSize = array.getDimension(R.styleable.MultiOptionGroup_mg_textSize, DensityUtil.sp2px(getContext(), 12));
        textColor = array.getColor(R.styleable.MultiOptionGroup_mg_textColor, -1);
        selectDrawable = array.getDrawable(R.styleable.MultiOptionGroup_mg_selectDrawable);
        array.recycle();
    }

    private List<MultiOption> createMultiOptions(List<String> options) {
        List<MultiOption> multiOptions = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            multiOptions.add(new MultiOption(i, options.get(i)));
        }
        return multiOptions;
    }

    /**
     * 设置选项
     *
     * @param options 选项集合
     */
    public List<MultiOption> setOptions(List<String> options) {
        List<MultiOption> multiOptions = createMultiOptions(options);
        for (int i = 0; i < multiOptions.size(); i++) {
            final MultiOption multiOption = multiOptions.get(i);
            CheckBox button = new CheckBox(getContext());
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT
                    , LayoutParams.WRAP_CONTENT);
            if (i != multiOptions.size() - 1)
                switch (getOrientation()) {
                    case HORIZONTAL:
                        params.setMargins(0, 0, margin, 0);
                        break;
                    case VERTICAL:
                        params.setMargins(0, 0, 0, margin);
                        break;
                }
            button.setText(titles[i] + multiOption.getOption());
            button.setTextSize(textSize);
            if (textColor != -1)
                button.setTextColor(textColor);
            if (selectDrawable != null)
                button.setButtonDrawable(selectDrawable);
            button.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked)
                    selMultiOption.add(multiOption);
                else
                    selMultiOption.remove(multiOption);
                Collections.sort(selMultiOption, (lhs, rhs) -> lhs.getIndex() - rhs.getIndex());
                if (mOnMultiSelectedListener != null)
                    mOnMultiSelectedListener.onMultiSelected(selMultiOption);
            });
            addView(button, params);
            mCheckBoxes.add(button);
        }
        return multiOptions;
    }

    public void setOptions(String... options) {
        List<String> ops = Arrays.asList(options);
        setOptions(ops);
    }

    public void setDefaultOptions(List<Integer> options) {
        for (Integer option : options) {
            mCheckBoxes.get(option).setChecked(true);
        }
//        postInvalidate();
    }

    public void setDefaultOptions(int... options) {
        List<Integer> ops = new ArrayList<>();
        for (int option : options) {
            ops.add(option);
        }
        setDefaultOptions(ops);
    }

    private String getOption(String s) {
        String[] strings = s.split("\\.");
        return strings[1];
    }

    private String getOptions(List<String> options) {
        StringBuffer buffer = new StringBuffer();
        for (String option : options) {
            buffer.append(option).append(",");
        }
        int length = buffer.length();
        if (length > 0)
            return buffer.substring(0, length - 1).toString();
        else
            return "";
    }

    private List<Integer> mCanBeChooseOptionsList = new ArrayList<>();

    public void addCanNotBeChooseOptions(Integer... options) {
        List<Integer> canBeChooseOptionsList = Arrays.asList(options);
        for (int option : options) {
            mCheckBoxes.get(option).setEnabled(false);
            if (!mCanBeChooseOptionsList.contains(options))
                mCanBeChooseOptionsList.add(option);
        }
    }

    public void setCanNotBeChoose() {
        for (int i = 0; i < mCheckBoxes.size(); i++) {
            mCheckBoxes.get(i).setEnabled(false);
            if (!mCanBeChooseOptionsList.contains(i))
                mCanBeChooseOptionsList.add(i);
        }
    }

    public void removeCanNotBeChooseOptions(int... options) {
        for (int option : options) {
            if (mCanBeChooseOptionsList.contains(options)) {
                mCheckBoxes.get(option).setEnabled(true);
                mCanBeChooseOptionsList.remove(option);
            }
        }
    }

    public void removeAllCanNotBeChooseOptions() {
        for (int option : mCanBeChooseOptionsList)
            mCheckBoxes.get(option).setEnabled(false);
        mCanBeChooseOptionsList.clear();
    }

    /**
     * 选项选择监听器
     */
    public interface OnMultiSelectedListener {

        void onMultiSelected(List<MultiOption> options);
    }

    /**
     * 多选选项组
     */
    public static class MultiOption {
        private int index;
        private String option;

        public MultiOption(int index, String option) {
            this.index = index;
            this.option = option;
        }

        public int getIndex() {
            return index;
        }

        public String getOption() {
            return option;
        }

        @Override
        public String toString() {
            return "MultiOption{" +
                    "index=" + index +
                    ", option='" + option + '\'' +
                    '}';
        }
    }
}
