package com.test.david;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.david.async.AsyncTasks;
import com.test.david.library.InternetResponse;

public class MainActivity extends Activity implements OnClickListener{
	public String username;
	public String password;
	
	// Widgets variables
	Button logIn;
	EditText usernameInput;
	EditText passwordInput;
	
	// Async logIn Function to be called
	String logInFunction = "logIn";
	
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

	@SuppressWarnings("deprecation")
	public void onClick(View v) {
		if(v == logIn){
			InternetResponse internetResponse = new InternetResponse();
			if(internetResponse.checkOnlineState(getApplicationContext())==false){
	            Toast.makeText(getBaseContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
			}
			else{
				username = URLEncoder.encode(usernameInput.getText().toString());
	    		password = URLEncoder.encode(passwordInput.getText().toString());
	    		final Activity activity = this;
	    		AsyncTasks getState = new AsyncTasks(activity, logInFunction, null, username, password, null, null, null);
	    		getState.execute();
	    		
			}
		}
	}

}
