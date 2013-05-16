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
import com.google.analytics.tracking.android.EasyTracker;
import com.mocoplex.adlib.AdlibActivity;
import com.mocoplex.adlib.AdlibConfig;

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

public class PreferenceIntroActivity extends AdlibActivity implements OnClickListener{
	
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
		
		EasyTracker.getInstance().activityStart(this);
		
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
		
		initAds();
		
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



	@Override
	protected void onDestroy() {		
		super.onDestroy();
		EasyTracker.getInstance().activityStop(this);
	}
	
	
	// AndroidManifest.xml에 권한과 activity를 추가하여야 합니다.     
    protected void initAds()
    {
    	// 광고 스케줄링 설정을 위해 아래 내용을 프로그램 실행시 한번만 실행합니다. (처음 실행되는 activity에서 한번만 호출해주세요.)    	
    	// 광고 subview 의 패키지 경로를 설정합니다. (실제로 작성된 패키지 경로로 수정해주세요.)

    	// 쓰지 않을 광고플랫폼은 삭제해주세요.
        AdlibConfig.getInstance().bindPlatform("ADAM","com.bigsensation.preference.ads.SubAdlibAdViewAdam");
        AdlibConfig.getInstance().bindPlatform("SHALLWEAD","com.bigsensation.preference.ads.SubAdlibAdViewShallWeAd");
//        AdlibConfig.getInstance().bindPlatform("ADMOB","test.adlib.project.ads.SubAdlibAdViewAdmob");
//        AdlibConfig.getInstance().bindPlatform("CAULY","test.adlib.project.ads.SubAdlibAdViewCauly");
//        AdlibConfig.getInstance().bindPlatform("TAD","test.adlib.project.ads.SubAdlibAdViewTAD");
//        AdlibConfig.getInstance().bindPlatform("NAVER","test.adlib.project.ads.SubAdlibAdViewNaverAdPost");
//        AdlibConfig.getInstance().bindPlatform("INMOBI","test.adlib.project.ads.SubAdlibAdViewInmobi");
        // 쓰지 않을 플랫폼은 JAR 파일 및 test.adlib.project.ads 경로에서 삭제하면 최종 바이너리 크기를 줄일 수 있습니다.        
        
        // SMART* dialog 노출 시점 선택시 / setAdlibKey 키가 호출되는 activity 가 시작 activity 이며 해당 activity가 종료되면 app 종료로 인식합니다.
        // adlibr.com 에서 발급받은 api 키를 입력합니다.
        // https://sec.adlibr.com/admin/dashboard.jsp
        AdlibConfig.getInstance().setAdlibKey("51933234e4b00e029838d47f");        
                

         /*
         // Locale 별 다른 스케줄을 적용하신다면,
         Locale locale = this.getResources().getConfiguration().locale;
         String lc = locale.getLanguage();
         
         if(lc.equals("ko"))
         {
         // 다국어 스케줄을 설정하시려면 애드립에서 별도로 키를 생성하시고 해당 키를 적용해주세요.
         AdlibConfig.getInstance().setAdlibKey("대한민국 광고 스케줄링");
         }
         else
         {
         // 다국어 스케줄을 설정하시려면 애드립에서 별도로 키를 생성하시고 해당 키를 적용해주세요.
         AdlibConfig.getInstance().setAdlibKey("그밖의 나라");
         }
         */

        
        /* 
         * deprecated : SMART* dialog 를 통해 보다 쉽게 버전관리를 할 수 있습니다.
         */

		/*
		// 광고뷰가 없는 activity 에서는 listener 대신 아래와 같은 방법으로 설정한 현재 버전을 가져올 수 있습니다.
		// 애드립에서 설정한 버전정보를 아래와 같이 수신합니다.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
       	  	String ver = _amanager.getCurrentVersion();
          }
        }, 1000);
	 	*/        
        /*
        // 클라이언트 버전관리를 위한 리스너 추가
        // 서버에서 클라이언트 버전을 관리하여 사용자에게 업데이트를 안내할 수 있습니다. (option)
        this.setVersionCheckingListner(new AdlibVersionCheckingListener(){

			@Override
			public void gotCurrentVersion(String ver) {
				
				// 서버에서 설정한 버전정보를 수신했습니다.
				// 기존 클라이언트 버전을 확인하여 적절한 작업을 수행하세요.
				double current = 0.9;
				
				double newVersion = Double.parseDouble(ver);				
				if(current >= newVersion)
					return;
					
				
				new AlertDialog.Builder(AdlibTestProjectActivity.this)
			    .setTitle("버전 업데이트")
			    .setMessage("프로그램을 업데이트 하시겠습니까?")
					    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int whichButton) {
					    	  // 마켓 또는 안내 페이지로 이동합니다.
					      }
					    })	    
					    .setNegativeButton("no", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int whichButton) {
					    	  // 업데이트를 하지 않습니다.
					      }
					    })	    
			    .show();
				
			}
        });
        */
        
    }
	
}
