/**
 * 
 */
package com.goanimal.util;


/**
 * @author 遥指天下
 *
 */
public class LevelSelect {
private int wlevel=0;
private int point=0;
	public static final LevelSelect[] LEVELS = new LevelSelect[]{
		new LevelSelect(1,100),
		new LevelSelect(2,150),
		new LevelSelect(3,200),
		new LevelSelect(4,200),
		new LevelSelect(5,500),
	};

	public static final int MAX_LEVEL=6;
	public static final int MAX_POINT=10;

	public LevelSelect(int wlevel,int point) {
		this.wlevel=wlevel;
		this.point=point;
	}

	/**
	 * 关卡选择
	 * @return
	 */
	public int getWlevel() {
		return wlevel;
	}

	/**
	 * 分数过关要求
	 * @return
	 */
	public int getPoint() {
		return point;
	}
}
