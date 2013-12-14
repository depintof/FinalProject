package com.test.david;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.david.library.DatabaseHandler;
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
    
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_MESSAGE = "message";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
	
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
		String fullName = inputFullName.getText().toString();
		String username = inputUserName.getText().toString();
		String password = inputPassword.getText().toString();
        String email = inputEmail.getText().toString();
        String homeID = inputHomeID.getText().toString();
        String homePassword = inputHomePassword.getText().toString();
        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.registerUser(fullName, username, password, email, homeID, homePassword);

        Log.e("JSON", "Probando");
        
        Log.e("JSON", fullName);
        Log.e("JSON", username);
        
    	// check for login response
//        try {
//            if (json.getString(KEY_SUCCESS) != null) {
//            	String res = json.getString(KEY_SUCCESS);
//            	
//            	// Receive response from the server DB
//            	JSONObject json_user = json.getJSONObject("user");
//            	// Show the create account message given by the server DB
//                Toast.makeText(getBaseContext(), json_user.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
//                if(Integer.parseInt(res) == 1){
//                    //// User Successfully Registred
//                	// Store user details in SQLite Database
//                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//                     
//                    // Clear all previous data in database
//                    userFunction.logoutUser(getApplicationContext());
//                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
//                    
//                    // Launch Dashboard Screen
//                                        
//                    // Close Registration Screen
//                    finish();
//                }
//                else{
//                	
//                }
//            }
//            
//            
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
	}
	
//	public void CreateNewAccount (View view){
//		String fullName = inputFullName.getText().toString();
//		String username = inputUserName.getText().toString();
//		String password = inputPassword.getText().toString();
//        String email = inputEmail.getText().toString();
//        String homeID = inputHomeID.getText().toString();
//        String homePassword = inputHomePassword.getText().toString();
//        UserFunctions userFunction = new UserFunctions();
//        JSONObject json = userFunction.registerUser(fullName, username, password, email, homeID, homePassword);
//
//	// check for login response
//        try {
//            if (json.getString(KEY_SUCCESS) != null) {
//            	String res = json.getString(KEY_SUCCESS);
//            	
//            	// Receive response from the server DB
//            	JSONObject json_user = json.getJSONObject("user");
//            	// Show the create account message given by the server DB
//                Toast.makeText(getBaseContext(), json_user.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
//                if(Integer.parseInt(res) == 1){
//                    //// User Successfully Registred
//                	// Store user details in SQLite Database
//                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//                     
//                    // Clear all previous data in database
//                    userFunction.logoutUser(getApplicationContext());
//                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
//                    
//                    // Launch Dashboard Screen
//                                        
//                    // Close Registration Screen
//                    finish();
//                }
//                else{
//                	
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//	}
	
}
