package com.example.smartwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class CaretakerApp extends Activity
{
	EditText et;
	Button btn1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caretaker);
        
        et = (EditText) findViewById(R.id.messages_info);
        
        btn1 = (Button) findViewById(R.id.geofence);
        btn1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//SWAP FROM MAINACTIVITY CLASS TO WATCH ACTIVITY CLASS
				Intent intent = new Intent(CaretakerApp.this, Fence.class);
				startActivity(intent);
			}
		});
    }
}
