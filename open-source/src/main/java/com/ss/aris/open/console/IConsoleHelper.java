package com.ss.aris.open.console;

import com.ss.aris.open.pipes.entity.Pipe;

public interface IConsoleHelper {
    boolean execute(Pipe pipe);
    void selectOnLongPress(Pipe pipe);
}
