package me.ile.Circles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LifeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.lifeinfo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.shopinfo_title); 
        TextView title=(TextView)findViewById(R.id.shopinfotitle);
        title.setText("菲斯特健身俱乐部");
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
        Button shouchang=(Button)findViewById(R.id.button_weather);
        shouchang.setOnClickListener(new View.OnClickListener(
        		) {

			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "收藏成功!", Toast.LENGTH_LONG).show();
				
			}
		});
     
    }   
}