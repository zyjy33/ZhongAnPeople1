package com.hengyushop.demo.hotel;

import java.io.Serializable;
import java.util.ArrayList;

public class HotelDetailDo implements Serializable{
	private String hotelName;
	private ArrayList<HotelImageDo> imageDos;
	
	public ArrayList<HotelImageDo> getImageDos() {
		return imageDos;
	}

	public void setImageDos(ArrayList<HotelImageDo> imageDos) {
		this.imageDos = imageDos;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
		
}
