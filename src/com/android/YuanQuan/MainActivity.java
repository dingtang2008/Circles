package com.android.YuanQuan;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.YuanQuan.Panel.OnPanelListener;
import com.android.YuanQuan.ScrollLayout.OnViewChangeListener;

import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class MainActivity extends Activity  implements OnClickListener,
android.view.View.OnKeyListener,OnPanelListener, OnItemClickListener  {

	//private Button b1;
	//private ListView list;
	private ListView mainList;
	//private EditText mUserText;
	private LinearLayout userView;
	
	private View titleView;
	private View headerView;
	private View loginView;
	private View userInfoView;
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
	private ScrollLayout mScrollLayout;
	ArrayList<HashMap<String, Object>> lstImageItem;
    //just for test
    ArrayList<HashMap<String, Object>> users;
	private static final String PANEL_CONTENT_IMAGE_KEY = "ItemImage";
	private static final String PANEL_CONTENT_TEXT_KEY = "ItemText";
	   private static int[] mainInfolistItems = {R.string.my_favor_activity,R.string.my_orginized_activity,R.string.my_joined_activity,R.string.my_favor_shops};
	   private static int[] mainInfolistItemsNum = {30,8,12,6};
	   private static int[] extraInfolistItems = {R.string.my_friends,R.string.my_circles};
	   private static int[] extraInfolistItemsNum = {229,11};

    private Integer[] mImageIds = {
            R.drawable.act_all, R.drawable.act_suggest,
            R.drawable.act_music, R.drawable.act_life,
            R.drawable.act_sport, R.drawable.act_travel,
            R.drawable.act_study, R.drawable.act_buy,
            R.drawable.act_other,
    };
    
    private Integer[] mStringIds = {
            R.string.str_act_all, R.string.str_act_suggest,
            R.string.str_act_music, R.string.str_act_life,
            R.string.str_act_sport, R.string.str_act_travel,
            R.string.str_act_study, R.string.str_act_buy,
            R.string.str_act_other,
    };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
		
		mPanelContent = (GridView) findViewById(R.id.panel_content);
		//生成动态数组，并且转入数据  
	     lstImageItem = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<9;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put(PANEL_CONTENT_IMAGE_KEY, mImageIds[i]);//添加图像资源的ID  
		    map.put(PANEL_CONTENT_TEXT_KEY, getString(mStringIds[i]));//按序号做ItemText  
	        lstImageItem.add(map);  
	      }  
	      //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应  
	      SimpleAdapter mPanelAdapter = new SimpleAdapter(this, //没什么解释  
	                                                lstImageItem,//数据来源   
	                                                R.layout.content,//content的XML实现  
	                                                  
	                                                //动态数组与ImageItem对应的子项          
	                                                new String[] {PANEL_CONTENT_IMAGE_KEY,PANEL_CONTENT_TEXT_KEY},   
	                                                  
	                                                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	                                                new int[] {R.id.ItemImage,R.id.ItemText});  
	      //添加并且显示  
	      mPanelContent.setAdapter(mPanelAdapter);  
	      //添加消息处理  
	      mPanelContent.setOnItemClickListener(this);  

	      //just for test
        users = new ArrayList<HashMap<String, Object>>();
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
	      //just for test

        mainList = (ListView) findViewById(R.id.mainlist);
        mainList.setAdapter(new mainListAdapter(this));
        

		headerImage = (ImageView) findViewById(R.id.act_image);
		headerText = (TextView) findViewById(R.id.act_name);
		
		ArrayList<HashMap<String, Object>> mainInfolist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < mainInfolistItems.length; i++) {
			HashMap<String, Object> user = new HashMap<String, Object>();
			user.put("itemtitle", getString(mainInfolistItems[i]).toString());
			user.put("itemNums", mainInfolistItemsNum[i]+">");
			mainInfolist.add(user);
		}
        
        SimpleAdapter maininfoItems = new SimpleAdapter(this,
        		mainInfolist,
                R.layout.user_info_list_item,
                new String[] { "itemtitle", "itemNums"},
                new int[] { R.id.userinfolist_itemtitle, R.id.userinfolist_itemnum });
        
        ((ListView) findViewById(R.id.main_userinfo_list)).setAdapter(maininfoItems);
		
        Spinner sp = (Spinner) findViewById(R.id.spinner1);
        String[] mStrings =  {
                "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
                "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
                "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
        };
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings));
        

    	titleView = findViewById(R.id.title);
    	headerView = findViewById(R.id.mheader);
    	panelView = (Panel)findViewById(R.id.topPanel);
		userView = (LinearLayout) findViewById(R.id.userlayout);
		loginView = userView.findViewById(R.id.login);
		userInfoView = userView.findViewById(R.id.userlayout);
		loginButton = (Button) findViewById(R.id.signin_button);
		loginButton.setOnClickListener(this);
        
	}

	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
			return;
		}
		mImageViews[mCurSel].setEnabled(true);
		mImageViews[index].setEnabled(false);
		mCurSel = index;
		
//		if (mCurSel != 1){
//			titleView.setVisibility(View.GONE);
//			headerView.setVisibility(View.GONE);
//			panelView.setVisibility(View.GONE);
//		} else {
//			titleView.setVisibility(View.VISIBLE);
//			headerView.setVisibility(View.VISIBLE);
//			panelView.setVisibility(View.VISIBLE);
//		}
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
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onClick(View v) {
		//Need add login event here
		loginView.setVisibility(View.GONE);
		userInfoView.setVisibility(View.VISIBLE);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2); 
	    
	    headerImage.setBackgroundResource((Integer)item.get(PANEL_CONTENT_IMAGE_KEY));
	    headerText.setText((String)item.get(PANEL_CONTENT_TEXT_KEY));
		
	}


    static class ViewHolder {
        ImageView actType;
        TextView actTitle;
        ImageView actPosters;
        ImageView actTag;
    }

    private class mainListAdapter extends BaseAdapter {
    	Context mContext;
        private LayoutInflater mInflater;
		public mainListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
			mContext = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return users.size();
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

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.actType = (ImageView) convertView.findViewById(R.id.activity_type);
                holder.actTitle = (TextView) convertView.findViewById(R.id.activity_title);
                holder.actPosters = (ImageView) convertView.findViewById(R.id.activity_posters);
                holder.actTag = (ImageView) convertView.findViewById(R.id.activity_tag);

                
                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }
            
            String title = (String)users.get(position).get("activitytitle");
            Integer typeId = (Integer)users.get(position).get("typeimg");
            Integer postersId = (Integer)users.get(position).get("activityposters");
            holder.actTitle.setText(title);
            holder.actType.setImageResource(typeId);
            if (postersId != null)
            holder.actPosters.setImageResource(postersId);

            return convertView;
		}
    	
    }
}
