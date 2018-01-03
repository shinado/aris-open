package com.ss.aris.open.pipes.entity;


import java.util.ArrayList;
import java.util.List;

public class PipeArray {

    public List<Pipe> data;

    public PipeArray() {
        data = new ArrayList<>();
    }

    public void add(Pipe p){
        data.add(p);
    }

}
