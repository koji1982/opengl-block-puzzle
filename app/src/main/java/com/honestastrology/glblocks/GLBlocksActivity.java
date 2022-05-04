package com.honestastrology.glblocks;

import java.util.Random;

//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GLBlocksActivity extends BlocksCommonActivity {
	
	private GLBaseSurfaceView _glBaseSurfaceView;
	private CollateralConnector _collateralConnector;
	private FrameLayout _glRendererLayout;
	private TextView  _stageNumText;
	private TextView _moveCountText;
	private TextView _stageDataText;
	private static float   windowMag;
	
//	private String           _mobileId = "73A2636C50CF242959E23260DE976B1B";
//	private InterstitialAd _interstitialAd;
	private AdView		   _adView;
	
	private static int _sceneNumber;
	private int _bestScore;
	private int _limitScore;
	
	private Handler         handler = new Handler();
	private boolean nowDialogShown = false;
	
	private boolean   isRestarting = false;
	
	public static final String LAUNCHED_INTENT_TYPE = "launced_intent_type";
	
	public static final int  RETURN_BUTTON_INTENT = 1;
	public static final int RESTART_BUTTON_INTENT = 2;
	public static final int     NEXT_STAGE_INTENT = 3;
	
	public static final int  RETURN_BUTTON = 9;
	public static final int RESTART_BUTTON = 10;
	private Intent intent;
	private int adRate = 20; // * 10 % 広告表示の確率
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Log.e("onCreate","start onCreate");
		super.onCreate(savedInstanceState);
		SystemAnalyzer.resetMoveCount();
		RadioUIGroup.getInstance().clearInstance();
		_sceneNumber         = getIntent().getExtras().getInt(StageListView.STAGE_NUMBER);
		_collateralConnector = CollateralConnector.getInstance();
		_collateralConnector.setGLBlocksActivity(this);
		setWindowMag(CompatibleOsManager.windowSetting(this));
		getStageData();
		playStageSong();
		setGLSurfaceView();
		adInitialize();
		tutorialCheck();
		Log.e("onCreate","through onCreate");
	}
	
	public void setGLSurfaceView(){
		setContentView(R.layout.glblocks_layout);
		_glRendererLayout  = (FrameLayout)findViewById(R.id.gl_renderer_layout);
		_stageNumText      = (TextView)findViewById(R.id.stage_text);
		_moveCountText     = (TextView)findViewById(R.id.move_count);
		_stageDataText     = (TextView)findViewById(R.id.debug_text); 
		_glBaseSurfaceView = (GLBaseSurfaceView)findViewById(R.id.gl_blocks);
		_glBaseSurfaceView.setGL(this);
	}
	
	private void getStageData(){
		_bestScore  = StageDataArray.getBestScore(_sceneNumber);
		_limitScore = StageDataArray.getMoveLimit(_sceneNumber);
	}
	
	public void rendererNotify(){
		handler.post(new Runnable(){
			@Override
			public void run() {
				_stageNumText.setText(
			" " + "\n" +
			" " + "\n" +
			" " + "\n" +
			" " + "\n" +
			"STAGE " + String.valueOf(_sceneNumber));
				_moveCountText.setText(
			"Move    " + String.valueOf(SystemAnalyzer.getMoveCount()));
				_stageDataText.setText(
//			"FPS             " + String.valueOf(SystemAnalyzer.getFPS()) + "\n" +
//			"SPF             "+ String.valueOf(SystemAnalyzer.getSecondParFrame()) + "\n" +
//			"adjustSum  "+ String.valueOf(SystemAnalyzer.getAdjustSum()) + "\n" +
//			"timeOver    "+ String.valueOf(SystemAnalyzer.getTimeOverCount()) + "\n" + 
//			"onTime      "+ String.valueOf(SystemAnalyzer.getOnTimeCount()) + "\n" +
//			"Tex ID     "+ String.valueOf(TexManager.getTextureIdArray().get(TexManager.getTextureIdArray().size()-1)) + "\n" +

			"Min      " + String.valueOf(_bestScore) + "\n" + 
			"Limit  "+ String.valueOf(_limitScore));
				
			}
		});
	}
	
	private static void playStageSong(){
		if(_sceneNumber < 21){
			SoundManager.changeSong(SoundManager.SONG_STAGE_BLUE);
		}else if( _sceneNumber < 41){
			SoundManager.changeSong(SoundManager.SONG_STAGE_GREEN);
		}else {
			SoundManager.changeSong(SoundManager.SONG_STAGE_GRAY);
		}
	}
	
	protected void onPause(){
		super.onPause();
//		_glBaseSurfaceView.onPause();
	}
	
	protected void onResume(){
		super.onResume();
//		_glBaseSurfaceView.onResume();
	}
	
	public static int getSceneNumber(){
		return _sceneNumber;
	}
	
	public void touchedRestart(int buttonId){
		if(!nowDialogShown ){
			nowDialogShown = true;
			selectDialog(buttonId);
		}
	}
	
	private void selectDialog(int buttonId){
		if(buttonId == RETURN_BUTTON){
			returnDialog();
		}else if(buttonId == RESTART_BUTTON){
			restartDialog();
		}
	}
	
	private void returnDialog(){
		AlertDialog dialog = new AlertDialog.Builder
				(new ContextThemeWrapper(this,R.style.CustomTheme))
		.setTitle(R.string.return_dialog_title)
		.setMessage(R.string.return_dialog_message)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				createIntentForReturnStageSelect();
//				adRate = 15;
//				adDecision(adRate);
				launchIntent();
			}
		})
		.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog,int which) {
				nowDialogShown = false;
			}
		})
		.setOnCancelListener(new DialogInterface.OnCancelListener(){
			
			@Override
			public void onCancel(DialogInterface dialog) {
				nowDialogShown = false;
			}
		})
		.create();
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){

			@Override
			public void onDismiss(DialogInterface dialog) {
				nowDialogShown = false;
			}
		});
		dialog.show();
	}
	
	private void restartDialog(){
		AlertDialog dialog = new AlertDialog.Builder
				(new ContextThemeWrapper(this,R.style.CustomTheme))
		.setTitle(R.string.restart_dialog_title)
		.setMessage(R.string.restart_dialog_message)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				setRestarting(true);
//				adRate = 20;
//				adDecision(adRate);
				restartGLSurface();
			}
		})
		.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog,int which) {
				nowDialogShown = false;
			}
		})
		.setOnCancelListener(new DialogInterface.OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				nowDialogShown = false;
			}
		})
		.create();
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				nowDialogShown = false;
			}
		});
		dialog.show();
	}
	
	private void restartGLSurface(){
		_glRendererLayout.removeAllViews();
		RadioUIGroup.getInstance().clearInstance();
		setGLSurfaceView();
		SystemAnalyzer.resetMoveCount();
		
		AdState.makeBannerAd(
						this ,
						findViewById( R.id.on_play_ad_layout ));
	}
	
	private void tutorialCheck(){
		if( _sceneNumber == 1 ){
			startTutorials();
		} else if ( _sceneNumber == 3){
			showTutorialMultipleMoveDialog();
		}
	}
	
	private void startTutorials(){
		showTutorialDialog(R.drawable.tutorial_move, 
				           "Next", 
				           (dialogInterface, i)->{showTutorialRestrictDialog();});
	}
	
	private void showTutorialRestrictDialog(){
		showTutorialDialog(R.drawable.tutorial_restrict,
				           "Next",
				           (dialogInterface, i)->{showTutorialFillUpDialog();});
	}
	
	private void showTutorialFillUpDialog(){
		showTutorialDialog(R.drawable.tutorial_fillup,
				           "OK",
				            null );
	}
	
	private void showTutorialMultipleMoveDialog(){
		showTutorialDialog(R.drawable.tutorial_automulti,
				           "OK",
				           null );
	}
	
	private void showTutorialDialog(int imageResourceId,
									String buttonText,
									DialogInterface.OnClickListener listener){
		ImageView imageView = new ImageView( this );
		imageView.setImageResource( imageResourceId );
		imageView.setBackgroundColor(getResources().getColor(R.color.black));
		AlertDialog tutorialDialog = new AlertDialog.Builder(this)
											 .setView(imageView)
											 .setPositiveButton( buttonText, listener)
											 .setCancelable(false)
											 .create();
		tutorialDialog.show();
		Button ok_button = tutorialDialog.getButton(DialogInterface.BUTTON_POSITIVE);
		ok_button.setBackgroundColor(getResources().getColor(R.color.gray));
	}
	
	private void createIntentForReturnStageSelect(){
		intent = new Intent(this,StageListView.class);
		intent.putExtra(StageListView.STAGE_NUMBER,_sceneNumber);
	}
	
	private void createIntentToConnectActivity(){
		int result = SystemAnalyzer.getMoveCount();
		intent = new Intent(this,ConnectActivity.class);
		intent.putExtra(StageListView.STAGE_NUMBER, _sceneNumber);
		intent.putExtra(ConnectActivity.LAST_SCORE, result);
		ConnectActivity.setCallingSoundEffect(true);
	}
	
	public void stageFinish(){
		int result = SystemAnalyzer.getMoveCount();
		StagePreference.putResult(_sceneNumber, result);
		createIntentToConnectActivity();
//		decideAdRate(result);
//		adDecision(adRate);
		launchIntent();
	}
	
	private void decideAdRate(int result){
		if(result <= _bestScore){
			adRate = 0;
		}else{
			adRate = 15;
		}
	}
	
	private void launchIntent(){
//		if(isRestarting()){
//			setRestarting(false);
//			
//			restartGLSurface();
//			return;
//		}
		changingActivityBool(true);
		startActivity(intent);
		finish();
	}
	
	private void adInitialize(){
		AdState.initialize( this );
		AdState.makeBannerAd(
						this,
						findViewById( R.id.on_play_ad_layout ));
		AdState.prepareInterstitialAd( this );
	}
	
	private void adDecision(int rate){
		if( randomInt() < rate ){
		    launchIntent();
			showAd();
		} else {
			launchIntent();
		}
	}
	
	private void showAd(){
		Log.e("Ad","show Ad");
		handler.post(new Runnable(){
			@Override
			public void run() {
				if( AdState.isInterstitialPrepared() ){
//				if( _interstitialAd != null ){
//					_interstitialAd.show( GLBlocksActivity.this );
					AdState.showInterstitialAd( GLBlocksActivity.this );
				}else{
					Log.e("Ad","_interstitialAd is null !!");
					launchIntent();
				}
			}
		});
	}
	
	private int randomInt(){
		Random random = new Random();
		int    n     = random.nextInt(100);
		return n;
	}

	public static float getWindowMag() {
		return windowMag;
	}

	public static void setWindowMag(float windowMag) {
		GLBlocksActivity.windowMag = windowMag;
	}

	public boolean isRestarting() {
		return isRestarting;
	}

	public void setRestarting(boolean isRestarting) {
		this.isRestarting = isRestarting;
	}
	
}
