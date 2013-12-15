package com.test.david;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
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
		finish();
	}

	public void LogIn (View view){
//		String email = inputEmail.getText().toString();
//        String password = inputPassword.getText().toString();
//        UserFunctions userFunction = new UserFunctions();
//        JSONObject json = userFunction.loginUser(email, password);
//
//        // check for login response
//        try {
//            if (json.getString(KEY_SUCCESS) != null) {
//                loginErrorMsg.setText("");
//                String res = json.getString(KEY_SUCCESS); 
//                if(Integer.parseInt(res) == 1){
//                    // user successfully logged in
//                    // Store user details in SQLite Database
//                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//                    JSONObject json_user = json.getJSONObject("user");
//                     
//                    // Clear all previous data in database
//                    userFunction.logoutUser(getApplicationContext());
//                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
//                     
//                    // Launch Dashboard Screen
//                    Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
//                     
//                    // Close all views before launching Dashboard
//                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(dashboard);
//                     
//                    // Close Login Screen
//                    finish();
//                }else{
//                    // Error in login
//                    loginErrorMsg.setText("Incorrect username/password");
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
	}
}
