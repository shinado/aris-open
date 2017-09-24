package indi.ss.pipes.weather.yweathergetter4a.yahoo;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.util.List;

import indi.ss.pipes.weather.yweathergetter4a.IGeoCoder;


public class GoogleGeoCoder implements IGeoCoder {

    @Override
    public String provide(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context);
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			return addressToPlaceName(addresses.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    private String addressToPlaceName(final Address address) {
	    String result = "";
	    if (address.getLocality() != null) {
	        result += address.getLocality();
	        result += " ";
	    }
	    if (address.getAdminArea() != null) {
	        result += address.getAdminArea();
	        result += " ";
	    }
	    if (address.getCountryName() != null) {
	        result += address.getCountryName();
	        result += " ";
	    }
	    return result;
	}
}
