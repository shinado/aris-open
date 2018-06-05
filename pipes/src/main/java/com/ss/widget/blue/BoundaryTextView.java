package com.ss.widget.blue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BoundaryTextView extends AppCompatTextView{

    private RectF mRect = new RectF();
    private float radious;
//    private Path mPath = new Path();
    private Paint mPaint;

    public BoundaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);

        float width = dip2px(1f);
        radious = 0f;
        mPaint.setStrokeWidth(width);
    }

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Math.abs(mPaint.getStrokeWidth()) < 0.01f) return;

        mPaint.setColor(getTextColors().getDefaultColor());
//        float stroke = mPaint.getStrokeWidth();
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = getWidth();
        mRect.bottom = getHeight();

        canvas.drawRoundRect(mRect, radious, radious, mPaint);
    }

    public void setBoundaryColor(int color){
        mPaint.setColor(color);
        invalidate();
    }

}