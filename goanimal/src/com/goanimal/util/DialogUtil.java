package com.goanimal.util;

import com.example.goanimal.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 弹出窗体的工具类
 * @version 1.0
 */
public class DialogUtil {
	
	/**
	 * 创建弹出窗体的方法
	 * @param context
	 * @param message
	 * @return
	 */
	public static Dialog createDialog(final Context context, String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage(message);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//((Activity)context).finish();
				AppManager.getAppManager().AppExit(context);
			}
		});
		builder.setNegativeButton("取消", null);
		return builder.create();
	}
}
