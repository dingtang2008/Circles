package me.ile.Circles;

import java.util.ArrayList;
import java.util.HashMap;

import me.ile.Circles.PullToRefreshListView.OnRefreshListener;
import me.ile.Circles.ScrollLayout.OnViewChangeListener;
import me.ile.Panel.BounceInterpolator;
import me.ile.Panel.EasingType.Type;
import me.ile.Panel.Panel;
import me.ile.Panel.Panel.OnPanelListener;

import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
OnPanelListener, OnItemClickListener {


	private Animation mShowAction;
	private Animation mHiddenAction;
	private ListView mainList;
	private Spinner sp;
	private Spinner sp2;
	private boolean isLogin = false;
	private LinearLayout userView;
	private Account mAccount;
	private String strUserName = "";
	private String strPassWord = "";
	private String user_name = "";
	private TextView mLoginUserName;
	private TextView mLoginPassWord;
	private TextView actTitleView;
	private View headerView;
	private View loginView;
	private View userInfoView;
	private View userMessageView;
	private Button loginButton;
	private Panel panelView;
	private Panel bottomPanel;
	private Panel topPanel;
	private GridView mPanelContent;
	private ImageView[] mImageViews;
	private ImageView headerImage;
	private TextView headerText;
	private int mViewCount;
	private int mCurSel;
	private static ScrollLayout mScrollLayout;
	private SharedPreferences mSharePreference;
	private static final String LOGIN_STATE_KEY = "login_state_key";
	private static final String LOGIN_STATE_SHARE_ID = "login_state_id";
	ArrayList<HashMap<String, Object>> lstImageItem;
	ArrayList<HashMap<String, Object>> allactivity;
	ArrayList<HashMap<String, Object>> suggestactivity;
	private static final String PANEL_CONTENT_IMAGE_KEY = "ItemImage";
	private static final String PANEL_CONTENT_TEXT_KEY = "ItemText";
	private static int[] mainInfolistItems = { R.string.my_favor_activity,
		R.string.my_orginized_activity, R.string.my_joined_activity,
		R.string.my_favor_shops };
	private static int[] mainInfolistItemsNum = { 30, 8, 12, 6 };
	private static int[] extraInfolistItems = {R.string.my_friends,R.string.my_circles};
	private static int[] extraInfolistItemsNum = {229,11};

	private Integer[] mImageIds = { R.drawable.act_all, R.drawable.act_suggest,
			R.drawable.act_music, R.drawable.act_life, R.drawable.act_sport,
			R.drawable.act_travel, R.drawable.act_study, R.drawable.act_buy,
			R.drawable.act_other, };

	private Integer[] mImageIdsmall = { R.drawable.act_alls, R.drawable.act_suggests,
			R.drawable.act_musics, R.drawable.act_lifes, R.drawable.act_sports,
			R.drawable.act_travels, R.drawable.act_studys, R.drawable.act_buys,
			R.drawable.act_others, };

	private Integer[] mStringIds = { R.string.str_act_all,
			R.string.str_act_suggest, R.string.str_act_music,
			R.string.str_act_life, R.string.str_act_sport,
			R.string.str_act_travel, R.string.str_act_study,
			R.string.str_act_buy, R.string.str_act_other, };
	private String[] mStrings; 

	// add from zhe.lin
	private ListView lvwCustom ;
	private ImageView ivComposer;
	private RotateAnimation clockwiseAm;
	private RotateAnimation anticlockwiseAm;

	private ImageView ivCamera;
	private TranslateAnimation cameraOutTA;
	private TranslateAnimation cameraInTA;

	private ImageView ivWith;
	private TranslateAnimation withOutTA;
	private TranslateAnimation withInTA;

	private ImageView ivPlace;
	private TranslateAnimation placeOutTA;
	private TranslateAnimation placeInTA;

	private ImageView ivMusic;
	private TranslateAnimation musicOutTA;
	private TranslateAnimation musicInTA;

	private ImageView ivThought;
	private TranslateAnimation thoughtOutTA;
	private TranslateAnimation thoughtInTA;

	private ImageView ivSleep;
	private TranslateAnimation sleepOutTA;
	private TranslateAnimation sleepInTA;

	// add by dingtang 03.20
	private Button sortByPeople;
	private Button sortByTime;
	private long exitTime = 0;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor mEditor;

	private enum extraItem {
		MY_FRIENDS((int) 0),
		MY_CIRCLES((int) 1);

		private int mValue;

		private extraItem(int value) {
			mValue = value;
		}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sharedPreferences=getPreferences(Context.MODE_PRIVATE);
		mEditor=sharedPreferences.edit();
		mAccount = new Account("1", "1");

		mStrings = getResources().getStringArray(R.array.test_school);

		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,   
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,   
				1.0f, Animation.RELATIVE_TO_SELF, 0.0f);   
		mShowAction.setDuration(500); 
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,   
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,   
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,   
				-1.0f);
		mHiddenAction.setDuration(500); 

		init();
		initshoplayout();
		initHeaderView();

	}

	public static ScrollLayout getscroll(){
		return mScrollLayout;
	}

	private void initshoplayout() {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.shopllayout);
		findView();
		/*
		 * TextView listtitleTextView=(TextView)findViewById(R.id.listtitle);
		 * listtitleTextView.setText(R.string.listtitle); ImageView
		 * mapsview=(ImageView)findViewById(R.id.mapsview);
		 * mapsview.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { Intent mapIntent=new Intent();
		 * mapIntent.setClass(MainActivity.this, MapsActivity.class);
		 * startActivity(mapIntent); finish(); } });
		 */
		ImageView mapicon=(ImageView)findViewById(R.id.mapcion);
		mapicon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, MapsActivity.class);
				startActivity(intent);
				
			}
		});
		lvwCustom = (ListView) findViewById(R.id.ListView);
		final ArrayList<HashMap<String, Object>> items = getItems();
		SimpleAdapter adapter = new SimpleAdapter(this, items,
				R.layout.lvw_custom, new String[] { "lvw_custom_img",
				"lvw_custom_name", "lvw_custom_description" },
				new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
				R.id.lvw_custom_description });
		lvwCustom.setAdapter(adapter);

		lvwCustom.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap shopinfo=(HashMap)items.get(arg2);
				if(shopinfo.get("lvw_custom_name")=="凡仔汉堡"){
					Intent shopinfoIntent = new Intent();
					shopinfoIntent.setClass(MainActivity.this,
							FoodActivity.class);
					startActivity(shopinfoIntent);
				}
				if(shopinfo.get("lvw_custom_name")=="银泰百货江东店"){
					Intent shopinfoIntent = new Intent();
					shopinfoIntent.setClass(MainActivity.this,
							ShopActivity.class);
					startActivity(shopinfoIntent);
				}
				if(shopinfo.get("lvw_custom_name")=="菲斯特健身俱乐部"){
					Intent shopinfoIntent = new Intent();
					shopinfoIntent.setClass(MainActivity.this,
							LifeActivity.class);
					startActivity(shopinfoIntent);
				}
				if(shopinfo.get("lvw_custom_name")=="江东区图书馆"){
					Intent shopinfoIntent = new Intent();
					shopinfoIntent.setClass(MainActivity.this,
							StudyActivity.class);
					startActivity(shopinfoIntent);
				}
				if(shopinfo.get("lvw_custom_name")=="明新运动馆"){
					Intent shopinfoIntent = new Intent();
					shopinfoIntent.setClass(MainActivity.this,
							SportActivity.class);
					startActivity(shopinfoIntent);
				}
			}
		});
	}

	private void findView() {
		ivComposer = (ImageView) this.findViewById(R.id.ivComposer);
		ivComposer.setOnClickListener(this);
		clockwiseAm = new RotateAnimation(0, +360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		clockwiseAm.setDuration(300);
		LinearInterpolator lin = new LinearInterpolator();
		clockwiseAm.setInterpolator(lin);
		clockwiseAm.setAnimationListener(clockwiseAmListener);

		anticlockwiseAm = new RotateAnimation(0, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anticlockwiseAm.setDuration(300);
		anticlockwiseAm.setInterpolator(lin);
		anticlockwiseAm.setAnimationListener(anticlockwiseAmListener);

		ivCamera = (ImageView) this.findViewById(R.id.ivCamera);
		ivCamera.setOnClickListener(this);
		cameraOutTA = new TranslateAnimation(Animation.ABSOLUTE, -5.0f,
				Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 240.0f,
				Animation.ABSOLUTE, 10.0f);
		cameraOutTA.setDuration(100);
		cameraInTA = new TranslateAnimation(Animation.ABSOLUTE, 0.0f,
				Animation.ABSOLUTE, -5.0f, Animation.ABSOLUTE, 10.0f,
				Animation.ABSOLUTE, 240.0f);
		cameraInTA.setDuration(100);

		ivWith = (ImageView) this.findViewById(R.id.ivWith);
		ivWith.setOnClickListener(this);
		withOutTA = new TranslateAnimation(Animation.ABSOLUTE, -75f,
				Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 225.0f,
				Animation.ABSOLUTE, 10.0f);
		withOutTA.setDuration(100);
		withInTA = new TranslateAnimation(Animation.ABSOLUTE, 0.0f,
				Animation.ABSOLUTE, -75f, Animation.ABSOLUTE, 10.0f,
				Animation.ABSOLUTE, 225.0f);
		withInTA.setDuration(100);

		ivPlace = (ImageView) this.findViewById(R.id.ivPlace);
		ivPlace.setOnClickListener(this);
		placeOutTA = new TranslateAnimation(Animation.ABSOLUTE, -135f,
				Animation.ABSOLUTE, -10.0f, Animation.ABSOLUTE, 190f,
				Animation.ABSOLUTE, 10.0f);
		placeOutTA.setDuration(100);
		placeInTA = new TranslateAnimation(Animation.ABSOLUTE, -10.0f,
				Animation.ABSOLUTE, -135f, Animation.ABSOLUTE, 10.0f,
				Animation.ABSOLUTE, 190f);
		placeInTA.setDuration(100);

		ivMusic = (ImageView) this.findViewById(R.id.ivMusic);
		ivMusic.setOnClickListener(this);
		musicOutTA = new TranslateAnimation(Animation.ABSOLUTE, -175f,
				Animation.ABSOLUTE, -10.0f, Animation.ABSOLUTE, 130f,
				Animation.ABSOLUTE, 10.0f);
		musicOutTA.setDuration(100);
		musicInTA = new TranslateAnimation(Animation.ABSOLUTE, -10.0f,
				Animation.ABSOLUTE, -175f, Animation.ABSOLUTE, 10.0f,
				Animation.ABSOLUTE, 130f);
		musicInTA.setDuration(100);

		ivThought = (ImageView) this.findViewById(R.id.ivThought);
		ivThought.setOnClickListener(this);
		thoughtOutTA = new TranslateAnimation(Animation.ABSOLUTE, -205f,
				Animation.ABSOLUTE, -10.0f, Animation.ABSOLUTE, 70f,
				Animation.ABSOLUTE, 10.0f);
		thoughtOutTA.setDuration(100);
		thoughtInTA = new TranslateAnimation(Animation.ABSOLUTE, -10.0f,
				Animation.ABSOLUTE, -205f, Animation.ABSOLUTE, 10.0f,
				Animation.ABSOLUTE, 70f);
		thoughtInTA.setDuration(100);

		ivSleep = (ImageView) this.findViewById(R.id.ivSleep);
		ivSleep.setOnClickListener(this);
		sleepOutTA = new TranslateAnimation(Animation.ABSOLUTE, -215f,
				Animation.ABSOLUTE, -10.0f, Animation.ABSOLUTE, 5f,
				Animation.ABSOLUTE, 10.0f);
		sleepOutTA.setDuration(100);
		sleepInTA = new TranslateAnimation(Animation.ABSOLUTE, -10.0f,
				Animation.ABSOLUTE, -215f, Animation.ABSOLUTE, 10.0f,
				Animation.ABSOLUTE, 5f);
		sleepInTA.setDuration(100);
	}

	private void init() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayout);
		topPanel = (Panel) findViewById(R.id.topPanel);
		topPanel.setOnPanelListener(this);
		topPanel.setInterpolator(new BounceInterpolator(Type.OUT));
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
		mScrollLayout.SetOnViewChangeListener(new OnViewChangeListener() {
			@Override
			public void OnViewChange(int view) {
				setCurPoint(view);
			}

		});

		mPanelContent = (GridView) findViewById(R.id.panel_content);
		lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 9; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(PANEL_CONTENT_IMAGE_KEY, mImageIds[i]);
			map.put(PANEL_CONTENT_TEXT_KEY, getString(mStringIds[i]));
			lstImageItem.add(map);
		}
		SimpleAdapter mPanelAdapter = new SimpleAdapter(
				this,
				lstImageItem,
				R.layout.content,

				new String[] { PANEL_CONTENT_IMAGE_KEY, PANEL_CONTENT_TEXT_KEY },

				new int[] { R.id.ItemImage, R.id.ItemText });
		mPanelContent.setAdapter(mPanelAdapter);
		mPanelContent.setOnItemClickListener(this);

		// just for test
		String[] allActStrings = getResources().getStringArray(R.array.all_activity);
		allactivity = new ArrayList<HashMap<String, Object>>();
		suggestactivity = new ArrayList<HashMap<String, Object>>();
		int id = 0;
		for (int i = 0; i < allActStrings.length; i++) {
			id = this.getResources().getIdentifier("test"+i, "drawable", "me.ile.Circles");
			HashMap<String, Object> user = new HashMap<String, Object>();
			//HashMap<String, Object>  suggestItem = new HashMap<String, Object>();
			user.put("typeimg", R.drawable.listicon);
			user.put("activitytitle", allActStrings[i]);
			user.put("activityposters", id);
			allactivity.add(user);
			if (allActStrings[i].contains(getResources().getString(R.string.test))){
//				suggestItem.put("typeimg", R.drawable.listicon);
//				suggestItem.put("activitytitle", allActStrings[i]);
//				suggestItem.put("activityposters", id);
				suggestactivity.add(user);
			}
		}
		// just for test

		mainList = (ListView) findViewById(R.id.mainlist);
		((PullToRefreshListView) mainList).setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});
		mainList.setAdapter(new mainListAdapter(this, 0));


		headerImage = (ImageView) findViewById(R.id.act_image);
		headerText = (TextView) findViewById(R.id.act_name);



		//from zhangjingbo
		ArrayList<HashMap<String, Object>> mainInfolist = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < mainInfolistItems.length; i++) {
			HashMap<String, Object> user = new HashMap<String, Object>();
			user.put("itemtitle", getString(mainInfolistItems[i]).toString());
			user.put("itemNums", mainInfolistItemsNum[i] + ">");
			mainInfolist.add(user);
		}

		SimpleAdapter maininfoItems = new SimpleAdapter(this, mainInfolist,
				R.layout.user_info_list_item, new String[] { "itemtitle",
		"itemNums" }, new int[] { R.id.userinfolist_itemtitle,
				R.id.userinfolist_itemnum });
		ListView mMainInforList =  ((ListView) findViewById(R.id.main_userinfo_list));
		mMainInforList.setAdapter(maininfoItems);
		mMainInforList.setOnItemClickListener(mMainInfoClickHandler);

		ListView mExtraInforList = ((ListView) findViewById(R.id.extra_userinfo_list));
		ArrayList<HashMap<String, Object>> extraInfolist = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < extraInfolistItems.length; i++) {
			HashMap<String, Object> user = new HashMap<String, Object>();
			user.put("itemtitle", getString(extraInfolistItems[i]).toString());
			user.put("itemNums", extraInfolistItemsNum[i]+">");
			extraInfolist.add(user);
		}
		SimpleAdapter extrainfoItems = new SimpleAdapter(this,
				extraInfolist,
				R.layout.user_info_list_item,
				new String[] { "itemtitle", "itemNums"},
				new int[] { R.id.userinfolist_itemtitle, R.id.userinfolist_itemnum });

		mExtraInforList.setAdapter(extrainfoItems);
		mExtraInforList.setOnItemClickListener(mExtraInfoClickHandler);
		final Button edit_button = (Button) findViewById(R.id.userinfo_edit);
		edit_button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message message = new Message();   
				message.what = CirclesService.MSG_UserInfoCenter_Activity;   
				handler.sendMessage(message); 
			}   			
		});

		//from zhangjingbo end

		sp = (Spinner) findViewById(R.id.select_spinner);
		sp2 = (Spinner) findViewById(R.id.select_spinner2);

		spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, mStrings);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(spinnerAdapter);
		sp2.setAdapter(spinnerAdapter);
		sp.setOnItemSelectedListener(spinnerOnItemSelectedListener1);
		sp2.setOnItemSelectedListener(spinnerOnItemSelectedListener2);
		//sp.startAnimation(mShowAction);


		String mLocation = getIntent().getStringExtra("location");

		if (mLocation.equals("")) {
			sp.setSelection(0, true);
			sp2.setSelection(0, true);
		} else {
			for (int i = 0; i < mStrings.length; i++) {
				if (mLocation.equals(mStrings[i])) {
					sp.setSelection(i, true);
					sp2.setSelection(i, true);
				}
			}
		}


		actTitleView = (TextView) findViewById(R.id.act_title);

		sp.setVisibility(View.VISIBLE);
		actTitleView.setVisibility(View.GONE);

		findViewById(R.id.userView).setOnClickListener(this);
		findViewById(R.id.mapView).setOnClickListener(this);
		findViewById(R.id.user_messages).setOnClickListener(this);
		findViewById(R.id.user_messages2).setOnClickListener(this);

		userView = (LinearLayout) findViewById(R.id.userlayout);
		loginView = userView.findViewById(R.id.login);
		userInfoView = userView.findViewById(R.id.userinfo);
		userMessageView = userView.findViewById(R.id.usermessageview);
		mLoginUserName = (TextView) userView.findViewById(R.id.eUsername);
		mLoginPassWord = (TextView) userView.findViewById(R.id.eUserpwd);
		mLoginUserName.addTextChangedListener(mUserNameChangedListener);
		mLoginPassWord.addTextChangedListener(mPasswordChangedListener);
		loginButton = (Button) findViewById(R.id.signin_button);
		loginButton.setOnClickListener(this);


		mSharePreference = this.getSharedPreferences(LOGIN_STATE_SHARE_ID, Context.MODE_PRIVATE);
		isLogin = mSharePreference.getBoolean(LOGIN_STATE_KEY, false);

		if (isLogin){
			loginView.setVisibility(View.GONE);
			userInfoView.setVisibility(View.VISIBLE);
		} else {
			loginView.setVisibility(View.VISIBLE);
			userInfoView.setVisibility(View.GONE);
		}
	}
	ArrayAdapter spinnerAdapter;
	private void initHeaderView() {
		sortByPeople = (Button) findViewById(R.id.sort_by_people);
		sortByTime = (Button) findViewById(R.id.sort_by_time);
		sortByPeople.setOnClickListener(this);
		sortByTime.setOnClickListener(this);
		String temp=sharedPreferences.getString("sort", "null");
		if(temp.equals("sortByPeople"))onClick(sortByPeople);
		if(temp.equals("sortByTime"))onClick(sortByTime);

	}

	OnItemSelectedListener spinnerOnItemSelectedListener1 = new OnItemSelectedListener(){
		@Override
		public void onItemSelected(AdapterView<?> adapter, View view, int position,
				long id) {
			//sp2.setAdapter(spinnerAdapter);
			SharedPreferences locationShare = getSharedPreferences(CirclesActivity.LOCATION_SHARE_ID, Context.MODE_PRIVATE);
			Editor editor = locationShare.edit();
			editor.putString(CirclesActivity.LOCATION_KEY, mStrings[position]);
			editor.commit();

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	OnItemSelectedListener spinnerOnItemSelectedListener2 = new OnItemSelectedListener(){
		@Override
		public void onItemSelected(AdapterView<?> adapter, View view, int position,
				long id) {
			//sp.setAdapter(spinnerAdapter);
			SharedPreferences locationShare = getSharedPreferences(CirclesActivity.LOCATION_SHARE_ID, Context.MODE_PRIVATE);
			Editor editor = locationShare.edit();
			editor.putString(CirclesActivity.LOCATION_KEY, mStrings[position]);
			editor.commit();

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	/////////////////////////zhangjingbo
	private void getUserInfo(String string) {
		//user_name = getIntent().getStringExtra("user_name");

		final TextView userNameText = (TextView) findViewById(R.id.user_name);
		userNameText.setText(string);
	}

	private AdapterView.OnItemClickListener mMainInfoClickHandler = 
			new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView parent, View v,
				int position, long id) {
			sp.setVisibility(View.GONE);
			actTitleView.setVisibility(View.VISIBLE);
			actTitleView.setText(mainInfolistItems[position]);
			Log.i("position", "position = "+position);
			if(position == 3){
				mScrollLayout.snapToScreen(mScrollLayout.mDefaultScreen + 1);
			} else{
				mScrollLayout.snapToScreen(mScrollLayout.mDefaultScreen);
			}
		}

	};

	private AdapterView.OnItemClickListener mExtraInfoClickHandler = 
			new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView parent, View v,
				int position, long id) {
			if(position == extraItem.MY_FRIENDS.mValue)
			{
				Message message = new Message();   
				message.what = CirclesService.MSG_MyFriends_Activity;   
				handler.sendMessage(message); 
			}
		}

	};
	Handler handler = new Handler(){   
		public void handleMessage(Message msg) {   
			switch (msg.what) {   
			case CirclesService.MSG_MyFriends_Activity:
				Intent intent = new Intent(MainActivity.this,MyFriends.class);
				startActivity(intent);
				break;

			default:
				break;
			}   
			super.handleMessage(msg);   
		}   
	};
	/////////////////////////zhangjingbo

	TextWatcher mUserNameChangedListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			strUserName = s.toString();

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	};

	TextWatcher mPasswordChangedListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			strPassWord = s.toString();

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	};

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				;
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// mListItems.addFirst("Added after refresh...");

			// Call onRefreshComplete when the list has been refreshed.
			((PullToRefreshListView) mainList).onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
			return;
		}
		mImageViews[mCurSel].setEnabled(true);
		mImageViews[index].setEnabled(false);
		mCurSel = index;
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
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			mScrollLayout.snapToScreen(mScrollLayout.mDefaultScreen);

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				if (mCurSel == 1)
					Toast.makeText(getApplicationContext(), R.string.back_to_exit,
							Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else
				finish();
			return true;
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		HashMap<String, Object> item = (HashMap<String, Object>) arg0
				.getItemAtPosition(arg2);
		topPanel.setOpen(false, true);
		headerImage.setBackgroundResource(mImageIdsmall[arg2]);
		headerText.setText((String) item.get(PANEL_CONTENT_TEXT_KEY));
		int actid = arg2;
		mainList.setAdapter(new mainListAdapter(this, actid));

	}

	static class ViewHolder {
		ImageView actType;
		TextView actTitle;
		ImageView actPosters;
		ImageView actTag;
		int position;
	}

	int mActId;
	private class mainListAdapter extends BaseAdapter {
		Context mContext;
		private LayoutInflater mInflater;

		public mainListAdapter(Context context, int actid) {
			mInflater = LayoutInflater.from(context);
			mContext = context;
			mActId = actid;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mActId == 0){
				return allactivity.size();
			}
			else if (mActId == 1){
				return suggestactivity.size();
			} 
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.activityitem, null);

				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				holder = new ViewHolder();
				holder.actType = (ImageView) convertView
						.findViewById(R.id.activity_type);
				holder.actTitle = (TextView) convertView
						.findViewById(R.id.activity_title);
				holder.actPosters = (ImageView) convertView
						.findViewById(R.id.activity_posters);
				holder.actPosters.setOnClickListener(MainActivity.this);
				holder.position = position;
				/*holder.actTag = (ImageView) convertView
						.findViewById(R.id.activity_tag);*/

				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}

			if (mActId == 0){
				String title = (String) allactivity.get(position).get("activitytitle");
				Integer typeId = (Integer) allactivity.get(position).get("typeimg");
				Integer postersId = (Integer) allactivity.get(position).get("activityposters");
				holder.actTitle.setText(title);
				holder.actType.setImageResource(typeId);
				//if (postersId != null)
					holder.actPosters.setImageResource(postersId);
			} else if(mActId == 1){
				String title = (String) suggestactivity.get(position).get("activitytitle");
				Integer typeId = (Integer) suggestactivity.get(position).get("typeimg");
				Integer postersId = (Integer) suggestactivity.get(position).get("activityposters");
				holder.actTitle.setText(title);
				holder.actType.setImageResource(typeId);
				//if (postersId != null)
					holder.actPosters.setImageResource(postersId);

			}
			return convertView;
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivComposer:
			if (isClockwise) {
				ivComposer.startAnimation(clockwiseAm);
				ivCamera.startAnimation(cameraOutTA);
				ivCamera.setVisibility(View.VISIBLE);

				withOutTA.setStartOffset(20);
				ivWith.startAnimation(withOutTA);
				ivWith.setVisibility(View.VISIBLE);

				placeOutTA.setStartOffset(40);
				ivPlace.startAnimation(placeOutTA);
				ivPlace.setVisibility(View.VISIBLE);

				musicOutTA.setStartOffset(60);
				ivMusic.startAnimation(musicOutTA);
				ivMusic.setVisibility(View.VISIBLE);

				thoughtOutTA.setStartOffset(80);
				ivThought.startAnimation(thoughtOutTA);
				ivThought.setVisibility(View.VISIBLE);

				sleepOutTA.setStartOffset(100);
				ivSleep.startAnimation(sleepOutTA);
				ivSleep.setVisibility(View.VISIBLE);
			} else {
				ivComposer.startAnimation(anticlockwiseAm);

				ivSleep.startAnimation(sleepInTA);
				ivSleep.setVisibility(View.GONE);

				thoughtInTA.setStartOffset(20);
				ivThought.startAnimation(thoughtInTA);
				ivThought.setVisibility(View.GONE);

				musicInTA.setStartOffset(40);
				ivMusic.startAnimation(musicInTA);
				ivMusic.setVisibility(View.GONE);

				placeInTA.setStartOffset(60);
				ivPlace.startAnimation(placeInTA);
				ivPlace.setVisibility(View.GONE);

				withInTA.setStartOffset(80);
				ivWith.startAnimation(withInTA);
				ivWith.setVisibility(View.GONE);

				cameraInTA.setStartOffset(100);
				ivCamera.startAnimation(cameraInTA);
				ivCamera.setVisibility(View.GONE);
			}
			break;
		case R.id.ivCamera:
			ivComposer.startAnimation(anticlockwiseAm);

			ivSleep.startAnimation(sleepInTA);
			ivSleep.setVisibility(View.GONE);

			thoughtInTA.setStartOffset(20);
			ivThought.startAnimation(thoughtInTA);
			ivThought.setVisibility(View.GONE);

			musicInTA.setStartOffset(40);
			ivMusic.startAnimation(musicInTA);
			ivMusic.setVisibility(View.GONE);

			placeInTA.setStartOffset(60);
			ivPlace.startAnimation(placeInTA);
			ivPlace.setVisibility(View.GONE);

			withInTA.setStartOffset(80);
			ivWith.startAnimation(withInTA);
			ivWith.setVisibility(View.GONE);

			cameraInTA.setStartOffset(100);
			ivCamera.startAnimation(cameraInTA);
			ivCamera.setVisibility(View.GONE);
			
			ArrayList<HashMap<String, Object>> items1 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < 100; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("lvw_custom_img", R.drawable.food);
				map.put("lvw_custom_name", "凡仔汉堡");
				map.put("lvw_custom_description", "聚贤路999弄2号楼");
				items1.add(map);}
			SimpleAdapter adapter = new SimpleAdapter(this, items1,
					R.layout.lvw_custom, new String[] { "lvw_custom_img",
					"lvw_custom_name", "lvw_custom_description" },
					new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
					R.id.lvw_custom_description });
			lvwCustom.setAdapter(adapter);
