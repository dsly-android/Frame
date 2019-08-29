package com.htxtdshopping.htxtd.frame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.htxtdshopping.htxtd.frame.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author 陈志鹏
 * @date 2019/1/14
 */
public class MsgNumView extends AppCompatTextView {

    private int mNumber;
    private int mBackgroundColor;
    private int mPaddingLeftAndRight;

    public MsgNumView(Context context) {
        this(context, null);
    }

    public MsgNumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MsgNumView);
        mNumber = typedArray.getInteger(R.styleable.MsgNumView_mnv_number, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.MsgNumView_mnv_backgroundColor, ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
        mPaddingLeftAndRight = typedArray.getDimensionPixelSize(R.styleable.MsgNumView_mnv_paddingLeftAndRight, AutoSizeUtils.pt2px(getContext(), 10));
        typedArray.recycle();

        setBackground();
        setPadding(mPaddingLeftAndRight, 0, mPaddingLeftAndRight, 0);
        setNumber(mNumber);
    }

    public void setBackground() {
        float textSize = getTextSize();
        int height = (int) (textSize + AutoSizeUtils.pt2px(getContext(), 10));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(height / 2);
        drawable.setColor(mBackgroundColor);
        drawable.setSize(height, height);
        setBackground(drawable);
    }

    public void setNumber(int number) {
        mNumber = number;
        if (mNumber <= 0) {
            setVisibility(GONE);
        } else if (mNumber > 99) {
            setText("99+");
            setVisibility(VISIBLE);
        } else {
            setText(mNumber + "");
            setVisibility(VISIBLE);
        }
    }
}
