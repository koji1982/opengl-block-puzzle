package com.honestastrology.glblocks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import com.google.android.gms.ads.*;

import java.util.Random;

public class ConnectActivity extends Activity implements OnClickListener{
	
	private static final int RATE_ZERO = 0;
	private static final int LOW_RATE  = 12;
	private static final int HIGH_RATE = 30;
	
	private String           _mobileId = "73A2636C50CF242959E23260DE976B1B";
	private String _interstitialUnitId = "ca-app-pub-2263172161263292/7094260160";
	private String _bannerId		   = "ca-app-pub-2263172161263292/2334486566";
	private InterstitialAd _interstitialAd;
	private AdView		   _adView;
	
	private boolean isCleared;
	
	private int _stageNumber;
	private int _yourScore;
	private int _bestScore;
	private int _moveLimit;
	private String _scoreComment;
	
	private Handler _handler = new Handler();
	
	private boolean _isAlreadyClicked = false;
	
	private final int RETURN_IMAGE = 0;
	private final int LIST_IMAGE   = 1;
	private final int NEXT_IMAGE   = 2;
	
	private final String COMMENT_PERFECT = "PERFECT!!!";
	private final String COMMENT_GOOD    = "GOOD!!";
	private final String COMMENT_OK      = "OK";
	private final String COMMENT_MISS    = "Try again!";
	
	public final static String LAST_SCORE = "last_score";
	
	private static boolean callingSoundEffect = false;
	private static int	   _adRateByScore	  = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		CompatibleOsManager.windowSetting(this);
		setContentView(R.layout.connect_activity);
		
		initializeAd();
		
		checkScoreData();
		callScores();
		setImageOnClick();
		Log.e("sound",String.valueOf(isCallingSoundEffect()));
		setDelayedSound();
		SoundManager.stopSong();
	}
	
	private void checkScoreData(){
		Intent intent = getIntent();
		if(intent.hasExtra(StageListView.STAGE_NUMBER)){
			_stageNumber = intent.getExtras().getInt(StageListView.STAGE_NUMBER);
			_yourScore   = intent.getExtras().getInt(LAST_SCORE);
			_bestScore   = StageDataArray.getBestScore(_stageNumber);
			_moveLimit   = StageDataArray.getMoveLimit(_stageNumber);
			judgeCleared();
		}else{
			Intent cancelIntent = new Intent(this, StageListView.class);
			startActivity(cancelIntent);
			finish();
		}
	}
	
	private void callScores(){
		TextView stagetext  = (TextView)findViewById(R.id.stage_number);
		TextView scoretext  = (TextView)findViewById(R.id.score_text);
		TextView yourscore  = (TextView)findViewById(R.id.your_score);
		TextView bestscore  = (TextView)findViewById(R.id.best_score);
		TextView limitscore = (TextView)findViewById(R.id.limit_score);
		stagetext.setText("STAGE" + String.valueOf(_stageNumber));
		scoretext.setText(_scoreComment);
		yourscore.setText("  Result  " + String.valueOf(_yourScore));
		bestscore.setText(" Min    " + String.valueOf(_bestScore));
		limitscore.setText(" Limit  " + String.valueOf(_moveLimit)); 
	}
	
	private void judgeCleared(){
		ImageView score1 = (ImageView)findViewById(R.id.score_image1);
		ImageView score2 = (ImageView)findViewById(R.id.score_image2);
		ImageView score3 = (ImageView)findViewById(R.id.score_image3);
		score1.setImageResource(R.drawable.star);
		isCleared = _yourScore <= _moveLimit;
		if(isCleared){
			if(_yourScore == _moveLimit){
				_scoreComment = COMMENT_OK;
				score1.setImageResource(R.drawable.nostar);
				score2.setImageResource(R.drawable.star);
				score3.setImageResource(R.drawable.nostar);
				_adRateByScore = HIGH_RATE;
			}else if(_yourScore <= _bestScore){
				_scoreComment = COMMENT_PERFECT;
				score1.setImageResource(R.drawable.star);
				score2.setImageResource(R.drawable.star);
				score3.setImageResource(R.drawable.star);
				_adRateByScore = RATE_ZERO;
			}else{
				_scoreComment = COMMENT_GOOD;
				score1.setImageResource(R.drawable.star);
				score2.setImageResource(R.drawable.star);
				score3.setImageResource(R.drawable.nostar);
				_adRateByScore = LOW_RATE;
			}
		}else{
			_scoreComment = COMMENT_MISS;
			score1.setImageResource(R.drawable.nostar);
			score2.setImageResource(R.drawable.nostar);
			score3.setImageResource(R.drawable.nostar);
		}
	}
	
	private void setImageOnClick(){
		ImageView returnImage = (ImageView)findViewById(R.id.return_image);
		ImageView listImage   = (ImageView)findViewById(R.id.list_image);
		ImageView nextImage    = (ImageView)findViewById(R.id.next_image);
		if(isCleared && _stageNumber < SceneData.LAST_STAGE){
			nextImage.setImageResource(R.drawable.nexticon);
			nextImage.setId(NEXT_IMAGE);
			nextImage.setOnClickListener(this);
		}else{
			nextImage.setImageResource(R.drawable.inactivenext);
		}
		returnImage.setId(RETURN_IMAGE);
		listImage.setId(LIST_IMAGE);
		returnImage.setOnClickListener(this);
		listImage.setOnClickListener(this);
	}
	
	private void setDelayedSound(){
		if(!isCallingSoundEffect())return;
		setCallingSoundEffect(false);
//		SoundManager.playSoundEffect(7);
		_handler.postDelayed(new Runnable(){
			@Override
			public void run() {
				if( isCleared ){
//					SoundManager.playSoundEffect(7);
					SoundManager.playSoundEffect(9);
				} else {
					SoundManager.playSoundEffect(8);
				}
			}}, 5);
	}
	
	
	private void settingAdBanner(){
//		LinearLayout linear = (LinearLayout)findViewById(R.id.ad_linear);
		AdView adview       = new AdView(this);
		adview.setAdUnitId( _bannerId );
		adview.setAdSize(AdSize.BANNER);
//		linear.addView(adview);
		//AdRequest adrequest = new AdRequest.Builder().build();
		AdRequest adrequest = new AdRequest.Builder()
//									  .addTestDevice(_mobileId)
									  .build();
		adview.loadAd(adrequest);
	}
	
	private void initializeAd(){
		if(BuildConfig.DEBUG) {
			Log.i("DEBUG BUILD", "AVOID DISPLAY AD");
			return;
		}
		Log.i("RELEASE BUILD", "DISPLAY AD");
		AdState.initialize( this );
		AdState.makeBannerAd(
				this,
				findViewById( R.id.connect_ad ));
		AdState.prepareInterstitialAd( this );
	}
	
