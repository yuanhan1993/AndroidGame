/**
 * 
 */
package com.goanimal.activity;

import com.example.goanimal.R;
import com.goanimal.util.AppManager;
import com.goanimal.util.DialogUtil;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author 遥指天下
 *
 */
public class selectLevel extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.level_select);
		Button button1=(Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Intent play1=new Intent(selectLevel.this,Level1.class);
				startActivity(play1);
				
			}
		});
		Button button2=(Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(selectLevel.this, "恭喜达到本关的要求", Toast.LENGTH_SHORT).show();
				finish();
				Intent play2=new Intent(selectLevel.this,Level2.class);
				startActivity(play2);
					
				
			}
		});
		Button button3=(Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				Intent play3=new Intent(selectLevel.this,Level3.class);
				startActivity(play3);
					Toast.makeText(selectLevel.this, "恭喜达到本关的要求", Toast.LENGTH_SHORT).show();
				
			}
		});
		Button button4=(Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				finish();
				Intent play4=new Intent(selectLevel.this,Level4.class);
				startActivity(play4);
				
				
					
					Toast.makeText(selectLevel.this, "恭喜达到本关的要求", Toast.LENGTH_SHORT).show();
				
				
			}
		});
		Button button5=(Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				Intent play5=new Intent(selectLevel.this,Level5.class);
				startActivity(play5);
				
					
					Toast.makeText(selectLevel.this, "恭喜达到本关的要求", Toast.LENGTH_SHORT).show();
				
				
			}
		});
		Button button6=(Button) findViewById(R.id.button6);
		button6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(selectLevel.this, "呵呵，欢迎您提供以下关卡的新创意！", Toast.LENGTH_SHORT).show();
				
			}
		});
		Button button7=(Button) findViewById(R.id.button7);
		button7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(selectLevel.this, "呵呵，欢迎您提供以下关卡的新创意！", Toast.LENGTH_SHORT).show();
				
			}
		});
		Button button8=(Button) findViewById(R.id.button8);
		button8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(selectLevel.this, "呵呵，欢迎您提供以下关卡的新创意！", Toast.LENGTH_SHORT).show();
				
			}
		});
		Button button9=(Button) findViewById(R.id.button9);
		button9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(selectLevel.this, "呵呵，欢迎您提供以下关卡的新创意！", Toast.LENGTH_SHORT).show();
				
			}
		});
		Button button_gomenu=(Button) findViewById(R.id.button_gomenu);
		button_gomenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Intent play1=new Intent(selectLevel.this,MainMenu.class);
				startActivity(play1);
				
			}
		});
		
	}
	
	/** 监听是不是按返回键 */
	@Override
	public void onBackPressed() {
		DialogUtil.createDialog(this, "您确定要退出游戏吗?").show();
	}


}
