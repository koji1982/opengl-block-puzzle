package com.honestastrology.glblocks;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class StagePreference {
	
	private static StagePreference _stagePreference = new StagePreference();
	private static SharedPreferences _sharedPreferences;
	private static Map<Integer,Integer> _resultMap;
	
	public static final String  INITIALIZE_KEY   = "initialize";
	public static final String  CLEARED_STAGE    = "cleared_stage";
	public static final String  STAGE_SUB_STRING = "stage";
	
	private StagePreference(){
	}
	
	public static void createInstance(Context context){
		_sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		initialize();
		prepareAllResult();
	}
	
	private static void initialize(){
		if(!_sharedPreferences.getBoolean(INITIALIZE_KEY,false)){
			_sharedPreferences.edit().putBoolean(INITIALIZE_KEY,true).commit();
			_sharedPreferences.edit().putInt(CLEARED_STAGE,0).commit();
		}
	}
	
	
	public static void prepareAllResult(){
		_resultMap = new HashMap<Integer,Integer>();
		int clearedStage = _sharedPreferences.getInt(CLEARED_STAGE, 0);
//		clearedStage = 42;
		for(int i=1;i<=clearedStage;i++){
			_resultMap.put(i,_sharedPreferences.getInt(STAGE_SUB_STRING + String.valueOf(i),100));
		}
	}
	
	public static void putResult(int stageNum, int result){
		if(result <= StageDataArray.getMoveLimit(stageNum)){
			if(stageNum > getClearedStage()){
				_sharedPreferences.edit().putInt(CLEARED_STAGE, stageNum).commit();
			}
			if(result < getResult(stageNum)){
				_sharedPreferences.edit().putInt(STAGE_SUB_STRING + String.valueOf(stageNum), result).commit();
				_resultMap.put(stageNum, result);
			}
		}
	}
	
	public static Map<Integer,Integer> getResultMap(){
		return _resultMap;
	}
	
	public static int getClearedStage(){
		return _sharedPreferences.getInt(CLEARED_STAGE, 0);
//		return 42;
	}
	
	public static int getResult(int stageNumber){
		return _sharedPreferences.getInt(STAGE_SUB_STRING + String.valueOf(stageNumber), 100);
	}
	
}
