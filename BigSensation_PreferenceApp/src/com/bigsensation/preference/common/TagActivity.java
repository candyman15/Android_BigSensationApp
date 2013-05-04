package com.bigsensation.preference.common;

import android.os.Environment;

public class TagActivity {
	
	public static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String DB_PATH = "/PicYouOut/";
	public static final String PIC_YOU_OUT_DB_NAME = "picyouout_db.db";

	public static final String PIC_YOU_OUT_DB_FILE_PATH = SDCARD + DB_PATH + PIC_YOU_OUT_DB_NAME;
	
	public static final String DB_TB_MY_INFO = "tb_my_info";

	public final static String TEST_START_NUM = "tesStartNum"; 
	
	public final static String SOCIAL_ANSWER_MATCH_NUM = "socailAnswerMatchNum";
	public final static String SOCIAL_ANSWER_MATCH_COMMENT = "socailAnswerMatchComment";
	
	public final static String SELECT_PIC_NUM_TEXT = "선택한 사진 개수";
}
