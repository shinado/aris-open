package com.ss.aris.open.console;

import android.view.View;

import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.text.OnTextClickListener;
import com.ss.aris.open.pipes.BasePipe;
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
     * to display a string on input area
     * @param string
     */
    void displayInput(String string);

    void replaceCurrentLine(String line);

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
     * clear console
     */
    void clear();

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

    /**
     * under this mode, any input will not be received
     * whatever you type will not be displayed
     */
    @TargetVersion(4)
    void blindMode();

    @TargetVersion(4)
    void quitBlind();

    @TargetVersion(4)
    void addInputCallback(InputCallback inputCallback);

    @TargetVersion(4)
    void removeInputCallback(InputCallback inputCallback);

    @TargetVersion(4)
    void setIndicator(String indicator);

    @TargetVersion(11)
    void setInputType(int inputType);

    @TargetVersion(11)
    int getInputType();

    /**
     * to notify a pipe that's been displayed in the console
     * call it when display content has been changed
     * for instance, when the pipe ANOTE is set as initializing text
     * call it when your note has been modified
     * so the display will correspond to it
     */
    void notify(Pipe pipe);

    /**
     * to notify a pipe by name to refresh
     */
    void notifyByName(String name);

    @TargetVersion(1144)
    void reshowTerminal();

    @TargetVersion(1144)
    void replaceCurrentView(View view);

    BasePipe.OutputCallback getOutputCallback();

}
