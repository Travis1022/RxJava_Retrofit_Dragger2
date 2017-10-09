package com.rxandroid.fragment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View2：绘制圆形
 */
public class Practice2DrawCircleView extends View {

    Paint mPaint;

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //红色实心圆
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(120, 120, 100, mPaint);

        //线宽为10的空心圆
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);
        canvas.drawCircle(350, 120, 100, mPaint);

    }
}
