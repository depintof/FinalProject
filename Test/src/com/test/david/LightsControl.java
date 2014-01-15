package com.test.david;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.test.david.pager.ManualFragment;
import com.test.david.pager.MenuListAdapter;
//import android.support.v4.view.GravityCompat;

public class LightsControl extends SherlockFragmentActivity {
	
	// Declare Variables
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	Fragment fManual = new ManualFragment();
//	Fragment fragment2 = new Fragment2();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from drawer_main.xml
		setContentView(R.layout.lights_control);
		
		// Get and Pass the data to the other classes
		String username = "";
		String password = "";
		// Get data from the LogIn Activity
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    username = extras.getString("USERNAME");
		    password = extras.getString("PASSWORD");
		}
		Bundle b = new Bundle();
		b.putString("USERNAME", username);
		b.putString("PASSWORD", password);
		fManual.setArguments(b);
		

		// Get the Title
		mTitle = mDrawerTitle = getTitle();

		// Generate title
		title = new String[] { "Control de Luces" , "Configuración" , "Cerrar Sesión"};

		// Generate subtitle
		subtitle = new String[] { "Modifique el estado de sus luces" , "Cambie sus ajustes" , "Salir de la App"};

		// Generate icon
		icon = new int[] { R.drawable.control, R.drawable.tools, R.drawable.log_out };

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.listview_drawer);

		// Pass string arrays to MenuListAdapter
		mMenuAdapter = new MenuListAdapter(LightsControl.this, title, subtitle,
				icon);

		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);

		// Capture listview menu item click
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle navigation drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// ActionBarDrawerToggle ties together the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				// Set the title on the action when drawer open
				getSupportActionBar().setTitle(mDrawerTitle);
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	// Interaction when the home icon is pressed: Opens or closes the list drawer
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	// ListView click listener in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			ft.replace(R.id.content_frame, fManual);
			break;
//		case 1:
//			ft.replace(R.id.content_frame, fragment2);
//			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Get the title followed by the position
		setTitle(title[position]);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {

		FragmentManager manager = getSupportFragmentManager();
		if (manager.getBackStackEntryCount() > 0) {
			// If there are back-stack entries, leave the FragmentActivity
			// implementation take care of them.
			manager.popBackStack();

		} else {
			// Otherwise, ask user if he wants to leave :)
			super.onBackPressed();
		}
	}

}
