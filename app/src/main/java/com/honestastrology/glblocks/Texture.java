package com.honestastrology.glblocks;

public class Texture {
	
	private int _serialNumber;
	private String _fileName; //without extention
	private int _height;
	private int _width;
	
	public Texture(int serial,String name,int width,int height){
		setSerialNumber(serial);
		setFileName(name);
		setWidth(width);
		setHeight(height);
	}
	
	public Texture(int width, int height){
		setWidth(width);
		setHeight(height);
	}
	
	public int getSerialNumber() {
		return _serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this._serialNumber = serialNumber;
	}
	public String getFileName() {
		return _fileName;
	}
	public void setFileName(String fileName) {
		this._fileName = fileName;
	}
	public int getHeight() {
		return _height;
	}
	public void setHeight(int height) {
		this._height = height;
	}
	public int getWidth() {
		return _width;
	}
	public void setWidth(int width) {
		this._width = width;
	}
	
}
