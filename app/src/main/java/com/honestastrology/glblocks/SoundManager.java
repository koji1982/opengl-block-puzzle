package com.honestastrology.glblocks;

import java.util.Random;

import com.honestastrology.glblocks.R.raw;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseArray;
import android.util.SparseIntArray;

public class SoundManager {
	
	private static SoundManager _soundManager = new SoundManager();
//	private static SparseArray<MediaPlayer> _songArray;
	private static SoundPool    _soundPool;
	private static int[]     	_soundPoolIds;
	private static Context      _context;
	private static AudioManager _systemAudio;
	
	private static SoundPool 	_finishEffect;
	private static int          _finishId;
	
	private static float[]   	_effectVolumes;//添字はidの添字と同じ
	private static int       	_soundParFrame;
	
	private static boolean 		_stageBgmSilent    = false;
	private static boolean 	 	_isRequestedChange = false;
	private static boolean		_isAdShowing	   = false;
	
	private static MediaPlayer  _currentPlayer;
	private static MediaPlayer  _nextPlayer;
	private static MediaPlayer  _swapTempPlayer;
	
	public static final int COLLISION_EFFECT_BLOCK = 5;
	public static final int COLLISION_EFFECT_WALL  = 6;
	
	public static int       nowPlayingNumber = 0;
	public static final int NOT_APPLICABLE 	 = -1;
	public static final int SONG_TITLE		 = 0;
	public static final int SONG_STAGE_BLUE  = 1;
	public static final int SONG_STAGE_GREEN = 2;
	public static final int SONG_STAGE_GRAY  = 3;
	
	private static SparseIntArray 	  _songNumber_res_map;
	private static SparseArray<Float> _volumeMap;
	private static AudioAttributes	  _audioAttributesForMusic;
	
	static{
		_songNumber_res_map = new SparseIntArray();
		_songNumber_res_map.put(SONG_TITLE, 	  raw.cube_title);
		_songNumber_res_map.put(SONG_STAGE_BLUE,  raw.cube_blue );
		_songNumber_res_map.put(SONG_STAGE_GREEN, raw.cube_green);
		_songNumber_res_map.put(SONG_STAGE_GRAY,  raw.sorus);
		
		_volumeMap = new SparseArray<>();
		_volumeMap.put( SONG_TITLE, 	  (Float)0.4f );
		_volumeMap.put( SONG_STAGE_BLUE,  (Float)0.9f );
		_volumeMap.put( SONG_STAGE_GREEN, (Float)0.8f );
		_volumeMap.put( SONG_STAGE_GRAY,  (Float)0.7f );
		
		_audioAttributesForMusic = new AudioAttributes.Builder()
										   .setUsage( AudioAttributes.USAGE_GAME )
										   .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
										   .build();
	}
	
	public static SoundManager getInstance(Context context){
		_context = context;
		_systemAudio = (AudioManager)_context.getSystemService(
															Context.AUDIO_SERVICE);
		adjustSystemVolume();
		loading();
		return _soundManager;
	}
	
	private static void loading(){
		setupSoundPool();
	}
	
	public static void startSoundProcess(int songNumber){
		adjustSystemVolume();
		changeSong(songNumber);
	}
	
	public static void startAd(){
		_isAdShowing = true;
	}
	
	public static void dismissAd(){
		_isAdShowing = false;
	}
	
	public static void changeSong(int songNumber){
		_isRequestedChange = ( nowPlayingNumber != songNumber );
		nowPlayingNumber   = songNumber;
	}
	
	public static void resumeSong(){
		if(isStageBgmSilent())return;
		if( _isRequestedChange
					|| _currentPlayer == null
					|| !(_currentPlayer.isPlaying()) ){
			startSong( nowPlayingNumber );
			_isRequestedChange = false;
		}
	}
	
	public static void pauseSong(){
		if( _currentPlayer == null ) return;
		if( !_currentPlayer.isPlaying() ) return;
		
		_currentPlayer.pause();
	}
	
