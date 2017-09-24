package indi.ss.pipes.weather.yweathergetter4a.china;


public class HeWeatherUtils {

    public static final int SUNNY = 0;
    public static final int CLOUDY = 1;
    public static final int RAINNY = 2;
    public static final int HEAVY_RAIN = 3;

    public static int getWeahter(int weatherId) {
        if (weatherId == 100 || weatherId == 103)
            return SUNNY;
        else if (weatherId == 101 || weatherId == 102 || weatherId == 104)
            return CLOUDY;
        else if (weatherId >= 300 && weatherId < 400) {
            if (weatherId == 305 || weatherId == 306 || weatherId == 309)
                return RAINNY;
            else return HEAVY_RAIN;
        } else {
            return CLOUDY;
        }
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
