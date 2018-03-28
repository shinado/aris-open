package com.ss.aris.open.pipes;

public class PConstants {

    public static final int ID_CONFIG = 10;
    public static final int ID_CLIPBOARD = 11;
    public static final int ID_CLEAR = 14;
    public static final int ID_RESTART = 16;
    public static final int ID_HELP = 17;
    public static final int ID_MANAGER = 18;
    public static final int ID_STORE = 19;
    public static final int ID_DISABLE = 20;
    public static final int ID_ENABLE = 21;
    public static final int ID_DIRECTORY = 22;
    public static final int ID_CONTACT_ARIS = 23;
    public static final int ID_WIDGETS = 24;


    public static final int ID_TEXT = 3;
    public static final int ID_APPLICATION = 2;
    public static final int ID_CONTACT = 1;
    public static final int ID_MAX_DEFAULT = 100;

    public static boolean isGuidance(int id){
        return id >=30 && id < 40;
    }

}
