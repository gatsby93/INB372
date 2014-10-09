package com.example.gcmtest2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {	
	
	//GCMINTENTSERVICE MIGHT NEED TO DELETE FROM DEFAULT PACKAGE
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ComponentName comp = new ComponentName(context.getPackageName(),GcmIntentService.class.getName());
		startWakefulService(context,(intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
		
		Toast.makeText(context, "You have a new notifcation!", Toast.LENGTH_LONG).show();			
		
		/*
		//Start the medication activity 
		Intent i = new Intent();
		i.setClassName("com.example.gcmtest2", "com.example.gcmtest2.Medication");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);	
		*/
	}
	
	
	

}
