package me.ile.Circles;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
public class ImageShow  extends Activity{
        private ImageView iv;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.imgshow);

                findview();
        }

        /*
         * 初始化控件，加载数据
         */
        private void findview() {
                Intent intent = this.getIntent();
                Bundle bundle = intent.getExtras();
                String imgSrc = bundle.getString("imgSrc");
                //Bitmap bm = BitmapFactory.decodeFile(imgSrc);
                Bitmap bm = returnBitMap(imgSrc); 
                iv = (ImageView) findViewById(R.id.ivbig);
                iv.setImageBitmap(bm);
                iv.setOnClickListener(new ImageView.OnClickListener() {

                        public void onClick(View arg0) {
                                //Toast.makeText(ImageShow.this, "关闭图片", Toast.LENGTH_SHORT).show();
                                finish();
                        }
                });
                

        }
         //获取图片路径函数
          public static Bitmap returnBitMap(String url) {  
                  URL myFileUrl = null;  
                  Bitmap bitmap = null;  
                  try {  
                  myFileUrl = new URL(url);  
                  } catch (MalformedURLException e) {  
                  e.printStackTrace();  
                  }  
                  try {  
                  HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();  
                  conn.setDoInput(true);  
                  conn.connect();  
                  InputStream is = conn.getInputStream();  
                  bitmap = BitmapFactory.decodeStream(is);  
                  is.close();  
                  } catch (IOException e) {  
                  e.printStackTrace();  
                  }  
                  return bitmap;  
                } 
}

