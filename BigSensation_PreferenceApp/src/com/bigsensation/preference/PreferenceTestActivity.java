package com.bigsensation.preference;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bigsensation.preference.common.HttpUtil;
import com.bigsensation.preference.common.TagActivity;
import com.dhpreference.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PreferenceTestActivity extends Activity implements OnClickListener{

	private TableLayout tlImageTable;
	private TableRow tr;
	private TableRow.LayoutParams params; 
	
	private LayoutInflater inflater;
	
	private AssetManager am;
	
	private String testImageList[];
	private int testImageNum;	
	private int trRowNum;
	private int restImageNum;
	private int arrCount = 0;
	private List<String> ramdomTestImageList;
	
	private ArrayList<String> arrSelectImageList; // 선택한 사진 파일 이름 담기 	
	
	private LinearLayout llIntroduceTest;
	
	private Button btSendResult;
	private TextView tvSelectPicNum;
	
	private HttpUtil httpUtil;
	
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencetestactivity);
		
		mContext = this;
		
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tlImageTable = (TableLayout)findViewById(R.id.preferencetestactivity_tl_imagetable);
		
		llIntroduceTest = (LinearLayout)findViewById(R.id.preferencetestactivity_ll_introducetest);
		llIntroduceTest.bringToFront();
		llIntroduceTest.invalidate();
		llIntroduceTest.setOnClickListener(this);
		
		btSendResult = (Button)findViewById(R.id.preferencetestactivity_bt_sendresult);
		btSendResult.setOnClickListener(this);
		
		tvSelectPicNum = (TextView)findViewById(R.id.preferencetestactivity_tv_selectpicnum);
		//tvSelectPicNum.setText(TagActivity.SELECT_PIC_NUM_TEXT + "0 /" + testImageList.length);
		arrSelectImageList = new ArrayList<String>();
		
		//assets -> testImg 파일 이름 얻어오기 
		am = getApplicationContext().getResources().getAssets();
		displayFileName(am,"testImg");
		
		randomTestImage(testImageList,testImageList.length);
		
		testImageNum = testImageList.length; //총 이미지 개수
		restImageNum = testImageNum % 3;
		if(restImageNum % 3 == 0)
		{
			trRowNum = testImageNum/3;
		}
		else
		{
			trRowNum = testImageNum/3 + 1;
		}
		arrCount = 0;
		
		for(int i=0; i < trRowNum; i++){
			
			tr = new TableRow(this);
			
			for(int j=0; j < 3 ; j++){
				LinearLayout table_item = (LinearLayout)inflater.inflate(R.layout.preferencetestactivity_image_table_item, null);
				final ImageView ivTestImage = (ImageView)table_item.findViewById(R.id.preferencetestactivity_image_table_iv_item);				
				final TextView tvImageFileName = (TextView)table_item.findViewById(R.id.preferencetestactivity_image_table_tv_filename);
				final ImageView ivSelectYn = (ImageView)table_item.findViewById(R.id.preferencetestactivity_image_table_iv_select);
				ivTestImage.setImageBitmap(getTestBitmapImage(am,ramdomTestImageList.get(arrCount)));
				tvImageFileName.setText(ramdomTestImageList.get(arrCount));
				
				ivTestImage.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if(ivSelectYn.getVisibility() == View.VISIBLE) // unselect
						{							
							ivSelectYn.setVisibility(View.INVISIBLE);
							arrSelectImageList.remove(tvImageFileName.getText().toString());
							
						}
						else						// select
						{						
							ivSelectYn.setVisibility(View.VISIBLE);
							ivSelectYn.bringToFront();
							ivSelectYn.invalidate();
							
							arrSelectImageList.add(tvImageFileName.getText().toString());
						}
						//tvSelectPicNum.setText(TagActivity.SELECT_PIC_NUM_TEXT + "/" + testImageList.length);
						//Toast.makeText(getApplicationContext(), "파일이름은 : " + tvImageFileName.getText().toString(), Toast.LENGTH_SHORT).show();
					}
				});
				
				params = new TableRow.LayoutParams(0,200);
			    params.weight = 3;
			    
			    LinearLayout fake_table_item = new LinearLayout(this);
			    
			    if(i != trRowNum -1) //마지막 줄이 아닐때 - 무조건 다 돌아라!
				{
					tr.addView(table_item, params);
				}
				else // 마지막 줄일때는 갯수에 따라 결정
				{
					if(restImageNum ==0) // 마지막줄인데 3개일때 - 3의 배수이므로 무조건 다 돌아라!
					{
						tr.addView(table_item, params);						
					}
					else if(restImageNum ==1)// 마지막줄인데 하나만 있을때 
					{
						if(j==0) 
						{
							tr.addView(table_item, params);							
						}
						else
						{
							tr.addView(fake_table_item, params);							
						}
					}
					else if(restImageNum ==2)// 마지막줄인데 두개만 있을때
					{
						if(j==0 || j==1) 
						{
							tr.addView(table_item, params);							
						}
						else 
						{
							tr.addView(fake_table_item, params);							
						}
					}
				}
			    			    			   
			    if(arrCount < testImageNum-1)
				{
					arrCount++;
				}
			}
			tlImageTable.addView(tr);
		}
	}
	
	private void displayFileName(AssetManager am,String path){
		
		 try {
			  testImageList = am.list(path);
		        if (testImageList != null)
		            for (int i=0; i<testImageList.length; ++i)
		                {
		                    Log.v("Assets:", path +"/"+ testImageList[i]);		                    
		                }
		    } catch (IOException e) {
		        Log.v("List error:", "can't list" + path);
		    }
	}
	
	private Bitmap getTestBitmapImage(AssetManager am,String fileName){
		InputStream is = null;
		Bitmap bitMap = null;
		try{			
			is = am.open("testImg/"+fileName);
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inSampleSize = 4;
			bitMap = BitmapFactory.decodeStream(is,null,op);
		}catch(IOException e){
			e.printStackTrace();
		}catch (OutOfMemoryError e) {	        
	        System.gc();
		}
		return bitMap;
	}
	
	private void randomTestImage(String[] stringArray,int num){
		ramdomTestImageList = Arrays.asList(stringArray);
		Collections.shuffle(ramdomTestImageList);	
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferencetestactivity_ll_introducetest:
			llIntroduceTest.setVisibility(View.GONE);
			break;
			
		case R.id.preferencetestactivity_bt_sendresult:
			String resultSelectFile = "";
			for(int i = 0 ; i < arrSelectImageList.size(); i++)
			{
				if(i == arrSelectImageList.size() -1)
				{
					resultSelectFile += arrSelectImageList.get(i);
				}
				else
				{
					resultSelectFile += arrSelectImageList.get(i) + ","; 					
				}
			}
			Toast.makeText(getApplicationContext(), "선택한 파일은 : " + resultSelectFile, Toast.LENGTH_SHORT).show();
			
			httpUtil = new HttpUtil();
			try {				
				httpUtil.sendSelectFileName(mContext,resultSelectFile,handlerSendSelectFileName);
			} catch (Exception e) {				
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		
	}

	Handler handlerSendSelectFileName = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			try{
				String strResult = msg.getData().getString(HttpUtil.HANDLER_RETURN_MESSAGE_RESPONSE);
				
				if (strResult.equals(HttpUtil.HANDLER_RETURN_MESSAGE_ERROR)) 
				{
					//strResultErrorMessage = msg.getData().getString(HttpUtil.HANDLER_RETURN_MESSAGE_ERROR);
					Log.d("","---------------------------------------fail" + msg.getData().getString(HttpUtil.HANDLER_RETURN_MESSAGE_ERROR));
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
				    //tvTest.setText(nick + ":" + name);
				    Log.d("","---------------------------------------" + jo);
				}
			}catch (Exception e) {
				
			}finally{
				
				
			}
		}
		
	};
	
}
