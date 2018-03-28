package com.ss.aris.open.pipes.configs;

import android.content.Context;
import android.content.SharedPreferences;

public class Configurations {

    private SharedPreferences sharedPreferences;

    public Configurations(Context context){
        sharedPreferences = context.getSharedPreferences("configs", Context.MODE_PRIVATE);
    }

    public void needHistory(boolean b) {
        sharedPreferences.edit().putBoolean("history", b).apply();
    }

    public boolean needHistory() {
        return sharedPreferences.getBoolean("history", true);
    }

    public boolean needAlias() {
        return sharedPreferences.getBoolean("alias", true);
    }

    public void needAlias(boolean b) {
        sharedPreferences.edit().putBoolean("alias", b).apply();
    }

    public void setDeveloper(boolean b){
        sharedPreferences.edit().putBoolean("isDeveloper", b).apply();
    }

    public boolean isDeveloper(){
        return sharedPreferences.getBoolean("isDeveloper", false);
    }

}
