package com.bigsensation.preference;

import com.bigsensation.preference.common.TagActivity;
import com.dhpreference.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
	private EditText etBirtyDay;
	private EditText etBloodType;
	private EditText etPw;
	
	private Button btJoin;
	
	private Toast joinToast;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mContext = this;

		setContentView(R.layout.preferencememberjoinactivity);
		
		getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
		getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);    // 블러효과 추가
		
		etEmail = (EditText)findViewById(R.id.preferencememberjoinactivity_et_email);
		etNickName = (EditText)findViewById(R.id.preferencememberjoinactivity_et_nickname);
		etBirtyDay = (EditText)findViewById(R.id.preferencememberjoinactivity_et_birtyday);
		etBloodType = (EditText)findViewById(R.id.preferencememberjoinactivity_et_bloodtype);
		etPw = (EditText)findViewById(R.id.preferencememberjoinactivity_et_pw);
		
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
			else if(etBirtyDay.getText().toString().equals(""))
			{
				joinToast.makeText(mContext, "생일이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
			}
			else if(etBloodType.getText().toString().equals(""))
			{
				joinToast.makeText(mContext, "혈액형이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
			}
			else if(etPw.getText().toString().equals(""))
			{
				joinToast.makeText(mContext, "Password가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				insertMyInfo(etEmail.getText().toString(),
						     etPw.getText().toString(),
						     etNickName.getText().toString(),
						     etBirtyDay.getText().toString(),
						     etBloodType.getText().toString()
							);
							
			}
			
			break;

		default:
			break;
		}
		
	}

	public void insertMyInfo(String eMail,String pw,String nickName,String birtyDay,String bloodType){	    			    		
							
		SQLiteDatabase db = SQLiteDatabase.openDatabase(TagActivity.PIC_YOU_OUT_DB_FILE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
		String sqlSelect =  " select id,e_mail,pw,nickname,birty_day,blood_type from "+ TagActivity.DB_TB_MY_INFO;
		Cursor c1 = db.rawQuery(sqlSelect, null);		
		String _id = "";
		while(c1.moveToNext())
		{
			_id = c1.getString(c1.getColumnIndex("id")).toString();
		}
		
		String sqlInsertorUpdate = "";
		if(c1.getCount() == 0) // 로그인이 안되었다고 판단 -> 로그인 창을 띄운다.
		{
			sqlInsertorUpdate = "INSERT INTO " + TagActivity.DB_TB_MY_INFO + " (e_mail,pw,nickname,birty_day,blood_type)"+   					     
							    "VALUES('"+ eMail +"','"+ pw +"','"+ nickName +"','"+ birtyDay +"','"+ bloodType +"');";
		}
		else
		{
			sqlInsertorUpdate = "UPDATE " + TagActivity.DB_TB_MY_INFO + " SET e_mail = '" + eMail + "', " +
					                                                         "pw = '" + pw + "'," +
					                                                         "nickname = '" + nickName + "'," +
					                                                         "birty_day = '" + birtyDay + "'," +
					                                                         "blood_type = '" + bloodType + "'" +
					                                                         "where id= '" + _id + "'"; 
					                                                         		
		}
								    			
		try{			
    		db.execSQL(sqlInsertorUpdate);
    		Toast.makeText(mContext, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    		
    		finish();	
    	}catch(SQLException e){
    		e.printStackTrace();	    		
    	}finally{
    		db.close();
    	}
		
		
	}
	
}
