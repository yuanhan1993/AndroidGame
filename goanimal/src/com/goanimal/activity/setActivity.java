package com.goanimal.activity;

import com.example.goanimal.R;
import com.goanimal.service.MusicService;
import com.goanimal.util.AppManager;
import com.goanimal.util.Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class setActivity extends Activity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	// 设置的界面
	super.onCreate(savedInstanceState);
	AppManager.getAppManager().addActivity(this);
	setContentView(R.layout.dialog);
    RadioGroup musicSel=(RadioGroup)findViewById(R.id.radioGroup1);
	musicSel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			RadioButton r1=(RadioButton)findViewById(checkedId);
			r1.getText();
			if(r1.getText().equals("是")){
		     Intent intent=new Intent(setActivity.this,MusicService.class);
			startService(intent);
			
		}
			else{
			     Intent intent=new Intent(setActivity.this,MusicService.class);
				stopService(intent);
				
			}
			
		}
		
		});
    RadioGroup soundSel=(RadioGroup) findViewById(R.id.radioGroup2);
	
	soundSel.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			RadioButton r2=(RadioButton)findViewById(checkedId);
			Data isSoundPlay=(Data) getApplicationContext();
			
			
		    if(r2.getText().equals("是")){
		    isSoundPlay.set_isSound(true);
		    System.out.print(isSoundPlay.get_isSound());
		}
		    else {isSoundPlay.set_isSound(false);
		    System.out.print(isSoundPlay.get_isSound());
		    }
		}
	});
	
	Button buttonTrue=(Button) findViewById(R.id.button_true);
	buttonTrue.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			setResult(0x717,getIntent());
			finish();
			
		
			
		}
	});
}
 
}
