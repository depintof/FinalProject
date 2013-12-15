package com.test.david.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetResponse {

	// Constructor
    public InternetResponse() {
		// TODO Auto-generated constructor stub
	}

    // Function to know actual internet connection: 1 if online - 0 if offline
    public boolean checkOnlineState(Context context) {
        ConnectivityManager CManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = CManager.getActiveNetworkInfo();
        if (i == null)
          return false;
        if (!i.isConnected())
          return false;
        if (!i.isAvailable())
          return false;
        return true;
    }
}
