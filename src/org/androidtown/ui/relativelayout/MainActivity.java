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
 * �Ȼ���Ŵ�� 
 * main menu
 * menu1 _ �Ȼ��м��ϱ�
 * menu2 _ �ǰ��������� 
 * menu3 _ �Ȼ��м� �����丮
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        // menu1 _ �Ȼ��м��ϱ� 
        ImageButton button01 = (ImageButton) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Menu1_Activity.class);
				startActivity(intent);
			}
		});

     // menu2 _ �ǰ��������� 
        ImageButton button02 = (ImageButton) findViewById(R.id.button02);
        button02.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this, Menu2_Activity.class);
				startActivity(intent);
        	}
        });

        // menu3 _ �Ȼ��м� �����丮 
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
