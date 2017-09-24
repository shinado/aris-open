package indi.ss.pipes.weather.yweathergetter4a.china;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;

import indi.ss.pipes.weather.yweathergetter4a.IWeather;
import indi.ss.pipes.weather.yweathergetter4a.IWeatherQuery;
import indi.ss.pipes.weather.yweathergetter4a.NetworkUtils;

public class HeWeatherQuery implements IWeatherQuery{

    @Override
    public IWeather getWeather(Context context, final String place) {
        HttpClient httpClient = NetworkUtils.createHttpClient();

        try {
            HttpGet httpGet = new HttpGet("https://free-api.heweather.com/v5/weather?city=" +
                    URLEncoder.encode(place, "utf-8") + "&key=8d41ce95f4cc44028e01cff9cefdb020");

            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();
            String response = EntityUtils.toString(httpEntity);
            JSONObject json = new JSONObject(response);
            JSONArray array = json.getJSONArray("HeWeather5");
            if (array.length() > 0){
                JSONObject he = array.getJSONObject(0);
                JSONObject now = he.getJSONObject("now");
                JSONObject con = now.getJSONObject("cond");
                final int code = con.getInt("code");
                final String weather = con.getString("txt");
                final int temp = now.getInt("tmp");
                final int hum = now.getInt("hum");
                JSONObject wind = now.getJSONObject("wind");
                final int dir = wind.getInt("deg");
                final int speed = wind.getInt("spd");
                final int pm25 = he.getJSONObject("aqi").getJSONObject("city").getInt("pm25");

                return new IWeather() {
                    @Override
                    public int getWeatherCode() {
                        return HeWeatherUtils.getWeahter(code);
                    }

                    @Override
                    public String getTemperature() {
                        return temp  + "°C";
                    }

                    @Override
                    public String getWind() {
                        return dir < 0 ? "" : HeWeatherUtils.getDirection(dir) +
                                " " + speed + "km/h";
                    }

                    @Override
                    public String getHumility() {
                        return hum + "%";
                    }

                    @Override
                    public String getDescription() {
                        return "Weather in " + place;
                    }

                    @Override
                    public String getWeather() {
                        return weather;
                    }

                    @Override
                    public String getVisibility() {
                        return "PM2.5: " + pm25;
                    }
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return null;
    }

}
