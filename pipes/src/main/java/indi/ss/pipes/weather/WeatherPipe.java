package indi.ss.pipes.weather;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.console.impl.PermissionCallback;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.util.VersionUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

import indi.ss.pipes.weather.yweathergetter4a.ArisWeather;
import indi.ss.pipes.weather.yweathergetter4a.IWeather;
import indi.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherInfoListener;

@TargetVersion(1142)
public class WeatherPipe extends DefaultInputActionPipe {

    private SharedPreferences sp;

    public WeatherPipe(int id) {
        super(id);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        sp = context.getSharedPreferences("pipe_weather", Context.MODE_PRIVATE);
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
        String city = sp.getString("city", "");
        if (city.isEmpty()) {
            if (callback == getConsoleCallback()) {
                getConsole().input("Loading...");
            }

            ((DeviceConsole) getConsole()).requestPermission(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
                                                            getCurrentTime() + "\n" +
                                                                    new WeatherImageConverter()
                                                                            .getString(weatherInfo));
                                                    if (callback instanceof DisplayOutputCallback) {
                                                        addNotifyTimeCircle(20 * 60 * 1000);
                                                    }
                                                } else {
                                                    callback.onOutput("Unable to get weather information. ");
                                                }
                                            }
                                        });
                            }
                        }
                    }
            );
        } else {
            if (callback == getConsoleCallback()) {
                getConsole().input("Default city has been set. Loading weather in " + city);
            }

            getWeatherByCity(city, callback);
        }
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
                .format(new Date());
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (previous.get().getId() != PConstants.ID_TEXT){
            TreeSet<Pipe> allPrevious = previous.getAll();
            for (Pipe p: allPrevious){
                if (p.getId() == PConstants.ID_TEXT){
                    getWeatherByCity(p.getExecutable(), callback);
                    return;
                }
            }
        }else {
            getWeatherByCity(input, callback);
        }
    }

    private void getWeatherByCity(String city, final OutputCallback callback){
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
                            callback.onOutput(
                                    getCurrentTime() + "\n" +
                                            new WeatherImageConverter()
                                                    .getString(weatherInfo));
                            if (callback instanceof DisplayOutputCallback) {
                                addNotifyTimeCircle(30 * 60 * 1000);
                            }
                        } else {
                            callback.onOutput("Unable to get weather information. ");
                        }
                    }
                }, city);
    }

    @Override
    public boolean asDisplay() {
        return true;
    }

}
