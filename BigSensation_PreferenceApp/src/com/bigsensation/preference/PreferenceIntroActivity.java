package com.bigsensation.preference;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bigsensation.preference.common.HttpUtil;
import com.dhpreference.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PreferenceIntroActivity extends Activity implements OnClickListener{
	
	private Button btNewStart;
	private Button btNextStart;
	
	private Button btTest;
	private TextView tvTest;
	
	private HttpUtil httpUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferenceintroactivity);
		
		btNewStart = (Button)findViewById(R.id.preferenceintroactivity_bt_newstart);
		btNextStart = (Button)findViewById(R.id.preferenceintroactivity_bt_nextstart);
		
		btNewStart.setOnClickListener(this);
		btNextStart.setOnClickListener(this);
		
		btTest = (Button)findViewById(R.id.preferenceintroactivity_bt_test);
		btTest.setOnClickListener(this);
		
		tvTest = (TextView)findViewById(R.id.preferenceintroactivity_tv_test);
	}

	
	
	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
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

	
}
