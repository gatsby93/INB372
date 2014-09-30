package com.example.smartwatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
public class PantientApp extends FragmentActivity implements LocationListener
{

    
	static final LatLng NKUT = new LatLng(-27.477112,153.028015);
	private GoogleMap map;
	
	private static final String TAG = "MyActivity";

    private double lat, lng, la,ln;
    private double radius;
    private Button btn1;
    public String message;
    private Handler handler=new Handler();
    private Runnable runnable;
    private Context context;
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
       la = -27.477112;
       ln = 153.028015;
       radius = 200;
       //MAP
       CreateMap();
       System.out.println("Create Map Success");
       //FENCE
       CreateFence(-27.477112,153.028015,9000,map);
       System.out.println("Create Fence Success");
       //GPS
       SetGPS();
       System.out.println("Set GPS Success");
       //In or out
       OutFence();
       System.out.println("Out Fence Success");
       
       context = getApplicationContext();
        btn1 = (Button) findViewById(R.id.postbtn);
        btn1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// call AsynTask to perform network operation on separate thread
				//lat2 = lat;
				//lng2 = lng;
				//Marker ml = map.addMarker(new MarkerOptions().position(new LatLng(lat2,
           			 //lng2)).title("My location").snippet("current"));
				//Intent intent = new Intent(PantientApp.this, ConfirmLoc.class);
				//startActivity(intent);
				String msg1 = GcmBroadcastReceiver.getMessage();
		        System.out.println(msg1);
		        GcmBroadcastReceiver.clearMessage();
		        String msg2 = GcmBroadcastReceiver.getMessage();
		        System.out.println(msg2);
			}
		});

       TurnONTimer(10);


    }
    private void TurnONTimer(final int second){
    	 runnable=new Runnable(){
    	        @Override
    	        public void run() {
    	        // TODO Auto-generated method stub
    	        //要做的事情
    	        String msg = GcmBroadcastReceiver.getMessage();
    	        
				
    	        if (msg != "DEFAULT STRING")
    	        {
    	        	String[] parts = msg.split(",");
    				Double d = Double.parseDouble(parts[0].replaceAll("\"", ""));
    				Double d1 = Double.parseDouble(parts[1].replaceAll("\"", ""));
    				Double d2 = Double.parseDouble(parts[2].replaceAll("\"", ""));
    				if (parts[4] == "Fence")
    				{
    					Toast.makeText(context, "Your Fence is changed!!",  Toast.LENGTH_LONG).show();
    				}else if(parts[4] == "Medical")
    				{
    					Toast.makeText(context, "You receive a medical alert",  Toast.LENGTH_LONG).show();
    				}
    				map.clear();
    				CreateFence(d,d1,d2,map);
    			       System.out.println("Create Fence Success");
    			       //GPS
    			       SetGPS();
    			       System.out.println("Set GPS Success");
    			       //In or out
    			       OutFence();
    			       System.out.println("Out Fence Success");
    			       System.out.println(msg);
    	    	        GcmBroadcastReceiver.clearMessage();
    				
    	        }
    	        System.out.println(msg);
    	        handler.postDelayed(this, second*1000);
    	        }
    	        };
        handler.postDelayed(runnable, second*1000);//每两秒执行一次runnable.
    }
    private void TurnOFFTimer(){
        handler.removeCallbacks(runnable);
    }
    public void CreateMap(){ 
    	SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mf.getMap();
        //TextView text=(EditText)findViewById(R.id.text_View); 
        //Button but=(Button)findViewById(R.id.button1);
        
        Marker nkut = map.addMarker(new MarkerOptions().position(NKUT).title("QUT").snippet("GP"));
        // Move the camera instantly to NKUT with a zoom of 16.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
        
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void CreateFence(double lat, double lng, double r, GoogleMap m){
    	LatLng centre = new LatLng(lat,lng);
    	m.addCircle(new CircleOptions().
    			center(centre).
    			radius (r).
    			fillColor(Color.argb(182, 255, 255, 153)).
    			strokeWidth(1).
    			strokeColor(Color.rgb(255, 255, 153)));
    	
    }
    public boolean InOrOut(double lat1, double lng1,double r){
    	double distance = GetDistance( lat1, lng1,lat,lng);
    	System.out.println(distance);
    	return distance < r;
    }
    public void OutFence(){
		System.out.println(InOrOut(la,ln,radius));
    	if (!InOrOut(la,ln,radius)){
    		POST p = new POST("Help");
    		p.Post(4);
    		Toast.makeText(this, "You OUT of the fence now!!!!",Toast.LENGTH_LONG).show();
    	}
    	else{
    		Toast.makeText(this, "You IN the fence now!!!!",Toast.LENGTH_LONG).show();
    	}
    }
    public void SetGPS(){
    	 LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         Location loc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
         if (loc != null) {
             lat = loc.getLatitude();
             Log.d(TAG, "latitude: " + lat);
             lng = loc.getLongitude();
             Log.d(TAG, "longitude: " + lng);
         }

         LocationListener locLis = new MyLocationListener();
         locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10,
                         locLis);
    }
    private static double rad(double d)

	{

		return d * Math.PI / 180.0;

	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2)

	{
		final double EARTH_RADIUS = 6378.137*1000;

		double radLat1 = rad(lat1);

		double radLat2 = rad(lat2);

		double a = radLat1 - radLat2;

		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +

		Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));

		s = s * EARTH_RADIUS;

		//s = Math.round(s * 10000) / 10000;

		return s;

	}

  
	public class MyLocationListener implements LocationListener {
        @Override
        
        public void onLocationChanged(Location loc) {
        	 if (loc != null) {
             	CameraUpdate center=
             			 CameraUpdateFactory.newLatLng(new LatLng(lat,
             			 lng));
             			CameraUpdate zoom=CameraUpdateFactory.zoomTo(18);
             			 
             			map.moveCamera(center);
             			map.animateCamera(zoom);
             			
             	        System.out.println(lat);
             	        System.out.println(lng);
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
