package indi.ss.pipes.weather;


import indi.ss.pipes.weather.yweathergetter4a.IWeather;

/**
 *    \  /       局部多云
    _ /"".-.     32-35 °C
      \_(   ).   ↖ 19 km/h
      /(___(__)  10 km
                 5.3 mm

      \   /     晴天
       .-.      29-34 °C
    ― (   ) ―   ← 8-10 km/h
       `-’      13 km
      /   \
 */

public class WeatherImageConverter {

    private String[] RAINNY = new String[]{
                    "      .--.      ",
                    "     (    ).    ",
                    "    (_.__)__)   ",
                    "     ,   ,      ",
                    "    ,   ,       ",
    };

    private String[] HEAVY_RAIN = new String[]{
            "      .--.      ",
            "     (    ).    ",
            "    (_.__)__)   ",
            "    , , , , ,   ",
            "   , , , , , ,  ",
    };

    private String[] FULL_CLOUDY = new String[]{
                    "               ",
                    "      .--.     ",
                    "   .-(    ).   ",
                    "  (___.__)__)  ",
                    "               "
    };

    private String[] CLOUDY = new String[]{
                    "    \\  /         ",
                    "  _ /\"\".-.       ",
                    "    \\_(   ).     ",
                    "    /(___(__)    ",
                    "                 "
    };

    private String[] SUNNY = new String[]{
                    "      \\   /     ",
                    "       .-.      ",
                    "    ― (   ) ―   ",
                    "       `-’      ",
                    "      /   \\     "
    };

    private String[][] WEATHER = new String[][]{
            SUNNY, CLOUDY, RAINNY, HEAVY_RAIN
    };

    public String getString(IWeather info) {
        StringBuilder sb = new StringBuilder();
        int w = info.getWeatherCode();
        String weather[] = WEATHER[w];
        sb.append(info.getDescription())
                .append("\n")
                .append(weather[0])
                .append(info.getWeather())
                .append("\n")
                .append(weather[1])
                .append(info.getTemperature())
                .append("°C")
                .append("\n")
                .append(weather[2])
                .append(info.getWind())
                .append("\n")
                .append(weather[3])
                .append(info.getVisibility())
                .append("\n")
                .append(weather[4])
                .append(info.getHumility())
                .append("\n");
        return sb.toString();
    }
}
