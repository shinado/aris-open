package com.ss.aris.open.console.impl;

import android.annotation.TargetApi;
import android.content.Intent;

@TargetApi(1188)
public interface ResultCallback {
    void onActivityResult(int resultCode, Intent intent);
}

