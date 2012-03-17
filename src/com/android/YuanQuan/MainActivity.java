package com.android.YuanQuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.YuanQuan.Panel.OnPanelListener;
import com.android.YuanQuan.ScrollLayout.OnViewChangeListener;

import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity  implements OnClickListener,
android.view.View.OnKeyListener,OnPanelListener {

	private Button b1;
	private ListView list;
	private ListView mainList;
	private EditText mUserText;
	private Panel bottomPanel;
	private Panel topPanel;
	private ImageView[] mImageViews;
	private int mViewCount;
	private int mCurSel;
	private ScrollLayout mScrollLayout;



	private ArrayAdapter<String> mAdapter;

	private ArrayList<String> mStrings = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("", "asdasdas");
			}
		});
		list = (ListView) findViewById(R.id.list);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrings);

		list.setAdapter(mAdapter);

		mUserText = (EditText) findViewById(R.id.userText2);

		mUserText.setOnClickListener(this);
		mUserText.setOnKeyListener(this);

        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
			HashMap<String, Object> user = new HashMap<String, Object>();
			user.put("typeimg", R.drawable.type);
			user.put("activitytitle", this.getString(R.string.test).toString());
			if(i==2 || i==4)
			{
				user.put("activityposters", R.drawable.location_bg);
			}
			users.add(user);
		}

        mainList = (ListView) findViewById(R.id.mainlist);
        
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                users,
                R.layout.activityitem,
                new String[] { "typeimg", "activitytitle", "activityposters" },
                new int[] { R.id.activity_type, R.id.activity_title, R.id.activity_posters });
        mainList.setAdapter(saImageItems);
        init();
	}
	private void init() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayoutTest);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout);
		mViewCount = mScrollLayout.getChildCount();
		mImageViews = new ImageView[mViewCount];
		for (int i = 0; i < mViewCount; i++) {
			mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
			mImageViews[i].setEnabled(true);
			mImageViews[i].setOnClickListener(this);
			mImageViews[i].setTag(i);
		}
		mCurSel = 1;
		mImageViews[mCurSel].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(new OnViewChangeListener(){
			@Override
			public void OnViewChange(int view) {
				setCurPoint(view);
			}
			
		});
		Log.v("@@@@@@", "this is in  SwitchViewDemoActivity init()");
	}

	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
			return;
		}
		mImageViews[mCurSel].setEnabled(true);
		mImageViews[index].setEnabled(false);
		mCurSel = index;
	}



	public void onClick(View v) {
		sendText();
	}

	private void sendText() {
		String text = mUserText.getText().toString();
		mAdapter.add(text);
		mUserText.setText(null);
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_CENTER:
			case KeyEvent.KEYCODE_ENTER:
				sendText();
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_T) {
			topPanel.setOpen(!topPanel.isOpen(), false);
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_B) {
			bottomPanel.setOpen(!bottomPanel.isOpen(), true);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onPanelClosed(Panel panel) {
		String panelName = getResources().getResourceEntryName(panel.getId());
		Log.d("TestPanels", "Panel [" + panelName + "] closed");
	}

	public void onPanelOpened(Panel panel) {
		String panelName = getResources().getResourceEntryName(panel.getId());
		Log.d("TestPanels", "Panel [" + panelName + "] opened");
	}
}
