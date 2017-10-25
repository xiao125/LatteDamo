package com.latte.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 提示小红点
 * Created by Administrator on 2017/10/25 0025.
 */

public class CircleTextView extends AppCompatTextView {

    private  Paint PAINT;
    private  PaintFlagsDrawFilter FILTER;

    public CircleTextView(Context context) {
        super(context,null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        PAINT = new Paint();
        FILTER = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG); //设置规则
        PAINT.setColor(Color.WHITE); //设置画笔颜色
        PAINT.setAntiAlias(true);
    }

    public final void setCircleBackground(@ColorInt int color){
        PAINT.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = getMeasuredWidth();
        final int height = getMaxHeight();
        final int max = Math.max(width, height);
        setMeasuredDimension(max,max);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(FILTER);
        canvas.drawCircle(getWidth()/2,
                getHeight()/2,
                Math.max(getWidth(),getHeight())/2,
                PAINT);
        super.draw(canvas);
    }
}
