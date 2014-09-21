package com.example.smartwatch;

public class pLocation {
	double lat, lng;
	String patient;
	public pLocation(String p, float lat, float lng){
		setLat (lat);
		setLng (lng);
		setPatient (p);
		
	}
	/*
	 * set methods
	 */
	public void setLat (double la)
	{
		lat = la;
	}
	public void setLng (double ln)
	{
		lng = ln;
	}
	public void setPatient (String p)
	{
		patient = p;
	}
	
	/*
	 * get methods
	 */
	public double getLat ()
	{
		return lat;
	}
	public double getLng()
	{
		return lng;
	}
	public String getPatient()
	{
		return patient;
	}
}
