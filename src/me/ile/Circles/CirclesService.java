package me.ile.Circles;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CirclesService extends Service {
	
	public static final int MSG_Main_Activity = 1;
	public static final int MSG_MyFriends_Activity = 2;
	public static final int MSG_UserInfoCenter_Activity = 3;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
