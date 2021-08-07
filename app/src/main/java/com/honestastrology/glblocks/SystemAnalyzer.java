package com.honestastrology.glblocks;

public class SystemAnalyzer {
	
	private static long lastUpdateTime  = System.currentTimeMillis();
	private static long currentTime     = 0;
	
	private static float fps            = 0.0f;
	private static long secondParFrame  = 0;
	
	private static long timeAtBigin     = 0;
	private static long timeAtEnd       = 0;
	private static long measuredTime    = 0;
	
	private static long adjustSum       = 0;
	
	private static long onTimeCount     = 0;
	private static long timeOverCount   = 0;
	private static long totalCount      = 0;
	private static long thauframeTime   = 0;
	
	private static int touchedId        = -2;
	
	private static int moveCount        = 0;
	
	private static long frameLap       = 0;
	
	public SystemAnalyzer(){
		
	}
	
	public static float getFPSAverage(int frameCount){
		currentTime = System.currentTimeMillis();
		fps = ((float)(frameCount*1000)/(currentTime - lastUpdateTime));
		lastUpdateTime = currentTime;
		return fps;
	}
	
	public static long compareLastTime(){
		currentTime = System.currentTimeMillis();
		return currentTime - lastUpdateTime;
	}
	
	public static long adjustWaiting(long adjustTime){
		try {
			Thread.sleep(adjustTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return adjustTime;
	}
	
	public static void setFrameCount(long onTime,long overTime,long total){
		setOnTimeCount(onTime);
		setTimeOverCount(overTime);
		setTotalCount(total);
	}
	
	public static void updateSystemTime(){
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public static float getFPS(){
		return fps;
	}
	
	public static void setSpfLap(){
		frameLap      = System.currentTimeMillis();
	}
	
	public static long calcSecondParFrame(){
		
		return secondParFrame = System.currentTimeMillis() - frameLap;
	}
	
	public static long getSecondParFrame(){
		return secondParFrame;
	}
	
	public static void measuredBiginFlag(){
		timeAtBigin  = System.currentTimeMillis();
	}
	
	public static long measuredEndFlag(){
		timeAtEnd    = System.currentTimeMillis();
		measuredTime = timeAtEnd - timeAtBigin;
		return measuredTime;
	}

	public static long getAdjustSum() {
		return adjustSum;
	}

	public static void setAdjustSum(long adjustSum) {
		SystemAnalyzer.adjustSum     = adjustSum;
	}

	public static long getTimeOverCount() {
		return timeOverCount;
	}

	public static void setTimeOverCount(long timeOverCount) {
		SystemAnalyzer.timeOverCount = timeOverCount;
	}

	public static long getOnTimeCount() {
		return onTimeCount;
	}

	public static void setOnTimeCount(long onTimeCount) {
		SystemAnalyzer.onTimeCount = onTimeCount;
	}

	public static long getTotalCount() {
		return totalCount;
	}

	public static void setTotalCount(long totalCount) {
		SystemAnalyzer.totalCount = totalCount;
	}

	public static long getThauframeTime() {
		return thauframeTime;
	}

	public static void setThauframeTime(long thauframeTime) {
		SystemAnalyzer.thauframeTime = thauframeTime;
	}

	public static int getTouchedId() {
		return touchedId;
	}

	public static void setTouchedId(int touchedId) {
		SystemAnalyzer.touchedId = touchedId;
	}

	public static int getMoveCount() {
		return moveCount;
	}

	public static void incrementMoveCount() {
		SystemAnalyzer.moveCount ++;
	}
	
	public static void resetMoveCount(){
		SystemAnalyzer.moveCount = 0;
	}

	
}
