package com.ss.aris.open.w.text;


public class AutoTypeTag {

    public OnTypingFinishCallback callback;
    public TypingOption option;

    public AutoTypeTag(OnTypingFinishCallback callback, TypingOption option) {
        this.callback = callback;
        this.option = option;
    }
}
