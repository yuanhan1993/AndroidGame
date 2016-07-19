package com.goanimal.domain;

import android.graphics.Bitmap;

/**
 * 珠子实体
 * @version 1.0
 */
public class Bead {
	
	// 在珠盘二维数组中的x坐标
	public int x;
	// 在珠盘二维数组中的y坐标
	public int y;
	// 珠子的颜色 (默认的颜色)
	public String color = "Z";
	// 珠子的位图
	private Bitmap bitmap;
	
	@Override
	public boolean equals(Object o) {
		// 判断两个珠子相等(只要两个珠子的x、y都相等就代表是同一个珠子)
		if (o == null || !(o instanceof Bead)){
			return false;
		}
		Bead bead = (Bead)o;
		return bead.x == this.x && bead.y == this.y;
	}

	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		if (bitmap == null){
			this.color = "Z";
		}
		this.bitmap = bitmap;
	}
	
	
	
}
