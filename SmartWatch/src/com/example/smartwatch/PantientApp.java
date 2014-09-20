package com.example.smartwatch;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class PantientApp extends FragmentActivity implements LocationListener
{

	static final LatLng NKUT = new LatLng(-27.477112,153.028015);
	
	private GoogleMap map;
	private Button btn1;
	private static final String TAG = "MyActivity";
	private static final String URL ="http://172.19.31.119/locationservice/LocationService.svc/GetLocation";
    private double lat, lng, lat2,lng2;
	private String patientId = "P5551";
	private pLocation location;
	private float lat1, lng1;
	//private LatLng myl = new LatLng(-27.477112,153.029976);
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
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, locLis);

       
				
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
				Intent intent = new Intent(PantientApp.this, ConfirmLoc.class);
				startActivity(intent);
			}
		});
    }
    
    /*
    public JSONObject POST(String url, pLocation location){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
 
            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("Patient_ID", location.getPatient());
            jsonObject.accumulate("Latitude", location.getLat());
            jsonObject.accumulate("Longitude", location.getLng());
 
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person); 
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json,HTTP.UTF_8);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
            
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        // 11. return result
        JSONObject jsono = null;
        try{
        	jsono = new JSONObject(result);
        	
        }catch (JSONException e)
        {
        	e.printStackTrace();
        }
        return jsono;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
		// TODO 自动生成的方法存根
    	BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
	}
	private class postData extends AsyncTask<Void, Void, JSONObject>{
		ProgressDialog dialog;
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				dialog = new ProgressDialog(PantientApp.this, ProgressDialog.THEME_HOLO_LIGHT);
			}else{
				dialog = new ProgressDialog(PantientApp.this);
			}
			dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}
		@Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result != null){
            	dialog.dismiss();
            }
            else{
            	 Toast.makeText(getBaseContext(), "Succes", Toast.LENGTH_LONG).show();
            }
       }
		@Override
		protected JSONObject doInBackground(Void... params) {
			// TODO 自动生成的方法存根
			return POST(URL,location);
		}
	}
*/
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
