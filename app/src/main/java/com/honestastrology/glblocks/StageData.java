package com.honestastrology.glblocks;

public class StageData {
	
	private int        bestScore;
	private int        moveLimit;
	
	public StageData(){
		
	}
	
	public StageData(int bestscore,int movelimit){
		setBestScore(bestscore);
		setMoveLimit(movelimit);
	}
	
	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestscore) {
		this.bestScore = bestscore;
	}
	
	public int getMoveLimit() {
		return moveLimit;
	}
	
	public void setMoveLimit(int moveLimit) {
		this.moveLimit = moveLimit;
	}
}
