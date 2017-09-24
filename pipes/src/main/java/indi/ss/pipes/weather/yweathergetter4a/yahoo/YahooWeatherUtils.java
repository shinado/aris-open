package indi.ss.pipes.weather.yweathergetter4a.yahoo;


public class YahooWeatherUtils {

    public static final int SUNNY = 0;
    public static final int CLOUDY = 1;
    public static final int RAINNY = 2;
    public static final int HEAVY_RAIN = 3;

    public static int getWeahter(int code) {
        if (code >= 1 && code <= 4 || (code >= 37 && code <= 40) || code == 45 || code == 47)
            return HEAVY_RAIN;
        if (code >= 5 && code <= 12) return RAINNY;
        if (code >= 26 && code <= 30 || code == 44) return CLOUDY;
        if (code >= 31 && code <= 34 || code == 24) return SUNNY;
        else return CLOUDY;
    }

    //←↑→↓↔↕↖↗↘↙
    public static String getDirection(float dir) {
        if (dir < 23) {
            return "↑";
        } else if (dir < 45 + 23) {
            return "↗";
        } else if (dir < 90 + 23) {
            return "→";
        } else if (dir < 135 + 23) {
            return "↓";
        } else if (dir < 180 + 23) {
            return "↙";
        } else if (dir < 225 + 23) {
            return "←";
        } else if (dir < 315 + 23) {
            return "↖";
        } else {
            return "↑";
        }

    }
}
