package indi.shinado.piping.launcher;

import indi.shinado.piping.TargetVersion;

@TargetVersion(4)
public interface SingleLineInputCallback {
    void onUserInput(String userInput);
}
