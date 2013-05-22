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
		//Test
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
		
		//Sqlite db sd card濡��앹꽦
		copyDB();
		
		//�먯떊��怨꾩젙����븳 �곹깭���곕씪��濡쒓렇�몄갹���꾩슱吏��먮룞濡쒓렇�몄쓣 �좎� �먮떒
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
					
					//MessageUtil.showFinishConfirmDialog(ctx, "�덉쇅�곹솴 諛쒖깮", "�덉쇅�곹솴��諛쒖깮�섏뿬 �붾㈃��醫낅즺�⑸땲��\n\n" + strResult, mProgress);
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

	//asset�먯꽭 db瑜��몃� sd移대뱶濡���린���⑥닔 start
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
	//asset�먯꽭 db瑜��몃� sd移대뱶濡���린���⑥닔 end
	
	public void getMyInfo(){
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase(TagActivity.PIC_YOU_OUT_DB_FILE_PATH, null,SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		String sql =  " select id,e_mail,pw,nickname,birty_day,blood_type from "+ TagActivity.DB_TB_MY_INFO;
		Cursor c1 = db.rawQuery(sql, null);		
		
		if(c1.getCount() == 0) // 濡쒓렇�몄씠 �덈릺�덈떎怨��먮떒 -> 濡쒓렇��李쎌쓣 �꾩슫��
		{
			intent =  new Intent(this,PreferenceMemberLoginActivity.class);
			startActivity(intent);
		}
		else // 濡쒓렇�몄씠 �섏뼱�덈떎怨��먮떒�섏뿬���쒕쾭��濡쒓렇����
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
	
	
	// AndroidManifest.xml��沅뚰븳怨�activity瑜�異붽��섏뿬���⑸땲��     
    protected void initAds()
    {
    	// 愿묎퀬 �ㅼ�以꾨쭅 �ㅼ젙���꾪빐 �꾨옒 �댁슜���꾨줈洹몃옩 �ㅽ뻾���쒕쾲留��ㅽ뻾�⑸땲�� (泥섏쓬 �ㅽ뻾�섎뒗 activity�먯꽌 �쒕쾲留��몄텧�댁＜�몄슂.)    	
    	// 愿묎퀬 subview ���⑦궎吏�寃쎈줈瑜��ㅼ젙�⑸땲�� (�ㅼ젣濡��묒꽦���⑦궎吏�寃쎈줈濡��섏젙�댁＜�몄슂.)

    	// �곗� �딆쓣 愿묎퀬�뚮옯�쇱� ��젣�댁＜�몄슂.
        AdlibConfig.getInstance().bindPlatform("ADAM","com.bigsensation.preference.ads.SubAdlibAdViewAdam");
        AdlibConfig.getInstance().bindPlatform("SHALLWEAD","com.bigsensation.preference.ads.SubAdlibAdViewShallWeAd");
//        AdlibConfig.getInstance().bindPlatform("ADMOB","test.adlib.project.ads.SubAdlibAdViewAdmob");
//        AdlibConfig.getInstance().bindPlatform("CAULY","test.adlib.project.ads.SubAdlibAdViewCauly");
//        AdlibConfig.getInstance().bindPlatform("TAD","test.adlib.project.ads.SubAdlibAdViewTAD");
//        AdlibConfig.getInstance().bindPlatform("NAVER","test.adlib.project.ads.SubAdlibAdViewNaverAdPost");
//        AdlibConfig.getInstance().bindPlatform("INMOBI","test.adlib.project.ads.SubAdlibAdViewInmobi");
        // �곗� �딆쓣 �뚮옯�쇱� JAR �뚯씪 諛�test.adlib.project.ads 寃쎈줈�먯꽌 ��젣�섎㈃ 理쒖쥌 諛붿씠�덈━ �ш린瑜�以꾩씪 ���덉뒿�덈떎.        
        
        // SMART* dialog �몄텧 �쒖젏 �좏깮��/ setAdlibKey �ㅺ� �몄텧�섎뒗 activity 媛��쒖옉 activity �대ŉ �대떦 activity媛�醫낅즺�섎㈃ app 醫낅즺濡��몄떇�⑸땲��
        // adlibr.com �먯꽌 諛쒓툒諛쏆� api �ㅻ� �낅젰�⑸땲��
        // https://sec.adlibr.com/admin/dashboard.jsp
        AdlibConfig.getInstance().setAdlibKey("51933234e4b00e029838d47f");        
                

         /*
         // Locale 蹂��ㅻⅨ �ㅼ�以꾩쓣 �곸슜�섏떊�ㅻ㈃,
         Locale locale = this.getResources().getConfiguration().locale;
         String lc = locale.getLanguage();
         
         if(lc.equals("ko"))
         {
         // �ㅺ뎅���ㅼ�以꾩쓣 �ㅼ젙�섏떆�ㅻ㈃ �좊뱶由쎌뿉��蹂꾨룄濡��ㅻ� �앹꽦�섏떆怨��대떦 �ㅻ� �곸슜�댁＜�몄슂.
         AdlibConfig.getInstance().setAdlibKey("��븳誘쇨뎅 愿묎퀬 �ㅼ�以꾨쭅");
         }
         else
         {
         // �ㅺ뎅���ㅼ�以꾩쓣 �ㅼ젙�섏떆�ㅻ㈃ �좊뱶由쎌뿉��蹂꾨룄濡��ㅻ� �앹꽦�섏떆怨��대떦 �ㅻ� �곸슜�댁＜�몄슂.
         AdlibConfig.getInstance().setAdlibKey("洹몃컰���섎씪");
         }
         */

        
        /* 
         * deprecated : SMART* dialog 瑜��듯빐 蹂대떎 �쎄쾶 踰꾩쟾愿�━瑜������덉뒿�덈떎.
         */

		/*
		// 愿묎퀬酉곌� �녿뒗 activity �먯꽌��listener ��떊 �꾨옒��媛숈� 諛⑸쾿�쇰줈 �ㅼ젙���꾩옱 踰꾩쟾��媛�졇�����덉뒿�덈떎.
		// �좊뱶由쎌뿉���ㅼ젙��踰꾩쟾�뺣낫瑜��꾨옒��媛숈씠 �섏떊�⑸땲��
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
       	  	String ver = _amanager.getCurrentVersion();
          }
        }, 1000);
	 	*/        
        /*
        // �대씪�댁뼵��踰꾩쟾愿�━瑜��꾪븳 由ъ뒪��異붽�
        // �쒕쾭�먯꽌 �대씪�댁뼵��踰꾩쟾��愿�━�섏뿬 �ъ슜�먯뿉寃��낅뜲�댄듃瑜��덈궡�����덉뒿�덈떎. (option)
        this.setVersionCheckingListner(new AdlibVersionCheckingListener(){

			@Override
			public void gotCurrentVersion(String ver) {
				
				// �쒕쾭�먯꽌 �ㅼ젙��踰꾩쟾�뺣낫瑜��섏떊�덉뒿�덈떎.
				// 湲곗〈 �대씪�댁뼵��踰꾩쟾���뺤씤�섏뿬 �곸젅���묒뾽���섑뻾�섏꽭��
				double current = 0.9;
				
				double newVersion = Double.parseDouble(ver);				
				if(current >= newVersion)
					return;
					
				
				new AlertDialog.Builder(AdlibTestProjectActivity.this)
			    .setTitle("踰꾩쟾 �낅뜲�댄듃")
			    .setMessage("�꾨줈洹몃옩���낅뜲�댄듃 �섏떆寃좎뒿�덇퉴?")
					    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int whichButton) {
					    	  // 留덉폆 �먮뒗 �덈궡 �섏씠吏�줈 �대룞�⑸땲��
					      }
					    })	    
					    .setNegativeButton("no", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int whichButton) {
					    	  // �낅뜲�댄듃瑜��섏� �딆뒿�덈떎.
					      }
					    })	    
			    .show();
				
			}
        });
        */
        
    }
	
}
