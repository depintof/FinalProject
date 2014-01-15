package com.test.david.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

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
     
    /**
     * function make Login Request
     * @param username
     * @param password
     * */

    public String loginUser(String username, String password){
    	
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("function", login_tag));
        params.add(new BasicNameValuePair("login", username));
        params.add(new BasicNameValuePair("password", password));
        
        final String finalURL = loginURL + "?"+ url_function + login_tag + "&" + login_tag + "=" + username + "&" + url_password + password; 
        
        String json = jsonParser.getJSONFromUrl(finalURL);

        return json;

    }
     
    /**
     * function make Login Request
     * @param fullname
     * @param username
     * @param password
     * @param email
     * @param homeID
     * @param homePassword
     * */
    public String registerUser(String fullName, String username, String password, String email, String homeID, String homePassword){
        final String finalURL = registerURL + "?"+ url_function + register_tag + "&" + url_full_name + fullName + "&" + url_username + username + "&" + url_password + password + "&" + url_email + email + "&" + url_home_id + homeID + "&" + url_home_password + homePassword;
        String jsonString = jsonParser.getJSONFromUrl(finalURL);
        
        return jsonString;
    }
    
    /**
     * Function to get home data
     * @param username
     * @param password
     * */
    public String getHomeData(String username, String password){
        final String finalURL = registerURL + "?"+ url_function + homestate_tag + "&" + url_username + username + "&" + url_password + password + "&home_id=1&home_password=myhome_pass";
        String jsonString = jsonParser.getJSONFromUrl(finalURL);
        
        return jsonString;
    }
    
    /**
     * Function to set room automatic value
     * @param homeID
     * @param homePassword
     * @param roomID
     * @param automatic
     * */
    public String setRoomAutomatic(String homeID, String homePassword, String roomID, String automatic){
        final String finalURL = registerURL + "?"+ url_function + set_room_automatic_tag + "&" + url_home_id + homeID + "&" + url_home_password + homePassword + "&" + url_room_id + roomID + "&" + url_automatic + automatic;
        Log.e("Output", finalURL);
        String jsonString = jsonParser.getJSONFromUrl(finalURL);
        Log.e("Output", jsonString);
        
        return jsonString;
    }
    
    public String setRoomLevel(String homeID, String homePassword, String roomID, String level){
        final String finalURL = registerURL + "?"+ url_function + set_room_level_tag + "&" + url_home_id + homeID + "&" + url_home_password + homePassword + "&" + url_room_id + roomID + "&" + url_level + level;
        Log.e("Output", finalURL);
        String jsonString = jsonParser.getJSONFromUrl(finalURL);
        Log.e("Output", jsonString);
        
        return jsonString;
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

    
}