//			lvwCustom.setOnItemClickListener(new OnItemClickListener() {
//
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					Intent shopinfoIntent = new Intent();
//					shopinfoIntent.putExtra("type", 1);
//					shopinfoIntent.setClass(MainActivity.this,
//							ShopInfoActivity.class);
//					startActivity(shopinfoIntent);
//				}
//			});
//			
			break;
		case R.id.ivWith:
			ivComposer.startAnimation(anticlockwiseAm);

			ivSleep.startAnimation(sleepInTA);
			ivSleep.setVisibility(View.GONE);

			thoughtInTA.setStartOffset(20);
			ivThought.startAnimation(thoughtInTA);
			ivThought.setVisibility(View.GONE);

			musicInTA.setStartOffset(40);
			ivMusic.startAnimation(musicInTA);
			ivMusic.setVisibility(View.GONE);

			placeInTA.setStartOffset(60);
			ivPlace.startAnimation(placeInTA);
			ivPlace.setVisibility(View.GONE);

			withInTA.setStartOffset(80);
			ivWith.startAnimation(withInTA);
			ivWith.setVisibility(View.GONE);

			cameraInTA.setStartOffset(100);
			ivCamera.startAnimation(cameraInTA);
			ivCamera.setVisibility(View.GONE);
			
			ArrayList<HashMap<String, Object>> items2 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < 100; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("lvw_custom_img", R.drawable.life);
				map.put("lvw_custom_name", "明新运动馆");
				map.put("lvw_custom_description", "卖鱼路109号翠柏路汪弄社区内");
				items2.add(map);}
			SimpleAdapter adapter2 = new SimpleAdapter(this, items2,
					R.layout.lvw_custom, new String[] { "lvw_custom_img",
					"lvw_custom_name", "lvw_custom_description" },
					new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
					R.id.lvw_custom_description });
			lvwCustom.setAdapter(adapter2);
			
