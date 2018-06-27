package com.ss.aris.open.pipes;


import com.ss.aris.open.pipes.entity.Pipe;

public interface IPipeManager {

    void searchAction(BasePipe only);
    void reenableSearchAll();
    Pipe getPipeByScript(String script);

}
