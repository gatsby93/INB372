package com.example.smartwatch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class PantientApp extends FragmentActivity implements LocationListener
{

	static final LatLng NKUT = new LatLng(-27.477112,153.028015);
	private GoogleMap map;
	
	private static final String TAG = "MyActivity";

    private double lat, lng;
	
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        
        SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mf.getMap();
        //TextView text=(EditText)findViewById(R.id.text_View); 
        //Button but=(Button)findViewById(R.id.button1);
        
        Marker nkut = map.addMarker(new MarkerOptions().position(NKUT).title("QUT").snippet("GP"));

        // Move the camera instantly to NKUT with a zoom of 16.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
        
        map.setMyLocationEnabled(true);
        map.setMapType(map.MAP_TYPE_NORMAL);
        
        
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc != null) {
            lat = loc.getLatitude();
            Log.d(TAG, "latitude: " + lat);
            lng = loc.getLongitude();
            Log.d(TAG, "longitude: " + lng);
        }
        
        LocationListener locLis = new MyLocationListener();
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10,
                        locLis);

       

        
        /*try to get current long and lat, not completed just skip that...
        but.setOnClickListener(new Button.OnClickListener(){ 
            public void onClick(View v)
            {
                //Toast提示控件
                Toast.makeText(WatchActivity.this,
                        "TextView里的文字发生了改变,你注意到了吗?", 
                        Toast.LENGTH_LONG).show();
                //将TextView的文字发生改变
                text.setText("欢迎来到魏祝林的博客!");
            }
        });
        //int dLong;
		//double dLat;
       // dLong = (int) map.getMyLocation().getLongitude();
       // dLat =  map.getMyLocation().getLatitude();
      //  text.setText(dLong);*/
        
    }

    public class MyLocationListener implements LocationListener {
        @Override
        
        public void onLocationChanged(Location loc) {
            if (loc != null) {
            	CameraUpdate center=
            			 CameraUpdateFactory.newLatLng(new LatLng(lat,
            			 lng));
            			CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
            			 
            			map.moveCamera(center);
            			map.animateCamera(zoom);
            			
            }
        }
     
        @Override
        public void onProviderDisabled(String provider) {
        }
     
        @Override
        public void onProviderEnabled(String provider) {
        }
     
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
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
