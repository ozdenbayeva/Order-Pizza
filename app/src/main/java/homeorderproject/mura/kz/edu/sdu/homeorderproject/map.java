package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Mura on 28.02.2015.
 */
public class map extends Activity{

    static final LatLng Almaty = new LatLng(43.2565400, 76.9284800);
    private GoogleMap map;
    private double latitude;
    private Location location;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        locationManager = (LocationManager) this
                .getSystemService(LOCATION_SERVICE);
        location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude = location.getLatitude();

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Almaty, 50));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        Log.d("asd",map.getMyLocation() + " ");

    }

}
