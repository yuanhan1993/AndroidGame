package com.goanimal.domain;


import com.example.goanimal.R;
import com.goanimal.util.BitmapUtil;
import com.goanimal.util.Constant;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.WindowManager;

/**
 * 珠盘实体
 * @version 1.0
 */
public class BeadBoard {
	// 网格宽度
	public float gridWidth;
	// 网格的高度
	public float gridHeight;
	// 珠盘的背景位图
	public Bitmap boardImage;
	// 珠盘图片与手机之间的缩放率
    public float scale;
    // 珠子二维数组（珠盘上所有珠子)
    public Bead[][] beads = new Bead[Constant.BEAD_SIZE][Constant.BEAD_SIZE];
    // 小珠盘(放下一轮要显示的三个珠子)
    public Bitmap topImage;
	// 历史分数
    private int histScore;
    // 本次游戏的总分数
    private int totalScore;
    public BeadBoard(Context context){
    	// 通过Context获取窗口管理器
    	WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    	// 获取手机的屏幕的宽度
    	@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();
    	// 把珠盘图片转化成位图
    	
    	Bitmap source =BitmapUtil.getBitmap(context, R.drawable.board3);
    	
    	// 计算出珠盘图片与手机之间的缩放比率
    	this.scale = Float.valueOf(width) / Float.valueOf(source.getWidth());
    	
    	// 对珠盘图片进行缩放(按比率)
    	Matrix m = new Matrix();
    	// 设置缩放比率
    	m.setScale(this.scale, this.scale);
    	// 得到缩放过后的珠盘的位图
    	this.boardImage = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
    	
    	// 计算网格的宽度
    	this.gridWidth = (Float.valueOf(source.getWidth() - Constant.LEFT_RIGHT_SPACE * 2)  / Constant.BEAD_SIZE) * this.scale;
    	// 计算网格的高度
    	this.gridHeight = (Float.valueOf(source.getHeight() - Constant.TOP_BUTTOM_SPACE * 2) / Constant.BEAD_SIZE) * this.scale;
    	
    	// 获取小珠盘对应的位图
    	source = BitmapUtil.getBitmap(context, R.drawable.titile2);
    	// 得到缩放过后的小珠盘的位图
    	m.setScale(this.scale * Constant.MATRIX_SCALE , this.scale * Constant.MATRIX_SCALE);
    	this.topImage = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
    	// 调用初始方法(初始化珠盘上所有珠子)
    	
    	this.init();
    }
    
    /** 初始化珠盘上的所有的珠子 */
    private void init(){
    	for (int i = 0; i < beads.length; i++){
    		for (int j = 0; j < beads.length; j++){
    			
    			Bead bead = new Bead();
    			bead.x = i;
    			bead.y = j;
    			beads[i][j] = bead;
    		}
    	}
    }

    
	public int getHistScore() {
		return histScore;
	}

	public void setHistScore(int histScore) {
		this.histScore = histScore;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int perScore) {
		if (perScore == 0){
			this.totalScore = 0;
		}
		this.totalScore += perScore;
	}
}
