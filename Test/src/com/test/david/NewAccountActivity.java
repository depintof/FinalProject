package com.test.david;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
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

public class NewAccountActivity extends Activity implements OnClickListener{
	
	// Widgets variables
	EditText inputFullName;
	EditText inputUserName;
	EditText inputPassword;
    EditText inputEmail;
    EditText inputHomeID;
    EditText inputHomePassword;
    Button NewAccount;
    
    int creatingAccountFlag = 0;
    
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_MESSAGE = "message";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    
    MainActivity mainActivity = new MainActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_account);
		
		// Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.full_name_input);
        inputUserName = (EditText) findViewById(R.id.user_input);
        inputPassword = (EditText) findViewById(R.id.password_input);
        inputEmail = (EditText) findViewById(R.id.email_input);
        inputHomeID = (EditText) findViewById(R.id.home_id_input);
        inputHomePassword = (EditText) findViewById(R.id.home_password_input);
        NewAccount = (Button) findViewById(R.id.create_account);
        NewAccount.setOnClickListener(this);
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_account, menu);
		return true;
	}

	public void onClick(View v){
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
//		            // here goes your network call
//		        	String fullName = inputFullName.getText().toString();
//		    		String username = inputUserName.getText().toString();
//		    		String password = inputPassword.getText().toString();
//		            String email = inputEmail.getText().toString();
//		            String homeID = inputHomeID.getText().toString();
//		            String homePassword = inputHomePassword.getText().toString();
		            
		            // here goes your network call
		        	String fullName = URLEncoder.encode(inputFullName.getText().toString());
		    		String username = URLEncoder.encode(inputUserName.getText().toString());
		    		String password = URLEncoder.encode(inputPassword.getText().toString());
		            String email = URLEncoder.encode(inputEmail.getText().toString());
		            String homeID = URLEncoder.encode(inputHomeID.getText().toString());
		            String homePassword = URLEncoder.encode(inputHomePassword.getText().toString());
		            
		        	String jsonString = userFunction.registerUser(fullName, username, password, email, homeID, homePassword);
		        	
		        	String info = "";
		        	
		        	// Check if the inserted data is valid
		        	if(jsonString==null){
		        		Log.e("JSON String", "NO DATA");
		        		info = "Insert valid data";
		        	}
		        	else{

			            // here goes your UI code. i.e if you want to hide a button
			            try {
			            	
			            	JSONObject json = new JSONObject(jsonString);
			            	
			            	if (json.getString(KEY_SUCCESS) != null) {
			                	String successResponse = json.getString(KEY_SUCCESS);
			                	
			                    if(Integer.parseInt(successResponse) == 1){
			                    	Log.e("JSON String", "PROBANDO 2");
			    	        	    
			    	        	    // Receive response from the server DB
				                	JSONObject json_user = json.getJSONObject("user");
				                	// Show the create account message given by the server DB
			                        info = json.getString(KEY_MESSAGE);
				                	
			                        Log.e("JSON String", "CREATED");
			                        
			                        // Close Registration Screen
			                        creatingAccountFlag = 1;
				                	
			                    	// Store user details in SQLite Database
			                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			                         
			                        // Clear all previous data in database
			                        userFunction.logoutUser(getApplicationContext());
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
			            		info = json.getString(KEY_MESSAGE);
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
		        	Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
		        	Log.e("creatingAccountFlag", Integer.toString(creatingAccountFlag));
		        	// Finish the activity when the Sign In is successful
		        	if(creatingAccountFlag==1){
		        		final Handler handler = new Handler();
		                handler.postDelayed(new Runnable() {
		                    @Override
		                    public void run() {
		                        // Do something after 5s = 5000ms
		                    	finish();
		                    } }, 500);
		        	}

		        }
			}.execute();
			
		}

	}

}
