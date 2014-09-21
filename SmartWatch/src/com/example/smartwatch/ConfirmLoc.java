package com.example.smartwatch;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.smartwatch.Person;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConfirmLoc extends Activity {

	//private static final String URL ="http://luoxin-hp/locationservice/LocationService.svc/GetLocation";
	private static final String URL ="http://172.19.31.119/locationservice/LocationService.svc/GetLocation";
	private static String result ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_loc);
		
		Button btnPost,btnCancel;
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConfirmLoc.this, PantientApp.class);
				startActivity(intent);
			}
		});	
		btnPost = (Button)findViewById(R.id.btnPost);
		btnPost.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				POST();
				//Toast.makeText(getBaseContext(), "Succes", Toast.LENGTH_LONG).show();
				//Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
               // new HttpAsyncTask().execute(URL);
                //break;
			}
		});	
	}
	/*
	public void POST2()
	{
		URL url;
		HttpURLConnection conn;
		
		try
		{
			url = new URL(URL);
			String param = "param1" + URLEncoder.encode("CLEMENT","UTF-8")+"&param2="+URLEncoder.encode("2222.2222","UTF-8")+URLEncoder.encode("3.33333","UTF-8");
			
			conn = (HttpURLConnection)url.openConnection();
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setFixedLengthStreamingMode(param.getBytes().length);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.close();
			
			String response = "";
			Scanner inStream = new Scanner(conn.getInputStream());
			
			while(inStream.hasNextLine())
			response+=(inStream.nextLine());	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}
	*/
	
	public void POST()
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
					jsonObject.accumulate("Patient_ID", "York");
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
	/*
	public static String POST(String url, pLocation location){
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
            jsonObject.accumulate("PATIENT ID",location.getPatient());
            jsonObject.accumulate("LATITUDE", location.getLat());
            jsonObject.accumulate("LONGITUDE", location.getLng());
 
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person); 
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
 
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
        return result;
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
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            pLocation location = new pLocation();
            location.setPatient("CLEMENT");
            location.setLat(-27.477112);
            location.setLng(153.028015);
            
            return POST(urls[0],location);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
       }
    }
	
	
	*/
	/*
	
	
	public void POST() 
	{
		new AsyncTask<Void,Void,String>()
		{
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				//CREATE HTTPCLIENT
				DefaultHttpClient httpclient = new DefaultHttpClient();
				
				//MAKE POST REQUEST TO THIS URL
				HttpPost httppost = new HttpPost(URL);
				
				try
				{
					//CREATION OF JSON OBJECT
					JSONObject jobj = new JSONObject();
					jobj.put("Patient_ID", "P777");
					jobj.put("Latitiude", 7.7777);
					jobj.put("Longitude", 22.222);	
					
					//JSON OBJECT TO STRING
					String json ="";
					json = jobj.toString();
					
					//SETTING THE ENTITY OF THE HTTP
					StringEntity se = new StringEntity(json);
					httppost.setEntity(se);
					
					//LETTING THE SERVER KNOW THE HEADER TYPE 
					httppost.setHeader("Accept", "application/json");
			        httppost.setHeader("Content-type", "application/json");
					
					//EXECUTE HTTP POST REQUEST
					HttpResponse response = httpclient.execute(httppost);

					//RESPONSE TO STRING
					HttpEntity entity = response.getEntity();
					
					if(entity == null)
					{
						 Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
					}
					if(entity !=null)
					{
						InputStream instream = entity.getContent();
						String resultString = convertStreamToString(instream);
						instream.close();
						
						System.out.println("Response" + resultString);
					}
					
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
				return null;
			}
		}.execute(null,null,null);
	}	
	
	public String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	
	*/
}
	


