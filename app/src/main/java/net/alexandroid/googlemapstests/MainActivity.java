package net.alexandroid.googlemapstests;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private boolean isMapReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btnMap).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnSat).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnMix).setOnClickListener(mOnClickListener);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        isMapReady = true;
        LatLng homeLocation = new LatLng(32.0236516, 34.7575057);
        CameraPosition target = CameraPosition.builder().target(homeLocation).zoom(14).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isMapReady) return;
            switch (v.getId()) {
                case R.id.btnMap:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case R.id.btnSat:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case R.id.btnMix:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
            }
        }
    };
}
