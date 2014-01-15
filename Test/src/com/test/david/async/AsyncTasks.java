package com.test.david.async;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.test.david.LightsControl;
import com.test.david.library.DatabaseHandler;
import com.test.david.library.UserFunctions;

public class AsyncTasks extends AsyncTask<String, Void, String> {
	
	public AsyncTasksCompleted delegate = null;

	// NAMES FOR FUNCTIONS ON SERVER SIDE
    public final String FUNCTION_LOG_IN = "logIn";
    public final String FUNCTION_CREATE_ACCOUNT = "createAccount";
    public final String FUNCTION_GET_HOME_STATE = "getHomeState";
    public final String FUNCTION_SET_ROOM_AUTOMATIC = "setRoomAutomatic";
    public final String FUNCTION_SET_ROOM_LEVEL = "setRoomLevel";
	
	// KEY NAMES for function=createAccount and function=logIn responses
    private static String KEY_SUCCESS = "success";
    private static String KEY_MESSAGE = "message";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    
    // KEY NAMES for function=getHomeState responses
    private static String KEY_HOME = "home";
    // JSON Response for Home Object
    private static String KEY_NODES = "nodes";
    private static String KEY_HOMENAME = "name";
    private static String KEY_PANID = "panID";
    private static String KEY_DH = "DH";
    // JSON Response for Room Object
    private static String KEY_ROOM_ID = "id";
    private static String KEY_ROOM_DL = "DL";
    private static String KEY_ROOM_NAME = "name";
    private static String KEY_ROOM_LEVEL = "level";	// Change "state" for "level"
    private static String KEY_ROOM_CONTROL = "control";
    private static String KEY_ROOM_AUTOMATIC = "automatic";
    

    // Flag that verifies if log in was successful when log-in function is called
    private boolean logInFlag = false;
    // Flag that tells where is going the process of creating the new account
    private int creatingAccountFlag = 0;
    
    // VALUES MODIFIED INSIDE DOINBACKGROUND TASK
    // Number of nodes 
 	private int nodes = 0;
 	// Home Name
 	private String homeName = "";
 	// PAN-ID of Xbees at the house 
  	private int panID = 0;
  	// DH of Xbees at the house 
  	private int DH = 0;
    // Names of the nodes
 	private String[] name;
 	// ID of the Nodes
   	private int[] id;
 	// DL of the Nodes
  	private int[] DL;
 	// Light Levels of the Nodes
 	private int[] lightlevel;
 	// Control mode: true -> can be controlled remotely, false -> can't be controlled remotely
 	private boolean[] control;
 	// Automatic mode: true -> The node is controlled by sensor, false -> Node is controlled remotely
  	private boolean[] automatic;
  	
    // Initializing parameters for the Async task class
	private Activity mActivity;
	private String _function;
	private String _fullname;
	private String _username;
	private String _password;
	private String _email;
	private String _homeid;
	private String _homepassword;
	private String _roomID;
	private String _automatic;
	private String _level;


	public AsyncTasks(){
		
	}
	
	public AsyncTasks(Activity activity, String function, String fullname, String username, String password, String email, String homeid, String homepassword, String roomid, String automatic, String level){
	    mActivity = activity;
	    _function = function;
	    _fullname = fullname;
	    _username = username;
	    _password = password;
	    _email = email;
	    _homeid = homeid;
	    _homepassword = homepassword;
	    _roomID = roomid;
	    _automatic = automatic;
	    _level = level; 
	}
	
	@Override
    protected void onPreExecute()
    {
        // here is for code you do before the network call. you 
        // leave it empty
    }
	
	@Override
    protected String doInBackground(String... params)
    {
		String info = "";
		final UserFunctions userFunction = new UserFunctions();
		String jsonString = userFunction.getJSONResponse(_function, _fullname, _username, _password, _email, _homeid, _homepassword, _roomID, _automatic, _level);	// CHANGE USERFUNCTION CLASS TO RECEIVE NOW THE STRING FUNCTION
		if(jsonString==null){
	    		Log.e("JSON String", "NO DATA");
	    		info = "Insert valid data";
	    }
	    else{
	    	try{
	    		JSONObject json = new JSONObject(jsonString);
				if (json.getString(KEY_SUCCESS) != null) {
					String successResponse = json.getString(KEY_SUCCESS);
                	if(Boolean.parseBoolean(successResponse) == true){
                		
                		
                		// Here is where the code is executed depending on the function requested by the user
                		if(_function==FUNCTION_LOG_IN)
                			info = logInDoInBackground(json);
                		else if(_function==FUNCTION_CREATE_ACCOUNT)
                			info = createAccountDoInBackground(json);
                		else if(_function==FUNCTION_GET_HOME_STATE)
                			info = getHomeDataDoInBackground(json);
                		else if(_function==FUNCTION_SET_ROOM_AUTOMATIC)
                			info = setRoomAutomaticDoInBackground(json);
                		else if(_function==FUNCTION_SET_ROOM_LEVEL)
                			info = setRoomLevelDoInBackground(json);
                		
                	}
                   	else{
                       	info = json.getString(KEY_MESSAGE);
                   	}
				}
	            else{
	            	info = "Couldn't connect to server";
	            }
	    	}catch (Exception e) {
				e.printStackTrace();
			} 
	    }
	    return info;
	}
    
	
	@Override
    protected void onPostExecute(String info)
    {
		if(_function=="logIn")
			logInOnPostExecute(info);
		else if(_function=="createAccount")
			createAccountOnPostExecute(info);
		else if(_function=="getHomeState")
			getHomeDataOnPostExecute(info);
    }
	
