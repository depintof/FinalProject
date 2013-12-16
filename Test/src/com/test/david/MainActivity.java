package com.test.david;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.david.library.DatabaseHandler;
import com.test.david.library.InternetResponse;
import com.test.david.library.UserFunctions;

public class MainActivity extends Activity implements OnClickListener{

	// Widgets variables
	Button logIn;
	EditText usernameInput;
	EditText passwordInput;
	
	// Check the log in state
	int logInFlag = 0;
	
	// JSON Response node names
    private static String KEY_SUCCESS = "success";
//    private static String KEY_MESSAGE = "message";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
	
	public MainActivity(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Importing all assets like buttons, text fields
        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
		
        logIn = (Button) findViewById(R.id.log_in);
        logIn.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void CreateAccount (View view){
		Intent intentNewAccount = new Intent(this, NewAccountActivity.class);
		startActivity(intentNewAccount);
	}

	public void onClick(View v) {

		if(v == logIn){
			final UserFunctions userFunction = new UserFunctions();
			InternetResponse internetResponse = new InternetResponse();
			if(internetResponse.checkOnlineState(getApplicationContext())==false){
	            Toast.makeText(getBaseContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
			}
			else{
				new AsyncTask<String, Void, String>() {
					
					@Override
			        protected void onPreExecute()
			        {
			            // here is for code you do before the network call. you 
			            // leave it empty
			        }
		
			        @Override
			        protected String doInBackground(String... params)
			        {
			            String username = URLEncoder.encode(usernameInput.getText().toString());
			    		String password = URLEncoder.encode(passwordInput.getText().toString());
			            
			        	String jsonString = userFunction.loginUser(username, password);
			        	
			        	String info = "";
			        	
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
				                	
				                	
				                	
				                    if(Integer.parseInt(successResponse) == 1){
				                    	
				                    	
				                    	// Receive response from the server DB
					                	JSONObject json_user = json.getJSONObject("user");
					                	
				                        // Log In successful
				                        logInFlag = 1;
				                        
				                        // Show the create account message given by the server DB
				                        info = json_user.getString(KEY_NAME);
				                        
				                        // Logged In
				                    	Log.e("JSON String", info);
				                    	
				                    	// Store user details in SQLite Database
			                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			                            
			                            // NOT WORKING
			                            // Clear all previous data in database
			                            userFunction.logoutUser(getApplicationContext());
			                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
			                            
				                    }
				                    else{
				                    	info = "Wrong data";
				                    	
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
		
			        @Override
			        protected void onPostExecute(String res)
			        {
			        	// Show the message obtained by the server side
			        	Toast.makeText(getBaseContext(), "User " + res + " is logged", Toast.LENGTH_SHORT).show();
			        	Log.e("logInFlag", Integer.toString(logInFlag));
			        	Log.e("logInFlag", res);
			        	// Finish the activity when the Sign In is successful
			        	if(logInFlag==1){
			        		Intent intentLightsControl = new Intent(getApplicationContext(),LightsControl.class);
			        		startActivity(intentLightsControl);
			        		final Handler handler = new Handler();
			                handler.postDelayed(new Runnable() {
			                    @Override
			                    public void run() {
			                        
			                    	finish();
			                    } }, 500);
			        	}

			        }
				}.execute();
				
			}
		}
	}  



}
