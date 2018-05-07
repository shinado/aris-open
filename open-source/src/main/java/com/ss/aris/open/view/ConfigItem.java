package com.ss.aris.open.view;


import java.io.Serializable;

public class ConfigItem implements Serializable{

    public static final int TYPE_NONE = 0;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_BOOLEAN = 2;
    public static final int TYPE_COLOR = 3;
    public static final int TYPE_PROGRESS = 4;
    public static final int TYPE_TITLE = 5;
    public static final int TYPE_SELECTIONS = 6;

    public boolean isEnabled = true;
    public String displayName;
    public String key;
    public String value;
    public String[] selections = null;
    public int type = TYPE_TEXT;

    public ConfigItem(String displayName, String key, String value, int type) {
        this.displayName = displayName;
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public ConfigItem() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ConfigItem &&
                key != null && key.equals(((ConfigItem) obj).key);
    }
}
