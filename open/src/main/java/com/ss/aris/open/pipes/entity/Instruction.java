package com.ss.aris.open.pipes.entity;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the value of key
 * e.g.
 * "tran.ins -ls"  -> ["tran", "ins", ["ls"]]
 * ".txt.play" -> [".txt.", "play", null]
 * "what the -> [null, "what the", null]
 * "what the" -> [null, "what the", null]
 */
public class Instruction {

    public int length;

    public String input;

    public String pre = null;

    public String body = "";

    public String[] params;

    public Map<String, String> getParameterMap(){
        HashMap<String, String> map = new HashMap<>();
        if (params.length > 0){
            for (int i=0; i<params.length/2; i++){
                map.put(params[i*2], i*2+1 >= params.length ? "":params[i*2+1]);
            }
        }
        return map;
    }

    /**
     * return formatted value from user input.
     * e.g.
     * "tran.ins -ls" -> ["tran", "ins", ["ls"]]
     * "maya.txt.play" -> ["maya.txt", "play", null]
     * "maya" -> [null, "maya", null]
     *
     * @param input user input
     * @return formatted value
     */
    public Instruction(String input) {
        this.input = input;

        String right = "";
        if (input.contains("\"")) {
            String split[] = input.split("\"");
            String left = "";
            int i = split.length - 1;
            for (; i >= 0; i--) {
                String s = split[i];
                if (i % 2 == 1) {
                    //ignore
                } else {
                    if (s.contains(Keys.PIPE)) {
                        int indexDot = s.lastIndexOf(Keys.PIPE);
                        right = s.substring(indexDot + Keys.PIPE.length());
                        left = s.substring(0, indexDot);
                        break;
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(split[j]);
            }
            sb.append(left);

            this.pre = sb.toString();
        } else {
            int indexOfDot = input.lastIndexOf(Keys.PIPE);
            if (indexOfDot < 0) {
                right = input;
            } else {
                if (indexOfDot == 0) {
                    this.pre = null;
                } else {
                    this.pre = input.substring(0, indexOfDot);
                }
                right = input.substring(indexOfDot + Keys.PIPE.length(), input.length());
            }
        }

        if (right.contains("\"")) {
            //not allowed, for now
        }else {
            String split[] = right.split(Keys.SPACE);
            if (split.length > 0){
                boolean bodyFound = false;
                List<String> parameters = new ArrayList<>();
                for (int i=0; i<split.length; i++){
                    if (!split[i].isEmpty()){
                        if (!bodyFound){
                            body = split[i];
                            bodyFound = true;
                        }else {
                            parameters.add(split[i]);
                        }
                    }
                }

                this.params = parameters.toArray(new String[parameters.size()]);
            }
        }

        Log.d("Instruction", "pre: " + pre + ", body: " + body);
    }

    //why?
    public boolean isEmpty() {
        return isPreEmpty() && isParamsEmpty();
    }

    public boolean isPreEmpty() {
        return pre == null || pre.isEmpty();
    }

    public boolean isParamsEmpty() {
        return params == null || params.length == 0;
    }

    public boolean isBodyEmpty() {
        return body == null || body.isEmpty();
    }

    public boolean endsWith(String s) {
        return input.endsWith(s);
    }
}
