package com.ss.aris.open.console.functionality;

import android.content.Context;

public interface ILock {

    boolean needLock();
    boolean isLocked();
    boolean lock(OnUnlockedListener listener);
    void lockOnScreenOff();
    void onScreenOff(Context context);

}