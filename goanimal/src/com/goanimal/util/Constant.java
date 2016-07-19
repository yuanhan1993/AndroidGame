package com.goanimal.util;

import com.example.goanimal.R;





/**
 * 常量类
 * @version 1.0
 */
public final class Constant {
	/** 定义珠子二维数组的大小 */
	public static final int BEAD_SIZE = 9;
	/** 定义网格左边、右边的间隙 (一边) */
	public static final float LEFT_RIGHT_SPACE = 6.5f;
	/** 定义网格上边、下边的间隙 (一边) */
	public static final float TOP_BUTTOM_SPACE = 5.5f;
	/** 定义初始化珠子的数量 */
	public static final int INIT_NUM = 3;
	
	
	/** 定义六种动物珠子的资源文件的id */
	public static final int[] BEAD_ICONS = {
		R.drawable.bang_1,
		R.drawable.bang_2,
		R.drawable.bang_3,
		R.drawable.bang_4,
		R.drawable.bang_5,
		R.drawable.bang_6
	};
	/**定义第一关动物珠子的资源文件id */
	public static final int[] BEAD_ICONS1 = {
		R.drawable.bang_1,
		R.drawable.bang_2,
	};
	/**定义第二关动物珠子的资源文件id */
	public static final int[] BEAD_ICONS2 = {
		R.drawable.bang_1,
		R.drawable.bang_2,
		R.drawable.bang_3,
		
	};
	/**定义第三关动物珠子的资源文件id */
	public static final int[] BEAD_ICONS3 = {
		R.drawable.bang_1,
		R.drawable.bang_2,
		R.drawable.bang_4,
		R.drawable.bang_3,
	};
	/**定义第四关动物珠子的资源文件id */
	public static final int[] BEAD_ICONS4 = {
		R.drawable.bang_1,
		R.drawable.bang_2,
		R.drawable.bang_3,
		R.drawable.bang_4,
		R.drawable.bang_5,
		
	};
	
	
	/** 定义六种珠子的颜色 */
	public static final String[] BEAD_COLORS = {"A","B","C","D","E","F"};
	/** 定义六种珠子的可消颜色 */
	public static final String[] FINAL_COLORS = {"AAAAA", "BBBBB", "CCCCC", "DDDDD", "EEEEE", "FFFFF"};
	/** 定义每次生成珠子的数量 */
	public static final int PER_NUM = 3;
	
	/** 定义缩放比率 */
	public static final float MATRIX_SCALE = 0.8f;
	
	
	/** 定义珠子跳动的标识符与时长 */
	public static final long TIMER_1 = 200;
	public static final int FLAG_1 = 0x110;
	
	/** 定义走珠子的标识符与时长 */
	public static final long TIMER_2 = 100;
	public static final int FLAG_2 = 0x111;
	
	/** 定义显示珠子的标识符与时长 */
	public static final long TIMER_3 = 100;
	public static final int FLAG_3 = 0x112;
	
	/** 定义消珠子的标识符与时长 */
	public static final long TIMER_4 = 300;
	public static final int FLAG_4 = 0x113;
	
	/** 定义用户走完珠子要消珠子的标识符 */
	public static final int SCAN_TYPE_1 = 1;
	/** 定义系统生成了三个珠子要消珠子的标识符 */
	public static final int SCAN_TYPE_2 = 2;
	
	/** 定义音效的资源数组 */
	public static final int[] SOUNDS = {R.raw.selected, R.raw.error, R.raw.clear};
	/**定义音乐的资源数组 */
	public static final int[] MUSIC={R.raw.piano_for_elise,R.raw.piano_box,R.raw.piano_dream_wedding,R.raw.piano_kiss_the_rain,R.raw.piano_spring,R.raw.piano_summer};
	/** 定义开场动画音效的资源数组 */
	public static final int[] WSOUNDS = {R.raw.logo_music, R.raw.welcome};
}
