package pipes.weather;

import android.Manifest;
import com.ss.aris.open.w.impl.DeviceConsole;
import com.ss.aris.open.w.impl.PermissionCallback;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.util.VersionUtils;
import pipes.weather.yweathergetter4a.ArisWeather;
import pipes.weather.yweathergetter4a.IWeather;
import pipes.weather.yweathergetter4a.yahoo.YahooWeatherInfoListener;

public class WeatherPipe extends DefaultInputActionPipe {

    public WeatherPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$weather";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("weather");
    }

    @Override
    public void onParamsEmpty(Pipe rs, final OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            getConsole().input("Loading...");
        }

        ((DeviceConsole) getConsole()).requestPermission(
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                new PermissionCallback() {
                    @Override
                    public void onPermissionResult(boolean granted, boolean first) {
                        if (granted) {
                            ArisWeather weather = ArisWeather.getInstance();
                            weather.setNeedDownloadIcons(false);
                            weather.china(VersionUtils.isChina());
                            weather.queryYahooWeatherByGPS(getContext(),
                                    new YahooWeatherInfoListener() {

                                        boolean get = false;

                                        @Override
                                        public void gotWeatherInfo(IWeather weatherInfo) {
                                            if (weatherInfo != null && !get) {
                                                get = true;
                                                    callback.onOutput(
                                                            new WeatherImageConverter()
                                                                    .getString(weatherInfo));
                                            } else {
                                                callback.onOutput("Unable to get weather information. ");
                                            }
                                        }
                                    });
                        }
                    }
                }
        );
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, final OutputCallback callback) {
        ArisWeather weather = ArisWeather.getInstance();
        weather.setNeedDownloadIcons(false);
        weather.china(true);
        weather.queryYahooWeatherByCity(getContext(),
                new YahooWeatherInfoListener() {

                    boolean get = false;

                    @Override
                    public void gotWeatherInfo(IWeather weatherInfo) {
                        if (weatherInfo != null && !get) {
                            get = true;
//                            if (callback == getConsoleCallback()){
                                callback.onOutput(
                                        new WeatherImageConverter()
                                                .getString(weatherInfo));
//                            }else {
//                                callback.onOutput(JsonUtil.toJson(weatherInfo));
//                            }
                        } else {
                            callback.onOutput("Unable to get weather information. ");
                        }
                    }
                }, input);
    }

}
