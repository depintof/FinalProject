package com.test.david.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class UserFunctions {
	private JSONParser jsonParser;
    
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    
	// Probe on Raspberry
//    private static String loginURL = "http://181.54.32.81:8086/";
//    private static String registerURL = "http://181.54.32.81:8086/";
    
    // Probe on Web-Server
    private static String loginURL = "http://myhome.exeamedia.com/";
    private static String registerURL = "http://myhome.exeamedia.com/";
     
    private static String login_tag = "login";
    private static String register_tag = "newUser";
    
    private static String url_function = "function=";
    private static String url_full_name = "full_name=";
    private static String url_username = "username=";
    private static String url_password = "password=";
    private static String url_email = "email=";
    private static String url_home_id = "home_id=";
    private static String url_home_password = "home_password=";
    
    String finalResult = "";
     
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param username
     * @param password
     * */
//    public JSONObject loginUser(String username, String password){
    public String loginUser(String username, String password){
    	
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("function", login_tag));
        params.add(new BasicNameValuePair("login", username));
        params.add(new BasicNameValuePair("password", password));
        
        final String finalURL = loginURL + "?"+ url_function + login_tag + "&" + login_tag + "=" + username + "&" + url_password + password; 
        
//        JSONObject json = jsonParser.getJSONFromUrl(loginURL);
        String json = jsonParser.getJSONFromUrl(finalURL);

//        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
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
//    public JSONObject registerUser(String fullName, String username, String password, String email, String homeID, String homePassword){
    public String registerUser(String fullName, String username, String password, String email, String homeID, String homePassword){

    	// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("function", register_tag));
        params.add(new BasicNameValuePair("full_name", fullName));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("home_id", homeID));
        params.add(new BasicNameValuePair("home_password", homePassword));
        
        final String finalURL = registerURL + "?"+ url_function + register_tag + "&" + url_full_name + fullName + "&" + url_username + username + "&" + url_password + password + "&" + url_email + email + "&" + url_home_id + homeID + "&" + url_home_password + homePassword;

        // getting JSON Object
        String jsonString = jsonParser.getJSONFromUrl(finalURL);
        
//        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
//        Log.e("JSON String", jsonString);
        
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
