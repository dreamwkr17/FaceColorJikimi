package org.androidtown.ui.relativelayout;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 안색지킴이 
 * main menu
 * menu1 _ 안색분석하기
 * menu2 _ 건강정보보기 
 * menu3 _ 안색분석 히스토리
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        // menu1 _ 안색분석하기 
        ImageButton button01 = (ImageButton) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Menu1_Activity.class);
				startActivity(intent);
			}
		});

     // menu2 _ 건강정보보기 
        ImageButton button02 = (ImageButton) findViewById(R.id.button02);
        button02.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, Menu2_Activity.class);
				startActivity(intent);
        	}
        });

        // menu3 _ 안색분석 히스토리 
        ImageButton button03 = (ImageButton) findViewById(R.id.button03);
        button03.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, Menu3_HistoryActivity.class);
				startActivity(intent);
				finish();
			        	}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
