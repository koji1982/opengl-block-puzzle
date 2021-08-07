package com.honestastrology.glblocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.honestastrology.glblocks.R.raw;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
	
	private static SoundManager _soundManager = new SoundManager();
	private static ArrayList<MediaPlayer> _songArray;
	private static SoundPool     _soundPool;
	private static int[]     _soundPoolIds;
	private static Context         _context;
	
	private static SoundPool _finishEffect;
	private static int           _finishId;
	
	private static float[]   _effectVolumes;//�Y����id�̓Y���Ɠ���
	private static int       _soundParFrame;
	
	private static boolean _stageBgmSilent = false;
	
	public static final int COLLISION_EFFECT_BLOCK = 5;
	public static final int COLLISION_EFFECT_WALL  = 6;
	
	public static int       nowPlayingNumber = 0;
	public static final int   NOT_APPLICABLE = -1;
	public static final int       SONG_TITLE = 0;
	public static final int  SONG_STAGE_BLUE = 1;
	public static final int SONG_STAGE_GREEN = 2;
	
	public static int           songPosition = 0;
	
	public static SoundManager getInstance(Context context){
		_context = context;
		loading();
		return _soundManager;
	}
	
	private static void loading(){
		setupMediaPlayer();
		setupSoundPool();
	}
	
	public static void startSoundProcess(int songNumber){
		checkSystemVolume();
		changeSong(songNumber);
	}
	
	public static void changeSong(int songNumber){
		nowPlayingNumber = songNumber;
	}
	
	public static void resumeSong(){
		if(isStageBgmSilent())return;
		if(!_songArray.get(nowPlayingNumber).isPlaying()){
			startSong(nowPlayingNumber);
		}
	}
	
	public static void pauseSong(){
		if(_songArray.get(nowPlayingNumber).isPlaying()){
			_songArray.get(nowPlayingNumber).pause();
		}
	}
	
	public static void startSong(int songNumber){
		stopSong(songNumber);
		_songArray.get(nowPlayingNumber).start();
	}
	
	public static void stopSong(int nextPlayNumber){
		for(int i=0,j=_songArray.size();i<j;i++){
			if(i != nextPlayNumber){
				if(_songArray.get(i).isPlaying()){
					_songArray.get(i).pause();
					_songArray.get(i).seekTo(0);
				}
			}
		}
	}
	
	public static int randomSE(){
		Random random = new Random();
		int randomId = random.nextInt(4);
		return randomId;
	}
	
	public static void playSoundEffect(int id){
		if(_soundParFrame < 7){
			_soundPool.play(_soundPoolIds[id], _effectVolumes[id], _effectVolumes[id], 0, 0, 0);
			_soundParFrame++;
		}
	}
	
	public static void frameReset(){
		_soundParFrame = 0;
	}
	
	public static void finishEffect(){
		_finishEffect.play(_finishId, 1.0f, 1.0f, 0, 0, 0);
	}
	
	private static void setupMediaPlayer(){
		MediaPlayer titlesong = MediaPlayer.create(_context, raw.cube_title);
		titlesong.setLooping(true);
		titlesong.setVolume(0.9f,0.9f);
		MediaPlayer bluestagesong = MediaPlayer.create(_context, raw.cube_blue);
		bluestagesong.setLooping(true);
		bluestagesong.setVolume(0.9f, 0.9f);
		MediaPlayer greenstagesong = MediaPlayer.create(_context, raw.cube_green);
		greenstagesong.setLooping(true);
		greenstagesong.setVolume(0.9f, 0.9f);
		_songArray = new ArrayList<MediaPlayer>();
		_songArray.add(titlesong);
		_songArray.add(bluestagesong);
		_songArray.add(greenstagesong);
	}
	
	private static void setupSoundPool(){
		_soundPool         = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		_soundPoolIds      = new int[8];
		_effectVolumes     = new float[8];
		_soundPoolIds[0]   = _soundPool.load(_context,raw.metal_noise_c2,1);
		 _effectVolumes[0] = 0.75f;
		_soundPoolIds[1]   = _soundPool.load(_context,raw.metal_noise_g2,1);
		 _effectVolumes[1] = 0.55f;
		_soundPoolIds[2]   = _soundPool.load(_context,raw.metal_noise_c3,1);
		 _effectVolumes[2] = 0.4f;
		_soundPoolIds[3]   = _soundPool.load(_context,raw.metal_noise_g3,1);
		 _effectVolumes[3] = 0.3f;
		 _soundPoolIds[4]  = _soundPool.load(_context,raw.metal_noise_c4,1);
		 _effectVolumes[4] = 0.3f;
		 _soundPoolIds[5]  = _soundPool.load(_context,raw.metal_noise_g4,1);
		 _effectVolumes[5] = 0.3f;
		_soundPoolIds[6]   = _soundPool.load(_context,raw.collision,1);
		 _effectVolumes[6] = 0.70f;
		_soundPoolIds[7]   = _soundPool.load(_context,raw.collision4,1);
		 _effectVolumes[7] = 0.65f;
		 
		 _finishEffect     = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
		 _finishId         = _finishEffect.load(_context, raw.finisheffect, 0);
	}
	
	private static void checkSystemVolume(){
		AudioManager audiomanager = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
		int volume       = 0;
		int systemvolume = audiomanager.getStreamVolume(AudioManager.STREAM_SYSTEM);
		int musicvolume  = audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC);
		if(musicvolume < systemvolume){
			volume = musicvolume;
		}else{
			volume = systemvolume;
		}
		audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
	}

	public static boolean isStageBgmSilent() {
		return _stageBgmSilent;
	}

	public static void setStageBgmSilent(boolean _stageBgmSilent) {
		SoundManager._stageBgmSilent = _stageBgmSilent;
	}
	
	@Override
	protected void finalize() throws Throwable {
		for(int i=0;i<_songArray.size();i++){
			_songArray.get(i).pause();
			_songArray.get(i).stop();
			_songArray.get(i).release();
		}
		super.finalize();
	}
	
}
