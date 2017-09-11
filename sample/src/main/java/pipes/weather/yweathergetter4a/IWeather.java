package pipes.weather.yweathergetter4a;

public interface IWeather {
    int getWeatherCode();
    String getTemperature();
    String getWind();
    String getHumility();
    String getDescription();
    String getWeather();
    String getVisibility();
}
