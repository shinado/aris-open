package indi.shinado.piping.launcher;


import indi.shinado.piping.TargetVersion;

@TargetVersion(3)
public interface InputCallback {
    void onInput(String character);
}
