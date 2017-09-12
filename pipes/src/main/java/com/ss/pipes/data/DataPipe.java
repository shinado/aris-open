package com.ss.pipes.data;

import android.content.Context;
import android.net.ConnectivityManager;

import java.lang.reflect.Method;

import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public class DataPipe extends DefaultInputActionPipe{

    public DataPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$data";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("da", "ta");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        roll(callback);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        roll(callback);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        roll(callback);
    }

    private void roll(OutputCallback callback){
        ConnectivityManager mCM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass  = mCM.getClass();
        Boolean isEnable = true;
        try{
            Method method = cmClass.getMethod("getMobileDataEnabled", null);
            isEnable = (Boolean) method.invoke(mCM, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        String output;
        if (isEnable){
            output = "Data traffic is on.";
        }else{
            output = "Data traffic is off.";
        }
        if (callback == getConsoleCallback()){
            if (isEnable){
                output += " Turning off...";
            }else{
                output += " Turning on...";
            }

            Class[] arg  = new Class[1];
            arg[0]   = boolean.class;

            try{
                Method method = cmClass.getMethod("setMobileDataEnabled", arg);
                method.invoke(mCM, !isEnable);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        callback.onOutput(output);
    }

}
