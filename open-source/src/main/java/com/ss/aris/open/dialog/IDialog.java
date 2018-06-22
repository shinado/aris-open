package com.ss.aris.open.dialog;

import android.content.Context;

public interface IDialog {

    void show(Context context, int titleId, int contentId, OnClickListener listener);
    void show(Context context, String title, String msg, String yes, OnClickListener yl, String no, OnClickListener nl);

    interface OnClickListener{
        void onClick();
    }

}
