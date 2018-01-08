package com.ss.aris.open.console.impl;

import com.ss.aris.open.console.Console;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import java.util.Collection;

public interface LauncherConsole extends Console{

    void onSystemReady();

    void displayResult(Collection<Pipe> pipe, Instruction instruction, int selection);

    void onEnter(Pipe pipe);

    void onSelected(Pipe pipe);

    void onNothing();

}
