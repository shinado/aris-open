package com.ss.aris.open.console.text;


public class TypingOption {

    public TypingOption() {
    }

    public TypingOption(int delayOnNewLine, int delayOnFinish) {
        this.delayOnNewLine = delayOnNewLine;
        this.delayOnFinish = delayOnFinish;
    }

    public TypingOption(int delayOnStart, int delayOnNewLine, int delayOnFinish) {
        this.delayOnStart = delayOnStart;
        this.delayOnNewLine = delayOnNewLine;
        this.delayOnFinish = delayOnFinish;
    }

    public int delayOnNewLine = 100;
    public int delayOnFinish = 0;
    public int delayOnStart = 300;

    public int speed = 10;
    public boolean appendString = false;

    public static TypingOption appendString(){
        TypingOption option = new TypingOption();
        option.appendString= true;
        return option;
    }

    public static TypingOption ofDelayOnStart(int delay){
        TypingOption option = new TypingOption();
        option.delayOnStart = delay;
        return option;
    }

}
