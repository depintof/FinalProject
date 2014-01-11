package com.test.david.async;

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
	
	// JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_MESSAGE = "message";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    // Flag that verifies if log in was successful when log-in function is called
    private boolean logInFlag = false;
    // Flag that tells where is going the process of creating the new account
    private int creatingAccountFlag = 0;
    
    // Initializing parameters for the async task class
	private Activity mActivity;
	private String _function;
	private String _fullname;
	private String _username;
	private String _password;
	private String _email;
	private String _homeid;
	private String _homepassword;
	
	
	public AsyncTasks(Activity activity, String function, String fullname, String username, String password, String email, String homeID, String homePassword){
		mActivity = activity;
		_function = function;
		_fullname = fullname;
		_username = username;
		_password = password;
		_email = email;
		_homeid = homeID;
		_homepassword = homePassword;		
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
		if(_function=="logIn")
			return logInDoInBackground();
		else if(_function=="createAccount")
			return createAccountDoInBackground();
		else return null;
    }
	
	@Override
    protected void onPostExecute(String info)
    {
		if(_function=="logIn")
			logInOnPostExecute(info);
		else if(_function=="createAccount")
			createAccountOnPostExecute(info);
    }
	
	
	
	private String logInDoInBackground(){
		String info = "";
		final UserFunctions userFunction = new UserFunctions();
		
		String jsonString = userFunction.loginUser(_username, _password);
    	        	
    	// Check if the inserted data is valid
    	if(jsonString==null){
    		Log.e("JSON String", "NO DATA");
    		info = "Insert valid data";
    	}
    	else{

            try {
            	
            	JSONObject json = new JSONObject(jsonString);
            	
            	if (json.getString(KEY_SUCCESS) != null) {
                	String successResponse = json.getString(KEY_SUCCESS);
                	
                    if(Boolean.parseBoolean(successResponse) == true){
                    	
                    	// Receive response from the server DB
	                	JSONObject json_user = json.getJSONObject("user");
	                	
                        // Log In successful
                        logInFlag = true;
                        
                        // Show the create account message given by the server DB
                        info = "User " + json_user.getString(KEY_NAME) + " is logged";
                        
                        // Logged In
                    	Log.e("JSON String", info);
                    	
                    	// Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(mActivity);
                        
                        // NOT WORKING
                        // Clear all previous data in database
                        userFunction.logoutUser(mActivity);
                        db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
                        
                    }
                    else{
                    	info = json.getString(KEY_MESSAGE);
                    }
            	}
            	else{
            		info = "Insert valid data";
            	}
            } catch (Exception e) {
                e.printStackTrace();
            } 
    	}
    	return info;
		
	}
	
	private void logInOnPostExecute(String info){
		// Show the message obtained by the server side
    	Toast.makeText(mActivity, info, Toast.LENGTH_SHORT).show();
    	Log.e("logInFlag", Boolean.toString(logInFlag));
    	Log.e("logInFlag", info);
    	
    	// Finish the activity when the Sign In is successful
    	if(logInFlag==true){
    		Intent intentLightsControl = new Intent(mActivity,LightsControl.class);
    		mActivity.startActivity(intentLightsControl);
    		final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    
                	((Activity)mActivity).finish();
                } }, 500);
    	}
	}
	
	
	private String createAccountDoInBackground(){
		final UserFunctions userFunction = new UserFunctions();
		String jsonString = userFunction.registerUser(_fullname, _username, _password, _email, _homeid, _homepassword);
    	
    	String info = "";
    	
    	// Check if the inserted data is valid
    	if(jsonString==null){
    		Log.e("JSON String", "NO DATA");
    		info = "Insert valid data";
    	}
    	else{
    		//Log.e("JSON String", "Entrando");
            // here goes your UI code. i.e if you want to hide a button
            try {
            	
            	JSONObject json = new JSONObject(jsonString);
            	
            	if (json.getString(KEY_SUCCESS) != null) {
                	String successResponse = json.getString(KEY_SUCCESS);
                	
                    if(Boolean.parseBoolean(successResponse) == true){
                    	Log.e("JSON String", "PROBANDO 2");
    	        	    
    	        	    // Receive response from the server DB
	                	JSONObject json_user = json.getJSONObject("user");
	                	// Show the create account message given by the server DB
                        info = json.getString(KEY_MESSAGE);
	                	
                        Log.e("JSON String", "CREATED");
                        
                        // Close Registration Screen
                        creatingAccountFlag = 1;
	                	
                    	// Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(mActivity);
                         
                        // NOT WORKING			                        
                        // Clear all previous data in database
                        userFunction.logoutUser(mActivity);
                        db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
                    }
                    else{
                    	info = json.getString(KEY_MESSAGE);
                    	Log.e("JSON String", json.toString());
                    	
                    	creatingAccountFlag=2;
                    }
            	}
            	else{
            		
            		creatingAccountFlag=3;
            		info = "Couldn't connect to server";
            	}
            } catch (Exception e) {
                e.printStackTrace();
            } 
    	}
    	return info;
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
	
}