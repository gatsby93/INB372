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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SubMenu extends Activity
{
	Button btn1,btn2,btn3,btn4;
	
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_menu_patient);
        
        btn1 = (Button) findViewById(R.id.Map_btn);
        btn1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//SWAP FROM MAINACTIVITY CLASS TO WATCH ACTIVITY CLASS
				Intent intent = new Intent(SubMenu.this, PantientApp.class);
				startActivity(intent);
			}
		});
        btn2 = (Button) findViewById(R.id.Painc_btn);
        btn2.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				POST p = new POST("Help");
				p.Post(1);
			}
		});
        btn3 = (Button) findViewById(R.id.Help_btn);
        btn3.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//SWAP FROM MAINACTIVITY CLASS TO WATCH ACTIVITY CLASS
				Intent intent = new Intent(SubMenu.this, HelpMenu.class);
				startActivity(intent);
			}
		});
        btn4 = (Button) findViewById(R.id.Back_btn);
        btn4.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(SubMenu.this, MainMenu.class);
				startActivity(intent);
			}
		});
    }
   
        
}
