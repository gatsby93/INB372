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
	// private static final String Fence ="http://172.19.35.172/locationservice/LocationService.svc/GetFence";

    private double lat, lng;
    private double[] la,ln, radius;
    private Button btn1;
    public String message;
    private Handler handler=new Handler();
    private Runnable runnable;
    private Context context;
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
       la = new double[3];
       ln = new double[3];
       radius = new double[3];
       la[0] = -27.477112;
       ln[0] = 153.028015;
       radius[0] = 200;
       //MAP
       CreateMap();
       System.out.println("Create Map Success");
       //FENCE
       CreateFence(-27.477112,153.028015,9000,map,1);
       System.out.println("Create Fence Success");
       //GPS
       SetGPS();
       System.out.println("Set GPS Success");
       //In or out
       OutFence(0);
       System.out.println("In or Out(1) set Success");
       
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

    	        String msg = GcmBroadcastReceiver.getMessage();
    	        				
    	        if (msg != "DEFAULT STRING")
    	        {
    	        	
    	        	System.out.println(msg);
    	        	map.clear();
    				System.out.println("Map cleared");
    				SetGPS();
 			        System.out.println("reset GPS Success");
    	        	String[] parts = msg.split(",");
    	        	System.out.println("Array size:"+parts.length);
    	        	System.out.println("parts[0]:"+parts[0]);
    	        	System.out.println("parts[3]:"+parts[3]);
    	        	System.out.println("parts[6]:"+parts[6].length()+"fuck");
    	        	System.out.println("parts[9]:"+parts[9]);
    	        	if (parts[0].length() != 0){
    	        		Double d0 = Double.parseDouble(parts[0].replaceAll("\"", ""));
    					Double d1 = Double.parseDouble(parts[1].replaceAll("\"", ""));
    					Double d2 = Double.parseDouble(parts[2].replaceAll("\"", ""));
    					System.out.println("fence1 lat:"+d0);
        	        	System.out.println("fence1 lng:"+d1);
        	        	System.out.println("fence1 radius:"+d2);
        	        	CreateFence(d0,d1,d2,map,1);
        	        	System.out.println("Fence1 created");
        	        	la[0]=d0;
        	        	ln[0]=d1;
        	        	radius[0]=d2;
        	        	System.out.println("Input Lat, Lng and radius:"+la[0]+ln[0]+radius[0]);
        	        	//In or out
        	            OutFence(0);
        	            System.out.println("In or Out(1) reset Success");
        	        	if (parts[3].length() != 0 ){
        	        		Double d3 = Double.parseDouble(parts[3].replaceAll("\"", ""));
        	        		Double d4 = Double.parseDouble(parts[4].replaceAll("\"", ""));
        	        		Double d5 = Double.parseDouble(parts[5].replaceAll("\"", ""));
        	        		System.out.println("fence2 lat:"+d3);
        	        		System.out.println("fence2 lng:"+d4);
        	        		System.out.println("fence2 radius:"+d5);
        	        		CreateFence(d3,d4,d5,map,2);
        	        		System.out.println("Fence2 created");
        	        		la[1]=d3;
            	        	ln[1]=d4;
            	        	radius[1]=d5;
            	        	System.out.println("Input Lat, Lng and radius:"+la[1]+ln[1]+radius[1]);
            	        	//In or out
            	            OutFence(1);
            	            System.out.println("In or Out(2) reset Success");
        	        		if (parts[6].length() != 0 ){
        	        			Double d6 = Double.parseDouble(parts[6].replaceAll("\"", ""));
        	        			Double d7 = Double.parseDouble(parts[7].replaceAll("\"", ""));
        	        			Double d8 = Double.parseDouble(parts[8].replaceAll("\"", ""));
        	        			System.out.println("fence3 lat:"+d6);
        	        			System.out.println("fence3 lng:"+d7);
        	        			System.out.println("fence3 radius:"+d8);
        	        			CreateFence(d6,d7,d8,map,3);
        	        			System.out.println("Fence3 created");
        	        			la[2]=d6;
                	        	ln[2]=d7;
                	        	radius[2]=d8;
                	        	System.out.println("Input Lat, Lng and radius:"+la[2]+ln[2]+radius[2]);
                	        	//In or out
                	            OutFence(2);
                	            System.out.println("In or Out(3) reset Success");
        	        		}else {System.out.println("Invaild value of fence3");}
        	        	}else{System.out.println("Invail value of fence2");}
    	        	}else{System.out.println("Invaild value of fence1");}
    	        	System.out.println("reset Fence Success");
//    				Double d6= Double.parseDouble(parts[6].replaceAll("\"", ""));
//    				Double d7 = Double.parseDouble(parts[7].replaceAll("\"", ""));
//    				Double d8 = Double.parseDouble(parts[8].replaceAll("\"", ""));
//    				if (parts[4] == "Fence")
//    				{
//    					Toast.makeText(context, "Your Fence is changed!!",  Toast.LENGTH_LONG).show();
//    				}else if(parts[4] == "Medical")
//    				{
//    					Toast.makeText(context, "You receive a medical alert",  Toast.LENGTH_LONG).show();
//    				}
    				
    				//CreateFence(d0,d1,d2,map,1);
    				
    				//CreateFence(d3,d4,d5,map,2);
    				
    				//CreateFence(d6,d7,d8,map);
    			       
    			       //GPS
    			       
    			       //In or out
    			       //OutFence();
    			       //System.out.println("Out Fence Success");
    			       //System.out.println(msg);
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
        
        Marker nkut = map.addMarker(new MarkerOptions().position(NKUT).title("Fence").snippet("NO.1"));
        // Move the camera instantly to NKUT with a zoom of 16.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
        
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void CreateFence(double lat, double lng, double r, GoogleMap m, int reference){
    	LatLng centre = new LatLng(lat,lng);
    	m.addCircle(new CircleOptions().
    			center(centre).
    			radius (r).
    			fillColor(Color.argb(182, 255, 255, 153)).
    			strokeWidth(1).
    			strokeColor(Color.rgb(255, 255, 153)));
    	if(reference == 1 )
    	{
    		LatLng Fence1 = new LatLng(lat,lng);
    		Marker fence1 = map.addMarker(new MarkerOptions().position(Fence1).title("Fence").snippet("NO.1"));
    	}else if(reference == 2)
    	{
    		LatLng Fence2 = new LatLng(lat,lng);
    		Marker fence2 = map.addMarker(new MarkerOptions().position(Fence2).title("Fence").snippet("NO.2"));
    	}else if(reference == 3)
    	{
    		LatLng Fence3 = new LatLng(lat,lng);
    		Marker fence3 = map.addMarker(new MarkerOptions().position(Fence3).title("Fence").snippet("NO.3"));
    	}
    	
    }
    public boolean InOrOut(double lat1, double lng1,double r){
    	double distance = GetDistance( lat1, lng1,lat,lng);
    	System.out.println(distance);
    	return distance < r;
    }
    public void OutFence(int i){
		System.out.println(InOrOut(la[i],ln[i],radius[i]));
    	if (!InOrOut(la[i],ln[i],radius[i])){
    		POST p = new POST("Help");
    		p.Post(i+4);
    		Toast.makeText(this, "You OUT of the fence"+(i+1)+" now!!!!",Toast.LENGTH_LONG).show();
    	}
    	else{
    		Toast.makeText(this, "You IN the fence"+(i+1)+" now!!!!",Toast.LENGTH_LONG).show();
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
