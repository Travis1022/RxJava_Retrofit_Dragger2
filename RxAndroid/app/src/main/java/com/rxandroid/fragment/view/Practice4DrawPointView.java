package com.rxandroid.fragment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View4:绘制一个点
 */
public class Practice4DrawPointView extends View {

    private Paint mPaint;

    public Practice4DrawPointView(Context context) {
        super(context);
    }

    public Practice4DrawPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice4DrawPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制点一般常用于线条的端点

        //1、绘制一个绿色的圆形点
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //宽度：必不可少
        mPaint.setStrokeWidth(50f);
        canvas.drawPoint(100, 100, mPaint);

        //2、绘制一个浅蓝色的矩形点
        mPaint = new Paint();
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(50f);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(200, 200, mPaint);
    }
}
