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
 * menu1 _ 안색분석하기 정보입력 페이지
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
 	    			
		//사진찍기 버튼 
		Button button1 = (Button) findViewById(R.id.button1);
	    button1.setOnClickListener(new OnClickListener() {
	    	
	    	 @Override
	    	public void onClick(View v) {
	    		 // 사용자 입력 정보 저장 Editable userName = et1.getText(); //이름 저장
	    		 EditText et1 = (EditText) findViewById(R.id.editText1);
	 	    	user_name =  et1.getText().toString();
	 	    	
	 	   	
	 	    	//if user name is empty
	 	    	if(user_name.matches("")){
	 	    		Toast toast = Toast.makeText(Menu1_Activity.this,"이름을 입력해 주세요 !",Toast.LENGTH_LONG);
					toast.show();
	 	    	}else{
	 	    		
	 	    		 int selectedId = group.getCheckedRadioButtonId();
	 	    		 RadioButton rb = (RadioButton)findViewById(selectedId);
	 	    		 
	 	 	       
	 	    		 Log.i("test1",rb.getText() + "번이 선택됨 ");
	 	    		
	 	    		 //gender에 따라서 averg color rgb 값을 바꿔준다. 
	 	    		 if(rb.getText().equals("남성")){
	 	    			 	ave_red = 205;
							 ave_green = 161;
							 ave_blue = 136;
	 	    		 }else if(rb.getText().equals("여성")){
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
