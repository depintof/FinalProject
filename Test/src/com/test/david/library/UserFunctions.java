package com.test.david.library;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserFunctions {
	private JSONParser jsonParser;
    
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
	// Probe at house
//    private static String loginURL = "http://192.168.1.3/api/";
//    private static String registerURL = "http://192.168.1.3/api/";
    
	// Probe on Raspberry
    private static String loginURL = "http://181.54.32.81:8086/";
    private static String registerURL = "http://181.54.32.81:8086/";
     
    private static String login_tag = "login";
    private static String register_tag = "newUser";
     
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param username
     * @param password
     * */
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("function", login_tag));
        params.add(new BasicNameValuePair("login", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
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
    public JSONObject registerUser(String fullName, String username, String password, String email, String homeID, String homePassword){

    	// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("function", register_tag));
        params.add(new BasicNameValuePair("full_name", fullName));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("home_id", homeID));
        params.add(new BasicNameValuePair("home_password", homePassword));
                 
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        

        
        // return json
        return json;
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
