package indi.ss.pipes.weather.yweathergetter4a;


import android.content.Context;
import android.location.Location;

import indi.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherInfoListener;
import indi.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherLog;

public class SeniWeather implements UserLocationUtils.LocationResult{

    private Context mContext;
    private YahooWeatherInfoListener mWeatherInfoResult;

    public void queryYahooWeatherByGPS(final Context context, final YahooWeatherInfoListener result) {
		YahooWeatherLog.d("query yahoo weather by gps");
        if (!NetworkUtils.isConnected(context)) {

        	return;
        }
		mContext = context;
		mWeatherInfoResult = result;
		(new UserLocationUtils()).findUserLocation(context, this);
	}

    @Override
    public void gotLocation(Location location) {
        if (location != null) {

        }
    }
}
