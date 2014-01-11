package com.test.david.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	final int PAGE_COUNT = 2;
	private String titles[] = new String[] { "Lista", "Mapa" };

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {

			// Open ManualFragmentList.java
		case 0:
			ManualFragmentList mManualFragmentList = new ManualFragmentList();
			return mManualFragmentList;

			// Open FragmentTab2.java
		case 1:
			ManualFragmentMap mManualFragmentMap = new ManualFragmentMap();
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
