package com.test.david.pager;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.test.david.R;

public class ManualFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the Username and Password from Activity
		Bundle b = getArguments();
		View view = inflater.inflate(R.layout.viewpager_main, container, false);
		// Locate the ViewPager in viewpager_main.xml
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		// Set the ViewPagerAdapter into ViewPager and pass arguments to the viewpageradapter class
		mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),b));
		
		return view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
