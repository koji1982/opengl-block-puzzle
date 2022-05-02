package com.honestastrology.glblocks;

import java.util.ArrayList;

public class StageDataArray {
	
	private static StageDataArray  _stageDataArray = new StageDataArray();
	private static ArrayList<StageData> _arrayList = new ArrayList<StageData>();
	
	private StageDataArray(){
	}
	
	public static void createInstance(){
		_arrayList.add(new StageData(10,15));
		_arrayList.add(new StageData(3,6));
		_arrayList.add(new StageData(5,9));
		_arrayList.add(new StageData(1,4));
		_arrayList.add(new StageData(3,8));
		_arrayList.add(new StageData(4,9));//index5,stage5
		_arrayList.add(new StageData(3,8));
		_arrayList.add(new StageData(4,8));
		_arrayList.add(new StageData(5,9));
		_arrayList.add(new StageData(5,10));
		_arrayList.add(new StageData(6,12));//index10,stage10
		_arrayList.add(new StageData(5,12));
		_arrayList.add(new StageData(7,12));
		_arrayList.add(new StageData(12,15));
		_arrayList.add(new StageData(8,12));
		_arrayList.add(new StageData(8,12));//index15,stage15
		_arrayList.add(new StageData(12,16));
		_arrayList.add(new StageData(6,10));
		_arrayList.add(new StageData(6,9));
		_arrayList.add(new StageData(5,8));
		_arrayList.add(new StageData(9,15));//index20,stage20
		_arrayList.add(new StageData(2,10));
		_arrayList.add(new StageData(5,10));
		_arrayList.add(new StageData(6,14));
		_arrayList.add(new StageData(7,12));
		_arrayList.add(new StageData(5,12));//index25,stage25
		_arrayList.add(new StageData(3,6));
		_arrayList.add(new StageData(4,9));
		_arrayList.add(new StageData(5,12));
		_arrayList.add(new StageData(8,14));
		_arrayList.add(new StageData(5,11));//index30,stage30
		_arrayList.add(new StageData(3,10));
		_arrayList.add(new StageData(3,10));
		_arrayList.add(new StageData(5,10));
		_arrayList.add(new StageData(3,10));
		_arrayList.add(new StageData(7,12));//index35,stage35
		_arrayList.add(new StageData(3,10));
		_arrayList.add(new StageData(4,12));
		_arrayList.add(new StageData(7,14));
		_arrayList.add(new StageData(4,12));
		_arrayList.add(new StageData(5,15));//index40,stage40
		_arrayList.add(new StageData(3,15));
		_arrayList.add(new StageData(4,15));
	}
	
	public static int getBestScore(int index){
		return _arrayList.get(index).getBestScore();
	}
	
	public static int getMoveLimit(int index){
		return _arrayList.get(index).getMoveLimit();
	}
	
}
