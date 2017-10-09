package com.rxandroid.fragment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice3DrawRectView extends View {


    private Paint mPaint;

    public Practice3DrawRectView(Context context) {
        super(context);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制一个灰色的实心矩形
        mPaint = new Paint();
        mPaint.setColor(Color.LTGRAY);
        //四条边的坐标 左上右下距离坐标轴的距离(x,y,x,y)
        canvas.drawRect(100, 100, 200, 200, mPaint);


        //绘制一个空心的蓝色矩形
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(200, 200, 300, 300, mPaint);
    }
}