	public static void startSong(int songNumber){
		stopSong();
		if( _isAdShowing ) return;
		_currentPlayer = MediaPlayer.create(
										_context,
										_songNumber_res_map.get( nowPlayingNumber ));
		_currentPlayer.setOnPreparedListener( mediaPlayer -> {
			_currentPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
			float volume = _volumeMap.get( nowPlayingNumber );
			_currentPlayer.setVolume( volume, volume );
			_currentPlayer.start();
		});
//		adjustSystemVolume();
//		_currentPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
		createNextMediaPlayer();
	}
	
	public static void stopSong(){
		if( _currentPlayer == null )return;
		_currentPlayer.pause();
		_currentPlayer.seekTo( 0 );
		if( _nextPlayer != null){
			_nextPlayer.release();
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
		_finishEffect.play(_finishId, 0.20f, 0.20f, 0, 0, 0);
	}
	
	private static void createNextMediaPlayer(){
		_nextPlayer = MediaPlayer.create(_context, _songNumber_res_map.get(nowPlayingNumber));
		
		_nextPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
		float volume = _volumeMap.get( nowPlayingNumber );
		_nextPlayer.setVolume( volume, volume );
		
		_currentPlayer.setNextMediaPlayer(_nextPlayer);
		_currentPlayer.setOnCompletionListener(onCompletionListener);
	}
	
	private static final MediaPlayer.OnCompletionListener onCompletionListener
			= mediaPlayer -> {
				mediaPlayer.release();
				_currentPlayer = _nextPlayer;
				createNextMediaPlayer();
			};
	
	
	
	private static void setupSoundPool(){
		_soundPool         = new SoundPool(9, AudioManager.STREAM_MUSIC, 0);
		_soundPoolIds      = new int[9];
		_effectVolumes     = new float[9];
		_soundPoolIds[0]   = _soundPool.load(_context,raw.metal_noise_c2,1);
		 _effectVolumes[0] = 0.23f;
		_soundPoolIds[1]   = _soundPool.load(_context,raw.metal_noise_g2,1);
		 _effectVolumes[1] = 0.15f;
		_soundPoolIds[2]   = _soundPool.load(_context,raw.metal_noise_c3,1);
		 _effectVolumes[2] = 0.09f;
		_soundPoolIds[3]   = _soundPool.load(_context,raw.metal_noise_g3,1);
		 _effectVolumes[3] = 0.06f;
		 _soundPoolIds[4]  = _soundPool.load(_context,raw.metal_noise_c4,1);
		 _effectVolumes[4] = 0.06f;
		 _soundPoolIds[5]  = _soundPool.load(_context,raw.metal_noise_g4,1);
		 _effectVolumes[5] = 0.06f;
		_soundPoolIds[6]   = _soundPool.load(_context,raw.collision,1);
		 _effectVolumes[6] = 0.50f;
		_soundPoolIds[7]   = _soundPool.load(_context,raw.clear_jingle,1);
		 _effectVolumes[7] = 0.10f;
		_soundPoolIds[8]   = _soundPool.load(_context,raw.collision4,1);
		_effectVolumes[8] = 0.65f;
		 
		 _finishEffect     = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
		 _finishId         = _finishEffect.load(_context, raw.finisheffect, 0);
	}
	
	private static void adjustSystemVolume(){
		int currentVolume = _systemAudio.getStreamVolume(
											AudioManager.STREAM_MUSIC );
		_systemAudio.setStreamVolume( AudioManager.STREAM_MUSIC,
									  currentVolume,
									  AudioManager.ADJUST_SAME );
	}

	public static boolean isStageBgmSilent() {
		return _stageBgmSilent;
	}

	public static void setStageBgmSilent(boolean _stageBgmSilent) {
		SoundManager._stageBgmSilent = _stageBgmSilent;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if( _currentPlayer != null ){
			_currentPlayer.pause();
			_currentPlayer.stop();
			_currentPlayer.release();
		}
		if( _nextPlayer != null ){
			_nextPlayer.release();
		}
//		for(int i=0;i<_songArray.size();i++){
//			_songArray.get(i).pause();
//			_songArray.get(i).stop();
//			_songArray.get(i).release();
//		}
		super.finalize();
	}
	
}
