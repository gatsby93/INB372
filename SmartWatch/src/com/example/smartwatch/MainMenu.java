package com.example.smartwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainMenu extends Activity
{
	ImageButton btn1,btn2;
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
    }
        
}
