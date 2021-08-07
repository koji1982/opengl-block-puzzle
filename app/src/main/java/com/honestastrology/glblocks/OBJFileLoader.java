package com.honestastrology.glblocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.opengl.GLES20;

public class OBJFileLoader {
	
	private GLBaseObject obj;
	private ArrayList<Integer> indexNums;
	private boolean iniBool;
	private boolean texBool;
	
	public GLBaseObject objLoad(Context context, String objName)throws IOException{
		
		ArrayList<Float> preVer     = new ArrayList<Float>();
		ArrayList<Float> preNor     = new ArrayList<Float>();
		ArrayList<Float> preTex     = new ArrayList<Float>();
		ArrayList<Integer> preIndex = new ArrayList<Integer>();
		
		InputStream is=context.getAssets().open(objName);
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		BufferedReader bf     = new BufferedReader(isr);
		
		String objLine;
		indexNums = new ArrayList<Integer>();
		indexNums.add(0);
		iniBool = false;
		texBool = true;
		ArrayList<ArrayList<ArrayList<Float>>> vers   = new ArrayList<ArrayList<ArrayList<Float>>>();
		ArrayList<ArrayList<ArrayList<Float>>> texs   = new ArrayList<ArrayList<ArrayList<Float>>>();
		ArrayList<ArrayList<ArrayList<Float>>> nors   = new ArrayList<ArrayList<ArrayList<Float>>>();
		ArrayList<ArrayList<ArrayList<Integer>>> inds = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<Float> vertices    = new ArrayList<Float>();
		ArrayList<Float> textures    = new ArrayList<Float>();
		ArrayList<Float> normals     = new ArrayList<Float>();
		Map<String,String> texImage  = new HashMap<String,String>();
		ArrayList<String> newMtlList = new ArrayList<String>();
		int maxV     = 0;
		int maxT     = 0;
		int maxN     = 0;
		int verCount = 0;
		int texCount = 0;
		int norCount = 0;
		objLine=bf.readLine();
		
		String mtlLibrary  =null;
		ArrayList<String> useMtl = new ArrayList<String>();
		int useMtlNum     = 0;
		int currentMtlNum = 0;
		int currentObjNum = 0;
		float[] preMtl    = new float[16];
		ArrayList<float[]> mtlArray = new ArrayList<float[]>();
		
		do{
			
			String[] splitLine=objLine.split(" ", 5);
			
			if(splitLine[0].equals("v")){
				preVer.add(Float.parseFloat(splitLine[1]));
				preVer.add(Float.parseFloat(splitLine[2]));
				preVer.add(Float.parseFloat(splitLine[3]));
				verCount++;
			}else if(splitLine[0].equals("f")){//face, v, t, n, triangle
				
				if(!iniBool){
					boolean containBool = false;
					for(int i=0;i<splitLine.length;i++){
						containBool=splitLine[i].contains("//");
						if(containBool){
							texBool=false;
						}
					}
					iniBool=true;
				}
				if(texBool){
					
					for(int i=1;i<=3;i++){
						String[] splitFace = splitLine[i].split("/",3);
						int v = (Integer.parseInt(splitFace[0])-1)*3;
						int t = (Integer.parseInt(splitFace[1])-1)*2;
						int n = (Integer.parseInt(splitFace[2])-1)*3;
						
						inds.get(currentObjNum).get(currentMtlNum).add(indexNums.get(currentMtlNum));
						
						vers.get(currentObjNum).get(currentMtlNum).add(preVer.get(v));
						vers.get(currentObjNum).get(currentMtlNum).add(preVer.get(v+1));
						vers.get(currentObjNum).get(currentMtlNum).add(preVer.get(v+2));
						texs.get(currentObjNum).get(currentMtlNum).add(preTex.get(t));
						texs.get(currentObjNum).get(currentMtlNum).add(preTex.get(t+1));
						nors.get(currentObjNum).get(currentMtlNum).add(preNor.get(n));
						nors.get(currentObjNum).get(currentMtlNum).add(preNor.get(n+1));
						nors.get(currentObjNum).get(currentMtlNum).add(preNor.get(n+2));
						
						indexNums.set(currentMtlNum, indexNums.get(currentMtlNum)+1);
						
//						String[] splitFace=splitLine[i].split("/", 3);
//						
//						int v=(Integer.parseInt(splitFace[0])-1)*3;
//						int t=(Integer.parseInt(splitFace[1])-1)*2;
//						int n=(Integer.parseInt(splitFace[2])-1)*3;
//						
//						preIndex.add(indexNums.get(currentMtlNum));
//						
//						vertices.add(preVer.get(v));
//						vertices.add(preVer.get(v+1));
//						vertices.add(preVer.get(v+2));
//						
//						texs.add(preTex.get(t));
//						texs.add(preTex.get(t+1));
//						
//						normals.add(preNor.get(n));
//						normals.add(preNor.get(n+1));
//						normals.add(preNor.get(n+2));
//						
//						indexNums.set(currentMtlNum, indexNums.get(currentMtlNum)+1);
						
					}
					
				}else{
					////////
					for(int i=1;i<=3;i++){
						String[] splitFace=splitLine[i].split("//",3);
						
						int v=(Integer.parseInt(splitFace[0])-1)*3;
						//int t=(Integer.parseInt(splitFace[1])-1)*2;
						int n=(Integer.parseInt(splitFace[1])-1)*3;
						//int testVer=preVer.size();
						//int testTex=preTex.size();
						//int testNor=preNor.size();
						//if(testVer<v){
						//	GLBaseError.errorLog("v");
						//}else if(testTex<t){
							//GLBaseError.errorLog("t");
						//}else if(testNor<n){
						//	GLBaseError.errorLog("n");
						//}
						
						//if(maxV<Integer.parseInt(splitFace[0])){
						//	maxV=Integer.parseInt(splitFace[0]);
						//}
						/*if(maxT<Integer.parseInt(splitFace[1])){
							maxT=Integer.parseInt(splitFace[1]);
						}*/
						//if(maxN<Integer.parseInt(splitFace[1])){
						//	maxN=Integer.parseInt(splitFace[1]);
						//}
						
						//preIndex.add(Integer.parseInt(splitFace[0]));
						//preIndex.add(Integer.parseInt(splitFace[1]));
						
						inds.get(currentObjNum).get(currentMtlNum).add(indexNums.get(currentMtlNum));
						
						vers.get(currentObjNum).get(currentMtlNum).add(preVer.get(v));
						vers.get(currentObjNum).get(currentMtlNum).add(preVer.get(v+1));
						vers.get(currentObjNum).get(currentMtlNum).add(preVer.get(v+2));
						//texs.add(preTex.get(t));
						//texs.add(preTex.get(t+1));
						nors.get(currentObjNum).get(currentMtlNum).add(preNor.get(n));
						nors.get(currentObjNum).get(currentMtlNum).add(preNor.get(n+1));
						nors.get(currentObjNum).get(currentMtlNum).add(preNor.get(n+2));
						
						indexNums.set(currentMtlNum, indexNums.get(currentMtlNum)+1);
					}
					
				}
				
			}else if(splitLine[0].equals("vt")){
				preTex.add(Float.parseFloat(splitLine[1]));
				preTex.add(Float.parseFloat(splitLine[2]));
				texCount++;
			}else if(splitLine[0].equals("vn")){
				preNor.add(Float.parseFloat(splitLine[1]));
				preNor.add(Float.parseFloat(splitLine[2]));
				preNor.add(Float.parseFloat(splitLine[3]));
				norCount++;
			}else if(splitLine[0].equals("mtllib")){
				mtlLibrary=splitLine[1];
			}else if(splitLine[0].equals("usemtl")){
				if(!useMtl.contains(splitLine[1])){
					useMtl.add(splitLine[1]);
					vers.get(currentObjNum).add(new ArrayList<Float>());
					texs.get(currentMtlNum).add(new ArrayList<Float>());
					nors.get(currentObjNum).add(new ArrayList<Float>());
					inds.get(currentObjNum).add(new ArrayList<Integer>());
					indexNums.add(0);
				}
				currentMtlNum=useMtl.indexOf(splitLine[1]);
			}else if(splitLine[0].equals("o")){
				vers.add(new ArrayList<ArrayList<Float>>());
				texs.add(new ArrayList<ArrayList<Float>>());
				nors.add(new ArrayList<ArrayList<Float>>());
				inds.add(new ArrayList<ArrayList<Integer>>());
			}
			
			
		}while((objLine=bf.readLine())!=null);
		
		bf.close();
		
		if(mtlLibrary!=null){
			InputStream          mtlIs  = context.getAssets().open(mtlLibrary);
			InputStreamReader mtlReader = new InputStreamReader(mtlIs,"UTF-8");
			BufferedReader        mtlBr = new BufferedReader(mtlReader);
			
			String mtlLine;
			mtlLine = mtlBr.readLine();
			String currentMtl = null;
			
			do{
				String[] splitMtl=mtlLine.split(" ", 4);
				
				if(splitMtl[0].equals("newmtl")){
					newMtlList.add(splitMtl[1]);
					currentMtl=splitMtl[1];
					preMtl=new float[16];
				}else if(splitMtl[0].equals("Ka")){
					preMtl[0]=Float.parseFloat(splitMtl[1]);
					preMtl[1]=Float.parseFloat(splitMtl[2]);
					preMtl[2]=Float.parseFloat(splitMtl[3]);
					preMtl[3]=1.0f;
				}else if(splitMtl[0].equals("Kd")){
					preMtl[4]=Float.parseFloat(splitMtl[1]);
					preMtl[5]=Float.parseFloat(splitMtl[2]);
					preMtl[6]=Float.parseFloat(splitMtl[3]);
					preMtl[7]=1.0f;
				}else if(splitMtl[0].equals("Ks")){
					preMtl[8]=Float.parseFloat(splitMtl[1]);
					preMtl[9]=Float.parseFloat(splitMtl[2]);
					preMtl[10]=Float.parseFloat(splitMtl[3]);
					preMtl[11]=1.0f;
				}else if(splitMtl[0].equals("Ns")){
					preMtl[12]=Float.parseFloat(splitMtl[1]);
				}else if(splitMtl[0].equals("d")){
					preMtl[3]=Float.parseFloat(splitMtl[1]);
					preMtl[7]=Float.parseFloat(splitMtl[1]);
					preMtl[11]=Float.parseFloat(splitMtl[1]);
				}else if(splitMtl[0].equals("illum")){
					mtlArray.add(preMtl);
				}else if(splitMtl[0].contains("map_Kd")){
					if(splitMtl[1].contains(".jpg")){
						texImage.put(currentMtl, splitMtl[1]);
					}else if(splitMtl[1].contains(".png")){
						texImage.put(currentMtl, splitMtl[1]);
					}
				}
				
				
				
			}while((mtlLine=mtlBr.readLine())!=null);
		}
		
		ArrayList<ArrayList<InferiorVBO>> vArray=new ArrayList<ArrayList<InferiorVBO>>();
		ArrayList<ArrayList<InferiorTBO>> tArray=new ArrayList<ArrayList<InferiorTBO>>();
		ArrayList<ArrayList<InferiorNBO>> nArray=new ArrayList<ArrayList<InferiorNBO>>();
		ArrayList<ArrayList<InferiorIBO>> iArray=new ArrayList<ArrayList<InferiorIBO>>();
		
		for(int i=0,h=vers.size();i<h;i++){
			ArrayList<InferiorVBO> nestedArray=new ArrayList<InferiorVBO>();
			for(int j=0;j<vers.get(i).size();j++){
				int verSize=vers.get(i).get(j).size();
				float[] ver=new float[verSize];
				for(int k=0;k<verSize;k++){
					ver[k]=vers.get(i).get(j).get(k);
				}
				nestedArray.add(new InferiorVBO(ver));
			}
			vArray.add(nestedArray);
		}
		
		for(int i=0,h=texs.size();i<h;i++){
			ArrayList<InferiorTBO> nestedArray=new ArrayList<InferiorTBO>();
			for(int j=0;j<texs.get(i).size();j++){
				int texSize=texs.get(i).get(j).size();
				float[] tex=new float[texSize];
				for(int k=0;k<texSize;k++){
					tex[k]=texs.get(i).get(j).get(k);
				}
				nestedArray.add(new InferiorTBO(tex));
			}
			tArray.add(nestedArray);
		}
		
		
		for(int i=0,h=nors.size();i<h;i++){
			ArrayList<InferiorNBO> nestedArray=new ArrayList<InferiorNBO>();
			for(int j=0;j<nors.get(i).size();j++){
				int norSize=nors.get(i).get(j).size();
				float[] nor=new float[norSize];
				for(int k=0;k<norSize;k++){
					nor[k]=nors.get(i).get(j).get(k);
				}
				nestedArray.add(new InferiorNBO(nor));
			}
			nArray.add(nestedArray);
		}
		
		for(int i=0,h=inds.size();i<h;i++){
			ArrayList<InferiorIBO> nestedArray=new ArrayList<InferiorIBO>();
			for(int j=0;j<inds.get(i).size();j++){
				int indSize=inds.get(i).get(j).size();
				short[] ind=new short[indSize];
				for(int k=0;k<indSize;k++){
					ind[k]=inds.get(i).get(j).get(k).shortValue();
				}
				nestedArray.add(new InferiorIBO(ind));
			}
			iArray.add(nestedArray);
		}
		
		ArrayList<float[]> mtlSequence=(ArrayList<float[]>) mtlArray.clone();
		for(int i=0;i<useMtl.size();i++){
			mtlSequence.set(i, mtlArray.get(newMtlList.indexOf(useMtl.get(i))));
		}
		
		int bufferNum=3;
		
		if(texBool){
			bufferNum=4;
		}
		
		int[] bufferIds=new int[bufferNum];
		GLES20.glGenBuffers(bufferNum, bufferIds, 0);
		
		if(texBool){
			obj = new GLInferiorTexObject(objName);
			((GLInferiorTexObject) obj).setCubeIboGroup(iArray);
			((GLInferiorTexObject) obj).setCubeVboGroup(vArray);
			((GLInferiorTexObject) obj).setCubeTboGroup(tArray);
			((GLInferiorTexObject) obj).setCubeNboGroup(nArray);
	        ((GLInferiorTexObject) obj).setCubeMaterial(mtlSequence);
			((GLInferiorTexObject) obj).setTexImage(texImage);
		}else{
			obj = new GLInferiorObject(objName);
			((GLInferiorObject) obj).setIBO(iArray);
			((GLInferiorObject) obj).setVBO(vArray);
			((GLInferiorObject) obj).setNBO(nArray);
	        ((GLInferiorObject) obj).setMaterial(mtlSequence);
			((GLInferiorObject) obj).setMaterialForFlyweight(mtlSequence);
		}
		
		//obj.setIds(bufferIds);
		
		/*
			float[] tex=new float[textures.size()];
			for(int i=0;i<textures.size();i++){
				tex[i]=textures.get(i);
			}
			*/
			//obj.setTBO(tex);
		
		
		/*
		int size=vertices.size();
		float[] verFloat=new float[size];
		for(int i=0;i<size;i++){
			verFloat[i]=vertices.get(i);
		}*/
		
		
		return obj;
	}
	
}

