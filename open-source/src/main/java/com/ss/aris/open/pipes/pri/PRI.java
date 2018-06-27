package com.ss.aris.open.pipes.pri;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class PRI {

    private final String ENCODING = "utf-8";
    public static final String DIVIDER = "://";

    public String head = "";
    public String value = "";

    public PRI(String head, String value) {
        this.head = head;
        this.value = value;
    }

    public PRI(String head) {
        this.head = head;
    }

    public PRI addExecutable(String exe) {
        remove("exe=");
        try {
            value += "exe=" + URLEncoder.encode(exe, ENCODING) + "/";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PRI addId(int id) {
        remove("id=");
        value += "id=" + id + "/";
        return this;
    }

    public void addKeep(String keep) {
        remove("keep=");
        value += "keep=" + keep;
    }

    public PRI addAction(String action) {
        remove("action=");
        try {
            value += "action=" + URLEncoder.encode(action, ENCODING) + "/";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public static PRI parse(String str) {
        int index = str.indexOf(DIVIDER);
        if (index < 0) {
            return null;
        } else {
            index += DIVIDER.length();
            String head = "";
            String value = "";
            try {
                head = str.substring(0, index - DIVIDER.length());
                value = str.substring(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new PRI(head, value);
        }
    }

    private void remove(String key) {
        String[] split = value.split("/");
        for (String base : split) {
            if (base.startsWith(key)) {
                value = value.replaceFirst(base+"/", "");
                return;
            }
        }
    }

    public String getAction() {
        String[] split = value.split("/");
        for (String base : split) {
            if (base.startsWith("action=")) {
                try {
                    return URLDecoder.decode(base.replace("action=", ""), ENCODING);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public int getId() {
        String[] split = value.split("/");
        if (split.length > 0) {
            String base = split[0];
            if (base.startsWith("id=")) {
                try {
                    return Integer.parseInt(base.replace("id=", ""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public String getExecutable() {
        if (value.contains("/")) {
            String[] split = value.split("/");
            for (String base : split) {
                if (base.startsWith("exe=")) {
                    try {
                        return URLDecoder.decode(base.replace("exe=", ""), ENCODING);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return "";
                    }
                }
            }

            //if not founded
//            try {
//                return URLDecoder.decode(split[split.length - 1], "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }

        return value;
    }

    public String getParams() {
        if (value.contains("/")) {
            String[] split = value.split("/");
            for (String base : split) {
                if (base.startsWith("params=")) {
                    try {
                        return URLDecoder.decode(base.replace("params=", ""), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return "";
                    }
                }
            }
        }

        return "";
    }

    public String toString() {
        return head + DIVIDER + value;
    }

}
