package com.ss.aris.open.console.functionality;

import android.appwidget.AppWidgetProviderInfo;
import android.view.View;

public interface IAppWidget {

    void addWidget(int id, AppWidgetProviderInfo info);
    void addWidget(View view);
    void pickWidget();
    void clearWidgets();

}
