package com.android.YuanQuan;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import java.util.ArrayList;
import java.util.HashMap;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainView extends LinearLayout {

	private LayoutInflater mInflater;
	View mainView;
	ImageView dragIcon;
	HeaderView headerview;
	public MainView(Context context) {
		super(context);
        mInflater = LayoutInflater.from(context);
		init(context);
	}

	private void init(Context context ) {
        mainView = mInflater.inflate(R.layout.activitylist, this);
        ListView list = (ListView) mainView.findViewById(R.id.activitylist);
        //headerview = (HeaderView) mainView.findViewById(R.id.header);
        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
			HashMap<String, Object> user = new HashMap<String, Object>();
			user.put("typeimg", R.drawable.type);
			user.put("activitytitle", context.getString(R.string.test).toString());
			if(i==2 || i==4)
			{
				user.put("activityposters", R.drawable.location_bg);
			}
			users.add(user);
		}
        
        SimpleAdapter saImageItems = new SimpleAdapter(context,
                users,
                R.layout.activityitem,
                new String[] { "typeimg", "activitytitle", "activityposters" },
                new int[] { R.id.activity_type, R.id.activity_title, R.id.activity_posters });
        list.setAdapter(saImageItems);
		
	}
    
}