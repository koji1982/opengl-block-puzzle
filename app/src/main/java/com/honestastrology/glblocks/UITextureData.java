package com.honestastrology.glblocks;

import java.util.ArrayList;
import java.util.List;

public class UITextureData {
	
	private static UITextureData _textureData = new UITextureData();
	
	private static ArrayList<Texture> usualUIList;
	private static ArrayList<Texture> reactUIList;
	
	public static Texture testTex ;
	public static Texture upButton;
	public static Texture upReact;
	public static Texture downButton;
	public static Texture downReact;
	public static Texture rightButton;
	public static Texture rightReact;
	public static Texture leftButton;
	public static Texture leftReact;
	public static Texture nearButton;
	public static Texture nearReact;
	public static Texture farButton;
	public static Texture farReact;
	public static Texture axisXRadio;
	public static Texture axisXReact;
	public static Texture axisYRadio;
	public static Texture axisYReact;
	public static Texture axisZRadio;
	public static Texture axisZReact;
	public static Texture returnButton;
	public static Texture returnReact;
	public static Texture restartButton;
	public static Texture restartReact;
	public static Texture multipleButton;
	public static Texture multipleReact;
	public static Texture nicheButton;
	public static Texture nicheReact;
	public static Texture numberButton;
	public static Texture numberReact;
	
	private UITextureData(){
		usualUIList = new ArrayList<Texture>();
		usualUIList.add(new Texture(0,"upbutton",256,256));
		usualUIList.add(new Texture(1,"downbutton",256,256));
		usualUIList.add(new Texture(2,"rightbutton",256,256));
		usualUIList.add(new Texture(3,"leftbutton",256,256));
		usualUIList.add(new Texture(4,"nearbutton",256,256));
		usualUIList.add(new Texture(5,"farbutton",256,256));
		usualUIList.add(new Texture(6,"axisxradio",256,256));
		usualUIList.add(new Texture(7,"axisyradio",256,256));
		usualUIList.add(new Texture(8,"axiszradio",256,256));
		usualUIList.add(new Texture(9,"return_button",256,256));
		usualUIList.add(new Texture(10,"restart_button",256,256));
		usualUIList.add(new Texture(11,"bgm_button",256,256));
		usualUIList.add(new Texture(12,"multiplebutton",512,256));
		usualUIList.add(new Texture(13,"blank_button",512,256));
		usualUIList.add(new Texture(14,"button0",512,256));//Inkscape y0(-22.00)y1(334.00)
		usualUIList.add(new Texture(15,"button1",512,256));
		usualUIList.add(new Texture(16,"button2",512,256));
		usualUIList.add(new Texture(17,"button3",512,256));
		usualUIList.add(new Texture(18,"button4",512,256));
		usualUIList.add(new Texture(19,"button5",512,256));
		
		reactUIList = new ArrayList<Texture>();
		reactUIList.add(new Texture(0,"upreact",256,256));
		reactUIList.add(new Texture(1,"downreact",256,256));
		reactUIList.add(new Texture(2,"rightreact",256,256));
		reactUIList.add(new Texture(3,"leftreact",256,256));
		reactUIList.add(new Texture(4,"nearreact",256,256));
		reactUIList.add(new Texture(5,"farreact",256,256));
		reactUIList.add(new Texture(6,"axisxreact",256,256));
		reactUIList.add(new Texture(7,"axisyreact",256,256));
		reactUIList.add(new Texture(8,"axiszreact",256,256));
		reactUIList.add(new Texture(9,"return_button",256,256));
		reactUIList.add(new Texture(10,"restart_button",256,256));
		reactUIList.add(new Texture(11,"nobgm_button",256,256));
		reactUIList.add(new Texture(12,"multiplereact",512,256));
		reactUIList.add(new Texture(13,"blank_react",512,256));
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
