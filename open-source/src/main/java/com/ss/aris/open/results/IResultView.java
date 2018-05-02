package com.ss.aris.open.results;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.console.IConsoleHelper;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.widget.IResource;
import java.util.Collection;

public interface IResultView {
    void refresh();
    void setResource(IResource resource);
    void setup(Context context, Console console, IConsoleHelper consoleHelper, ViewGroup selections);
    void displayResult(Collection<Pipe> results, int selection);
    void setTypeface(Typeface typeface);
    void clear();
}