package com.android.YuanQuan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import java.util.Timer;   
import java.util.TimerTask;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

public class YuanQuanActivity extends Activity {
	
	private Drawable mDrawableBg;
	ProgressDialog pd;
	ImageView iv = null;
	AnimationDrawable anim = null;
	
	Handler handler = new Handler(){   
		public void handleMessage(Message msg) {   
		    switch (msg.what) {   
		       case YuanQuanService.MSG_Main_Activity:
		    	   Intent intent = new Intent(YuanQuanActivity.this,MainActivity.class);
		    	  // pd.dismiss();
		           startActivity(intent);
		           anim.stop();
		           break;
		       
		       default:
		    	   break;
		    }   
		    super.handleMessage(msg);   
		}   
	};
	
	TimerTask task = new TimerTask(){   
		public void run() {   
		    Message message = new Message();   
		    message.what = YuanQuanService.MSG_Main_Activity;   
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
        
        /*
        pd = ProgressDialog.show(this, "璇风◢鍚�.",
				"姝ｅ湪鏀堕泦浣犵殑涓汉淇℃伅...", true, false);*/
        timer.schedule(task, 3000);
        
        iv = (ImageView)findViewById(R.id.anminationtest);
        iv.setBackgroundResource(R.anim.loading);
        Object ob= iv.getBackground();
        anim = (AnimationDrawable)ob;
        anim.start();
    }
    
    /** Called when the activity is focused. */
    public void onWindowFocus()
    {
    }
}