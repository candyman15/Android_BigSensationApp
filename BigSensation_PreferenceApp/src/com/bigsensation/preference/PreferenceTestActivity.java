package com.bigsensation.preference;

import java.io.IOException;
import java.io.InputStream;

import com.bigsensation.preference.common.TagActivity;
import com.dhpreference.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencetestactivity);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tlImageTable = (TableLayout)findViewById(R.id.preferencetestactivity_tl_imagetable);
		
		//assets -> testImg 파일 이름 얻어오기 
		am = getApplicationContext().getResources().getAssets();
		displayFileName(am,"testImg");
		
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
				ivTestImage.setImageBitmap(getTestBitmapImage(am,testImageList[arrCount]));
				tvImageFileName.setText(testImageList[arrCount]);
				
				ivTestImage.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {											
						Toast.makeText(getApplicationContext(), "파일이름은 : " + tvImageFileName.getText().toString(), Toast.LENGTH_SHORT).show();
					}
				});
				
				params = new TableRow.LayoutParams(0,400);
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
		try{			
			is = am.open("testImg/"+fileName);
		}catch(IOException e){
			e.printStackTrace();
		}
		return BitmapFactory.decodeStream(is);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {		

		default:
			break;
		}
		
	}

	
}
