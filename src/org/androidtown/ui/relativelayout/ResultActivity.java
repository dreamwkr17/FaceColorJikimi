package org.androidtown.ui.relativelayout;


import java.util.Calendar;
import java.util.Vector;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �Ȼ���Ŵ�� 
 * main menu
 * menu1 _ �Ȼ��м��ϱ�
 * menu2 _ �ǰ��������� 
 * menu3 _ �Ȼ��м� �����丮
 *
 */
public class ResultActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultlayout);
        
        ActionBar ab = getActionBar();
        ab.hide();
        final String result = getIntent().getExtras().getString("result");
        TextView tv_result = (TextView)findViewById(R.id.textView1);
      tv_result.setText("Your result is " + result);
      
      
      final Calendar c = Calendar.getInstance();
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);
      month++;
      
      Log.i("time", year+"��"+month+"��"+day+"�� ");
      
      final String put_result = year+"@"+month+"@"+day;
      
      //���� ��ư 
      Button btn_save = (Button)findViewById(R.id.button1);
      btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		
				
				 ActionBar actionBar = getActionBar();
			        actionBar.hide();
			        
				SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				int resultnum = pref.getInt("resultnum", 0);
				resultnum++;
				String resname = "res"+resultnum;
			
				
				
				
				
				editor.putString(resname, put_result+"@"+result);
				
				
				editor.putInt("resultnum",resultnum);
				editor.commit();

				
				Toast toast = Toast.makeText(ResultActivity.this,"�����丮�� ����� ����Ǿ����ϴ� ^_^",Toast.LENGTH_LONG);
				toast.show();
		
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
