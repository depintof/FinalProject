package com.test.david.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	final int PAGE_COUNT = 2;
	private String titles[] = new String[] { "Lista", "Mapa" };
	// Username and Password
	private Bundle args;

	public ViewPagerAdapter(FragmentManager fm, Bundle b) {
		super(fm);
		args = b;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {

			// Open ManualFragmentList.java
		case 0:
			ManualFragmentList mManualFragmentList = new ManualFragmentList();
			// Set the log-in values to get the data from server for the list
			mManualFragmentList.setLogInValues(args.getString("USERNAME"), args.getString("PASSWORD"));
			return mManualFragmentList;

			// Open FragmentTab2.java
		case 1:
			ManualFragmentMap mManualFragmentMap = new ManualFragmentMap();
			// Set the log-in values to get the data from server for the map
			
			return mManualFragmentMap;
		}
		return null;
	}

	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

}
