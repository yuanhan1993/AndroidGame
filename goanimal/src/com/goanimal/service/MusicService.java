package com.goanimal.service;
import com.goanimal.util.Constant;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.os.IBinder;

public class MusicService extends Service{
	
	private MediaPlayer mp=new MediaPlayer();
		@Override
	public void onStart(Intent intent, int startId) {
		
		
		mp.start();
		 //播放完毕之后的处理
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				try {
					mp.start();
				} catch (IllegalStateException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		 //播放音乐发生错误的事件处理
		mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				try {
					mp.release();
				} catch (Exception e) {
	
					e.printStackTrace();
				}
				return false;
			}
			
		});
		
		  }
	
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.stop();
		mp.release();
		
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		try {
			    //创建随机播放的音乐
			    int it=(int) (Math.random()*(Constant.MUSIC.length-1));
			
			
				//从音乐数组里随机选择一个
				mp=new MediaPlayer();
				mp = MediaPlayer.create(MusicService.this, Constant.MUSIC[it]);
				// 设置音频流的类型
				mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mp.prepareAsync();
			
		} catch (IllegalStateException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		super.onCreate();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
