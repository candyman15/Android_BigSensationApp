package com.bigsensation.preference;

import com.dhpreference.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PreferenceMemberJoinActivity extends Activity implements OnClickListener{
	
	private EditText etEmail;
	private EditText etNickName;
	
	private Button btJoin;
	
	private Toast joinToast;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mContext = this;

		setContentView(R.layout.preferencememberjoinactivity);
		
		getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
		getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);    // 블러효과 추가
		
		etEmail = (EditText)findViewById(R.id.preferencememberjoinactivity_et_email);
		etNickName = (EditText)findViewById(R.id.preferencememberjoinactivity_et_nickname);
		
		btJoin = (Button)findViewById(R.id.preferencememberjoinactivity_bt_join);
		btJoin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferencememberjoinactivity_bt_join:
			
			if(etEmail.getText().toString().equals(""))
			{
				joinToast.makeText(mContext, "E-mail이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
			}
			else if(!etEmail.getText().toString().contains("@"))
			{
				joinToast.makeText(mContext, "E-mail 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
			}
			else if(etNickName.getText().toString().equals(""))
			{
				joinToast.makeText(mContext, "NickName이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				finish();				
			}
			
			break;

		default:
			break;
		}
		
	}

	
	
}
