package com.ss.aris.open.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.ss.aris.open.console.Console;

public interface IWidget {

    void onCreate(Context context, Console console);

    void onDestroy();

    void onResume();

    void onPause();

    View getView(ViewGroup parent, String value);

}