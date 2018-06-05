package com.ss.widget.blue;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ss.aris.open.widget.IResource;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class WidgetBlueLayout extends RelativeLayout {

    private RelativeLayout date_background;

    private ImageView date_arc;

    private TextView date_month;

    private TextView date_day;

    private LinearLayout LinearLayout9000000000000000002;

    private com.ss.widget.blue.BoundaryTextView time;

    private TextView date_weekday;

    private RelativeLayout grid_background;

    private TextView grid_percent;

    private TextView grid_status;

    private TextView power;

    private RelativeLayout RelativeLayout9000000000000000003;

    private ImageView more_background_1;

    private ImageView more_background_2;

    private ImageView more_background_3;

    public WidgetBlueLayout(Context context, IResource res) {
        super(context, null);
        init(res);
    }

    private int dip2px(float dpValue, Context context) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init(IResource res) {
        RelativeLayout.LayoutParams RelativeLayout9000000000000000001LayoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT,dip2px(184f, getContext()));
        this.setLayoutParams(RelativeLayout9000000000000000001LayoutParams);

        date_background = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams date_backgroundLayoutParams = new RelativeLayout.LayoutParams(dip2px(170f, getContext()),dip2px(170f, getContext()));
        date_background.setBackgroundDrawable(res.getDrawable("blue_date_background.png"));
        date_backgroundLayoutParams.setMargins(dip2px(20f, getContext()),dip2px(14f, getContext()),0,0);
        this.addView(date_background, date_backgroundLayoutParams);

        date_arc = new ImageView(getContext());
        RelativeLayout.LayoutParams date_arcLayoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        date_arc.setImageDrawable(res.getDrawable("blue_date_arc.png"));
        date_background.addView(date_arc, date_arcLayoutParams);

        date_month = new TextView(getContext());
        RelativeLayout.LayoutParams date_monthLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        date_monthLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        date_month.setText("APRIL");
        date_month.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        date_month.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        date_monthLayoutParams.setMargins(0,dip2px(35f, getContext()),0,0);
        date_background.addView(date_month, date_monthLayoutParams);

        date_day = new TextView(getContext());
        RelativeLayout.LayoutParams date_dayLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        date_dayLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        date_day.setText("24");
        date_day.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        date_day.setTextSize(TypedValue.COMPLEX_UNIT_DIP,46);
        date_dayLayoutParams.setMargins(0,dip2px(46f, getContext()),0,0);
        date_background.addView(date_day, date_dayLayoutParams);

        LinearLayout9000000000000000002 = new LinearLayout(getContext());
        RelativeLayout.LayoutParams LinearLayout9000000000000000002LayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        LinearLayout9000000000000000002.setOrientation(LinearLayout.VERTICAL);
        LinearLayout9000000000000000002LayoutParams.setMargins(dip2px(120f, getContext()),dip2px(14f, getContext()),0,0);
        this.addView(LinearLayout9000000000000000002, LinearLayout9000000000000000002LayoutParams);

        time = new com.ss.widget.blue.BoundaryTextView(getContext(), null);
        LinearLayout.LayoutParams timeLayoutParams = new LinearLayout.LayoutParams(
                dip2px(120f, getContext()), dip2px(23f, getContext()));
        time.setGravity(Gravity.RIGHT);
        time.setText("13: 17: 12");
        time.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        time.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
        LinearLayout9000000000000000002.addView(time, timeLayoutParams);

        date_weekday = new TextView(getContext());
        LinearLayout.LayoutParams date_weekdayLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        date_weekdayLayoutParams.gravity = Gravity.RIGHT;
        date_weekday.setText("Tuesday");
        date_weekday.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        date_weekday.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
        LinearLayout9000000000000000002.addView(date_weekday, date_weekdayLayoutParams);

        grid_background = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams grid_backgroundLayoutParams = new RelativeLayout.LayoutParams(dip2px(120f, getContext()),dip2px(120f, getContext()));
        grid_background.setBackgroundDrawable(res.getDrawable("blue_bcg_grid"));
        grid_backgroundLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        grid_backgroundLayoutParams.setMargins(dip2px(190f, getContext()),0,0,0);
        this.addView(grid_background, grid_backgroundLayoutParams);

        grid_percent = new TextView(getContext());
        RelativeLayout.LayoutParams grid_percentLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        grid_percentLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        grid_percent.setText("14%");
        grid_percent.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        grid_percent.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
        grid_percentLayoutParams.setMargins(0,dip2px(45f, getContext()),0,0);
        grid_background.addView(grid_percent, grid_percentLayoutParams);

        grid_status = new TextView(getContext());
        RelativeLayout.LayoutParams grid_statusLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        grid_statusLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        grid_status.setText("CHARGING");
        grid_status.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        grid_status.setTextSize(TypedValue.COMPLEX_UNIT_DIP,11);
        grid_statusLayoutParams.setMargins(0,dip2px(65f, getContext()),0,0);
        grid_background.addView(grid_status, grid_statusLayoutParams);

        power = new TextView(getContext());
        RelativeLayout.LayoutParams powerLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        power.setText("POWER");
        power.setTextColor(android.graphics.Color.parseColor("#04FCF2"));
        power.setTextSize(TypedValue.COMPLEX_UNIT_DIP,11);
        powerLayoutParams.setMargins(dip2px(80f, getContext()),dip2px(20f, getContext()),0,0);
        grid_background.addView(power, powerLayoutParams);

        RelativeLayout9000000000000000003 = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams RelativeLayout9000000000000000003LayoutParams = new RelativeLayout.LayoutParams(dip2px(110f, getContext()),dip2px(110f, getContext()));
        RelativeLayout9000000000000000003LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        RelativeLayout9000000000000000003LayoutParams.setMargins(dip2px(6f, getContext()),dip2px(6f, getContext()),dip2px(6f, getContext()),dip2px(6f, getContext()));
        this.addView(RelativeLayout9000000000000000003, RelativeLayout9000000000000000003LayoutParams);

        more_background_1 = new ImageView(getContext());
        RelativeLayout.LayoutParams more_background_1LayoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        more_background_1.setImageDrawable(res.getDrawable("blue_more_1.png"));
        RelativeLayout9000000000000000003.addView(more_background_1, more_background_1LayoutParams);

        more_background_2 = new ImageView(getContext());
        RelativeLayout.LayoutParams more_background_2LayoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        more_background_2.setImageDrawable(res.getDrawable("blue_more_2.png"));
        RelativeLayout9000000000000000003.addView(more_background_2, more_background_2LayoutParams);

        more_background_3 = new ImageView(getContext());
        RelativeLayout.LayoutParams more_background_3LayoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        more_background_3.setImageDrawable(res.getDrawable("blue_more_3.png"));
        RelativeLayout9000000000000000003.addView(more_background_3, more_background_3LayoutParams);

    }

    public RelativeLayout getDate_background() {
        return date_background;
    }

    public ImageView getDate_arc() {
        return date_arc;
    }

    public TextView getDate_month() {
        return date_month;
    }

    public TextView getDate_day() {
        return date_day;
    }

    public LinearLayout getLinearlayout9000000000000000002() {
        return LinearLayout9000000000000000002;
    }

    public com.ss.widget.blue.BoundaryTextView getTime() {
        return time;
    }

    public TextView getDate_weekday() {
        return date_weekday;
    }

    public RelativeLayout getGrid_background() {
        return grid_background;
    }

    public TextView getGrid_percent() {
        return grid_percent;
    }

    public TextView getGrid_status() {
        return grid_status;
    }

    public TextView getPower() {
        return power;
    }

    public RelativeLayout getRelativelayout9000000000000000003() {
        return RelativeLayout9000000000000000003;
    }

    public ImageView getMore_background_1() {
        return more_background_1;
    }

    public ImageView getMore_background_2() {
        return more_background_2;
    }

    public ImageView getMore_background_3() {
        return more_background_3;
    }
    
}
