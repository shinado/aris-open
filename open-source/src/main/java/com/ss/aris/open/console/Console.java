package com.ss.aris.open.console;

import android.annotation.TargetApi;
import android.content.Intent;
import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.impl.PermissionCallback;
import com.ss.aris.open.console.impl.ResultCallback;
import com.ss.aris.open.console.text.OnTextClickListener;
import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.IPipeManager;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.console.text.OnTypingFinishCallback;
import com.ss.aris.open.console.text.TypingOption;

public interface Console {

    /**
     * to TYPE in a string with animation
     */
    void input(String input);

    /**
     * to run the script
     * you probably don't want to use it right now
     */
    @TargetVersion(8)
    void runScript(String script, OnEnterListener onEnterListener);

    @TargetVersion(1143)
    void shareIntentByScript(String script);

    /**
     * to show input method
     */
    @TargetVersion(4)
    void showInputMethod();

    /**
     * to TYPE in a string with animation
     */
    @TargetVersion(4)
    void input(String string, OnTypingFinishCallback callback, TypingOption option, OnTextClickListener listener);

    /**
     * to display a string immediately without animation
     */
    @TargetVersion(4)
    void display(String string);

    /**
     * to display a string with pipe as tag
     * when this string is clicked, the pipe will be executed
     */
    @TargetVersion(1106)
    void display(String string, Pipe tag);

    /**
     * block user input
     * users won't be able to input
     */
    void blockInput();

    /**
     * release user input
     */
    void releaseInput();

    /**
     * to force any text to display, skipping animation
     */
    @TargetVersion(4)
    void intercept();

    @TargetVersion(1142)
    String getLastInput(int index);

    /**
     * callback after user press ENTER key
     */
    @TargetVersion(4)
    void waitForSingleLineInput(SingleLineInputCallback inputCallback, boolean requireClipboard);

    /**
     * callback after user press ENTER key
     */
    @TargetVersion(4)
    void waitForPasswordInput(SingleLineInputCallback inputCallback, boolean requireClipboard);

    /**
     * for single character input
     * call back after any key pressed
     */
    @TargetVersion(4)
    void waitForCharacterInput(CharacterInputCallback inputCallback);

    @TargetVersion(4)
    void setIndicator(String indicator);

    @TargetApi(5)
    void requestPermission(String[] permissions, PermissionCallback callback);

    @TargetApi(1188)
    void requestResult(Intent intent, ResultCallback callback);

    BasePipe.OutputCallback getOutputCallback();

    IPipeManager getPipeManager();

}
