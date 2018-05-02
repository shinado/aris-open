package com.ss.aris.open.console.functionality;


public interface IConsole {

    int DIR_LEFT = 0;
    int DIR_RIGHT = 1;
    int DIR_TOP = 2;
    int DIR_BOTTOM = 3;

    void setPadding(int p, int dir);
    void setPadding(int p);
    void setMargins(int m);
    void setMargin(int p, int dir);

}
