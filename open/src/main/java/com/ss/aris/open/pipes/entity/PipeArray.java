package com.ss.aris.open.pipes.entity;


import java.util.ArrayList;
import java.util.List;

import com.ss.aris.open.pipes.entity.Pipe;

public class PipeArray {

    public List<Pipe> data;

    public PipeArray() {
        data = new ArrayList<>();
    }

    public void add(Pipe p){
        data.add(p);
    }

}
