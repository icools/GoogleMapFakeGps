package location.kingwaytek.com.googlemaptest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyLocationManager implements LocationListener {
    private static LocationManager mLocMgr;
    static Location mLocation ;
    static LocationListener mLocationListener ;

    public MyLocationManager(Context context,LocationListener locationListener){
        mLocationListener = locationListener;
        mLocMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                100 ,
                0,
                this,null);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location ;
        mLocationListener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
