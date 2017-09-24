package indi.ss.pipes.weather.yweathergetter4a.yahoo;

import android.content.Context;
import android.net.Uri;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import indi.ss.pipes.weather.yweathergetter4a.IWeather;
import indi.ss.pipes.weather.yweathergetter4a.IWeatherQuery;
import indi.ss.pipes.weather.yweathergetter4a.NetworkUtils;
import indi.ss.pipes.weather.yweathergetter4a.ArisWeather;

import static indi.ss.pipes.weather.yweathergetter4a.ArisWeather.YAHOO_WEATHER_ERROR;

public class YahooWeatherQuery implements IWeatherQuery {

    private static final String YQL_WEATHER_ENDPOINT_AUTHORITY = "query.yahooapis.com";
    private static final String YQL_WEATHER_ENDPOINT_PATH = "/v1/public/yql";

    @Override
    public IWeather getWeather(Context context, String place) {
        String qResult = "";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority(YQL_WEATHER_ENDPOINT_AUTHORITY);
        builder.path(YQL_WEATHER_ENDPOINT_PATH);
        builder.appendQueryParameter("q", "select * from weather.forecast where woeid in" +
                "(select woeid from geo.places(1) where text=\"" +
                place +
                "\")");
        String queryUrl = builder.build().toString();

        HttpClient httpClient = NetworkUtils.createHttpClient();

        HttpGet httpGet = new HttpGet(queryUrl);

        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                Reader in = new InputStreamReader(inputStream);
                BufferedReader bufferedreader = new BufferedReader(in);
                StringBuilder stringBuilder = new StringBuilder();

                String readLine = null;

                while ((readLine = bufferedreader.readLine()) != null) {
                    stringBuilder.append(readLine + "\n");
                }

                qResult = stringBuilder.toString();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        Document doc = convertStringToDocument(qResult);
        final WeatherInfo info = parseWeatherInfo(doc);
        if (info == null) return null;

        return new IWeather() {
            @Override
            public int getWeatherCode() {
                return YahooWeatherUtils.getWeahter(info.getCurrentCode());
            }

            @Override
            public String getTemperature() {
                return info.getCurrentTemp() + "°C";
            }

            @Override
            public String getWind() {
                float dir = -1f;
                try {
                    dir = Float.parseFloat(info.getWindDirection());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return dir < 0 ? "" : YahooWeatherUtils.getDirection(dir) +
                        " " + info.getWindSpeed() + "km/h";
            }

            @Override
            public String getHumility() {
                return info.getAtmosphereHumidity() + "%";
            }

            @Override
            public String getDescription() {
                return info.getDescription();
            }

            @Override
            public String getWeather() {
                return info.getCurrentText();
            }

            @Override
            public String getVisibility() {
                return info.getAtmosphereVisibility() + "km";
            }
        };
    }


    private Document convertStringToDocument(String src) {
        Document dest = null;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser;

        try {
            parser = dbFactory.newDocumentBuilder();
            dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
        } catch (ParserConfigurationException e) {
            YahooWeatherLog.printStack(e);
        } catch (SAXException e) {
            YahooWeatherLog.printStack(e);
        } catch (IOException e) {
            YahooWeatherLog.printStack(e);
        }

        return dest;
    }

    private WeatherInfo parseWeatherInfo(Document doc) {
        WeatherInfo weatherInfo = new WeatherInfo();
        try {

            Node titleNode = doc.getElementsByTagName("title").item(0);

            if (titleNode.getTextContent().equals(YAHOO_WEATHER_ERROR)) {
                return null;
            }

            weatherInfo.setTitle(titleNode.getTextContent());
            weatherInfo.setDescription(doc.getElementsByTagName("description").item(0).getTextContent());
            weatherInfo.setLanguage(doc.getElementsByTagName("language").item(0).getTextContent());
            weatherInfo.setLastBuildDate(doc.getElementsByTagName("lastBuildDate").item(0).getTextContent());

            Node locationNode = doc.getElementsByTagName("yweather:location").item(0);
            weatherInfo.setLocationCity(locationNode.getAttributes().getNamedItem("city").getNodeValue());
            weatherInfo.setLocationRegion(locationNode.getAttributes().getNamedItem("region").getNodeValue());
            weatherInfo.setLocationCountry(locationNode.getAttributes().getNamedItem("country").getNodeValue());

            Node windNode = doc.getElementsByTagName("yweather:wind").item(0);
            weatherInfo.setWindChill(windNode.getAttributes().getNamedItem("chill").getNodeValue());
            weatherInfo.setWindDirection(windNode.getAttributes().getNamedItem("direction").getNodeValue());
            weatherInfo.setWindSpeed(windNode.getAttributes().getNamedItem("speed").getNodeValue());

            Node atmosphereNode = doc.getElementsByTagName("yweather:atmosphere").item(0);
            weatherInfo.setAtmosphereHumidity(atmosphereNode.getAttributes().getNamedItem("humidity").getNodeValue());
            weatherInfo.setAtmosphereVisibility(atmosphereNode.getAttributes().getNamedItem("visibility").getNodeValue());
            weatherInfo.setAtmospherePressure(atmosphereNode.getAttributes().getNamedItem("pressure").getNodeValue());
            weatherInfo.setAtmosphereRising(atmosphereNode.getAttributes().getNamedItem("rising").getNodeValue());

            Node astronomyNode = doc.getElementsByTagName("yweather:astronomy").item(0);
            weatherInfo.setAstronomySunrise(astronomyNode.getAttributes().getNamedItem("sunrise").getNodeValue());
            weatherInfo.setAstronomySunset(astronomyNode.getAttributes().getNamedItem("sunset").getNodeValue());

            weatherInfo.setConditionTitle(doc.getElementsByTagName("title").item(2).getTextContent());
            weatherInfo.setConditionLat(doc.getElementsByTagName("geo:lat").item(0).getTextContent());
            weatherInfo.setConditionLon(doc.getElementsByTagName("geo:long").item(0).getTextContent());

            Node currentConditionNode = doc.getElementsByTagName("yweather:condition").item(0);
            weatherInfo.setCurrentCode(
                    Integer.parseInt(
                            currentConditionNode.getAttributes().getNamedItem("code").getNodeValue()
                    ));
            weatherInfo.setCurrentText(
                    currentConditionNode.getAttributes().getNamedItem("text").getNodeValue());
            int curTempF = Integer.parseInt(currentConditionNode.getAttributes().getNamedItem("temp").getNodeValue());
            int curTempC = ArisWeather.turnFtoC(curTempF);
            weatherInfo.setCurrentTemp(curTempC);
            weatherInfo.setCurrentConditionDate(
                    currentConditionNode.getAttributes().getNamedItem("date").getNodeValue());

//            for (int i = 0; i < FORECAST_INFO_MAX_SIZE; i++) {
//                this.parseForecastInfo(weatherInfo.getForecastInfoList().get(i), doc, i);
//            }

        } catch (NullPointerException e) {
            YahooWeatherLog.printStack(e);
            weatherInfo = null;
        }

        return weatherInfo;
    }
}
