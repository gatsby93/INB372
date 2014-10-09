package com.example.gcmtest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.neel.jsonpost.pLocation;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static String TAG = "LaunchActivity";
    protected String SENDER_ID = "544968562871";
    private GoogleCloudMessaging gcm =null;
    private String regid = null;
    SharedPreferences prefs;
    private Context context= null;
    private static final String URL ="http://172.19.45.151/locationservice/LocationService.svc/Alert";
   
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		
		
		if(checkPlayServices())
		{
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			
			if(regid.isEmpty())
			{
				System.out.println("1");
				registerInBackground();
				System.out.println("2");
			}
			//MAYBE REQUIRED MAYBE NOT? IF NOT NECESSARY PUT OUTER ELSE INTO THIS
			else
			{
				System.out.println("3");
				Log.i(TAG, regid);
				System.out.println(regid);
			}
		}
		else
		{
			System.out.println("4");
			Log.i(TAG, "No valid Google Play Services APK Found");
		}
		
	}

	@Override 
	protected void onResume()
	{
		super.onResume();
		checkPlayServices();
	}
	
	private boolean checkPlayServices()
	{
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(resultCode != ConnectionResult.SUCCESS)
		{
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
			{
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else
			{
				Log.i(TAG, "This device is not supported - Google Play Services");
				finish();
			}
			return false;
		}
		return true;
	}
	
	@SuppressLint("NewApi")
	private String getRegistrationId(Context context)
	{
		final SharedPreferences prefs = getGCMPreferences(context);
		   String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		   if (registrationId.isEmpty()) {
		       Log.i(TAG, "Registration ID not found.");
		       return "";
		   }
		   int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		   int currentVersion = getAppVersion(context);
		   if (registeredVersion != currentVersion) {
		        Log.i(TAG, "App version changed.");
		        return "";
		    }
		    return registrationId;
	}
	
	private SharedPreferences getGCMPreferences(Context context)
	{
		//MAYBE GOT ERROR
		return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context)
	{
		try
		{
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		}
		catch(NameNotFoundException e)
		{
			throw new RuntimeException("Could not get package name" + e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void registerInBackground()
	{
		new AsyncTask()
		{
			/*
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(context, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
			*/			
			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				String msg = "";
				try
				{
					if(gcm == null)
					{
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;
					Log.i(TAG,"########################################");
					Log.i(TAG, "Current Device's Registration ID is: "+msg); 
					System.out.println(msg);
					
					//SENDS REGID TO SERVER
					 sendRegistrationIdToBackend();
					 
					 //STORES REGID
					 storeRegistrationId(context, regid);
				}
				catch(final IOException ex)
				{
					msg = "Error:" + ex.getMessage();
				}
				return null;
			}
			protected void onPostExecute(final Object result)
			{
				//MAYBE NEED TO STORE REGISTRATION ID HERE
				//do something here
				//mDisplay.append(msg + "\n");
			}
		}.execute(null,null,null);
	}

	//IMPLEMENTATION OF SEND REGID
	public void sendRegistrationIdToBackend()
	{
		InputStream inputStream = null;
		String result ="";
		//JSONObject json = data[0];
		DefaultHttpClient hc = new DefaultHttpClient();
		//HttpConnectionParams.setConnectionTimeout(hc.getParams(), 100000);
		
		JSONObject jsonResponse = null;
		HttpPost httpPost = new HttpPost(URL);
		try
		{
 					
			String json="";
			JSONObject  jsonObject = new JSONObject();
			jsonObject.accumulate("REGISTRATION_ID",regid);
			json = jsonObject.toString();
			
			//System.out.println(json);
			StringEntity se;
			se = new StringEntity(json);
			httpPost.setEntity(se);
			
			httpPost.setHeader("Accept","application/json");
			httpPost.setHeader("Content-type","application/json");
			//StringEntity entity = new StringEntity(json);
			System.out.println("1");
			//httpPost.setEntity(entity);				
			System.out.println("2");
		
			HttpResponse response = (HttpResponse)hc.execute(httpPost);
			System.out.println("3");

			HttpEntity entity = response.getEntity();
			System.out.println("4");
			//inputStream = response.getEntity().getContent();
			 
			if(entity!=null)
			{
				System.out.println("5");
				InputStream instream = entity.getContent();
				result = convertInputStreamToString(instream);
				instream.close();
				System.out.println("6");	
				System.out.println(result);
				System.out.println(entity.toString());
				//Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//return null;
	}
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
