package com.bigsensation.preference;

import com.dhpreference.R;
import com.mocoplex.adlib.AdlibActivity;

import android.app.Activity;
import android.os.Bundle;

public class PreferenceTestResultActivity extends AdlibActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencetestresultactivity);
		
		this.setAdsContainer(R.id.ads);		
	}

}
