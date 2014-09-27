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

import android.os.AsyncTask;

public class POST {
	//private static final String URL ="http://luoxin-hp/locationservice/LocationService.svc/GetLocation";
	private static final String Postion ="http://172.19.31.119/locationservice/LocationService.svc/GetLocation";
	private static final String Help ="http://172.19.31.119/locationservice/LocationService.svc/Alert";
	private static String URL;
	private static String result ="";
	
	public POST (String Type){
		if (Type == "Location")
		{
			URL = Postion;
		}else if (Type == "Help")
		{
			URL = Help;
		}
	}
	 public void Post(final int msgType)
		{
			new AsyncTask<Void, Void, String>()
			{
				@Override
				protected String doInBackground(Void... params) {
					// TODO Auto-generated method stub
					//InputStream inputStream = null;

					//JSONObject json = data[0];
					DefaultHttpClient hc = new DefaultHttpClient();
					//HttpConnectionParams.setConnectionTimeout(hc.getParams(), 100000);
					
					//JSONObject jsonResponse = null;
					HttpPost httpPost = new HttpPost(URL);
					try
					{
						//pLocation location;
						//StringEntity se = new StringEntity("json"+json.toString());						
						String json="";
						JSONObject  jsonObject = new JSONObject();
						if(msgType == 0){
							jsonObject.accumulate("Patient_ID", "York");
							jsonObject.accumulate("Latitude", 2.2222);
							jsonObject.accumulate("Longitude", 33.3333);
						}else if (msgType == 1){
							jsonObject.accumulate("Patient_ID", "York");
							jsonObject.accumulate("Message", "He is Panic!");
						}else if (msgType == 2){
							jsonObject.accumulate("Patient_ID", "York");
							jsonObject.accumulate("Message", "Wants to eat.");
						}else if (msgType == 3){
							jsonObject.accumulate("Patient_ID", "York");
							jsonObject.accumulate("Message", "go to bathroom.");
						}
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
