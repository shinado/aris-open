package com.ss.aris.open.console;

import com.ss.aris.open.TargetVersion;

@TargetVersion(4)
public interface SingleLineInputCallback {
    void onUserInput(String userInput);
}
