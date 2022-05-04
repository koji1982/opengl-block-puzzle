package com.honestastrology.glblocks;

import java.util.ArrayList;
import java.util.List;

public class UITextureData {
	
	private static UITextureData _textureData = new UITextureData();
	
	private static ArrayList<Texture> usualUIList;
	private static ArrayList<Texture> reactUIList;
	
	private UITextureData(){
		usualUIList = new ArrayList<Texture>();
		usualUIList.add(new Texture(0,"up_arrow",256,256));
		usualUIList.add(new Texture(1,"down_arrow",256,256));
		usualUIList.add(new Texture(2,"right_arrow",256,256));
		usualUIList.add(new Texture(3,"left_arrow",256,256));
		usualUIList.add(new Texture(4,"zoom_in",256,256));
		usualUIList.add(new Texture(5,"zoom_out",256,256));
		usualUIList.add(new Texture(6,"axisxradio",256,256));
		usualUIList.add(new Texture(7,"axisyradio",256,256));
		usualUIList.add(new Texture(8,"axiszradio",256,256));
		usualUIList.add(new Texture(9,"return_button",256,256));
		usualUIList.add(new Texture(10,"restart_button",256,256));
		usualUIList.add(new Texture(11,"bgm_button",256,256));
		usualUIList.add(new Texture(12,"multiplebutton",512,256));
		usualUIList.add(new Texture(13,"auto_detect",512,256));
		usualUIList.add(new Texture(14,"button0",512,256));//Inkscape y0(-22.00)y1(334.00)
		usualUIList.add(new Texture(15,"button1",512,256));
		usualUIList.add(new Texture(16,"button2",512,256));
		usualUIList.add(new Texture(17,"button3",512,256));
		usualUIList.add(new Texture(18,"button4",512,256));
		usualUIList.add(new Texture(19,"button5",512,256));
		
		reactUIList = new ArrayList<Texture>();
		reactUIList.add(new Texture(0,"up_react",256,256));
		reactUIList.add(new Texture(1,"down_react",256,256));
		reactUIList.add(new Texture(2,"right_react",256,256));
		reactUIList.add(new Texture(3,"left_react",256,256));
		reactUIList.add(new Texture(4,"zoom_in_react",256,256));
		reactUIList.add(new Texture(5,"zoom_out_react",256,256));
		reactUIList.add(new Texture(6,"axisxreact",256,256));
		reactUIList.add(new Texture(7,"axisyreact",256,256));
		reactUIList.add(new Texture(8,"axiszreact",256,256));
		reactUIList.add(new Texture(9,"return_button",256,256));
		reactUIList.add(new Texture(10,"restart_button",256,256));
		reactUIList.add(new Texture(11,"nobgm_button",256,256));
		reactUIList.add(new Texture(12,"multiplereact",512,256));
		reactUIList.add(new Texture(13,"auto_react",512,256));
		reactUIList.add(new Texture(14,"react0",512,256));
		reactUIList.add(new Texture(15,"react1",512,256));
		reactUIList.add(new Texture(16,"react2",512,256));
		reactUIList.add(new Texture(17,"react3",512,256));
		reactUIList.add(new Texture(18,"react4",512,256));
		reactUIList.add(new Texture(19,"react5",512,256));
	}
	
	public static UITextureData createTextureData(){
		return _textureData;
	}
	
	public static Texture getUsualUITex(int id){
		return usualUIList.get(id);
	}
	
	public static Texture getReactUITex(int id){
		return reactUIList.get(id);
	}
	
}
