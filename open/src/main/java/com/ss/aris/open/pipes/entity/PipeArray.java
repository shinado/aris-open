package com.ss.aris.open.pipes.entity;

import com.ss.aris.open.pipes.IPipeManager;
import com.ss.aris.open.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import indi.shinado.piping.pipes.impl.manage.ScriptConvertor;

public class PipeArray {

    public List<String> scripts = new ArrayList<>();

    public static PipeArray from(String json) {
        return JsonUtil.fromJson(json, PipeArray.class);
    }

    public PipeArray() {
    }

    public void add(Pipe p) {
        try {
            scripts.add(ScriptConvertor.getScript(p));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

}
