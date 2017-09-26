package com.ss.aris.open.console;

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
     * to run a script
     */
    @TargetVersion(8)
    void runScript(String script);

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

    void replaceCurrentLine(String line);

    /**
     * block user input
     * won't be able to input
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

    @TargetVersion(4)
    String getLastInput();

    /**
     * callback after user press ENTER key
     */
    @TargetVersion(4)
    void waitForSingleLineInput(SingleLineInputCallback inputCallback);

    /**
     * callback after user press ENTER key
     */
    @TargetVersion(4)
    void waitForPasswordInput(SingleLineInputCallback inputCallback);

    /**
     * for single character input
     * call back after any key pressed
     */
    @TargetVersion(4)
    void waitForCharacterInput(CharacterInputCallback inputCallback);

    /**
     * for system key such as BACK, MENU, etc.
     * kinda deprecated
     */
    @Deprecated
    @TargetVersion(4)
    void waitForKeyDown(KeyDownCallback inputCallback);

    /**
     * under this mode, you won't get any result from system
     * However, you will still find your input in the console
     * In another word, search is disabled
     */
    @Deprecated
    @TargetVersion(4)
    void occupyMode();

    @Deprecated
    @TargetVersion(4)
    void quitOccupy();

    /**
     * under this mode, any input will not be received
     * whatever you type will not be displayed
     */
    @TargetVersion(4)
    void blindMode();

    @TargetVersion(4)
    void quitBlind();

    @Deprecated
    @TargetVersion(4)
    void addInputCallback(InputCallback inputCallback);

    @Deprecated
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

    BasePipe.OutputCallback getOutputCallback();

}
