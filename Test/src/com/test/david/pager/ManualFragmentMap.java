package com.test.david.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.test.david.R;

public class ManualFragmentMap extends SherlockFragment {
	
	// Layout associations
	TextView homeNameMap;			// TextView - Home Name

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.manual_fragment_map, container, false);
		
		homeNameMap = (TextView) view.findViewById(R.id.house_name_map);
		
		return view;
	}
}