//			lvwCustom.setOnItemClickListener(new OnItemClickListener() {
//
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					Intent shopinfoIntent = new Intent();
//					shopinfoIntent.putExtra("type", 2);
//					shopinfoIntent.setClass(MainActivity.this,
//							ShopInfoActivity.class);
//					startActivity(shopinfoIntent);
//					
//				}
//			});
			break;
		case R.id.ivPlace:
			ivComposer.startAnimation(anticlockwiseAm);

			ivSleep.startAnimation(sleepInTA);
			ivSleep.setVisibility(View.GONE);

			thoughtInTA.setStartOffset(20);
			ivThought.startAnimation(thoughtInTA);
			ivThought.setVisibility(View.GONE);

			musicInTA.setStartOffset(40);
			ivMusic.startAnimation(musicInTA);
			ivMusic.setVisibility(View.GONE);

			placeInTA.setStartOffset(60);
			ivPlace.startAnimation(placeInTA);
			ivPlace.setVisibility(View.GONE);

			withInTA.setStartOffset(80);
			ivWith.startAnimation(withInTA);
			ivWith.setVisibility(View.GONE);

			cameraInTA.setStartOffset(100);
			ivCamera.startAnimation(cameraInTA);
			ivCamera.setVisibility(View.GONE);
			
			ArrayList<HashMap<String, Object>> items3 = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < 100; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("lvw_custom_img", R.drawable.life);
				map.put("lvw_custom_name", "菲斯特健身俱乐部");
				map.put("lvw_custom_description", "江东区江南路642号");
				items3.add(map);}
			SimpleAdapter adapter3= new SimpleAdapter(this, items3,
					R.layout.lvw_custom, new String[] { "lvw_custom_img",
					"lvw_custom_name", "lvw_custom_description" },
					new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
					R.id.lvw_custom_description });
			lvwCustom.setAdapter(adapter3);
