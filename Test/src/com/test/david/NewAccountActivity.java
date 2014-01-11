package com.test.david;

import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.david.async.AsyncTasks;
import com.test.david.library.InternetResponse;

public class NewAccountActivity extends Activity implements OnClickListener{
	
	// Widgets variables
	EditText inputFullName;
	EditText inputUserName;
	EditText inputPassword;
    EditText inputEmail;
    EditText inputHomeID;
    EditText inputHomePassword;
    Button NewAccount;
    
    // Async createAccount Function to be called
 	String createAccountFunction = "createAccount";
    
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
		InternetResponse internetResponse = new InternetResponse();
		if(internetResponse.checkOnlineState(getApplicationContext())==false){
            Toast.makeText(getBaseContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
		}
		else{
			// here goes your network call
		    String fullName = URLEncoder.encode(inputFullName.getText().toString());
		    String username = URLEncoder.encode(inputUserName.getText().toString());
		    String password = URLEncoder.encode(inputPassword.getText().toString());
		    String email = URLEncoder.encode(inputEmail.getText().toString());
		    String homeID = URLEncoder.encode(inputHomeID.getText().toString());
		    String homePassword = URLEncoder.encode(inputHomePassword.getText().toString());
		    final Activity activity = this;
    		AsyncTasks getState = new AsyncTasks(activity, createAccountFunction, fullName, username, password, email, homeID, homePassword);
    		getState.execute();
		   			
		}

	}

}
