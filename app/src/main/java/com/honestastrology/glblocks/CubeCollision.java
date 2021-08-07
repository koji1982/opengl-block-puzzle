package com.honestastrology.glblocks;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class CubeCollision {
	
	public static boolean detectCubeCollision(int movingIndex, int targetIndex,
			ArrayList<GLBaseObject> objList,float[] areafloat){
		int direction = objList.get(movingIndex).getMoveDirection();
		int axis      = directionToAxis(direction);
		if(targetIndex == -1){
			if(0 == direction%2){
				return collisionDetect(objList.get(movingIndex).getPlace()[axis],areafloat[axis],true);
			}else{
				return collisionDetect(objList.get(movingIndex).getPlace()[axis],0.0f,true);
			}
		}else{
			return collisionDetect(objList.get(movingIndex).getPlace()[axis],
					objList.get(targetIndex).getPlace()[axis],false);
		}
	}
	
	public static HashMap<Integer,Integer> detectAllCube(ArrayList<GLBaseObject> objList){
		HashMap<Integer,Integer> movingMap = new HashMap<Integer,Integer>();//Map<moveObjIndes, targetOBJIndex>
		int target = -2;
		for(int i=0,j=objList.size();i<j;i++){
			if(objList.get(i).getMovingBool()){
				target = nearestObjectIndex(objList, i,objList.get(i).getMoveDirection());
				movingMap.put(i, target);
			}
		}
		return movingMap;
	}
	
	public static int nearestObjectIndex(ArrayList<GLBaseObject> objList,int movingIndex,int direction){
		
		int   axis                   = directionToAxis(direction);
		int  convertedAxis           = -2;
		float matchCoord             = 0.0f;
		float currentPosition        = objList.get(movingIndex).getPlace()[axis];
		ArrayList<Integer> firstList  = new ArrayList<Integer>();
		ArrayList<Integer> secondList = new ArrayList<Integer>();
		ArrayList<Integer> matchList  = new ArrayList<Integer>();
		for(int i=0,j=objList.size();i<j;i++){
			if(onDirectionCompare(objList.get(i).getPlace()[axis],currentPosition,direction)){
				if(i != movingIndex){
					firstList.add(i);
				}
			}
		}
		
		if(firstList.size()<1)return -1;
		
		convertedAxis = convertAxisInt(axis+1);
		matchCoord    = objList.get(movingIndex).getPlace()[convertedAxis];
		for(int i=0,j=firstList.size();i<j;i++){
			if(objList.get(firstList.get(i)).getPlace()[convertedAxis] == matchCoord){
				secondList.add(firstList.get(i));
			}
		}
		
		if(secondList.size()<1)return -1;
		
		convertedAxis = convertAxisInt(axis-1);
		matchCoord    = objList.get(movingIndex).getPlace()[convertedAxis];
		for(int i=0,j= secondList.size();i<j;i++){
			if(objList.get(secondList.get(i)).getPlace()[convertedAxis] == matchCoord){
				matchList.add(secondList.get(i));
			}
		}
		
		if(matchList.size()<1)return -1;
		else if(matchList.size() == 1)return matchList.get(0);
		
		int selected = matchList.get(0);
		int compare  = -1;
		for(int i=0,j=matchList.size();i<j-1;i++){
			compare = matchList.get(i+1);
			if(0 == direction%2){
				if(objList.get(selected).getPlace()[axis] > objList.get(compare).getPlace()[axis]){
					selected = compare;
				}
			}else{
				if(objList.get(selected).getPlace()[axis] < objList.get(compare).getPlace()[axis]){
					selected = compare;
				}
			}
		}
		return selected;
	}
	
	private static boolean collisionDetect(float a,float b,boolean isWall){
		float distance = 2.0f;
		if(isWall)distance = 1.0f;
		return Math.abs(a-b) >= distance;
	}
	
	private static boolean onDirectionCompare(float compared,float currentPosition, int direction){
		if(0 == direction % 2){
			return compared > currentPosition;
		}else{
			return compared < currentPosition;
		}
	}
	
	public static int convertAxisInt(int val){
		if(val == -1)val=2;
		if(val == 3)val=0;
		return val;
	}
	
	public static int directionToAxis(int direction){
		int axis = -1;
		switch(direction){
		case 0:
			axis = 0;
			break;
		case 1:
			axis = 0;
			break;
		case 2:
			axis = 1;
			break;
		case 3:
			axis = 1;
			break;
		case 4:
			axis = 2;
			break;
		case 5:
			axis = 2;
			break;
		}
		return axis;
	}
	
	
}
