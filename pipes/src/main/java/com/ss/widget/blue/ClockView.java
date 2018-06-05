package com.ss.widget.blue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View{

    private int angle = 45;
    private Paint paint = new Paint();
    private RectF oval = new RectF();
    private int color = Color.parseColor("#04FCF2");

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = getWidth();
        int y = getHeight();
        int width = Math.min(x, y);
        int radius = (int) (width * 0.95f);
        paint.setStrokeWidth(width * 0.1f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        canvas.drawCircle(x/2, y/2, radius, paint);

        paint.setColor(ColorUtils.setAlphaComponent(color, 0x99));
        paint.setStrokeWidth(width*0.05f);
        canvas.drawCircle(x/2, y/2, width * 0.8f, paint);

        paint.setColor(color);
        paint.setStrokeWidth(width*0.24f);
        float padding = width * 0.8f;
        oval.set(padding, padding, width-padding*2, width-padding*2);
        canvas.drawArc(oval, angle, angle + 90, false, paint);
    }
}
