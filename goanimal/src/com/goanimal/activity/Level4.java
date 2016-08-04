/**
 * 
 */
package com.goanimal.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.example.goanimal.R;
import com.goanimal.domain.Bead;
import com.goanimal.service.GameService4;
import com.goanimal.service.impl.GameServiceImpl4;
import com.goanimal.util.AppManager;
import com.goanimal.util.Constant;
import com.goanimal.util.Data;
import com.goanimal.util.DialogUtil;
import com.goanimal.util.FileUtil;
import com.goanimal.view.GameView4;

/**
 * @author 遥指天下
 *
 */
@SuppressLint("ShowToast")
public class Level4 extends Activity{

	// 定义游戏的业务对象
		private GameService4 gameService4;
		// 定义GameView
		private GameView4 gameView4;
		// 定义定时器
		private Timer timer;
		// 定义可走点的集合
		private List<Point> points;
		// 定义要显示的珠子
		private List<Bead> displayBeads;
		// 定义消珠子的次数
		private int count = 0;
		// 定义一个锁
		private boolean lock = true;
		// 定义手机振动器
		private Vibrator vibrator;
		/// 定义音效播放数组
		private MediaPlayer[] soundPlayer = new MediaPlayer[Constant.SOUNDS.length];
		
		// 定义音效是否播放的标识符
		private boolean isSoundPlay =false;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			AppManager.getAppManager().addActivity(this);
			Data is=(Data) getApplicationContext();
			setContentView(R.layout.level4);
			isSoundPlay=is.get_isSound();
			// 获取游戏的主界面
			Level4.this.gameView4 = (GameView4)findViewById(R.id.gameView4);
			  
           
			// 创建业务对象
			gameService4 = (GameService4) new GameServiceImpl4(this, gameView4.getBeadBoard());
			// 设置业务对象
			gameView4.setGameService(gameService4);
			Log.i("here", "就这了");
			// 为gameView绑定触摸事件
			gameView4.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					// 按下
					if (event.getAction() == MotionEvent.ACTION_DOWN){
						System.out.println(event.getX() + "===" + event.getY());
						// 根据用户点击的坐标获取相对应的珠子
						Bead bead = gameService4.getSelectedBead(event.getX(), event.getY());
						// 用户选中的珠子(要走的珠子)
						if (bead != null && bead.getBitmap() != null && lock){
							// 为gameView设置选中的珠子
							gameView4.setSelectedBead(bead);
							// 要让选中的珠子跳动
							startAnim(Constant.FLAG_1, Constant.TIMER_1);
							// 播放音效
							if (isSoundPlay) soundPlayer[0].start();
						}else{
							// 珠子存在,但是没有位图
							if (bead != null && gameView4.getSelectedBead() != null){
								// 获取两个珠子之间的线路
								points = gameService4.getPath(gameView4.getSelectedBead(), bead);
								if (!points.isEmpty()){
									// 为gameView设置目的珠子
									gameView4.setTargetBead(bead);
									// 加锁
									lock = false;
									// 开启动画效果走珠子
									startAnim(Constant.FLAG_2, Constant.TIMER_2);
								}else{
									// 播放音效
									if (isSoundPlay) soundPlayer[1].start();
								}
							}
						}
					}
					return true;
				}
			});
			// 获取手机振动器
			vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
			// 初始化音效
			for (int i = 0; i < Constant.SOUNDS.length; i++){
				soundPlayer[i] = MediaPlayer.create(this, Constant.SOUNDS[i]);
				// 设置音频流的类型
				soundPlayer[i].setAudioStreamType(AudioManager.STREAM_MUSIC);
			}
			
		}
		
		/** 消息处理对象 */
		private Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what){
					case Constant.FLAG_1: // 代表选中珠子跳动
						gameView4.setIsFlag();
						break;
					case Constant.FLAG_2: // 代表走珠子
						Point point = (Point)msg.obj;
						if (point != null){
							// 走一点
							gameView4.moveBead(point);
						}else{
							// 代表走完了珠子 --->消珠子或者显示三个珠子
							autoScan(Constant.SCAN_TYPE_1);
						}
						break;
					case Constant.FLAG_3: // 代表依次显示珠子
						Bead bead = (Bead)msg.obj;
						if (bead != null){
							// 显示一个珠子
							gameView4.displayBead(bead);
						}else{
							// 代表系统生成了三个珠子 --->消珠子
							autoScan(Constant.SCAN_TYPE_2);
						}
						break;
					case Constant.FLAG_4: // 代表消珠子
						if (msg.obj != null){
							// 让珠子同时闪烁
							gameView4.setIsFlag();
						}else{
							// 获取分数
							int score = gameService4.getPerScore();
							if (score > 0){
								// 提示得分
								Toast.makeText(Level4.this, "+" + score, Toast.LENGTH_SHORT).show();
								// 设置分数累加
								gameService4.setTotalScore();
								int totalScore = gameView4.getBeadBoard().getTotalScore();
								if(totalScore>=50){
									Toast.makeText(Level4.this, "恭喜开启下一关", Toast.LENGTH_SHORT).show();
									Intent intent=new Intent(Level4.this,Level5.class);
									startActivity(intent);
									
									

								}
							}
							
							// 消除珠子
							gameService4.clearBead();
							// 组件重绘
							gameView4.postInvalidate();
							// 解锁
							lock = true;
						}
						break;
				}
				return true;
			}
		});
		
		/**
		 * 开启动画效果的方法
		 * @param flag 标符识
		 * @param time 时长
		 */
		private void startAnim(final int flag, long time){
			if (timer != null){
				timer.cancel(); // 关闭定时器
			}
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Message msg = new Message();
					msg.what = flag;
					switch (flag){
						case Constant.FLAG_1: // 代表选中珠子跳动
							handler.sendMessage(msg);
							break;
						case Constant.FLAG_2: // 代表走珠子
							if (!points.isEmpty()){
								// 每次删除第一个
								Point point = points.remove(0);
								msg.obj = point;
							}else{
								msg.obj = null;
								timer.cancel(); // 取消定时器
							}
							handler.sendMessage(msg);
							break;
						case Constant.FLAG_3: // 代表依次显示珠子
							if (!displayBeads.isEmpty()){
								// 每次删除第一个
								Bead bead = displayBeads.remove(0);
								msg.obj = bead;
							}else{
								msg.obj = null;
								timer.cancel(); // 取消定时器
							}
							handler.sendMessage(msg);
							break;
						case Constant.FLAG_4: // 代表消珠子
							if (count++ < Constant.PER_NUM){
								msg.obj = true;
							}else{
								msg.obj = null;
								count = 0;
								timer.cancel(); // 取消定时器
							}
							handler.sendMessage(msg);
							break;
					}
				}
			}, 0, time);
		}
		
		/**
		 * 自动扫描珠子
		 * @param scanType 1 : 用户走完珠子 2. 系统生成三个珠子
		 */
		private void autoScan(int scanType) {
			// 消珠子(有珠子可消，就不要显示三个珠子)
			if (gameService4.scanBead(scanType)){
				// 开启消珠子动画效果
				startAnim(Constant.FLAG_4, Constant.TIMER_4);
				// 播放音效
				if (isSoundPlay) soundPlayer[2].start();
			}else{// 没有珠子可消，就生成三个珠子
				if (scanType == 1){
					// 显示珠子
					displayBeads = gameService4.getDisplayBeads();
					if (displayBeads != null){
						// 开启动画效果依次显示珠子
						startAnim(Constant.FLAG_3, Constant.TIMER_3);
					}else{
						// 游戏结束
						gameOver();
						// 解锁
						lock = true;
					}
					
				}else{
					// 判断珠盘上是否有空珠子
					if (gameService4.getEmptyBeads().size() == 0){
						// 游戏结束
						gameOver();
					}
					// 解锁
					lock = true;
				}
			}
		}
	
		/** 游戏结束 */
		private void gameOver() {
			// 手机振动 (1000毫秒)
			vibrator.vibrate(1000);
			// 获取本次游戏总得分
			int totalScore = gameView4.getBeadBoard().getTotalScore();
			// 获取历史成绩
			int histScore = gameView4.getBeadBoard().getHistScore();
			// 提示本次游戏总得分
			Toast.makeText(this, "本次游戏总得分: " + totalScore, 500).show();
			// 记录最好成绩
			if (totalScore > histScore){
				// 记录下来(写到xml文件中)
				FileUtil.writeScore(this, totalScore);
				// 设置历史分数
				gameView4.getBeadBoard().setHistScore(totalScore);
			}
			// 游戏重新开始
			gameService4.reset();
			gameView4.postInvalidate();
		}

		/** 监听是不是按返回键 */
		@Override
		public void onBackPressed() {
			DialogUtil.createDialog(this, "您确定要退出游戏吗?").show();
			
			
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()){
				case R.id.returnMenu: // 返回菜单
					Intent intent1=new Intent(Level4.this,MainMenu.class);
					startActivity(intent1);
					break;
				case R.id.nextLevel: // 选关
					Intent intent2=new Intent(Level4.this,selectLevel.class);
					startActivity(intent2);
					break;
				case R.id.menu_close: // 游戏退出
					DialogUtil.createDialog(this, "您确定要退出游戏吗?").show();
					break;
			}
			return true;
		}
		/** 当前窗口不处于活跃状态 */
		@Override
		protected void onPause() {
			super.onPause();
		}
		/** 当前窗口重新处于活跃状态 */
		@Override
		protected void onResume() {
			
			super.onResume();
		}
		/** 当前Activity消毁时 */
		@Override
		protected void onDestroy() {
			for (MediaPlayer sound : soundPlayer){
				if (sound.isPlaying()){
					sound.stop();
				}
				sound.release();
			}
			super.onDestroy();
		}

}

