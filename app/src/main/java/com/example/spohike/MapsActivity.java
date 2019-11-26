package com.example.spohike;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Trail> TrailList;
    int currPhotoIndex = -1;
    String lat;
    String lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        HikingAPI hikingAPI = new HikingAPI(this);
        hikingAPI.fetchTrailList(47.666,-117.40235);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void receivedInterestingPhotos(List<Trail> trail) {
        TrailList = trail;
        for(int i = 0; i < TrailList.size(); i++) {
            nextTrail();
        }
    }

    public void nextTrail() {
        if (TrailList != null & TrailList.size() > 0) {
            currPhotoIndex++;
            currPhotoIndex %= TrailList.size();


            Trail trail = TrailList.get(currPhotoIndex);
            lon = trail.getLongitude();
            lat = trail.getLatitude();
            Double latitude = Double.parseDouble(lat);
            Double longitude = Double.parseDouble(lon);
            String name = trail.getName();
            LatLng cool = new LatLng(latitude,longitude);
            mMap.addMarker(new MarkerOptions().position(cool).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cool));


            //titleTextView.setText(interestingPhoto.getTitle());
            //dateTakenTextView.setText(interestingPhoto.getDateTaken());

            // GS: added after class
            //FlickrAPI flickrAPI = new FlickrAPI(this);
            //flickrAPI.fetchPhotoBitmap(interestingPhoto.getPhotoURL());
        }
    }
}
