package com.ss.aris.open.pipes.impl;

import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;

import com.ss.aris.open.util.JsonUtil;

public class ShareIntent {

    @Deprecated
    public String target;
    public String type = "";
    public String data = "";
    public String action;
    public int flags;
    public HashMap<String, String> extras = new HashMap<>();

    public ShareIntent(String action) {
        this.action = action;
    }

    public ShareIntent() {
        action = Intent.ACTION_VIEW;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    public static ShareIntent fromIntent(Intent intent) {
        ShareIntent shareIntent = new ShareIntent(intent.getAction());
        return shareIntent;
    }

    public Intent toIntent() {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setType(type);
        if (!data.isEmpty()){
            intent.setData(Uri.parse(data));
        }
        intent.setFlags(flags);

        for (String key : extras.keySet()) {
            Uri uri = Uri.parse(extras.get(key));
//            ArrayList<Uri> arrayList = new ArrayList<>();
//            arrayList.add(uri);
//            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
            intent.putExtra(key, uri);
        }

        return intent;
    }

}
