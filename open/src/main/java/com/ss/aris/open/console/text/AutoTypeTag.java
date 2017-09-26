package com.ss.aris.open.console.text;


public class AutoTypeTag {

    public OnTypingFinishCallback callback;
    public TypingOption option;
    public OnTextClickListener listener;

    public AutoTypeTag(OnTypingFinishCallback callback, TypingOption option, OnTextClickListener listener) {
        this.callback = callback;
        this.option = option;
        this.listener = listener;
    }
}
