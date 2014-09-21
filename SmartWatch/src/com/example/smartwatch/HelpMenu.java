package com.example.smartwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HelpMenu extends Activity
{
	Button btn1,btn2,btn3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_menu);
        
        btn1 = (Button) findViewById(R.id.Bathroom_btn);
        btn1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				POST p = new POST("Help");
				p.Post(3);
			}
		});
        btn2 = (Button) findViewById(R.id.Eat_btn);
        btn2.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				POST p = new POST("Help");
				p.Post(2);
			}
		});
        btn3 = (Button) findViewById(R.id.Back_btn);
        btn3.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(HelpMenu.this, SubMenu.class);
				startActivity(intent);
			}
		});
    }
        
}