//	public void initBannerAd(){
//		MobileAds.initialize(this, new OnInitializationCompleteListener() {
//			@Override
//			public void onInitializationComplete(InitializationStatus initializationStatus) {
//			}
//		});
//		
//		_adView = findViewById(R.id.ad_view);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		_adView.loadAd(adRequest);
//	}

	@Override
	public void onClick(View v) {
		if( _isAlreadyClicked ) return;
		
		int id = v.getId();
		Intent intent = null;
		switch(id){
		case RETURN_IMAGE:
			intent = new Intent(this,GLBlocksActivity.class);
			intent.putExtra(StageListView.STAGE_NUMBER, _stageNumber);
			break;
		case LIST_IMAGE:
			intent = new Intent(this,StageListView.class);
			intent.putExtra(StageListView.STAGE_NUMBER, _stageNumber);
			break;
		case NEXT_IMAGE:
			intent = new Intent( this,GLBlocksActivity.class);
			intent.putExtra(StageListView.STAGE_NUMBER, _stageNumber+1);
			break;
		}
		_isAlreadyClicked = true;
		startActivity(intent);
		finish();
		
		if( id != NEXT_IMAGE ) return;
		if( !AdState.isInterstitialPrepared() ) return;
		if( !isBelowRandomInt( _adRateByScore ) ) return;
		AdState.showInterstitialAd( this );
	}
	
	private boolean isBelowRandomInt(int rate){
		return getRandomInt() < rate;
	}
	
	private int getRandomInt(){
		Random random = new Random();
		int    n     = random.nextInt(100);
		return n;
	}
	
	public static boolean isCallingSoundEffect() {
		return callingSoundEffect;
	}

	public static void setCallingSoundEffect(boolean callingSoundEffect) {
		ConnectActivity.callingSoundEffect = callingSoundEffect;
	}
	
}
