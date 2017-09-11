package pipes.weather.yweathergetter4a.china;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import indi.ss.pipes.weather.yweathergetter4a.IGeoCoder;

public class TencentGeoCoder implements IGeoCoder {

    @Override
    public String provide(Context context, double lat, double lng) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://apis.map.qq.com/ws/geocoder/v1/?" +
                "key=IOTBZ-HKLW6-CYVSZ-MODV2-B4E4K-QEBP6&location=" +
                lat+","+lng);

        try {
            HttpResponse response = httpclient.execute(get);
            String json = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(json);
            JSONObject address = obj.getJSONObject("result").getJSONObject("address_component");
            return address.getString("city");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
