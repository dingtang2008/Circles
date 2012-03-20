package me.ile.Circles;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MapsActivity extends Activity implements OnGestureListener{
	
	private MapView myView;
    private GestureDetector mGestureDetector;
    
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mGestureDetector = new GestureDetector(this);
		DisplayMetrics display = this.getApplication().getResources().getDisplayMetrics();

		RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(display.widthPixels/2, LayoutParams.WRAP_CONTENT);
	    param2.addRule(RelativeLayout.ALIGN_LEFT);
	    param2.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		Bitmap imageMap = BitmapFactory
				.decodeResource(getResources(), R.drawable.map);

		myView = new MapView(this, imageMap, display.widthPixels,
				display.heightPixels);
		myView.setClickable(true);
		myView.setOnTouchListener(touchListener);
		
		GestureOverlayView total = new GestureOverlayView(this);
		total.setGestureVisible(false);
		total.setLayoutParams(param1);
		total.addView(myView);
		

		
		setContentView(total);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title); 
		
		ImageView listView=(ImageView)findViewById(R.id.mapsview);
		listView.setImageResource(R.drawable.list_1);
		TextView maptitleTextView=(TextView)findViewById(R.id.listtitle);
		maptitleTextView.setText(R.string.maptitle);
		
		ImageView mapsview=(ImageView)findViewById(R.id.mapsview);
        mapsview.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			/*	Intent mapIntent=new Intent();
				mapIntent.setClass(MapsActivity.this, ShopListActivity.class);
				startActivity(mapIntent);*/
				finish();
				
				
			}
		});

	}


	protected void onResume() {
		super.onResume();
	}


	protected void onPause() {
		super.onPause();
	}
	
	private OnTouchListener touchListener = new OnTouchListener(){

	
		public boolean onTouch(View v, MotionEvent event) {
			return mGestureDetector.onTouchEvent(event);
		}
		
	};
	
	static Bitmap bufferImageMap;


	public boolean onDown(MotionEvent e) {
		return false;
	}


	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}
	
	
	
	
	public void onLongPress(MotionEvent e) {
	}

	
	public boolean onScroll(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY) {
		myView.handleScroll(distanceX, distanceY);
		return true;
	}

	
	public void onShowPress(MotionEvent e) {
		
	}


	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}