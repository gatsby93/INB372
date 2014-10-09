package com.example.gcmtest2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Medication extends Activity {
	
	String patientId = "P001";
	
	//Panic
	String panicMessage = patientId + ":\n\n Panic! Panic!";
	
	//Help	
	String helpMessage = patientId + " needs help!";		
	
	//Medication
	String medicMessage = patientId + " didn't take the medicine!\n\nPlease remind him to take the medicine";
	
	//Out of fence
	String fenceMessage = patientId + " out of fence!";	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_medication);
	
        
        AlertDialog.Builder builder = new AlertDialog.Builder(Medication.this);
 
        // Set Alert Dialog Title
        builder.setTitle("Caretaker Alert");
 
        // Set an Icon for this Alert Dialog
        builder.setIcon(R.drawable.slogo);
 
        // Set Alert Dialog Message
        builder.setMessage(medicMessage)
 
        // Positive button functionality
        	.setPositiveButton("Ok",
        			new DialogInterface.OnClickListener() {
        				public void onClick(DialogInterface dialog,int arg0) {    
        					Toast.makeText(Medication.this,"You clicked on OK",
                                        		 Toast.LENGTH_SHORT).show();  
                        }
             });
                        
        // Create the Alert Dialog
        AlertDialog alertdialog = builder.create();
 
        // Show Alert Dialog
        alertdialog.show();
       
    }
}
