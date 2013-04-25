package com.bigsensation.preference;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bigsensation.preference.common.HttpUtil;
import com.bigsensation.preference.common.TagActivity;
import com.dhpreference.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PreferenceSocialActivity extends Activity implements OnClickListener{

	Context mContext;
	
	private Button btAddFriend;
	
	private HttpUtil httpUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencesocialactivity);
		
		mContext = getApplicationContext();
		
		btAddFriend = (Button)findViewById(R.id.preferencesocialactivity_bt_addfriend);
		btAddFriend.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferencesocialactivity_bt_addfriend:
			inputFriendIdDialog();
			break;

		default:
			break;
		}
		
	}

	private void inputFriendIdDialog(){
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.preferencesocialactivitiy_inputfrienddialog,null);
								
		AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
		aDialog.setTitle("매칭할 친구의 아이디를 입력해주세요");
		aDialog.setView(layout);
								
		aDialog.setPositiveButton("매칭", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				try
				{
					httpUtil = new HttpUtil();
					httpUtil.matchAnswerOfFriend(mContext, handlerMatchAnswerOfFriend);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();				
	}
	
	Handler handlerMatchAnswerOfFriend = new Handler()
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
//					JSONObject jsonObject = new JSONObject(strResult);
//					String jo = jsonObject.getString("returnList");
//					JSONArray jsonArray = new JSONArray(jo);
//					String nick = jsonArray.getJSONObject(0).getString("nick");
//				    String name = jsonArray.getJSONObject(0).getString("name");
////					tvTextTile.setText(jsonArray.getJSONObject(0).getString("title"));
//					//tvTextContent.setText(jsonObject.getString("title"));
//				    tvTest.setText(nick + ":" + name);
					
				}
			}catch (Exception e) {
				
			}finally{
				Intent intent = new Intent(mContext,PreferenceSocialResultActivity.class);
				intent.putExtra(TagActivity.SOCIAL_ANSWER_MATCH_NUM, "50");
				intent.putExtra(TagActivity.SOCIAL_ANSWER_MATCH_COMMENT, "헤어져");
				startActivity(intent);
				
			}
		}
		
	};
	
}
