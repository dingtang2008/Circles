package me.ile.Circles;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MyFriends extends Activity {
	
	private static int mMyFriendsItemsImage = R.drawable.default_user_image;
	private static int[] mMyFriendsItemsName = {R.string.friend_name_1,R.string.friend_name_2,R.string.friend_name_3,R.string.friend_name_4,
		                                         R.string.friend_name_5,R.string.friend_name_6,R.string.friend_name_1,R.string.friend_name_2,
		                                         R.string.friend_name_3,R.string.friend_name_4,
		                                         R.string.friend_name_5,R.string.friend_name_6,};
	
	
	private ListView mMyFrieddsList = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	
        setContentView(R.layout.myfriends);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mtitle);
        
        TextView titleText = (TextView)findViewById(R.id.tTitleText);
        titleText.setText(R.string.my_friends);
        
        mMyFrieddsList = ((ListView) findViewById(R.id.my_friends_list));
        ArrayList<HashMap<String, Object>> friendlist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < mMyFriendsItemsName.length; i++) {
			HashMap<String, Object> friend = new HashMap<String, Object>();
			friend.put("friendImage", mMyFriendsItemsImage);
			friend.put("friendNmae",  getString(mMyFriendsItemsName[i]).toString());
			friendlist.add(friend);
		}
        
        SimpleAdapter mFriendsItems = new SimpleAdapter(this,
        		friendlist,
                R.layout.default_list_item,
                new String[] { "friendImage", "friendNmae"},
                new int[] { R.id.iItemImage, R.id.tItemText });
        
        mMyFrieddsList.setAdapter(mFriendsItems);
        
        ImageButton backButton = (ImageButton)findViewById(R.id.back_button);
        backButton.setOnClickListener(backListener);
        ImageButton backMainButton = (ImageButton)findViewById(R.id.home_button);
        backMainButton.setOnClickListener(backMainListener);
    }
    
    private OnClickListener backListener = new OnClickListener()
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
    };
    
    private OnClickListener backMainListener = new OnClickListener()
    {
		@Override
		public void onClick(View v) {
			MainActivity.getscroll().snapToScreen(1);
			finish();
		}
    };

}
