package com.android.YuanQuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends Activity {

	private MyViewPager awesomePager;
	
	private MyPagerAdapter awesomeAdapter;
	
	private List<View> mListView;
	private LayoutInflater mInflater;
	/** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
       
        awesomeAdapter = new MyPagerAdapter();
        awesomePager = (MyViewPager) findViewById(R.id.awesomepager);
        awesomePager.setAdapter(awesomeAdapter);
        mListView = new ArrayList<View>();
        mInflater = this.getLayoutInflater();

    	mListView.add(mInflater.inflate(R.layout.layout1, null));
    	MainView mMainView = new MainView(this);

        //CircleUtil.getInstence().setMainView(mMainView);
        //CircleUtil.getInstence().init(this);
        //this.startService(intent);
    	
    	mListView.add(mMainView);
    	mListView.add(mInflater.inflate(R.layout.layout3, null));
        awesomePager.setCurrentItem(1);
    }
    
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
    
    private class MyPagerAdapter extends PagerAdapter{

		
		@Override
		public int getCount() {
			return mListView.size();
		}

	    /**
	     * Create the page for the given position.  The adapter is responsible
	     * for adding the view to the container given here, although it only
	     * must ensure this is done by the time it returns from
	     * {@link #finishUpdate()}.
	     *
	     * @param container The containing View in which the page will be shown.
	     * @param position The page position to be instantiated.
	     * @return Returns an Object representing the new page.  This does not
	     * need to be a View, but can be some other container of the page.
	     */
		@Override
		public Object instantiateItem(View collection, int position) {

			((ViewPager) collection).addView(mListView.get(position),0);
			
			return mListView.get(position);

		}

	    /**
	     * Remove a page for the given position.  The adapter is responsible
	     * for removing the view from its container, although it only must ensure
	     * this is done by the time it returns from {@link #finishUpdate()}.
	     *
	     * @param container The containing View from which the page will be removed.
	     * @param position The page position to be removed.
	     * @param object The same object that was returned by
	     * {@link #instantiateItem(View, int)}.
	     */
		@Override
		public void destroyItem(View collection, int position, Object view) {
			//((ViewPager) collection).removeView((View) view);
			((ViewPager) collection).removeView(mListView.get(position));
		}

		
	    /**
	     * Called when the a change in the shown pages has been completed.  At this
	     * point you must ensure that all of the pages have actually been added or
	     * removed from the container as appropriate.
	     * @param container The containing View which is displaying this adapter's
	     * page views.
	     */
		@Override
		public void finishUpdate(View arg0) {}
		

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==((View)object);
		}
    	
    }
}
