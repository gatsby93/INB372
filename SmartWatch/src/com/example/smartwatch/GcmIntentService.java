package com.example.smartwatch;
//import com.example.smartwatch.MainActivity;

import com.example.smartwatch.R;
import com.example.smartwatch.GcmBroadcastReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import android.util.Log;


public class GcmIntentService extends IntentService
{
	public static final int NOTIFICATION_ID =1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	private final static String TAG ="GcmIntentService";
	
	public GcmIntentService()
	{
		super("GcmIntentService");
	}
	@Override
	protected void onHandleIntent(Intent intent)
	{
		Bundle extras = intent.getExtras();
		Log.d(TAG, "Notification Data JSON:" + extras.getString("message"));
		
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		if(!extras.isEmpty())
		{
			if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
			{
				sendNotification("Send error"+extras.toString());
			}
			else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
			{
				sendNotification("Delete messages on Server"+extras.toString());
			}
			else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
			{
				for(int i = 0; i<5; i++)
				{
					Log.d(TAG, "Working...." + (i+1)+ "/5@" + SystemClock.elapsedRealtime());
					try
					{
						Thread.sleep(5000);
					}catch(Exception e)
					{
						
					}
				}
				Log.d(TAG,"Completed work @" + SystemClock.elapsedRealtime());
				sendNotification(extras.getString("message"));
				Log.i(TAG,"Received" + extras.toString());
				//MESSAGE IS IN HERE
			}
		}//RELEASE THE WAKE LOCK PROVIDED BY WAKEFULBROADCASTRECEIVER
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	
	private void sendNotification(String msg)
	{
		mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		//NEW CODES TESTING
		//MAYBE CHANGE SUBMENU.CLASS
		Intent myIntent = new Intent(this, SubMenu.class);
		myIntent.putExtra("message", msg);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		 NotificationCompat.Builder mBuilder =
	                new NotificationCompat.Builder(this)
	        .setSmallIcon(R.drawable.logo)
	        .setContentTitle("GCM Notification")
	        .setStyle(new NotificationCompat.BigTextStyle()
	        .bigText(msg))
	        .setContentText(msg);

	        mBuilder.setContentIntent(contentIntent);
	        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
//		
//		//SMALLICON MAYBE NEED CHANGE TO ICON
//		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo).setContentTitle("GCM NOTIFICATION").setStyle(new NotificationCompat.BigTextStyle().bigText(msg)) .setContentText(msg).setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//		mBuilder.setContentIntent(contentIntent);  
//		//System.out.println(contentIntent.toString());
//		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());       	
	}
	

}


