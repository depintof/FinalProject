package com.test.david.listview;

import java.io.Serializable;

public class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int lightlevel;
	private boolean control;
	private boolean automatic;
	
	public Data(int lightlevel, boolean control, boolean automatic) {
		this.lightlevel = lightlevel;
		this.control = control;
		this.automatic = automatic;
	}
	
	
	public int getLightLevel() {
		return lightlevel;
	}
	public void setLightLevel(int lightlevel) {
		this.lightlevel = lightlevel;
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


}