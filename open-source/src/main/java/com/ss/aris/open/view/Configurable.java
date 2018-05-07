package com.ss.aris.open.view;

import java.util.ArrayList;

public interface Configurable {
    void onConfigChanged(ConfigItem value);
    ArrayList<ConfigItem> provideConfigurations();
    String provideConfigName();
}
