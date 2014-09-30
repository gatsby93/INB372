package com.example.smartwatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainMenu extends Activity
{
	ImageButton btn1,btn2;
	Button btnTest;
	private static String result="";
	private static final String URL ="http://172.19.10.127/locationservice/LocationService.svc/Alert";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        btn1 = (ImageButton) findViewById(R.id.imageButton1);
        btn1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//SWAP FROM MAINACTIVITY CLASS TO WATCH ACTIVITY CLASS
				Intent intent = new Intent(MainMenu.this, SubMenu.class);
				startActivity(intent);
			}
		});
        btn2 = (ImageButton) findViewById(R.id.imageButton2);
        btn2.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//SWAP FROM MAINACTIVITY CLASS TO WATCH ACTIVITY CLASS
				Intent intent = new Intent(MainMenu.this, CaretakerApp.class);
				startActivity(intent);
			}
		});
        
        //TEST CODE
        btnTest = (Button)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				POST();
		
			}
		});     
    }
    
    //TEST CODE
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
						
						
						//String[] parts = result.split("(,)|(^\")");
						String[] parts = result.split(",");
						Double d = Double.parseDouble(parts[0].replaceAll("\"", " "));
						Double d1 = Double.parseDouble(parts[1].replaceAll("\"", " "));
						Double d2 = Double.parseDouble(parts[2].replaceAll("\"", " "));
//						DecimalFormat dF = new DecimalFormat("0.00");
//				        Number num = dF.parse(parts[1]);
//				        double msg = num.doubleValue();
						//double msg = new Doulbe(parts[0]);
						System.out.println(d);
						System.out.println(d1);
						System.out.println(d2);
						//System.out.printf("This msg %d",msg);
//						System.out.println(parts[0]);
//						System.out.println(parts[1]);
//						System.out.println(parts[2]);
						//setMessage(result);
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
