package com.ss.aris.open.dialog;

import android.content.Context;

public interface IDialog {

    public void show(Context context, int titleId, int contentId,
                     OnClickListener listener);

    interface OnClickListener{
        void onClick();
    }

}
