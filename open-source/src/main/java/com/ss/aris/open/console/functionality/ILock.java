package com.ss.aris.open.console.functionality;

import android.content.Context;

public interface ILock {

    boolean needLock();
    boolean isLocked();
    @Deprecated
    boolean lock(OnUnlockedListener listener);
    boolean lock(OnUnlockedListener listener, LockedAfterPwdCallback callback);
    void lockOnScreenOff();
    void onScreenOff(Context context);

    interface LockedAfterPwdCallback{
        void onLockedAfterPwdCallback();
    }

}