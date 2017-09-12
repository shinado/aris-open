/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2014 Zhenghong Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ss.pipes.weather.yweathergetter4a;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import com.ss.pipes.weather.yweathergetter4a.china.HeWeatherQuery;
import com.ss.pipes.weather.yweathergetter4a.china.TencentGeoCoder;
import com.ss.pipes.weather.yweathergetter4a.yahoo.GoogleGeoCoder;
import com.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherExceptionListener;
import com.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherInfoListener;
import com.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherLog;
import com.ss.pipes.weather.yweathergetter4a.yahoo.YahooWeatherQuery;


/**
 * A wrapper for accessing Yahoo weather informations.
 *
 * @author Zhenghong Wang
 */
public class ArisWeather implements UserLocationUtils.LocationResult {

    private static final String YQL_WEATHER_ENDPOINT_AUTHORITY = "query.yahooapis.com";
    private static final String YQL_WEATHER_ENDPOINT_PATH = "/v1/public/yql";

    private static final int CONNECT_TIMEOUT_DEFAULT = 20 * 1000;
    private static final int SOCKET_TIMEOUT_DEFAULT = 20 * 1000;

    public enum SEARCH_MODE {
        GPS,
        PLACE_NAME
    }

    public enum UNIT {
        FAHRENHEIT,
        CELSIUS,
    }

    public static final String YAHOO_WEATHER_ERROR = "Yahoo! Weather - Error";

    public static final int FORECAST_INFO_MAX_SIZE = 5;

    private String mWoeidNumber;
    private YahooWeatherInfoListener mWeatherInfoResult;
    private YahooWeatherExceptionListener mExceptionListener;
    private boolean mNeedDownloadIcons;
    private boolean useChina = false;
    private SEARCH_MODE mSearchMode;

    // Use Metric units by default
    private UNIT mUnit = UNIT.CELSIUS;

    private Context mContext;
    private static ArisWeather mInstance = new ArisWeather();

    public SEARCH_MODE getSearchMode() {
        return mSearchMode;
    }

    public void setSearchMode(SEARCH_MODE searchMode) {
        mSearchMode = searchMode;
    }

    public UNIT getUnit() {
        return mUnit;
    }

    /**
     * Get the ArisWeather instance.
     * Use this to query weather information from Yahoo.
     *
     * @return ArisWeather instance
     */
    public static ArisWeather getInstance() {
        getInstance(CONNECT_TIMEOUT_DEFAULT, SOCKET_TIMEOUT_DEFAULT);
        return mInstance;
    }

    /**
     * Get the ArisWeather instance.
     * Use this to query weather information from Yahoo.
     *
     * @param connectTimeout in milliseconds, 5 seconds in default
     * @param socketTimeout  in milliseconds, 5 seconds in default
     * @return ArisWeather instance
     */
    public static ArisWeather getInstance(int connectTimeout, int socketTimeout) {
        return getInstance(connectTimeout, socketTimeout, true);
    }

    /**
     * Get the ArisWeather instance.
     * Use this to query weather information from Yahoo.
     *
     * @param connectTimeout in milliseconds, 5 seconds in default
     * @param socketTimeout  in milliseconds, 5 seconds in default
     * @param isDebuggable   set if you want some debug log in Logcat
     * @return ArisWeather instance
     */
    public static ArisWeather getInstance(int connectTimeout, int socketTimeout, boolean isDebuggable) {
        YahooWeatherLog.setDebuggable(isDebuggable);
        NetworkUtils.getInstance().setConnectTimeout(connectTimeout);
        NetworkUtils.getInstance().setSocketTimeout(socketTimeout);
        return mInstance;
    }

    /**
     * Set it to true will enable downloading the default weather icons.
     * The Default icons are too tiny, so in most cases, you don't need them.
     *
     * @param needDownloadIcons Weather it will enable downloading the default weather icons
     */
    public void setNeedDownloadIcons(final boolean needDownloadIcons) {
        mNeedDownloadIcons = needDownloadIcons;
    }

    public void china(boolean b) {
        useChina = b;
    }

    /**
     * Set exception listener.
     * If this is not set, stack info will be printed in logcat if 'isDebuggable' is set to true.
     * Remember, these methodas may be called on background thread. Therefore, any UI related
     * activities must be post to UI thread, using {@link Handler} or something else.
     *
     * @param exceptionListener
     */
    public void setExceptionListener(final YahooWeatherExceptionListener exceptionListener) {
        this.mExceptionListener = exceptionListener;
    }

    public void queryYahooWeatherByCity(Context context, YahooWeatherInfoListener result, String city) {
        YahooWeatherLog.d("query yahoo weather by gps");
        if (!NetworkUtils.isConnected(context)) {
            if (mExceptionListener != null) mExceptionListener.onFailConnection(
                    new Exception("Network is not avaiable"));
            return;
        }
        mContext = context;
        mWeatherInfoResult = result;
        new WeatherQueryByCityTask().execute(city);
    }

    public void queryYahooWeatherByGPS(final Context context, final YahooWeatherInfoListener result) {
        YahooWeatherLog.d("query yahoo weather by gps");
        if (!NetworkUtils.isConnected(context)) {
            if (mExceptionListener != null) mExceptionListener.onFailConnection(
                    new Exception("Network is not avaiable"));
            return;
        }
        mContext = context;
        mWeatherInfoResult = result;
        (new UserLocationUtils()).findUserLocation(context, this);
    }

    @Override
    public void gotLocation(final Location location) {
        if (location == null) {
            if (mExceptionListener != null) mExceptionListener.onFailFindLocation(
                    new Exception("Location cannot be found"));
            return;
        }
        final Double lat = location.getLatitude();
        final Double lon = location.getLongitude();
        final WeatherQueryByLatLonTask task = new WeatherQueryByLatLonTask();
        task.execute(lat, lon);
    }

    public static int turnFtoC(int tempF) {
        return (int) ((tempF - 32) * 5.0f / 9);
    }

    private class WeatherQueryByCityTask extends AsyncTask<String, Void, IWeather> {

        @Override
        protected IWeather doInBackground(String... params) {
            String address = params[0];
            return useChina ?
                    new HeWeatherQuery().getWeather(mContext, address) :
                    new YahooWeatherQuery().getWeather(mContext, address);
        }

        @Override
        protected void onPostExecute(IWeather result) {
            super.onPostExecute(result);
            mWeatherInfoResult.gotWeatherInfo(result);
            mContext = null;
        }
    }


    private class WeatherQueryByLatLonTask extends AsyncTask<Double, Void, IWeather> {

        @Override
        protected IWeather doInBackground(Double... params) {
            if (params == null || params.length != 2) {
                throw new IllegalArgumentException("Parameter of WeatherQueryByLatLonTask is illegal."
                        + "No Lat Lon exists.");
            }
            final Double lat = params[0];
            final Double lon = params[1];
            // Get city name or place name

            if (mContext != null) {
                String address = useChina ?
                        new TencentGeoCoder().provide(mContext, lat, lon) :
                        new GoogleGeoCoder().provide(mContext, lat, lon);
                return useChina ?
                        new HeWeatherQuery().getWeather(mContext, address) :
                        new YahooWeatherQuery().getWeather(mContext, address);
            }

            return null;
        }

        @Override
        protected void onPostExecute(IWeather result) {
            super.onPostExecute(result);
            mWeatherInfoResult.gotWeatherInfo(result);
            mContext = null;
        }
    }

}
