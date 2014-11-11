package org.androidtown.ui.relativelayout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * 
 * menu1 _ 안색분석하기 정보입력 페이지
 * 
 */
public class Menu2_Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu2_layout);
	
		 ActionBar actionBar = getActionBar();
	        actionBar.hide();

		       
		        ImageButton redface = (ImageButton) findViewById(R.id.redface);
		        redface.setOnClickListener(new OnClickListener() {
		        	public void onClick(View v) {
		        		setContentView(R.layout.red_info);
		        	}
		        });

		        
		        ImageButton whiteface = (ImageButton) findViewById(R.id.redface);
		        whiteface.setOnClickListener(new OnClickListener() {
		        	public void onClick(View v) {
		        		setContentView(R.layout.red_info);
		        	}
		        });
		        
		        
		        
		        
		        
		        
		        
		        
		    }

		 
	
}
