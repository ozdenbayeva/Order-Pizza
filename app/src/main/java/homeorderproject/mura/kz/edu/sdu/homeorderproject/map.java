package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

    private LatLng Almaty;
    private GoogleMap map;
    private double latitude;
    private double longtitude;
    private Location location;
    private ImageButton gps;
    private Marker marker;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        initialize();




        gps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = location.getLatitude();
                longtitude = location.getLongitude();

                Almaty = new LatLng(latitude,longtitude);

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(Almaty, 15));

                map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

                map.clear();
                marker = map.addMarker(new MarkerOptions().position(Almaty).title("Ваше местоположение"));


            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longtitude = latLng.longitude;
                Almaty = new LatLng(latitude,longtitude);

                map.clear();
                marker = map.addMarker(new MarkerOptions().position(Almaty).title("Новое местоположение"));



            }
        });



    }

    private void initialize(){
        Almaty  = new LatLng(43.2565400, 76.9284800);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Almaty, 15));

        // Zoom
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        gps = (ImageButton) findViewById(R.id.gpsButton);

        ActionBar actionBar = getActionBar();


        if(actionBar != null){
            actionBar.setTitle("Карта");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
