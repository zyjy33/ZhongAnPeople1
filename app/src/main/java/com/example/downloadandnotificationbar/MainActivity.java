//package com.example.downloadandnotificationbar;
//
//import com.tdotapp.wjwy.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//public class MainActivity extends Activity {
//
//    private Button button;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        button = (Button) findViewById(R.id.bb);
//        final String filePath = Environment.getExternalStorageDirectory() + "/ss";
//        button.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                new UpdateApkThread("http://mobile.waji51.com/upload/201711/07/201711071933550815.apk", filePath, "wjwy.apk",MainActivity.this).start();
//            }
//        });
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//}
