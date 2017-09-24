package indi.ss.pipes.weather.yweathergetter4a.yahoo;

public interface YahooWeatherExceptionListener {
    public void onFailConnection(final Exception e);
    public void onFailParsing(final Exception e);
    public void onFailFindLocation(final Exception e);
}