//			lvwCustom.setOnItemClickListener(new OnItemClickListener() {
//
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					Intent shopinfoIntent = new Intent();
//					shopinfoIntent.putExtra("type", 3);
//					shopinfoIntent.setClass(MainActivity.this,
//							ShopInfoActivity.class);
//					startActivity(shopinfoIntent);
//					
//				}
//			});
//			
			break;
		case R.id.ivThought:
			ivComposer.startAnimation(anticlockwiseAm);

			ivSleep.startAnimation(sleepInTA);
			ivSleep.setVisibility(View.GONE);

			thoughtInTA.setStartOffset(20);
			ivThought.startAnimation(thoughtInTA);
			ivThought.setVisibility(View.GONE);

			musicInTA.setStartOffset(40);
			ivMusic.startAnimation(musicInTA);
			ivMusic.setVisibility(View.GONE);

			placeInTA.setStartOffset(60);
			ivPlace.startAnimation(placeInTA);
			ivPlace.setVisibility(View.GONE);

			withInTA.setStartOffset(80);
			ivWith.startAnimation(withInTA);
			ivWith.setVisibility(View.GONE);

			cameraInTA.setStartOffset(100);
			ivCamera.startAnimation(cameraInTA);
			ivCamera.setVisibility(View.GONE);
			
			ArrayList<HashMap<String, Object>> items4= new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < 100; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("lvw_custom_img", R.drawable.study);
				map.put("lvw_custom_name", "江东区图书馆");
				map.put("lvw_custom_description", "桑田路七二二弄16号");
				items4.add(map);}
			SimpleAdapter adapter4 = new SimpleAdapter(this, items4,
					R.layout.lvw_custom, new String[] { "lvw_custom_img",
					"lvw_custom_name", "lvw_custom_description" },
					new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
					R.id.lvw_custom_description });
			lvwCustom.setAdapter(adapter4);
