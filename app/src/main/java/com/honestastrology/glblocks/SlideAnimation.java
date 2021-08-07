package com.honestastrology.glblocks;

import com.honestastrology.glblocks.R;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SlideAnimation {
	
	private static SlideAnimation _slideAnimation = new SlideAnimation();
	
	private static boolean isCreated = false;
	
	private static Animation outRight;
	private static Animation outLeft;
	private static Animation inRight;
	private static Animation inLeft;
	
	private SlideAnimation(){
		
	}
	
	public static void createInstance(Context context){
		if(!isCreated()){
			outRight = AnimationUtils.loadAnimation(context, R.anim.trans_out_right);
			outLeft  = AnimationUtils.loadAnimation(context, R.anim.trans_out_left);
			inRight  = AnimationUtils.loadAnimation(context, R.anim.trans_in_right);
			inLeft   = AnimationUtils.loadAnimation(context, R.anim.trans_in_left);
			setCreated(true);
		}
	}
	
	public static void slideOutRight(View view){
		view.startAnimation(outRight);
	}
	
	public static void slideOutLeft(View view){
		view.startAnimation(outLeft);
	}
	
	public static void slideInRight(View view){
		view.startAnimation(inRight);
	}
	
	public static void slideInLeft(View view){
		view.startAnimation(inLeft);
	}

	public static boolean isCreated() {
		return isCreated;
	}

	public static void setCreated(boolean isCreated) {
		SlideAnimation.isCreated = isCreated;
	}
	
}
