package com.hengyushop.demo.hotel;

import java.io.Serializable;

public class HotelPriceItemDo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String price;
	private String floor;
	private String bedType;
	private String describe;
	private String roomType;
	private String Quantity;
	private String roomName;
	private String size;
	private String StandardOccupancy;
private String RoomTypeName;

	public String getStandardOccupancy() {
	return StandardOccupancy;
}

public void setStandardOccupancy(String standardOccupancy) {
	StandardOccupancy = standardOccupancy;
}

public String getRoomTypeName() {
	return RoomTypeName;
}

public void setRoomTypeName(String roomTypeName) {
	RoomTypeName = roomTypeName;
}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
