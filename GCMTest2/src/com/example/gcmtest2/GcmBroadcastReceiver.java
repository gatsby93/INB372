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
		
		Toast.makeText(context, "WOW!!! RECEIVED NEW NOTIFCATION", Toast.LENGTH_LONG).show();
		String state = intent.getStringExtra("state");
		System.out.println(state);
		
		//MEDICINE RESPONSE FROM PATIENTS
	}

}
