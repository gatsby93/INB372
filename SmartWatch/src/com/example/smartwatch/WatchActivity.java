package com.example.smartwatch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WatchActivity extends FragmentActivity implements LocationListener
{

	static final LatLng NKUT = new LatLng(-27.477112,153.028015);
	private GoogleMap map;
	
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        
        SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mf.getMap();
        
        Marker nkut = map.addMarker(new MarkerOptions().position(NKUT).title("QUT").snippet("GP"));

        // Move the camera instantly to NKUT with a zoom of 16.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
        
        map.setMyLocationEnabled(true);
        map.setMapType(map.MAP_TYPE_NORMAL);
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自动生成的方法存根
		
	}
}
