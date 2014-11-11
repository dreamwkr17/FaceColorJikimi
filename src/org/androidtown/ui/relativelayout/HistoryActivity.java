package org.androidtown.ui.relativelayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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

/**
 * 안색지킴이 
 * main menu
 * menu1 _ 안색분석하기
 * menu2 _ 건강정보보기 
 * menu3 _ 안색분석 히스토리
 *
 */
public class HistoryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3_layout);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        ArrayList<HashMap> resultArrList = new ArrayList<HashMap>();
        //저장된 검사 결과 불러오기 
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		int i;
		
		int  resultnum= pref.getInt("resultnum", 0);
		Vector<String> resultArr = new Vector<String>();
		Log.i("resu", resultnum+" ");
		
		for(i=1;i<resultnum+1;i++){
			
			String resname = "res"+i;
			String resvalue = pref.getString(resname, "failed");
			String[] StringArray = resvalue.split("@");
			
			HashMap temp_hm = new HashMap();
			temp_hm.put("year",StringArray[0]);
			temp_hm.put("month",StringArray[1]);
			temp_hm.put("day",StringArray[2]);
			temp_hm.put("result",StringArray[3]);
			
			resultArrList.add(temp_hm);
			String ColorResult = StringArray[3];
			String ColorResult_Ko = "null";
			if(ColorResult.equals("cyan")){
				ColorResult_Ko = "청색 ";
			}else if(ColorResult.equals("red")){
				ColorResult_Ko = "적색 ";
			}else if(ColorResult.equals("orange")){
				ColorResult_Ko = "황색  ";
				}else if(ColorResult.equals("black")){
					ColorResult_Ko = "흑색";
				}else if(ColorResult.equals("white")){
					ColorResult_Ko = "백색";
				}
			
			String resultforListView = StringArray[0]+"/"+StringArray[1]+"/"+StringArray[2]+"         "+ColorResult_Ko;
			resultArr.add(resultforListView);
			Log.i("dd", resvalue);
		}
		
		for(i=0;i<resultArrList.size();i++){
			HashMap temp_hm = resultArrList.get(i);
			String a = (String) temp_hm.get("year");
			Log.i("testtest",(String) temp_hm.get("year"));
			Log.i("testtest",(String) temp_hm.get("month"));
			Log.i("testtest",(String) temp_hm.get("day"));
			Log.i("testtest",(String) temp_hm.get("result"));
			Log.i("testtest","--------------------------------------");
			
			
			
			
		}
		//리스트뷰에 붙이기 
		ListView listview = (ListView)findViewById(R.id.listView1);

		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, resultArr);
	    
	    
	            // Assign adapter to ListView
	            listview.setAdapter(adapter);

      
    }

       
}
