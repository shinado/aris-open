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

    private String[] CODES = new String[]{
            "@echo off",
            "title SecurityGuard v1.03",
            "color C",
            "echo SecurityGuard v1.03 enabled!",
            "echo This program has been...",
            "ping -n 2 127.0.0.1>nul",
            "echo.",
            "echo     _####_",
            "echo    #      #",
            "echo   #        #",
            "echo   #        #",
            "echo   #        #",
            "echo  ############ ",
            "echo #   LOCKED   #",
            "echo #     ##     #",
            "echo #    ####    #",
            "echo #    ####    #",
            "echo #     ##     #",
            "echo #     ##     #",
            "echo ##############",
            "echo.",
            "ping -n 2 127.0.0.1>nul",
            "echo Enter password to activate the program...",
            "set/p \"pass=>\"",
            "if NOT %pass%== fakehack goto :incorrect_pass",
            "if %pass%== fakehack goto :correct_pass</p><p>:correct_pass</p><p>cls",
            "echo Password hidden...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "echo PASSWORD CORRECT",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "color 97",
            "cls",
            "title svr_hack_var_aggressive.exe",
            "echo Initializing svr_hack_var_aggressive.exe",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "tree /var/system",
            "echo Initializing Complete! Are you ready to continue enable Proxy Servers?",
            "@echo off",
            ":choice",
            "set /P c=Are you sure you want to continue[Y/N]?",
            "if /I \"%c%\" EQU \"Y\" goto :somewhere",
            "if /I \"%c%\" EQU \"N\" goto :somewhere_else",
            "goto :choice</p><p>:somewhere</p><p>cls",
            "echo Proxy Servers enabling...",
            "ping -n 2 127.0.0.1>nul",
            "echo Proxy Servers 27%% enabled...",
            "ping -n 2 127.0.0.1>nul",
            "echo Proxy Servers 56%% enabled...",
            "ping -n 2 127.0.0.1>nul",
            "echo Proxy Servers 89%% enabled...",
            "ping -n 2 127.0.0.1>nul",
            "echo Proxy Servers have been enabled. Are you ready to enable software rooting?",
            "@echo off",
            ":choice",
            "set /P c=Are you sure you want to continue[Y/N]?",
            "if /I \"%c%\" EQU \"Y\" goto :somewhere2",
            "if /I \"%c%\" EQU \"N\" goto :somewhere_else2",
            "goto :choice</p><p>:somewhere_else</p><p>echo svr_hack_var_aggressive.exe had been closed, exiting out of cmd...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "exit</p><p>:somewhere2</p><p>echo Collecting files to root... (Warning, this process may take several minutes!)",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "@echo off",
            "tree C:\\",
            "@echo off",
            ":choice",
            "set /P c=Are you sure you want to root all of the selected files?[Y/N]?",
            "if /I \"%c%\" EQU \"Y\" goto :somewhere3",
            "if /I \"%c%\" EQU \"N\" goto :somewhere_else3",
            "goto :choice</p><p>:somewhere_else2</p><p>echo svr_hack_var_aggressive.sh had been closed, exiting out of cmd...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "exit</p><p>:somewhere_else3</p><p>echo svr_hack_var_aggressive.sh had been closed, exiting out of cmd...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "exit</p><p>:somewhere3</p><p>@echo off",
            "cls",
            "echo Rooting files... (Warning this process may take several minutes!)",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "echo File rooting has been completed.",
            "goto :hardcore</p><p>:incorrect_pass</p><p>cls",
            "echo Password hidden...",
            "ping -n 2 127.0.0.1>nul",
            "echo PASSWORD INCORRECT: svr_hack_var_aggressive.exe has been closed, exiting out of cmd...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "exit</p><p>:hardcore</p><p>@echo off",
            "cls",
            "echo Transfering user to passive mode...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "title Passive",
            "color B",
            "echo svr_hack_var_aggressive.exe has been enabled...",
            "ping -n 2 127.0.0.1>nul",
            "echo Checking if svr_hack_var_aggressive.exe is running correctly...",
            "ping -n 2 127.0.0.1>nul ",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "cls",
            "echo svr_hack_var_aggressive.exe has initialized ",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "echo svr_hack_var_aggressive.exe has enabled Proxy servers ",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "echo svr_hack_var_aggressive.exe has rooted files ",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "cls",
            "echo Removing traces of svr_hack_var_aggressive.exe in files...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "tree /dev/bus",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "cls",
            "echo svr_hack_var_aggressive.exe has been removed...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "echo cmd will be replaced in 3...",
            "ping -n 2 127.0.0.1>nul",
            "echo 2...",
            "ping -n 2 127.0.0.1>nul",
            "echo 1...",
            "ping -n 2 127.0.0.1>nul",
            "cls",
            "echo ...",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "ping -n 2 127.0.0.1>nul",
            "cls",
            "start cmd",
            "exit</p>"
    };

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
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        Console console = getConsole();
        if (console instanceof ILock) {
            boolean b = ((ILock) console).lock(new OnUnlockedListener() {
                @Override
                public void onUnlocked() {
                    isLocked = false;
                }
            });

            if (b) {
                isRunning = true;
                isLocked = true;
                startOutput();
            }
        }
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
        if (isLocked)
            startOutput();
    }

    private void startOutput() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index >= CODES.length) index = 0;
                getConsole().input(CODES[index++]);

                if (isLocked && isRunning)
                    handler.postDelayed(this, 200);
                else index = 0;
            }
        }, 200);
    }

}
