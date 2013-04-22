package com.dh.preference;

import com.dhpreference.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PreferenceTestActivity extends Activity implements OnClickListener{

	private TextView tvTestNumber;
	private TextView tvTestTitle;
	
	private ImageView ivSelect1;
	private ImageView ivSelect2;
	
	private TextView tvSelect1;
	private TextView tvSelect2;
	
	private Button btNext;
	
	private SharedPreferences prefs = null;
	
	int testNum = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencetestactivity);
		
		prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
		testNum = prefs.getInt("testNum", 1);
		
		tvTestNumber = (TextView)findViewById(R.id.preferencetestactivity_tv_testnumber); // 번호
		
		tvTestTitle = (TextView)findViewById(R.id.preferencetestactivity_tv_testtile); // 문제제목
		
		tvSelect1 = (TextView)findViewById(R.id.preferencetestactivity_tv_select1);
		tvSelect2 = (TextView)findViewById(R.id.preferencetestactivity_tv_select2);
		
		ivSelect1 = (ImageView)findViewById(R.id.preferencetestactivity_iv_select1); // 선택이미지1
		ivSelect2 = (ImageView)findViewById(R.id.preferencetestactivity_iv_select2); // 선택이미지2
		
		btNext = (Button)findViewById(R.id.preferencetestactivity_bt_next);
		btNext.setOnClickListener(this);
		
		tvTestNumber.setText(String.valueOf(testNum));
		
		if(testNum == 1)
		{
			tvTestTitle.setText("둘중에 선택하시오.");
			ivSelect1.setBackgroundResource(R.drawable.jajangmen);
			ivSelect2.setBackgroundResource(R.drawable.jjamppong);
			
			tvSelect1.setText("짜장면");
			tvSelect2.setText("짬뽕");
			
		}else if(testNum == 2){
			tvTestTitle.setText("둘중에 좋아하는걸 선택하시오.");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferencetestactivity_bt_next:
			SharedPreferences.Editor ed = prefs.edit();
			ed.putInt("testNum", testNum + 1);			
			ed.commit();
			
			Intent intent = new Intent(this,PreferenceTestActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
		
	}

	
}