//			lvwCustom.setOnItemClickListener(new OnItemClickListener() {
//
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					Intent shopinfoIntent = new Intent();
//					shopinfoIntent.putExtra("type", 4);
//					shopinfoIntent.setClass(MainActivity.this,
//							ShopInfoActivity.class);
//					startActivity(shopinfoIntent);
//					
//				}
//			});
			break;
		case R.id.ivMusic:
			ivComposer.startAnimation(anticlockwiseAm);

			ivSleep.startAnimation(sleepInTA);
			ivSleep.setVisibility(View.GONE);

			thoughtInTA.setStartOffset(20);
			ivThought.startAnimation(thoughtInTA);
			ivThought.setVisibility(View.GONE);

			musicInTA.setStartOffset(40);
			ivMusic.startAnimation(musicInTA);
			ivMusic.setVisibility(View.GONE);

			placeInTA.setStartOffset(60);
			ivPlace.startAnimation(placeInTA);
			ivPlace.setVisibility(View.GONE);

			withInTA.setStartOffset(80);
			ivWith.startAnimation(withInTA);
			ivWith.setVisibility(View.GONE);

			cameraInTA.setStartOffset(100);
			ivCamera.startAnimation(cameraInTA);
			ivCamera.setVisibility(View.GONE);
			
			
			ArrayList<HashMap<String, Object>> items5= new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < 100; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("lvw_custom_img", R.drawable.shop);
				map.put("lvw_custom_name", "银泰百货江东店");
				map.put("lvw_custom_description", "世纪东方商业广场64号");
				items5.add(map);}
			SimpleAdapter adapter5 = new SimpleAdapter(this, items5,
					R.layout.lvw_custom, new String[] { "lvw_custom_img",
					"lvw_custom_name", "lvw_custom_description" },
					new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
					R.id.lvw_custom_description });
			lvwCustom.setAdapter(adapter5);
