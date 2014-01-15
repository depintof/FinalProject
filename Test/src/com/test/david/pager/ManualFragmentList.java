package com.test.david.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.test.david.R;
import com.test.david.async.AsyncTasks;
import com.test.david.async.AsyncTasksCompleted;
import com.test.david.listview.Data;
import com.test.david.listview.ExpandableAdapter;
import com.test.david.listview.Room;

public class ManualFragmentList extends SherlockFragment implements AsyncTasksCompleted{
	// List for feeding the Adapter
	private List<Room> roomList;
	
	// Layout associations
	ExpandableListView exList;	// Expandable ListView
	TextView homeName;			// TextView - Home Name
	
	// Customized adapter
	ExpandableAdapter exAdpt;
	
	// Values of the log In
	String _username;
	String _password;
	
	// Function to be called getHomeState
	String getHomeStateFunction = "getHomeState";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab1.xml
		View view = inflater.inflate(R.layout.manual_fragment_list, container, false);
		
		homeName = (TextView) view.findViewById(R.id.house_name);
		
		// Call the AsyncTasks class to get the values of the house
		AsyncTasks getState = new AsyncTasks();
		getState = new AsyncTasks(null, getState.FUNCTION_GET_HOME_STATE, null, _username, _password, null, null, null, null, null, null);
		getState.delegate = this;
		getState.execute();
		
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    DisplayMetrics metrics = new DisplayMetrics();
	    ((WindowManager) context().getSystemService(Context.WINDOW_SERVICE))
	    .getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; 
        
        exList = (ExpandableListView) getActivity().findViewById(R.id.expandableListView1);
        exList.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
	}
	
	// Function to get the pixels from the screen
	public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale 
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
	
	// Function to store from outside the values for log in, to make the request
	public void setLogInValues(String username, String password){
		_username = username;
		_password = password;
	}
	
	
	// Function that get results from AsyncData and set this data in array and set the adapter to create the expandable list
	@Override
	public void getHomeData(int nodes, String homename, int[] id, int[] DL, String[] name,
//    public void getHomeData(int nodes, String homename, int panID, int DH, int[] DL, String[] name,
			int[] lightlevel, boolean[] control, boolean[] automatic) {
		// TODO Auto-generated method stub
		// Set the home name in the textview
		homeName.setText(homename);
		
		storeData(nodes, homename, id, name, lightlevel,control,automatic);
//		storeData(nodes, homename, panID, DH, DL, name, lightlevel,control,automatic);
		exAdpt = new ExpandableAdapter(roomList, getActivity());
		exList.setAdapter(exAdpt);

	}
	
	private void storeData(int nodes, String homename, int[] id, String[] name,
//	private void storeData(int nodes, String homename, int panID, int DH, int[] DL, String[] name,
			int[] lightlevel, boolean[] control, boolean[] automatic ){
		roomList = new ArrayList<Room>();
		
		for(int i=0; i<nodes; i++){
			Room room = createRoom(id[i],name[i],control[i],automatic[i]);
			room.setRoomsList(setData(lightlevel[i],control[i],automatic[i]));
			roomList.add(room);
		}
	}
	
	private Room createRoom(int DL, String name, boolean control, boolean automatic) {
    	return new Room(DL, name, control, automatic);
    }
	
	private List<Data> setData(int lightlevel, boolean control, boolean automatic) {
    	List<Data> result = new ArrayList<Data>();
    	Data item = new Data(lightlevel, control, automatic);
    	result.add(item);
    	return result;
    }
	
	
}