package com.test.david.library;

import android.util.Log;

import com.test.david.async.AsyncTasks;

public class UserFunctions {
private JSONParser jsonParser;
    
    // Probe on Web-Server
    private static String loginURL = "http://myhome.exeamedia.com/";
    private static String registerURL = "http://myhome.exeamedia.com/";
     
    private static String login_tag = "login";
    private static String register_tag = "newUser";
    private static String homestate_tag = "getHomeLevel";
    private static String set_room_automatic_tag = "setRoomAutomatic";
    private static String set_room_level_tag = "setRoomLevel";
    
    private static String url_function = "function=";
    private static String url_full_name = "full_name=";
    private static String url_username = "username=";
    private static String url_password = "password=";
    private static String url_email = "email=";
    private static String url_home_id = "home_id=";
    private static String url_home_password = "home_password=";
    private static String url_room_id = "room_id=";
    private static String url_automatic = "automatic=";
    private static String url_level = "level=";
    
    String finalResult = "";
     
    // Constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

	public String getJSONResponse(String function, String fullname, String username, String password, String email, String homeid, String homepassword, String roomID, String automatic, String level){
	 	// // Building Parameters
        // List<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair("function", login_tag));
        // params.add(new BasicNameValuePair("login", username));
        // params.add(new BasicNameValuePair("password", password));
		AsyncTasks data = new AsyncTasks();

    	String finalURL="";
    	if(function==data.FUNCTION_LOG_IN)
    		finalURL = loginURL + "?"+ url_function + login_tag + "&" + login_tag + "=" + username + "&" + url_password + password;
    	else if(function==data.FUNCTION_CREATE_ACCOUNT)
    		finalURL = registerURL + "?"+ url_function + register_tag + "&" + url_full_name + fullname + "&" + url_username + username + "&" + url_password + password + "&" + url_email + email + "&" + url_home_id + homeid + "&" + url_home_password + homepassword;
    	else if(function==data.FUNCTION_GET_HOME_STATE)
    		finalURL = registerURL + "?"+ url_function + homestate_tag + "&" + url_username + username + "&" + url_password + password + "&home_id=1&home_password=myhome_pass";
    	else if(function==data.FUNCTION_SET_ROOM_AUTOMATIC)
    		finalURL = registerURL + "?"+ url_function + set_room_automatic_tag + "&" + url_home_id + homeid + "&" + url_home_password + homepassword + "&" + url_room_id + roomID + "&" + url_automatic + automatic;
    	else if(function==data.FUNCTION_SET_ROOM_LEVEL)
    		finalURL = registerURL + "?"+ url_function + set_room_level_tag + "&" + url_home_id + homeid + "&" + url_home_password + homepassword + "&" + url_room_id + roomID + "&" + url_level + level;
    	
    	Log.e("Output", finalURL);
        String jsonString = jsonParser.getJSONFromUrl(finalURL);
        Log.e("Output", jsonString);
        
        return jsonString;
	}

    
}
