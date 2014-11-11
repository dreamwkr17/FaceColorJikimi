package org.androidtown.ui.relativelayout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 
 * menu1 _ �Ȼ��м��ϱ� �����Է� ������
 * 
 */
public class Menu1_Activity extends Activity {
	static RadioGroup group;
	
	
	static int ave_red ;
	 static int ave_green;
	 static int ave_blue ;
	 static String user_name;
	 static String user_gender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu1_layout);
		 ActionBar actionBar = getActionBar();
	        actionBar.hide();

	        group = (RadioGroup) findViewById(R.id.radioGroup_gender);
 	    			
		//������� ��ư 
		Button button1 = (Button) findViewById(R.id.button1);
	    button1.setOnClickListener(new OnClickListener() {
	    	
	    	 @Override
	    	public void onClick(View v) {
	    		 // ����� �Է� ���� ���� Editable userName = et1.getText(); //�̸� ����
	    		 EditText et1 = (EditText) findViewById(R.id.editText1);
	 	    	user_name =  et1.getText().toString();
	 	    	
	 	   	
	 	    	//if user name is empty
	 	    	if(user_name.matches("")){
	 	    		Toast toast = Toast.makeText(Menu1_Activity.this,"�̸��� �Է��� �ּ��� !",Toast.LENGTH_LONG);
					toast.show();
	 	    	}else{
	 	    		
	 	    		 int selectedId = group.getCheckedRadioButtonId();
	 	    		 RadioButton rb = (RadioButton)findViewById(selectedId);
	 	    		 
	 	 	       
	 	    		 Log.i("test1",rb.getText() + "���� ���õ� ");
	 	    		
	 	    		 //gender�� ���� averg color rgb ���� �ٲ��ش�. 
	 	    		 if(rb.getText().equals("����")){
	 	    			 	ave_red = 205;
							 ave_green = 161;
							 ave_blue = 136;
	 	    		 }else if(rb.getText().equals("����")){
	 	    			ave_red = 211;
						 ave_green =173;
						 ave_blue = 152;
	 	    		 }
	 	    		
	    			Intent intent = new Intent(Menu1_Activity.this, Menu1_Camera_Activity.class);
	    			startActivity(intent);
	    			}
	    		}
	    		 
 
	    	
	    });

	    
	   
	}
	

	
}
