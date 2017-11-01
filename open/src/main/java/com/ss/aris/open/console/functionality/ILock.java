package com.ss.aris.open.console.functionality;

public interface ILock {

    boolean lock(OnUnlockedListener listener);

    void setPwd();
}