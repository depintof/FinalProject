/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.david.listview;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.test.david.R;
import com.test.david.async.AsyncTasks;


public class ExpandableAdapter extends BaseExpandableListAdapter {

	private List<Room> roomList;
	private int itemLayoutId;
	private int groupLayoutId;
	private Context ctx;
	
	public ExpandableAdapter(List<Room> roomList, Context ctx) {
		this.itemLayoutId = R.layout.item_layout;
		this.groupLayoutId = R.layout.group_layout;
		this.roomList = roomList;
		this.ctx = ctx;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return roomList.get(groupPosition).getRoomsList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return roomList.get(groupPosition).getRoomsList().get(childPosition).hashCode();
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_layout, parent, false);
		}
		
		final ImageView modeImage = (ImageView) v.findViewById(R.id.mode);
		final SeekBar dimmerControl = (SeekBar) v.findViewById(R.id.dimmer);
		
		final Data info = roomList.get(groupPosition).getRoomsList().get(childPosition);
		
		dimmerControl.setProgress(info.getLightLevel());
		if((info.getRoomAutomatic())||(!info.getRoomControl()))
			dimmerControl.setEnabled(false);
		else
			dimmerControl.setEnabled(true);
		
		modeImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int sendState = 0;
				// TODO Auto-generated method stub
				Log.e("Prueba", "Whatever " + Integer.toString(groupPosition));
				if(info.getRoomAutomatic()){
					roomList.get(groupPosition).getRoomsList().get(childPosition).setRoomAutomatic(false);
					roomList.get(groupPosition).setRoomAutomatic(false);
					sendState = 0;
				}
				else{
					roomList.get(groupPosition).getRoomsList().get(childPosition).setRoomAutomatic(true);
					roomList.get(groupPosition).setRoomAutomatic(true);
					sendState = 1;
				}
				AsyncTasks setAutomatic = new AsyncTasks();
				setAutomatic = new AsyncTasks(null, setAutomatic.FUNCTION_SET_ROOM_AUTOMATIC, null, null, null, null, "1", "myhome_pass", Integer.toString(roomList.get(groupPosition).getRoomID()), Integer.toString(sendState), null); 
//						new AsyncTasks("setRoomAutomatic", "1", "myhome_pass", Integer.toString(roomList.get(groupPosition).getRoomID()), Integer.toString(sendState));
				setAutomatic.execute();
				notifyDataSetChanged();
			}
		});
		
		dimmerControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int newDimmerValue;
				newDimmerValue = seekBar.getProgress();
				roomList.get(groupPosition).getRoomsList().get(childPosition).setLightLevel(newDimmerValue);
				AsyncTasks setLevel = new AsyncTasks();
				setLevel = new AsyncTasks(null, setLevel.FUNCTION_SET_ROOM_LEVEL, null, null, null, null, "1", "myhome_pass", Integer.toString(roomList.get(groupPosition).getRoomID()), null, Integer.toString(roomList.get(groupPosition).getRoomsList().get(childPosition).getLightLevel()));
//						new AsyncTasks("setRoomLevel", "1", "myhome_pass", Integer.toString(roomList.get(groupPosition).getRoomID()), Integer.toString(roomList.get(groupPosition).getRoomsList().get(childPosition).getLightLevel()));
				setLevel.execute();
				notifyDataSetChanged();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return v;
		
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		int size = roomList.get(groupPosition).getRoomsList().size();
		System.out.println("Child for group ["+groupPosition+"] is ["+size+"]");
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return roomList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
	  return roomList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return roomList.get(groupPosition).hashCode();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.group_layout, parent, false);
		}
		
		ImageView modeInfoImage = (ImageView) v.findViewById(R.id.mode_info);
		TextView groupName = (TextView) v.findViewById(R.id.groupName);
			
		Room info = roomList.get(groupPosition);
		
		
		
		if(info.getRoomControl())
			groupName.setText(info.getRoomName() + " (Habilitado)");
		else
			groupName.setText(info.getRoomName() + " (Deshabilitado)");
		
		if(info.getRoomAutomatic())
			modeInfoImage.setImageResource(R.drawable.automatic);
		else
			modeInfoImage.setImageResource(R.drawable.manual);
		
		return v;

	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//public class ExpandableAdapter extends BaseExpandableListAdapter {
//
//	private List<Category> catList;
//	private int itemLayoutId;
//	private int groupLayoutId;
//	private Context ctx;
//	
//	public ExpandableAdapter(List<Category> catList, Context ctx) {
//		
//		this.itemLayoutId = R.layout.item_layout;
//		this.groupLayoutId = R.layout.group_layout;
//		this.catList = catList;
//		this.ctx = ctx;
//	}
//	
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		return catList.get(groupPosition).getItemList().get(childPosition);
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		return catList.get(groupPosition).getItemList().get(childPosition).hashCode();
//	}
//
//	@Override
//	public View getChildView(int groupPosition, int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//		
//		View v = convertView;
//		
//		if (v == null) {
//			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
//				      (Context.LAYOUT_INFLATER_SERVICE);
//			v = inflater.inflate(R.layout.item_layout, parent, false);
//		}
//		
////		TextView itemName = (TextView) v.findViewById(R.id.itemName);
////		TextView itemDescr = (TextView) v.findViewById(R.id.itemDescr);
//		
//		ItemDetail det = catList.get(groupPosition).getItemList().get(childPosition);
//		
////		itemName.setText(det.getName());
////		itemDescr.setText(det.getDescr());
//		
//		return v;
//		
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		int size = catList.get(groupPosition).getItemList().size();
//		System.out.println("Child for group ["+groupPosition+"] is ["+size+"]");
//		return size;
//	}
//
//	@Override
//	public Object getGroup(int groupPosition) {
//		return catList.get(groupPosition);
//	}
//
//	@Override
//	public int getGroupCount() {
//	  return catList.size();
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		return catList.get(groupPosition).hashCode();
//	}
//
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//
//		View v = convertView;
//		
//		if (v == null) {
//			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
//				      (Context.LAYOUT_INFLATER_SERVICE);
//			v = inflater.inflate(R.layout.group_layout, parent, false);
//		}
//		
//		TextView groupName = (TextView) v.findViewById(R.id.groupName);
////		TextView groupDescr = (TextView) v.findViewById(R.id.groupDescr);
//		
//			
//		Category cat = catList.get(groupPosition);
//		
//		groupName.setText(cat.getName());
////		groupDescr.setText(cat.getDescr());
//		
//		return v;
//
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		return true;
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		return true;
//	}
//
//}
//







