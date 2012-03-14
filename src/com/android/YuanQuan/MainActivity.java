package com.android.YuanQuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.activitylist);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        
        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
			HashMap<String, Object> user = new HashMap<String, Object>();
			user.put("typeimg", R.drawable.type);
			user.put("activitytitle", getString(R.string.test).toString());
			if(i==2 || i==4)
			{
				user.put("activityposters", R.drawable.location_bg);
			}
			users.add(user);
		}
        
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                users,// ÊýŸÝÀŽÔŽ
                R.layout.activityitem,//Ã¿Ò»žöuser xml Ïàµ±ListViewµÄÒ»žö×éŒþ 
                new String[] { "typeimg", "activitytitle", "activityposters" },

                // ·Ö±ð¶ÔÓŠview µÄid
                new int[] { R.id.activity_type, R.id.activity_title, R.id.activity_posters });
        
        ((ListView) findViewById(R.id.activitylist)).setAdapter(saImageItems);
	}

}
