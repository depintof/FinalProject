package com.test.david.listview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;	
	private String name;
	private boolean control;
	private boolean automatic;
	
	private List<Data> roomsList = new ArrayList<Data>();
	
	public Room(int id, String name, boolean control, boolean automatic){
		this.id = id;
		this.name = name;
		this.control = control;
		this.automatic = automatic;
	}
	
	public int getRoomID() {
		return id;
	}
	
	public void setRoomID(int id) {
		this.id = id;
	}
	
	public String getRoomName() {
		return name;
	}
	
	public void setRoomName(String name) {
		this.name = name;
	}
	
	public boolean getRoomControl() {
		return control;
	}
	public void setRoomControl(boolean control) {
		this.control = control;
	}
	public boolean getRoomAutomatic() {
		return automatic;
	}
	public void setRoomAutomatic(boolean automatic) {
		this.automatic = automatic;
	}
	
	public List<Data> getRoomsList() {
		return roomsList;
	}
	
	public void setRoomsList(List<Data> roomsList) {
		this.roomsList = roomsList;
	}

}
