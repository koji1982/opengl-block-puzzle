package com.honestastrology.glblocks;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class GLBaseSurfaceView extends GLSurfaceView implements
GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	private GLBlocksActivity _glBlocksActivity = CollateralConnector.getInstance().getGLBlocksActivity();
	private GLBaseRenderer _glBaseRenderer;
	private GestureDetector _gestureDetector;
	private boolean touchEventEnable = false;
	private Handler handler = new Handler();
	
	private static float previousX = 0.0f;
	private static float previousY = 0.0f;
	
	private long   previousTimeForDoubleTap = 0;
	private static final long  DOUBLETAP_INTERVAL = 200;
	
	private static int _eventAction  = -1;
	
	public GLBaseSurfaceView(Context context) {
		super(context);
	}
	
	public GLBaseSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setGL(Context context){
		setEGLContextClientVersion(2);
		setEGLConfigChooser(8,8,8,8,16,8);
		_glBaseRenderer=new GLBaseRenderer(context);
		setRenderer(_glBaseRenderer);
		_gestureDetector=new GestureDetector(context,this);
		CollateralConnector.getInstance().setGLBaseSurfaceView(this);
	}
	
	
	public boolean onTouchEvent(MotionEvent e){
		if(!isTouchEventEnable())return false;
		
		if(_gestureDetector.onTouchEvent(e))return true;
		
		int   actionType = e.getActionMasked();
		int pointerIndex = e.getActionIndex();
		int    pointerId = e.getPointerId(pointerIndex);
		switch(actionType){
		case MotionEvent.ACTION_DOWN:
//			if(detectDoubleTap())break;
			setEventAction(MotionEvent.ACTION_DOWN);
			_glBaseRenderer.onTouch(e.getX(pointerIndex), e.getY(pointerIndex),pointerId);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			setEventAction(MotionEvent.ACTION_DOWN);
			_glBaseRenderer.onTouch(e.getX(pointerIndex),e.getY(pointerIndex),pointerId);
			break;
//		case MotionEvent.ACTION_MOVE:
//			setEventAction(MotionEvent.ACTION_MOVE);
//			processOnMove(e);
//			break;
		case MotionEvent.ACTION_POINTER_UP:
			setEventAction(MotionEvent.ACTION_UP);
			_glBaseRenderer.onUpMotion(pointerId);
			break;
		case MotionEvent.ACTION_UP:
			setEventAction(MotionEvent.ACTION_UP);
			_glBaseRenderer.onUpMotion(pointerId);
			break;
		case MotionEvent.ACTION_CANCEL:
			setEventAction(MotionEvent.ACTION_UP);
			_glBaseRenderer.onUpMotion(pointerId);
			break;
		}
		
		return true;
	}
	
	private boolean detectDoubleTap(){
		long currentTime = System.currentTimeMillis();
		if((currentTime - previousTimeForDoubleTap) < DOUBLETAP_INTERVAL){
			_glBaseRenderer.stopAllRepeat();
			if(!GLIrregularFixedUI.isMultipleButtonOn()){
				UIManager.clearRadio();
			}
			return true;
		}
		previousTimeForDoubleTap = currentTime;
		return false;
	}
	
	private boolean processOnMove(MotionEvent e1,MotionEvent e2){
		
		if(_glBaseRenderer.queryRepeatId(e1.getPointerId(e1.getActionIndex())))return false;
		if(_glBaseRenderer.queryRepeatId(e2.getPointerId(e2.getActionIndex())))return false;
		
		float dx = e2.getX() - e1.getX();
		float dy = e2.getY() - e1.getY();
		float  c = BaseCalculator.calcPythagoreanLong(dx, dy);
		if(c > 2200){
			Log.d("onMove",String.valueOf(c));
			setEventAction(MotionEvent.ACTION_MOVE);
			_glBaseRenderer.onSwipe(new float[]{dx,dy});
		}
		
		return true;
		
		
//		float x = e.getX();
//		float y = e.getY();
//		if(e.getActionMasked() == MotionEvent.ACTION_MOVE){
//			float dx = x-previousX;
//			float dy = y-previousY;
//			float c  = BaseCalculator.calcPythagoreanLong(dx, dy);
//			if(c>2200){
//				_glBaseRenderer.onSwipe(new float[]{dx,dy});
//			}else{
//				for(int i=0;i<historySize;i++){
//					for(int j=0;j<pointerCount;j++ ){
//						int id = e.getPointerId(j);
//						int pointerIndex = e.findPointerIndex(id);
//						float histX = e.getHistoricalX(j, i);
//						float histY = e.getHistoricalY(j, i);
//						setEventAction(e.getActionMasked());
//						_glBaseRenderer.onTouch(histX, histY,id);
//					}
//				}
//			}
//		}
//		previousX=x;
//		previousY=y;
	}
	
	
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		_glBaseRenderer.stopAllRepeat();
		if(!GLIrregularFixedUI.isMultipleButtonOn()){
			UIManager.clearRadio();
		}
		return true;
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return false;
	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onDown(MotionEvent e) {
		setEventAction(e.getActionMasked());
		_glBaseRenderer.onTouch(e.getX(), e.getY(),0);
		return true;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return processOnMove(e1,e2);
	}
	@Override
	public void onLongPress(MotionEvent e) {
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	public static void resetPreviousCoords(){
		previousX = 0.0f;
		previousY = 0.0f;
	}
	
	public static int getEventAction() {
		return _eventAction;
	}

	private static void setEventAction(int _eventAction) {
		GLBaseSurfaceView._eventAction = _eventAction;
	}

	public boolean isTouchEventEnable() {
		return touchEventEnable;
	}

	public void setTouchEventEnable(boolean touchEventEnable) {
		this.touchEventEnable = touchEventEnable;
	}
	
}
