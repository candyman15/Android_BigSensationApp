package com.bigsensation.preference;

import com.dhpreference.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PreferenceMemberLoginActivity extends Activity implements OnClickListener{
	
	private EditText etEmail;
	private EditText etNickName;
	
	private Button btGoJoin;
	
	private Toast joinToast;
	
	private Context mContext;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mContext = this;

		setContentView(R.layout.preferencememberloginactivity);
		
		getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);    // 블러효과 추가
		
		btGoJoin = (Button)findViewById(R.id.preferencememberloginactivity_bt_gojoin);
		btGoJoin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferencememberloginactivity_bt_gojoin:
			intent = new Intent(this,PreferenceMemberJoinActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
		
	}

	
	
	
}
