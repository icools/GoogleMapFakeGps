package location.kingwaytek.com.googlemaptest;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    SupportMapFragment mMapFragment ;
    MockLocationProvider mock;
    MyLocationManager mLocationMgr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        findViews();
        setListener();
        syncMap();
        try {
            mock = new MockLocationProvider(LocationManager.NETWORK_PROVIDER, this);
            //mock.pushLocation(25.0367676,121.5427091);
        }catch(java.lang.SecurityException e){
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        //mock.shutdown();
        super.onDestroy();
    }

    private void setListener() {

    }

    void findViews(){
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    void syncMap(){
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        zoomToCurrentPosition();
        mMap.setOnMapClickListener(this);
        mLocationMgr = new MyLocationManager(this, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                addMarker(location.getLatitude(),location.getLongitude());
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
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
    }

    final static int SCROLL_VALUE = 50 ;
    public void onBtnRightClick(View view){
        mMap.moveCamera(CameraUpdateFactory.scrollBy(SCROLL_VALUE,0));
    }

    public void onBtnLeftClick(View view){
        mMap.moveCamera(CameraUpdateFactory.scrollBy(-SCROLL_VALUE,0));
    }

    public void onBtnTopClick(View view){
        mMap.moveCamera(CameraUpdateFactory.scrollBy(0,-SCROLL_VALUE));
    }

    public void onBtnBottomClick(View view){
        mMap.moveCamera(CameraUpdateFactory.scrollBy(0,SCROLL_VALUE));
    }

    void addMarker(double lat,double lon){
        LatLng latlng = new LatLng(lat,lon);
        addMarker(latlng);
    }

    void addMarker(LatLng latLng){
        mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    void zoomToCurrentPosition(){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(25.0367676f,121.5427091f)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
    }
}