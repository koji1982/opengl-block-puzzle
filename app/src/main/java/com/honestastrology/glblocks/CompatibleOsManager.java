package com.honestastrology.glblocks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class CompatibleOsManager {
	
	private static CompatibleOsManager _compatibleOsManager = new CompatibleOsManager();
	
	private static int   displayX;
	private static int   displayY;
	private static int sdkVersion;
	
	private static float displayXMag;
	private static float displayYMag;
	
	private CompatibleOsManager(){
	}
	
	public static void onCreate(Activity activity){
		measureDisplayX(activity);
		measureDisplayY(activity);
		decideDisplayRatio();
		sdkVersion = Build.VERSION.SDK_INT;
		windowSetting(activity);
	}
	
	public static CompatibleOsManager getInstance(){
		return _compatibleOsManager;
	}
	
	public static float windowSetting(Activity activity){
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return displayXMag;
	}
	
	public static int getDisplayX(){
		return displayX;
	}
	
	public static int getDisplayY(){
		return displayY;
	}
	
	public static int getOsVersion(){
		return sdkVersion;
	}
	
	private static void measureDisplayX(Activity activity){
		displayX = getDisplayXY(activity)[0];
	}
	
	private static void measureDisplayY(Activity activity){
		displayY = getDisplayXY(activity)[1];
	}
	
	private static void decideDisplayRatio(){
		displayXMag = (float)displayX/1920;
		displayYMag = (float)displayY/1080;
	}
	
	private static int[] getDisplayXY(Activity activity){
		int[] xy = new int[2];
		Display display = activity.getWindowManager().getDefaultDisplay();
		if(getOsVersion()<13){
			xy[0] = display.getWidth();
			xy[1] = display.getHeight();
		}else{
			Point p = new Point();
			display.getSize(p);
			xy[0]   = p.x;
			xy[1]   = p.y;
		}
		return xy;
	}
}
