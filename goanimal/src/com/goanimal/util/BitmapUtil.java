package com.goanimal.util;

import java.util.Random;

import com.goanimal.domain.Bead;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;


/**
 * 操作位图的工具类
 *
 * @version 1.0
 */
public class BitmapUtil {
	// 定义随机对象
	private static Random random = new Random();
	// 定义Matrix矩阵类
	private static Matrix m = new Matrix();
	/**
	 * 根据资源文件获取位图
	 * @param context 当前的Activity (上下文)
	 * @param resId 资源文件的id
	 * @return 位图对象
	 */
	public static Bitmap getBitmap(Context context, int resId){
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}
	/**
	 * 随机生成一个珠子
	 * @param context 当前的Activity (上下文)
	 * @param scale 缩放比率
	 * @return 珠子
	 */
	public static Bead randomBead(Context context, float scale) {
		// 随机获取一个珠子图片资源id (0-1)
		int cursor;
	
		//第一关资源
		
			cursor=random.nextInt(Constant.BEAD_ICONS1.length);
			// 得到位图
			Bitmap source = getBitmap(context, Constant.BEAD_ICONS1[cursor]);
			// 对珠子按比例缩放
			m.setScale(scale, scale);
			// 按比率缩放过后的位图
			source = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
			Bead bead = new Bead();
			bead.setBitmap(source);
			bead.color = Constant.BEAD_COLORS[cursor];
			return bead;
		
	}
}