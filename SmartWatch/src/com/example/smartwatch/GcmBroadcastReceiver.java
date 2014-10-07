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
	 private static final String Check ="http://172.19.24.191/locationservice/LocationService.svc/CheckType";
	 private static final String Fence ="http://172.19.24.191/locationservice/LocationService.svc/GetFence";
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
		
		POST(context);
//		POST(Check);
//		System.out.println("Check success");
//		
		
//    	
//    	if(test == "alert"){
//			GcmBroadcastReceiver.clearMessage();
//			System.out.println("Alert success");
//		}else if(test == "fence"){
//			System.out.println("Checking fence");
//			GcmBroadcastReceiver.clearMessage();
//			System.out.println("Clear");
//			//result="";
//			GcmBroadcastReceiver.POST(Fence);
//			System.out.println("Posted");}
//		
//		clearMessage();}
	}
		
		//String state = intent.getStringExtra("state");
		//System.out.println(state);
		
		//MEDICINE RESPONSE FROM PATIENTS
	
	
	public static void setMessage(String msg)
	{
		
		message =msg;
	}
	
	public static String getMessage()
	{
		return message;
	}
	
	public static void clearMessage(){
		GcmBroadcastReceiver.message = "DEFAULT STRING";
	}
	
	public static void POST(Context context)
	{
		new AsyncTask<Void, Void, String>()
		{
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				InputStream inputStream1 = null;
				//String result ="";
				//JSONObject json = data[0];
				DefaultHttpClient hc1 = new DefaultHttpClient();
				//HttpConnectionParams.setConnectionTimeout(hc.getParams(), 100000);
				
				JSONObject jsonResponse1 = null;
				HttpPost httpPost1 = new HttpPost(Check);
				try
				{
					//pLocation location;
					//StringEntity se = new StringEntity("json"+json.toString());
					
					String json1="";
					JSONObject  jsonObject1 = new JSONObject();
					jsonObject1.accumulate("Patient_ID", "Jessica");
					jsonObject1.accumulate("Latitude", 2.2222);
					jsonObject1.accumulate("Longitude", 33.3333);
					json1 = jsonObject1.toString();
					
					//System.out.println(json);
					StringEntity se1;
					se1 = new StringEntity(json1);
					httpPost1.setEntity(se1);
					
					httpPost1.setHeader("Accept","application/json");
					httpPost1.setHeader("Content-type","application/json");
					//StringEntity entity = new StringEntity(json);
					System.out.println("1");
					//httpPost.setEntity(entity);				
					System.out.println("2");
				
					HttpResponse response = (HttpResponse)hc1.execute(httpPost1);
					System.out.println("3");

					HttpEntity entity1 = response.getEntity();
					System.out.println("4");
					//inputStream = response.getEntity().getContent();
					 
					if(entity1!=null)
					{
						System.out.println("5");
						InputStream instream1 = entity1.getContent();
						result = convertInputStreamToString(instream1);
						instream1.close();
						System.out.println("6");	
						System.out.println(result);
						setMessage(result);
						System.out.println(entity1.toString());
						System.out.println("Result :"+ result);
						System.out.println("Message :" + message);
						String test1 = message.replaceAll("\"", "");
				    	System.out.println("test" + test1);
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
		//if (message == "fence"){
			Toast.makeText(context, "Your fence(s) has(have) been changed", Toast.LENGTH_LONG).show();
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
					HttpPost httpPost = new HttpPost(Fence);
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
							System.out.println("Result :"+ result);
							System.out.println("Message :" + message);
							String test = message.replaceAll("\"", "");
							System.out.println("test" + test);
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
			//}else if (message == "alert"){
				//Toast.makeText(context, "You receive an alert", Toast.LENGTH_LONG).show();
				/**
				 * Need add dialog to confirm whether the patient take pills
				**/
			//}
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
