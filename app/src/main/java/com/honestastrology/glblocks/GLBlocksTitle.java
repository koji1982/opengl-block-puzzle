package com.honestastrology.glblocks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class GLBlocksTitle extends BlocksCommonActivity implements OnClickListener{
	
	private int _calledPage = 0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		createCompatibleOsManager();
		StageDataArray.createInstance();
		StagePreference.createInstance(this);
		decidCallPage();
		setContentView(R.layout.title);
		setTouchListener();
		SoundManager.getInstance(this);
		SoundManager.startSoundProcess(SoundManager.SONG_TITLE);
		AdState.decideBannerAdId();
	}
	
	private void setTouchListener(){
		ImageView imageview = (ImageView)findViewById(R.id.title_image);
		imageview.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		changingActivityBool(true);
		Intent intent = new Intent(this , StageListView.class);
		intent.putExtra(StageListView.PAGE_NUMBER, _calledPage);
		startActivity(intent);
		finish();
	}
	
	private void decidCallPage(){
		int clearedstage = StagePreference.getClearedStage();
		if(clearedstage == SceneData.LAST_STAGE){
			clearedstage = SceneData.LAST_STAGE - 1;
		}
		_calledPage = (int)Math.floor((double)clearedstage / 10);
	}
	
	private void createCompatibleOsManager(){
		CompatibleOsManager.onCreate(this);
	}
	
}
