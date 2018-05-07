package indi.ss.pipes.superlock;

import android.os.Handler;
import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.console.functionality.ILock;
import com.ss.aris.open.console.functionality.OnUnlockedListener;
import com.ss.aris.open.pipes.action.ExecuteOnlyPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

@TargetVersion(1144)
public class SLockPipe extends ExecuteOnlyPipe {

    private String CODES = "@echo off\n" +
            "title SecurityGuard v1.03\n" +
            "color C\n" +
            "echo SecurityGuard v1.03 enabled!\n" +
            "echo This program has been...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo.\n" +
            "echo     _####_\n" +
            "echo    #      #\n" +
            "echo   #        #\n" +
            "echo   #        #\n" +
            "echo   #        #\n" +
            "echo  ############ \n" +
            "echo #   LOCKED   #\n" +
            "echo #     ##     #\n" +
            "echo #    ####    #\n" +
            "echo #    ####    #\n" +
            "echo #     ##     #\n" +
            "echo #     ##     #\n" +
            "echo ##############\n" +
            "echo.\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo Enter password to activate the program...\n" +
            "set/p \"pass=>\"\n" +
            "if NOT %pass%== fakehack goto :incorrect_pass\n" +
            "if %pass%== fakehack goto :correct_pass</p><p>:correct_pass</p><p>cls\n" +
            "echo Password hidden...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo PASSWORD CORRECT\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "color 97\n" +
            "cls\n" +
            "title svr_hack_var_aggressive.exe\n" +
            "echo Initializing svr_hack_var_aggressive.exe\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "tree /var/system\n" +
            "echo Initializing Complete! Are you ready to continue enable Proxy Servers?\n" +
            "@echo off\n" +
            ":choice\n" +
            "set /P c=Are you sure you want to continue[Y/N]?\n" +
            "if /I \"%c%\" EQU \"Y\" goto :somewhere\n" +
            "if /I \"%c%\" EQU \"N\" goto :somewhere_else\n" +
            "goto :choice</p><p>:somewhere</p><p>cls\n" +
            "echo Proxy Servers enabling...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo Proxy Servers 27%% enabled...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo Proxy Servers 56%% enabled...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo Proxy Servers 89%% enabled...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo Proxy Servers have been enabled. Are you ready to enable software rooting?\n" +
            "@echo off\n" +
            ":choice\n" +
            "set /P c=Are you sure you want to continue[Y/N]?\n" +
            "if /I \"%c%\" EQU \"Y\" goto :somewhere2\n" +
            "if /I \"%c%\" EQU \"N\" goto :somewhere_else2\n" +
            "goto :choice</p><p>:somewhere_else</p><p>echo svr_hack_var_aggressive.exe had been closed, exiting out of cmd...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "exit</p><p>:somewhere2</p><p>echo Collecting files to root... (Warning, this process may take several minutes!)\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "@echo off\n" +
            "tree C:\\\n" +
            "@echo off\n" +
            ":choice\n" +
            "set /P c=Are you sure you want to root all of the selected files?[Y/N]?\n" +
            "if /I \"%c%\" EQU \"Y\" goto :somewhere3\n" +
            "if /I \"%c%\" EQU \"N\" goto :somewhere_else3\n" +
            "goto :choice</p><p>:somewhere_else2</p><p>echo svr_hack_var_aggressive.sh had been closed, exiting out of cmd...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "exit</p><p>:somewhere_else3</p><p>echo svr_hack_var_aggressive.sh had been closed, exiting out of cmd...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "exit</p><p>:somewhere3</p><p>@echo off\n" +
            "cls\n" +
            "echo Rooting files... (Warning this process may take several minutes!)\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo File rooting has been completed.\n" +
            "goto :hardcore</p><p>:incorrect_pass</p><p>cls\n" +
            "echo Password hidden...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo PASSWORD INCORRECT: svr_hack_var_aggressive.exe has been closed, exiting out of cmd...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "exit</p><p>:hardcore</p><p>@echo off\n" +
            "cls\n" +
            "echo Transfering user to passive mode...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "title Passive\n" +
            "color B\n" +
            "echo svr_hack_var_aggressive.exe has been enabled...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo Checking if svr_hack_var_aggressive.exe is running correctly...\n" +
            "ping -n 2 127.0.0.1>nul \n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "cls\n" +
            "echo svr_hack_var_aggressive.exe has initialized \n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo svr_hack_var_aggressive.exe has enabled Proxy servers \n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo svr_hack_var_aggressive.exe has rooted files \n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "cls\n" +
            "echo Removing traces of svr_hack_var_aggressive.exe in files...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "tree /dev/bus\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "cls\n" +
            "echo svr_hack_var_aggressive.exe has been removed...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo cmd will be replaced in 3...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo 2...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "echo 1...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "cls\n" +
            "echo ...\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "ping -n 2 127.0.0.1>nul\n" +
            "cls\n" +
            "start cmd\n" +
            "exit</p>";

    private int index = 0;
    private boolean isRunning = false;
    private boolean isLocked = false;

    public SLockPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "SLOCK";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("slock");
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        lock(input);
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        lock(CODES);
    }

    private void lock(final String input){
        Console console = getConsole();
        if (console instanceof ILock) {
            boolean b = ((ILock) console).lock(new OnUnlockedListener() {
                @Override
                public void onUnlocked() {
                    isLocked = false;
                }
            }, new ILock.LockedAfterPwdCallback() {
                @Override
                public void onLockedAfterPwdCallback() {
                    doLock(input);
                }
            });

            if (b) doLock(input);
        }
    }

    private void doLock(String input){
        isRunning = true;
        isLocked = true;
        startOutput(input);
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    private void startOutput(String input) {
        final Handler handler = new Handler();
        final String[] codes = input.split("\n");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index >= codes.length) index = 0;
                getConsole().input(codes[index++]);

                if (isLocked && isRunning)
                    handler.postDelayed(this, 200);
                else {
                    if (!isLocked) index = 0;
                    if (!isRunning) handler.postDelayed(this, 600);
                }
            }
        }, 200);
    }

}
