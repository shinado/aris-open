package indi.ss.pipes.mylocation;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import com.ss.aris.open.console.impl.PermissionCallback;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.pipes.pri.PRI;

public class MyLocationPipe extends SimpleActionPipe{

    public MyLocationPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, final OutputCallback callback) {
        getConsole().requestPermission(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                new PermissionCallback() {
                    @Override
                    public void onPermissionResult(boolean granted, boolean first) {
                        if (granted) {
                            new UserLocationUtils().findUserLocation(context, new UserLocationUtils.LocationResult() {
                                @Override
                                public void gotLocation(Location location) {
                                    if (location != null){
                                        String latlng = location.getLatitude()+","+location.getLongitude();
                                        String url = "https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyBtPy4BVRTEJMTI--cuJwF_N1t2Dkz-cKA&center=" +
                                                latlng + "8&zoom=12&format=png&maptype=roadmap&style=element:geometry%7Ccolor:0x242f3e&style=element:labels.text.fill%7Ccolor:0x746855&style=element:labels.text.stroke%7Ccolor:0x242f3e&style=feature:administrative.locality%7Celement:labels.text.fill%7Ccolor:0xd59563&style=feature:poi%7Celement:labels.text.fill%7Ccolor:0xd59563&style=feature:poi.park%7Celement:geometry%7Ccolor:0x263c3f&style=feature:poi.park%7Celement:labels.text.fill%7Ccolor:0x6b9a76&style=feature:road%7Celement:geometry%7Ccolor:0x38414e&style=feature:road%7Celement:geometry.stroke%7Ccolor:0x212a37&style=feature:road%7Celement:labels.text.fill%7Ccolor:0x9ca5b3&style=feature:road.highway%7Celement:geometry%7Ccolor:0x746855&style=feature:road.highway%7Celement:geometry.stroke%7Ccolor:0x1f2835&style=feature:road.highway%7Celement:labels.text.fill%7Ccolor:0xf3d19c&style=feature:transit%7Celement:geometry%7Ccolor:0x2f3948&style=feature:transit.station%7Celement:labels.text.fill%7Ccolor:0xd59563&style=feature:water%7Celement:geometry%7Ccolor:0x17263c&style=feature:water%7Celement:labels.text.fill%7Ccolor:0x515c6d&style=feature:water%7Celement:labels.text.stroke%7Ccolor:0x17263c&size=480x360";
                                        ShareIntent intent = new ShareIntent(Intent.ACTION_VIEW);
                                        intent.setData("geo:"+latlng);
                                        PRI pri = new PRI("com.aris.image")
                                                .addExecutable(url)
                                                .addAction(intent.toString());
                                        callback.onOutput(pri.toString());
                                    }
                                }
                            });
                        }else {
                            callback.onOutput("Permission denied.");
                        }
                    }
                });
    }

    @Override
    public String getDisplayName() {
        return "myLocation";
    }

}