//			lvwCustom.setOnItemClickListener(new OnItemClickListener() {
//
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					Intent shopinfoIntent = new Intent();
//					shopinfoIntent.putExtra("type", 5);
//					shopinfoIntent.setClass(MainActivity.this,
//							ShopInfoActivity.class);
//					startActivity(shopinfoIntent);
//					
//				}
//			});
			
			break;
		case R.id.ivSleep:
			ivComposer.startAnimation(anticlockwiseAm);

			ivSleep.startAnimation(sleepInTA);
			ivSleep.setVisibility(View.GONE);

			thoughtInTA.setStartOffset(20);
			ivThought.startAnimation(thoughtInTA);
			ivThought.setVisibility(View.GONE);

			musicInTA.setStartOffset(40);
			ivMusic.startAnimation(musicInTA);
			ivMusic.setVisibility(View.GONE);

			placeInTA.setStartOffset(60);
			ivPlace.startAnimation(placeInTA);
			ivPlace.setVisibility(View.GONE);

			withInTA.setStartOffset(80);
			ivWith.startAnimation(withInTA);
			ivWith.setVisibility(View.GONE);

			cameraInTA.setStartOffset(100);
			ivCamera.startAnimation(cameraInTA);
			ivCamera.setVisibility(View.GONE);
			
			ArrayList<HashMap<String, Object>> items = getItems();
			SimpleAdapter adapter6 = new SimpleAdapter(this, items,
					R.layout.lvw_custom, new String[] { "lvw_custom_img",
					"lvw_custom_name", "lvw_custom_description" },
					new int[] { R.id.lvw_custom_img, R.id.lvw_custom_name,
					R.id.lvw_custom_description });
			lvwCustom.setAdapter(adapter6);
			break;
		case R.id.signin_button:
			Log.i("test", "strUserName ="+strUserName);
			Log.i("test", "strPassWord ="+strPassWord);
			if (!TextUtils.isEmpty(strUserName) && !TextUtils.isEmpty(strPassWord)){
				Account adminAccount = new Account(strUserName, strPassWord);

				if (mAccount.equals(adminAccount)){
					loginView.setVisibility(View.GONE);
					userInfoView.setVisibility(View.VISIBLE);

					final EditText use_name = (EditText) findViewById(R.id.eUsername);
					getUserInfo(use_name.getText().toString());
					isLogin = true;
				} else {
					isLogin = false;
					Toast.makeText(this, "Wrong usearname or passward", Toast.LENGTH_SHORT).show();
				}
			} else {
				isLogin = false;
				Toast.makeText(this, "Wrong usearname or passward", Toast.LENGTH_SHORT).show();
			}
			Editor editor = mSharePreference.edit();
			editor.putBoolean(LOGIN_STATE_KEY, isLogin);
			editor.commit();
			break;
		case R.id.userView:
			mScrollLayout.snapToScreen(0);
			break;
		case R.id.mapView:
			mScrollLayout.snapToScreen(2);
			break;
		case R.id.user_messages:
			if (!isMessagesShow){
				isMessagesShow = true;
				userInfoView.startAnimation(mHiddenAction);
				userInfoView.setVisibility(View.GONE);
				userMessageView.startAnimation(mShowAction);
				userMessageView.setVisibility(View.VISIBLE);
			} 
			break;
		case R.id.user_messages2:
			if (isMessagesShow){
				userMessageView.startAnimation(mHiddenAction);
				userMessageView.setVisibility(View.GONE);
				userInfoView.startAnimation(mShowAction);
				userInfoView.setVisibility(View.VISIBLE);
				isMessagesShow = false;
			}
			break;
		case R.id.activity_posters:
			//            Intent intent = new Intent(MainActivity.this, ImageShow.class);
			//            Bundle bundle = new Bundle();
			//           // bundle.putString("imgSrc", imgSrc);
			//            intent.putExtras(bundle);
			//            startActivity(intent);
			int position = mainList.getPositionForView((View)v.getParent()) - 1;

			Log.i("test", "position = "+position);

			Dialog dialog = new Dialog(MainActivity.this, R.style.MyDialog);
			dialog.setContentView(R.layout.image_dialog);
			if (mActId == 0){
				if(position < allactivity.size()){
					Integer postersId = (Integer) allactivity.get(position).get("activityposters");
					ImageView mImage = (ImageView)dialog.getWindow().findViewById(R.id.imageViewShow);

					//if (postersId != null){
						dialog.getWindow().setBackgroundDrawableResource(postersId);
						dialog.show();
					//}
				}
			}
			else if (mActId == 1){
				if(position < suggestactivity.size()){
					Integer postersId = (Integer) suggestactivity.get(position).get("activityposters");
					ImageView mImage = (ImageView)dialog.getWindow().findViewById(R.id.imageViewShow);

					//if (postersId != null){
						dialog.getWindow().setBackgroundDrawableResource(postersId);
						dialog.show();
					//}
				}
			} 
			break;
		case R.id.sort_by_people:
			sortByPeople.setBackgroundResource(R.drawable.ic_sort_people_press);
			sortByTime.setBackgroundResource(R.drawable.ic_sort_time_normal);
			mEditor.putString("sort", "sortByPeople");
			mEditor.commit();
			break;
		case R.id.sort_by_time:
			sortByTime.setBackgroundResource(R.drawable.ic_sort_time_press);
			sortByPeople
			.setBackgroundResource(R.drawable.ic_sort_people_normal);
			mEditor.putString("sort", "sortByTime");
			mEditor.commit();
			break;
		}
	}

	private boolean isMessagesShow = false;
	private boolean isClockwise = true;
	private AnimationListener clockwiseAmListener = new AnimationListener() {
		public void onAnimationEnd(Animation arg0) {
			Matrix matrix = new Matrix();
			matrix.setRotate(45);
			Bitmap source = BitmapFactory.decodeResource(
					MainActivity.this.getResources(),
					R.drawable.composer_icn_plus);
			Bitmap resizedBitmap = Bitmap.createBitmap(source, 0, 0,
					source.getWidth(), source.getHeight(), matrix, true);
			ivComposer.setImageBitmap(resizedBitmap);
			if (source != null && !source.isRecycled())
				source.recycle();
			isClockwise = false;
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
		}
	};
	private AnimationListener anticlockwiseAmListener = new AnimationListener() {
		public void onAnimationEnd(Animation arg0) {
			Bitmap source = BitmapFactory.decodeResource(
					MainActivity.this.getResources(),
					R.drawable.composer_icn_plus);
			ivComposer.setImageBitmap(source);
			isClockwise = true;
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
		}
	};

	private ArrayList<HashMap<String, Object>> getItems() {
		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 100; i++) {
			if(i%5==0){HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lvw_custom_img", R.drawable.food);
			map.put("lvw_custom_name", "凡仔汉堡");
			map.put("lvw_custom_description", "聚贤路999弄2号楼");
			items.add(map);}
			
			if(i%5==1){HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lvw_custom_img", R.drawable.shop);
			map.put("lvw_custom_name", "银泰百货江东店");
			map.put("lvw_custom_description", "世纪东方商业广场64号");
			items.add(map);}
			if(i%5==2){HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lvw_custom_img", R.drawable.life);
			map.put("lvw_custom_name", "菲斯特健身俱乐部");
			map.put("lvw_custom_description", "江东区江南路642号");
			items.add(map);}
			if(i%5==3){HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lvw_custom_img", R.drawable.study);
			map.put("lvw_custom_name", "江东区图书馆");
			map.put("lvw_custom_description", "桑田路七二二弄16号");
			items.add(map);}
			if(i%5==4){HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lvw_custom_img", R.drawable.sport);
			map.put("lvw_custom_name", "明新运动馆");
			map.put("lvw_custom_description", "卖鱼路109号翠柏路汪弄社区内");
			items.add(map);}
			
		}
		return items;
	}

}
