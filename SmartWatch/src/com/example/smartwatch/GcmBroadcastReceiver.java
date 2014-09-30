package com.example.smartwatch;

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


//import com.example.gcmtest2.*;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	 private static final String URL ="http://172.19.10.127/locationservice/LocationService.svc/Alert";
	 private static String result="";
	 public static String message="DEFAULT STRING";
	 
	//GCMINTENTSERVICE MIGHT NEED TO DELETE FROM DEFAULT PACKAGE
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ComponentName comp = new ComponentName(context.getPackageName(),GcmIntentService.class.getName());
		startWakefulService(context,(intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
		
		Toast.makeText(context, "WOW!!! RECEIVED NEW NOTIFCATION", Toast.LENGTH_LONG).show();
		POST();
		//String state = intent.getStringExtra("state");
		//System.out.println(state);
		
		//MEDICINE RESPONSE FROM PATIENTS
	}
	
	public void setMessage(String message)
	{
		GcmBroadcastReceiver.message = message;
	}
	
	public static String getMessage()
	{
		return message;
	}
	
	public static void clearMessage(){
		GcmBroadcastReceiver.message = "DEFAULT STRING";
	}
	
	public void POST()
	{
		new AsyncTask<Void, Void, String>()
		{
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				InputStream inputStream = null;
				//String result ="";
				//JSONObject json = data[0];
				DefaultHttpClient hc = new DefaultHttpClient();
				//HttpConnectionParams.setConnectionTimeout(hc.getParams(), 100000);
				
				JSONObject jsonResponse = null;
				HttpPost httpPost = new HttpPost(URL);
				try
				{
					//pLocation location;
					//StringEntity se = new StringEntity("json"+json.toString());
					
					String json="";
					JSONObject  jsonObject = new JSONObject();
					jsonObject.accumulate("Patient_ID", "Jessica");
					jsonObject.accumulate("Latitude", 2.2222);
					jsonObject.accumulate("Longitude", 33.3333);
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
						setMessage(result);
						System.out.println(entity.toString());
						//Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
					}		
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
			
		}.execute(null,null,null);
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

}
