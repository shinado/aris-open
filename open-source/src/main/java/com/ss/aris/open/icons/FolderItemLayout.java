package com.ss.aris.open.icons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FolderItemLayout extends LinearLayout{

    public FolderItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView getIconView(){
        return (ImageView) findViewWithTag("icon");
    }

    public TextView getLabelView(){
        ViewGroup parent = (ViewGroup) getParent();
        return (TextView) parent.findViewWithTag("label");
    }
}
