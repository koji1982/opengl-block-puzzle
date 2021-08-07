package com.honestastrology.glblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StageListView extends BlocksCommonActivity implements View.OnClickListener{
	
	private ArrayList<FrameLayout> testImages;
	private ImageView _toPreviousPage;
	private ImageView _toNextPage;
	private FrameLayout _wholeFrame;
	private LinearLayout _outerLinear;
	private int          _currentPage;
	private int          _clearedStage;
	private Map<Integer,Integer> _resultMap;
	private int          _slideValue = 0;
	
	private final int TO_PREVIOUS_PAGE = -3;
	private final int TO_NEXT_PAGE     = -1;
	
	public final static String PAGE_NUMBER  = "page_number";
	public final static String STAGE_NUMBER = "stage_number";
	private final static String SLIDE_ANIM  = "slide_anim";
	private final static int       TO_RIGHT = -1;
	private final static int       TO_LEFT  =  1;
	private final static int       NO_SLIDE =  0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		CompatibleOsManager.windowSetting(this);
		prepareData();
		pageDecide();
		setContentView( R.layout.stage_list );
		settingView();
		if( AdState.isValid() ){
			AdState.makeBannerAd( this, _outerLinear );
		}
		SlideAnimation.createInstance(this);
		decideSlideAnim();
		SoundManager.changeSong(SoundManager.SONG_TITLE);
	}
	
	private void prepareData(){
		_clearedStage = StagePreference.getClearedStage();
		_resultMap    = new HashMap<Integer,Integer>();
		_resultMap    = StagePreference.getResultMap();
	}
	
	private void pageDecide(){
		Intent intent = getIntent();
		if(intent.hasExtra(PAGE_NUMBER)){
			_currentPage = intent.getIntExtra(PAGE_NUMBER, 0);
		}else{
			_currentPage = 0;
			if(intent.hasExtra(STAGE_NUMBER)){
				_currentPage = decidePage(intent.getExtras().getInt(STAGE_NUMBER));
			}
		}
		if(intent.hasExtra(SLIDE_ANIM)){
			_slideValue = intent.getIntExtra(SLIDE_ANIM, 0);
		}else{
			_slideValue = 0;
		}
	}
	
	private void settingView(){
		_wholeFrame     = (FrameLayout)findViewById(R.id.whole_frame);
		_outerLinear    = (LinearLayout)findViewById(R.id.outer_linear);
		_toPreviousPage = (ImageView)findViewById(R.id.previous_page);
		_toPreviousPage.setImageResource(R.drawable.arrowleft);
		_toPreviousPage.setId(TO_PREVIOUS_PAGE);
		_toPreviousPage.setOnClickListener(this);
		_toNextPage     = (ImageView)findViewById(R.id.next_page);
		_toNextPage.setImageResource(R.drawable.arrowright);
		_toNextPage.setId(TO_NEXT_PAGE);
		_toNextPage.setOnClickListener(this);
		
		testImages = new ArrayList<FrameLayout>();
		testImages.add((FrameLayout)findViewById(R.id.upper_frame1));
		testImages.add((FrameLayout)findViewById(R.id.upper_frame2));
		testImages.add((FrameLayout)findViewById(R.id.upper_frame3));
		testImages.add((FrameLayout)findViewById(R.id.upper_frame4));
		testImages.add((FrameLayout)findViewById(R.id.upper_frame5));
		testImages.add((FrameLayout)findViewById(R.id.lower_frame1));
		testImages.add((FrameLayout)findViewById(R.id.lower_frame2));
		testImages.add((FrameLayout)findViewById(R.id.lower_frame3));
		testImages.add((FrameLayout)findViewById(R.id.lower_frame4));
		testImages.add((FrameLayout)findViewById(R.id.lower_frame5));
		LinearLayout innerlinear;
		int playerScore;
		int bestScore;
		int limit;
		for(int i=1;i<=10;i++){
			innerlinear = (LinearLayout)testImages.get(i-1).getChildAt(1);
			int stageindex  = _currentPage * 10 + i;
			if( stageindex > SceneData.LAST_STAGE){
				decideNoStar(innerlinear);
				decideBackColor((ImageView)testImages.get(i-1).getChildAt(0));
				((TextView)innerlinear.getChildAt(1)).setText("UNDER "+" \n"+ "CONSTRUCTION");
				((TextView)innerlinear.getChildAt(2)).setText("Result"+"    "+"--");
				((TextView)innerlinear.getChildAt(3)).setText("Min"+"        "+"--");
				((TextView)innerlinear.getChildAt(4)).setText("Limit"+"      "+"--");
			}else if(stageindex <= _clearedStage){
				((ImageView)testImages.get(i-1).getChildAt(0)).setImageResource(decideImageResource(stageindex));
				innerlinear.setOnClickListener(this);
				innerlinear.setId(i);
				playerScore = _resultMap.get(stageindex);
				decideResultStars(stageindex,playerScore,innerlinear);
				((TextView)innerlinear.getChildAt(1)).setText("Stage"+"  "+String.valueOf(stageindex));
				((TextView)innerlinear.getChildAt(2)).setText("Result"+"     "+String.valueOf(playerScore));
				((TextView)innerlinear.getChildAt(3)).setText("Min"+"         "+String.valueOf(StageDataArray.getBestScore(stageindex)));
				((TextView)innerlinear.getChildAt(4)).setText("Limit"+"     "+String.valueOf(StageDataArray.getMoveLimit(stageindex)));
			}else if(stageindex == _clearedStage + 1){
				((ImageView)testImages.get(i-1).getChildAt(0)).setImageResource(decideImageResource(stageindex));
				innerlinear.setOnClickListener(this);
				innerlinear.setId(i);
				decideNoStar(innerlinear);
				((TextView)innerlinear.getChildAt(1)).setText("Stage"+"  "+String.valueOf(stageindex));
				((TextView)innerlinear.getChildAt(2)).setText("Result"+"    "+"--");
				((TextView)innerlinear.getChildAt(3)).setText("Min"+"        "+"--");
				((TextView)innerlinear.getChildAt(4)).setText("Limit"+"      "+"--");
			}else{
				decideNoStar(innerlinear);
				decideBackColor((ImageView)testImages.get(i-1).getChildAt(0));
				((TextView)innerlinear.getChildAt(1)).setText("Stage"+"  "+String.valueOf(stageindex));
				((TextView)innerlinear.getChildAt(2)).setText("Result"+"    "+"--");
				((TextView)innerlinear.getChildAt(3)).setText("Min"+"        "+"--");
				((TextView)innerlinear.getChildAt(4)).setText("Limit"+"      "+"--");
			}
		}
	}
	
	private void decideResultStars(int stageindex, int playerscore, LinearLayout linearlayout){
		((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.little_star);
		if(playerscore < StageDataArray.getMoveLimit(stageindex)){
			((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(1)).setImageResource(R.drawable.little_star);
			if(playerscore <= StageDataArray.getBestScore(stageindex)){
				((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(2)).setImageResource(R.drawable.little_star);
			}else{
				((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(2)).setImageResource(R.drawable.little_nostar);
			}
		}else{
			((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(1)).setImageResource(R.drawable.little_nostar);
			((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(2)).setImageResource(R.drawable.little_nostar);
		}
	}
	
	private void decideNoStar(LinearLayout linearlayout){
		((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.little_nostar);
		((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(1)).setImageResource(R.drawable.little_nostar);
		((ImageView)((LinearLayout)linearlayout.getChildAt(0)).getChildAt(2)).setImageResource(R.drawable.little_nostar);
	}
	
	private void decideBackColor(ImageView imageview){
		if(_currentPage < 2){
			imageview.setImageResource(R.drawable.blue_back);
		}else if(_currentPage > 1 &&_currentPage < 4){
			imageview.setImageResource(R.drawable.green_back);
		}else if(_currentPage > 3 && _currentPage < 6){
			imageview.setImageResource(R.drawable.gray_back);
		}else if(_currentPage > 5 && _currentPage < 8){
			imageview.setImageResource(R.drawable.red_back);
		}else{
			imageview.setImageResource(R.drawable.blue_back);
		}
	}
	
	private void decideSlideAnim(){
		if(_slideValue < 0){
			SlideAnimation.slideInLeft(_outerLinear);
		}else if(_slideValue > 0){
			SlideAnimation.slideInRight(_outerLinear);
		}
	}
	
	@Override
	public void onClick(View v) {
		int clickedId  = v.getId();
		if(clickedId < 0){
			movePage(clickedId);
		}else{
			startStage(clickedId);
		}
	}
	
	private void movePage(int clickedId){
		_currentPage += clickedId + 2;
		if(_currentPage < 0){
			_currentPage = 0;
			return;
		}
		if(_currentPage > 7){
			_currentPage = 7;
			return;
		}
		Intent intent = new Intent(this, StageListView.class);
		intent.putExtra(PAGE_NUMBER,_currentPage);
		if(clickedId + 2 < 0){
			SlideAnimation.slideOutRight(_outerLinear);
			intent.putExtra(SLIDE_ANIM, TO_RIGHT);
		}else{
			SlideAnimation.slideOutLeft(_outerLinear);
			intent.putExtra(SLIDE_ANIM , TO_LEFT);
		}
		changingActivityBool(true);
		startActivity(intent);
		finish();
	}
	
	private void startStage(int clickedId){
		int stagenumber = _currentPage * 10 + clickedId;
		Intent intent = new Intent(this, GLBlocksActivity.class);
		intent.putExtra(STAGE_NUMBER,stagenumber);
		changingActivityBool(true);
		startActivity(intent);
		finish();
	}
	
	private int decideImageResource(int stageIndex){
		int resource = 0;
		if(stageIndex < 21){
			resource = R.drawable.blue_stage;
		}else if(stageIndex > 20 && stageIndex < 41){
			resource = R.drawable.green_stage;
		}
		return resource;
	}
	
	private int decidePage(int stageNumber){
		if(stageNumber == SceneData.LAST_STAGE){
			stageNumber = SceneData.LAST_STAGE -1;
		}
		return (int)Math.floor((double)stageNumber/10);
	}
	
}
