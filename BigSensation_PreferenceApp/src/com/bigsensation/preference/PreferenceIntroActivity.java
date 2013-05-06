package com.bigsensation.preference;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bigsensation.preference.common.HttpUtil;
import com.bigsensation.preference.common.TagActivity;
import com.dhpreference.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PreferenceIntroActivity extends Activity implements OnClickListener{
	
	private Button btNewStart;
	private Button btMemberJoin;
	private Button btGoSocial;
	
	private Button btTest;
	private TextView tvTest;
	
	private HttpUtil httpUtil;
	
	private Intent intent;
	
	private TextView tvNickName;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferenceintroactivity);				
		
		btNewStart = (Button)findViewById(R.id.preferenceintroactivity_bt_newstart);
		btMemberJoin = (Button)findViewById(R.id.preferenceintroactivity_bt_memberjoin);
		btGoSocial = (Button)findViewById(R.id.preferenceintroactivity_bt_gosocial);
		
		btNewStart.setOnClickListener(this);
		btMemberJoin.setOnClickListener(this);
		btGoSocial.setOnClickListener(this);
		
		btTest = (Button)findViewById(R.id.preferenceintroactivity_bt_test);
		btTest.setOnClickListener(this);
		
		tvNickName = (TextView)findViewById(R.id.preferenceintroactivity_tv_nickname);
		
		tvTest = (TextView)findViewById(R.id.preferenceintroactivity_tv_test);
		
		//Sqlite db sd card로 생성
		copyDB();
		
		//자신의 계정에 대한 상태에 따라서 로그인창을 띄울지 자동로그인을 할지 판단
		getMyInfo();
	}

	
	
	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.preferenceintroactivity_bt_newstart:						
			
			intent =  new Intent(this,PreferenceTestActivity.class);
			startActivity(intent);
			break;
			
		case R.id.preferenceintroactivity_bt_memberjoin:
			intent = new Intent(this,PreferenceMemberJoinActivity.class);
			startActivity(intent);
			break;
			
		case R.id.preferenceintroactivity_bt_gosocial:
			intent = new Intent(this,PreferenceSocialActivity.class);
			startActivity(intent);
			break;
		
		case R.id.preferenceintroactivity_bt_test:
			try
			{
				httpUtil = new HttpUtil();
				httpUtil.test(this, handlerTest);
			}catch(Exception e){
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	Handler handlerTest = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			try{
				String strResult = msg.getData().getString(HttpUtil.HANDLER_RETURN_MESSAGE_RESPONSE);
				
				if (strResult.equals(HttpUtil.HANDLER_RETURN_MESSAGE_ERROR)) 
				{
					//strResultErrorMessage = msg.getData().getString(HttpUtil.HANDLER_RETURN_MESSAGE_ERROR);
					
					//MessageUtil.showFinishConfirmDialog(ctx, "예외상황 발생", "예외상황이 발생하여 화면이 종료됩니다.\n\n" + strResult, mProgress);
				}
				else
				{
					//JSONArray jsonArray = new JSONArray(strResult);
					JSONObject jsonObject = new JSONObject(strResult);
					String jo = jsonObject.getString("returnList");
					JSONArray jsonArray = new JSONArray(jo);
					String nick = jsonArray.getJSONObject(0).getString("nick");
				    String name = jsonArray.getJSONObject(0).getString("name");
//					tvTextTile.setText(jsonArray.getJSONObject(0).getString("title"));
					//tvTextContent.setText(jsonObject.getString("title"));
				    tvTest.setText(nick + ":" + name);
				}
			}catch (Exception e) {
				
			}finally{
				
				
			}
		}
		
	};

	//asset에세 db를 외부 sd카드로 옮기는 함수 start
	public void copyDB()
	{
		File dir = new File(TagActivity.SDCARD + TagActivity.DB_PATH);
		if(!dir.exists())
		{			
			dir.mkdir();			
		}
			
		AssetManager am = this.getResources().getAssets();
							
		File f = new File(TagActivity.PIC_YOU_OUT_DB_FILE_PATH);		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
			
		try{
			
			InputStream is = am.open("db/"+ TagActivity.PIC_YOU_OUT_DB_NAME);
			BufferedInputStream bis = new BufferedInputStream(is);
				
			if(f.exists())
			{
				
			}	
			else
			{
				fos = new FileOutputStream(f);
				bos = new BufferedOutputStream(fos);
					
				int read = -1;
					
				byte[] buffer = new byte[1024];
				while((read = bis.read(buffer,0,1024)) != -1)
				{
					bos.write(buffer, 0, read);
				}
				bos.flush();
					
				fos.close();
				bos.close();
				is.close();
				bis.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//asset에세 db를 외부 sd카드로 옮기는 함수 end
	
	public void getMyInfo(){
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase(TagActivity.PIC_YOU_OUT_DB_FILE_PATH, null,SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		String sql =  " select id,e_mail,pw,nickname,birty_day,blood_type from "+ TagActivity.DB_TB_MY_INFO;
		Cursor c1 = db.rawQuery(sql, null);		
		
		if(c1.getCount() == 0) // 로그인이 안되었다고 판단 -> 로그인 창을 띄운다.
		{
			intent =  new Intent(this,PreferenceMemberLoginActivity.class);
			startActivity(intent);
		}
		else // 로그인이 되어있다고 판단하여서 서버에 로그인 함
		{
			while(c1.moveToNext())
			{
				tvNickName.setText(c1.getString(c1.getColumnIndex("nickname")).toString());				
			}
		}
		
		
		db.close();
		
	}
}
