package me.ile.Circles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;   
import java.util.TimerTask;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class CirclesActivity extends Activity {

	private Drawable mDrawableBg;
	private ProgressDialog pd;
	private ImageView iv = null;
	private AnimationDrawable anim = null;
	private LocationListener gpsListener=null;  
	private LocationListener networkListner=null;  
	private Location currentLocation;
	private LocationManager locationManager; 
	private SharedPreferences mSharePreference;
	private String mLocationPlace;
	private static final String LOCATION_KEY = "location_key";
	private static final String LOCATION_SHARE_ID = "mLocation";


	private Handler handler = new Handler(){   
		public void handleMessage(Message msg) {   
			switch (msg.what) {   
			case CirclesService.MSG_Main_Activity:
				Intent intent = new Intent(CirclesActivity.this,MainActivity.class);
				intent.putExtra("location", (String)msg.obj);
				// pd.dismiss();
				startActivity(intent);
				// finish();
				anim.stop();
				break;

			default:
				break;
			}   
			super.handleMessage(msg);   
		}   
	};

	private TimerTask task = new TimerTask(){   
		public void run() {   
			Message message = new Message();   
			message.what = CirclesService.MSG_Main_Activity;   
			handler.sendMessage(message);   
		}   
	}; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainview);

		Timer timer = new Timer();   

		mDrawableBg = getResources().getDrawable(R.drawable.location_bg);
		getWindow().setBackgroundDrawable(mDrawableBg);


		pd = ProgressDialog.show(this, "GPS 定位", "GPS 开始定位", true, false);
		//timer.schedule(task, 3000);

		iv = (ImageView)findViewById(R.id.anminationtest);
		iv.setBackgroundResource(R.anim.loading);
		Object ob= iv.getBackground();
		anim = (AnimationDrawable)ob;
		anim.start();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		if (!isNetworkAvailable(this)){
			pd.dismiss();
			Message message = new Message();   
			message.what = YuanQuanService.MSG_Main_Activity;  
			message.obj = "";
			handler.sendMessageDelayed(message, 1000);
			Toast.makeText(this, "No networknow Please set wifi or 3G", Toast.LENGTH_SHORT).show();
		} else {
			mSharePreference = this.getSharedPreferences(LOCATION_SHARE_ID, Context.MODE_PRIVATE);
			mLocationPlace = mSharePreference.getString(LOCATION_KEY, "");
			if (mLocationPlace.equals("")){ 
				registerLocationListener();
			}
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		finish();
	}

	/** Called when the activity is focused. */
	public void onWindowFocus()
	{
	}

	private class MyLocationListner implements LocationListener{  
		@Override  
		public void onLocationChanged(Location location) {  
			// Called when a new location is found by the location provider.  
			Log.v("GPSTEST", "Got New Location of provider:"+location.getProvider());  
			if(currentLocation!=null){  
				if(isBetterLocation(location, currentLocation)){  
					Log.v("GPSTEST", "It's a better location");  
					currentLocation=location;  
					showLocation(location);  
				}  
				else{  
					Log.v("GPSTEST", "Not very good!");  
				}  
			}  
			else{  
				Log.v("GPSTEST", "It's first location");  
				currentLocation=location;  
				showLocation(location); 
			}  
			//移除基于LocationManager.NETWORK_PROVIDER的监听器  
			if(LocationManager.NETWORK_PROVIDER.equals(location.getProvider())){  
				locationManager.removeUpdates(this);  
			}  
		}  
		//后3个方法此处不做处理  
		public void onStatusChanged(String provider, int status, Bundle extras) {  
		}  
		public void onProviderEnabled(String provider) {  
		}  
		public void onProviderDisabled(String provider) {  
		}  
	};  

	private void showLocation(Location location){  

		//纬度  
		Log.v("GPSTEST","Latitude:"+location.getLatitude());  
		//经度  
		Log.v("GPSTEST","Longitude:"+location.getLongitude());  
		//精确度  
		Log.v("GPSTEST","Accuracy:"+location.getAccuracy());  
		//Location还有其它属性，请自行探索  
		Geocoder geocoder=new Geocoder(this); 
		List places = null;
		try {
			//          Thread.sleep(2000);
			places = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
			//          Thread.sleep(2000);
			Log.v("GPSTEST","places size:"+places.size()); 
		} catch (Exception e) {  
			e.printStackTrace();
		}
		String placename = "";
		if (places != null && places.size() > 0) {
			// placename=((Address)places.get(0)).getLocality();
			//一下的信息将会具体到某条街
			//其中getAddressLine(0)表示国家，getAddressLine(1)表示精确到某个区，getAddressLine(2)表示精确到具体的街
			placename = ((Address) places.get(0)).getAddressLine(0) + ", " + System.getProperty("line.separator")
					+ ((Address) places.get(0)).getAddressLine(1) + ", "
					+ ((Address) places.get(0)).getAddressLine(2);
			Toast.makeText(this, "您现在的位置大概是"+placename, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "No network", Toast.LENGTH_LONG).show();
		}
		Log.v("GPSTEST","placename:"+placename); 

		mLocationPlace = "软件学院5";
		Editor editor = mSharePreference.edit();
		editor.putString(LOCATION_KEY, mLocationPlace);
		Message message = new Message();   
		message.what = YuanQuanService.MSG_Main_Activity;  
		message.obj = mLocationPlace;
		handler.sendMessage(message);
	}  

	private void registerLocationListener(){  
		networkListner=new MyLocationListner();  
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, networkListner);  
		gpsListener=new MyLocationListner();  
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, gpsListener);  
	} 

	private static final int CHECK_INTERVAL = 1000 * 30;  
	protected boolean isBetterLocation(Location location,  
			Location currentBestLocation) {  
		if (currentBestLocation == null) {  
			// A new location is always better than no location  
			return true;  
		}  
		// Check whether the new location fix is newer or older  
		long timeDelta = location.getTime() - currentBestLocation.getTime();  
		boolean isSignificantlyNewer = timeDelta > CHECK_INTERVAL;  
		boolean isSignificantlyOlder = timeDelta < -CHECK_INTERVAL;  
		boolean isNewer = timeDelta > 0;  
		// If it's been more than two minutes since the current location,  
		// use the new location  
		// because the user has likely moved  
		if (isSignificantlyNewer) {  
			return true;  
			// If the new location is more than two minutes older, it must  
			// be worse  
		} else if (isSignificantlyOlder) {  
			return false;  
		}  
		// Check whether the new location fix is more or less accurate  
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation  
				.getAccuracy());  
		boolean isLessAccurate = accuracyDelta > 0;  
		boolean isMoreAccurate = accuracyDelta < 0;  
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;  
		// Check if the old and new location are from the same provider  
		boolean isFromSameProvider = isSameProvider(location.getProvider(),  
				currentBestLocation.getProvider());  
		// Determine location quality using a combination of timeliness and  
		// accuracy  
		if (isMoreAccurate) {  
			return true;  
		} else if (isNewer && !isLessAccurate) {  
			return true;  
		} else if (isNewer && !isSignificantlyLessAccurate  
				&& isFromSameProvider) {  
			return true;  
		}  
		return false;  
	}  
	/** Checks whether two providers are the same */  
	private boolean isSameProvider(String provider1, String provider2) {  
		if (provider1 == null) {  
			return provider2 == null;  
		}  
		return provider1.equals(provider2);  
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(gpsListener!=null){  
			locationManager.removeUpdates(gpsListener);  
			gpsListener=null;  
		}  
	}
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
		NetworkInfo mobNetInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						boolean isNetworking = true;
						if (mobNetInfo != null)
						{
							isNetworking = netTest(context);
						}
						return isNetworking;	
					}
				}
			}
		}
		return false;
	}

	public static boolean netTest(Context context) {
		boolean isNetworking = true;
		try{
			URL url = new URL("http://www.baidu.com");
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			Log.i("test","ConnectTimeout1 = "+urlConn.getConnectTimeout());
			urlConn.setConnectTimeout(6000);
			Log.i("test","ConnectTimeout2 = "+urlConn.getConnectTimeout());
			if (urlConn.getResponseCode() != 200)
				isNetworking = false;
			urlConn.connect();   
			urlConn.disconnect();
		} catch (IOException e) {
			isNetworking = false;
		}
		return isNetworking;
	}


}