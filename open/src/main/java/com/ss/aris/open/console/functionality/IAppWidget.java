package com.ss.aris.open.console.functionality;

import android.appwidget.AppWidgetProviderInfo;
import android.view.View;

@Deprecated
public interface IAppWidget {

    void addWidget(int id, AppWidgetProviderInfo info);
    void addWidget(View view);
    void removeWidget(View view);
    void pickWidget();
    void clearWidgets();

}
