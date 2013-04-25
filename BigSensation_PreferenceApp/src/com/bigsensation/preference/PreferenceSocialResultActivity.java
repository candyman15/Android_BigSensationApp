package com.bigsensation.preference;

import com.bigsensation.preference.common.TagActivity;
import com.dhpreference.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PreferenceSocialResultActivity extends Activity {

	private TextView tvAnswerMatchNum;
	private TextView tvComment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencesocialresultactivity);
		
		Intent intent = getIntent();
		
		tvAnswerMatchNum = (TextView)findViewById(R.id.preferencesocialresult_tv_answermatchnum);
		tvComment = (TextView)findViewById(R.id.preferencesocialresult_tv_comment);
		
		tvAnswerMatchNum.setText(intent.getStringExtra(TagActivity.SOCIAL_ANSWER_MATCH_NUM).toString());
		tvComment.setText(intent.getStringExtra(TagActivity.SOCIAL_ANSWER_MATCH_COMMENT).toString());
	}

}
