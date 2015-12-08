package net.alexandroid.googlemapstests;

import android.graphics.Color;
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
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, OnStreetViewPanoramaReadyCallback {

    private GoogleMap mGoogleMap;
    private StreetViewPanorama mStreetViewPanorama;
    private boolean isMapReady;
    private View fragmentWithMap, fragmentWithStreetView;
    private boolean isMapShown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setViewsAndListeners();

        fragmentWithMap = findViewById(R.id.map);
        fragmentWithStreetView = findViewById(R.id.streetviewpanorama);
        //fragmentWithStreetView.setVisibility(View.GONE);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        StreetViewPanoramaFragment streetViewFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewFragment.getStreetViewPanoramaAsync(this);
    }

    private void setViewsAndListeners() {
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
        findViewById(R.id.btnHome).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnWork).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnParents).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnShowStreetView).setOnClickListener(mOnClickListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        isMapReady = true;
        LatLng homeLocation = new LatLng(32.0236516, 34.7575057);
        // Zoom 0-21
        // Bearing 0 north
        // Tilt 30-67.5 depending on zoom
        CameraPosition cameraPosition = CameraPosition.builder().target(homeLocation).zoom(10).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        addMarkers();
        addPolyLine();
        addCircle();
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        mStreetViewPanorama = streetViewPanorama;
        mStreetViewPanorama.setPosition(new LatLng(32.024331, 34.759168));
        StreetViewPanoramaCamera streetViewPanoramaCamera = new StreetViewPanoramaCamera.Builder()
                .bearing(180)
                .build();
        mStreetViewPanorama.animateTo(streetViewPanoramaCamera, 1000);

/*        mStreetViewPanorama.isStreetNamesEnabled();
        mStreetViewPanorama.setStreetNamesEnabled(true);
        mStreetViewPanorama.isZoomGesturesEnabled();
        mStreetViewPanorama.setZoomGesturesEnabled(true);
        mStreetViewPanorama.isUserNavigationEnabled();
        mStreetViewPanorama.setUserNavigationEnabled(true);*/
    }

    private void addCircle() {
        LatLng center = new LatLng(32.024286, 34.759269);
        CircleOptions circleOptions = new CircleOptions()
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(64, 0, 255, 0))
                .strokeWidth(5)
                .center(center)
                .radius(200);
        mGoogleMap.addCircle(circleOptions);
    }

    private void addPolyLine() {
        LatLng a = new LatLng(32.061232, 34.782048);
        LatLng b = new LatLng(32.062000, 34.782619);
        LatLng c = new LatLng(32.061564, 34.783977);
        LatLng d = new LatLng(32.060444, 34.783391);

        PolylineOptions polylineOptions = new PolylineOptions().geodesic(true)
                .add(a).add(b).add(c).add(d).add(a);
        mGoogleMap.addPolyline(polylineOptions);
    }

    private void addMarkers() {
        MarkerOptions homeMarker = new MarkerOptions()
                .position(new LatLng(32.0236516, 34.7575057))
                .title("Home");

        MarkerOptions workMarker = new MarkerOptions()
                .position(new LatLng(32.060678, 34.7824809))
                .title("Work")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_google_signin_btn_icon_light_normal));


        mGoogleMap.addMarker(homeMarker);
        mGoogleMap.addMarker(workMarker);
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
                case R.id.btnHome:
                    moveToOnMap(new LatLng(32.023954, 34.7600323));
                    break;
                case R.id.btnWork:
                    moveToOnMap(new LatLng(32.060678, 34.7824809));
                    break;
                case R.id.btnParents:
                    moveToOnMap(new LatLng(31.9358989, 34.8374434));
                    break;
                case R.id.btnShowStreetView:
                    if (isMapShown) {
                        isMapShown = false;
                       // fragmentWithMap.setVisibility(View.GONE);
                       // fragmentWithStreetView.setVisibility(View.VISIBLE);
                    } else {
                        isMapShown = true;
                        //fragmentWithMap.setVisibility(View.VISIBLE);
                       // fragmentWithStreetView.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private void moveToOnMap(LatLng location) {
        if (isMapShown) {
            CameraPosition newPosition = CameraPosition.builder().target(location).zoom(16).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition), 5000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            mStreetViewPanorama.setPosition(location);
            StreetViewPanoramaCamera streetViewPanoramaCamera = new StreetViewPanoramaCamera.Builder()
                    .bearing(180)
                    .build();
            mStreetViewPanorama.animateTo(streetViewPanoramaCamera, 1000);
        }
    }


}