	private String logInDoInBackground(JSONObject json) throws JSONException{
	   	JSONObject json_user = json.getJSONObject("user");
	    // Log In successful
	    logInFlag = true;
	    // Show the create account message given by the server DB
	    String info = "User " + json_user.getString(KEY_NAME) + " is logged";
	    return info;
	}

	private String createAccountDoInBackground(JSONObject json) throws JSONException{
	    // Show the create account message given by the server DB
	    String info = json.getString(KEY_MESSAGE);
	    // Close Registration Screen
	    creatingAccountFlag = 1;
	    return info;
	}

	private String getHomeDataDoInBackground(JSONObject json) throws JSONException{
	    JSONObject homeObject = json.getJSONObject(KEY_HOME);
	    homeName = homeObject.getString(KEY_HOMENAME);
	    nodes = Integer.parseInt(homeObject.getString(KEY_NODES));
	    // panID = Integer.parseInt(homeObject.getString(KEY_PANID));
	    // DH = Integer.parseInt(homeObject.getString(KEY_DH));
	    
	    String room = "room_x";
	                        
	    id = new int[nodes];
	    DL = new int[nodes];
	    name = new String[nodes];
	    lightlevel = new int[nodes];
	    control = new boolean[nodes];
	    automatic = new boolean[nodes];
	                        
	    for(int i=0; i<nodes; i++){
	        room = changeCharInPosition(5, (char)(i+'0'), room);
	        JSONObject roomObject = homeObject.getJSONObject(room);
	        id[i] = Integer.parseInt(roomObject.getString(KEY_ROOM_ID));
	        DL[i] = Integer.parseInt(roomObject.getString(KEY_ROOM_DL));
	        name[i] = roomObject.getString(KEY_ROOM_NAME);
	        lightlevel[i] = Integer.parseInt(roomObject.getString(KEY_ROOM_LEVEL));
	        control[i] = Boolean.parseBoolean(roomObject.getString(KEY_ROOM_CONTROL));
	        automatic[i] = Boolean.parseBoolean(roomObject.getString(KEY_ROOM_AUTOMATIC));
	    }
	    return null;
	}

	private String setRoomAutomaticDoInBackground(JSONObject json) throws JSONException{
	    String info = json.getString(KEY_MESSAGE);
	    return info;
	}

	private String setRoomLevelDoInBackground(JSONObject json) throws JSONException{
		String info = json.getString(KEY_MESSAGE);
		return info;
	}
	
	private void logInOnPostExecute(String info){
		// Show the message obtained by the server side
    	Toast.makeText(mActivity, info, Toast.LENGTH_SHORT).show();
    	
    	// Finish the activity when the Sign In is successful
    	if(logInFlag==true){
    		Intent intentLightsControl = new Intent(mActivity,LightsControl.class);
    		intentLightsControl.putExtra("USERNAME", _username);
    		intentLightsControl.putExtra("PASSWORD", _password);
    		
    		mActivity.startActivity(intentLightsControl);
    		final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    
                	((Activity)mActivity).finish();
                } }, 500);
    	}
	}
	
	
	private void createAccountOnPostExecute(String info){
		// Show the message obtained by the server side
    	Toast.makeText(mActivity, info, Toast.LENGTH_SHORT).show();
    	Log.e("creatingAccountFlag", Integer.toString(creatingAccountFlag));
    	// Finish the activity when the Sign In is successful
    	if(creatingAccountFlag==1){
    		final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                	((Activity)mActivity).finish();
                } }, 500);
    	}
	}
	
	
	private void getHomeDataOnPostExecute(String info){
		delegate.getHomeData(nodes, homeName, id, DL, name, lightlevel, control, automatic);
//		delegate.getHomeData(nodes, homeName, panID, DH, DL, name, lightlevel, control, automatic);
	}
	
	
	// Function for replace the nodes names dynamically
	private String changeCharInPosition(int position, char ch, String str){
		char[] charArray = str.toCharArray();
	    charArray[position] = ch;
	    return String.copyValueOf(charArray);
	}
	
	// UN-USED FUNCTIONS: DOESN'T WORKS
	// Functions for get the values of the home object
	public int getNodeNumbers(){
		return nodes;
	}
	public String getHomeName(){
		return homeName;
	}
	// Functions for get the values of the room object
	public String[] getNodesNames(){
		return name;
	}
	public int[] getNodesLightLevels(){
		return lightlevel;
	}
	public boolean[] getNodesControl(){
		return control;
	}
	public boolean[] getNodesAutomatic(){
		return automatic;
	}
}