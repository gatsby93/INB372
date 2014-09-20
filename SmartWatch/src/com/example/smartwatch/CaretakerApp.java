package com.example.smartwatch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;


public class CaretakerApp extends Activity
{
	EditText et;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caretaker);
        
        et = (EditText) findViewById(R.id.messages_info);
    }
}
