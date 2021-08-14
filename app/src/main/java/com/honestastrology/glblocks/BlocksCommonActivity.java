package com.honestastrology.glblocks;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

public class BlocksCommonActivity extends Activity {
	
	private boolean isChangingActivity = false;
	
	@Override
	protected void onCreate(Bundle bundle){
		Log.e("onCreate","start onCreate common");
		super.onCreate(bundle);
		changingActivityBool(false);
		Log.e("onCreate","through onCreate common");
	}
	
	@Override
	protected void onStart(){
		Log.e("onStart","start onStart");
		super.onStart();
		Log.e("onStart","through onStart");
	}
	
	@Override
	protected void onRestart(){
		Log.e("onRestart","start onRestart");
		super.onRestart();
		Log.e("onRestart","through onRestart");
	}
	
	@Override
	protected void onPause(){
		Log.e("onPause","Activity start onPause");
		super.onPause();
		if(!isChangingActivity())SoundManager.pauseSong();
		Log.e("onPause","Activity through onPause");
	}
	
	@Override
	protected void onResume(){
		Log.e("onResume","Activity start onResume");
		super.onResume();
		SoundManager.resumeSong();
		Log.e("onResume","Activity through onResume");
	}
	
	@Override
	protected void onStop(){
		Log.e("onStop","start onStop");
		super.onStop();
		Log.e("onStop","through onStop");
	}
	
	@Override
	protected void onDestroy(){
		Log.e("onDestroy","start onDestroy");
		super.onDestroy();
		Log.e("onDestroy","through onDestroy");
	}
	
	public boolean isChangingActivity() {
		return isChangingActivity;
	}

	public void changingActivityBool(boolean isChangingActivity) {
		this.isChangingActivity = isChangingActivity;
	}
	
}
