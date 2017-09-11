package pipes.weather.yweathergetter4a;

import android.content.Context;

public interface IGeoCoder {
    public String provide(Context context, double lat, double lng);
}
