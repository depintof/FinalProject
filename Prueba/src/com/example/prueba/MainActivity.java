package com.example.prueba;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	// The data to show
//	List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();
	
	List<String> planetsList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 initList();
		 // We get the ListView component from the layout
		 ListView lv = (ListView) findViewById(R.id.listView);
		     
		 // This is a simple adapter that accepts as parameter
		 // Context
		 // Data list
		 // The row layout that is used during the row creation
		 // The keys used to retrieve the data
		 // The View id used to show the data. The key number and the view id must match
//		 SimpleAdapter simpleAdpt = new SimpleAdapter(this, planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
//		 lv.setAdapter(simpleAdpt);
		 
		 ArrayAdapter aAdpt= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, planetsList);
		 lv.setAdapter(aAdpt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
//	 private void initList() {
//		 // We populate the planets
//		 planetsList.add(createPlanet("planet", "Mercury"));
//		 planetsList.add(createPlanet("planet", "Venus"));
//		 planetsList.add(createPlanet("planet", "Mars"));
//		 planetsList.add(createPlanet("planet", "Jupiter"));
//		 planetsList.add(createPlanet("planet", "Saturn"));
//		 planetsList.add(createPlanet("planet", "Uranus"));
//		 planetsList.add(createPlanet("planet", "Neptune"));
//	 }
//		 
//	 private HashMap<String, String> createPlanet(String key, String name) {
//		 HashMap<String, String> planet = new HashMap<String, String>();
//		 planet.put(key, name);
//		 return planet;
//	 }
	
	private void initList() {
		// We populate the planets
		planetsList.add("Mercury");
		planetsList.add("Venus");
		planetsList.add("Mars");
		planetsList.add("Jupiter");
		planetsList.add("Saturn");
		planetsList.add("Uranus");
		planetsList.add("Neptune");
		
		planetsList.add("Mercury");
		planetsList.add("Venus");
		planetsList.add("Mars");
		planetsList.add("Jupiter");
		planetsList.add("Saturn");
		planetsList.add("Uranus");
		planetsList.add("Neptune");
		planetsList.add("Mercury");
		planetsList.add("Venus");
		planetsList.add("Mars");
		planetsList.add("Jupiter");
		planetsList.add("Saturn");
		planetsList.add("Uranus");
		planetsList.add("Neptune");
		planetsList.add("Mercury");
		planetsList.add("Venus");
		planetsList.add("Mars");
		planetsList.add("Jupiter");
		planetsList.add("Saturn");
		planetsList.add("Uranus");
		planetsList.add("Neptune");
		planetsList.add("Mercury");
		planetsList.add("Venus");
		planetsList.add("Mars");
		planetsList.add("Jupiter");
		planetsList.add("Saturn");
		planetsList.add("Uranus");
		planetsList.add("Neptune");
	}
	
	
//	public class PlanetAdapter extends ArrayAdapter<Planet> {
//		 
//		private List<Planet> planetList;
//		private Context context;
//		 
//		public PlanetAdapter(List<Planet> planetList, Context ctx) {
//		    super(ctx, R.layout.row_layout, planetList);
//		    this.planetList = planetList;
//		    this.context = ctx;
//		}
//		 
//		public View getView(int position, View convertView, ViewGroup parent) {
//		     
//		    // First let's verify the convertView is not null
//		    if (convertView == null) {
//		        // This a new view we inflate the new layout
//		        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		        convertView = inflater.inflate(R.layout.row_layout, parent, false);
//		    }
//		        // Now we can fill the layout with the right values
//		        TextView tv = (TextView) convertView.findViewById(R.id.name);
//		        TextView distView = (TextView) convertView.findViewById(R.id.dist);
//		        Planet p = planetList.get(position);
//		 
//		        tv.setText(p.getName());
//		        distView.setText("" + p.getDistance());
//		     
//		     
//		    return convertView;
//	}
	
	
}
