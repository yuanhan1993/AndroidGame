package com.goanimal.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.example.goanimal.R;
import com.goanimal.domain.Bead;
import com.goanimal.domain.BeadBoard;

import com.goanimal.service.GameService2;
import com.goanimal.util.Constant;
import com.goanimal.util.FileUtil;

/**
 * 游戏的主界面
 * @version 1.0
 */
public class GameView2 extends View {
	
	/** 定义珠盘 */
	private BeadBoard beadBoard;
	/** 定义业务层对象 */
	private GameService2 gameService2;
	/** 定义Matrix */
	private Matrix m = new Matrix();
	/** 定义选中的珠子 */
	private Bead selectedBead;
	/** 定义选中的珠子放大或者缩小的标识符 */
	private boolean isFlag = true;
	/** 定义目的地珠子 */
	private Bead targetBead;
	/** 定义一个临时的珠子来缓存选中的珠子 */
	private Bead tempBead;
	/** 定义一个属性缓存上一个点 */
	private Point upPoint;
	
	public GameView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.beadBoard = new BeadBoard(context);
		// 设置历史分数
		this.beadBoard.setHistScore(FileUtil.readScore(context));
		m.setScale(Constant.MATRIX_SCALE, Constant.MATRIX_SCALE);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		// 设置字体大小
		paint.setTextSize(30);
		// 设置颜色
		paint.setColor(Color.BLACK);
		// 计算出左边的位置
		float left = this.getWidth() / 2 - beadBoard.topImage.getWidth() / 2;
		// 绘制最好分数 （左边最中间）
		canvas.drawText(this.getResources().getString(R.string.hist_score), 
				left / 4, 
				beadBoard.topImage.getHeight() / 2
				+ paint.getTextSize() / 2, paint);
		
		// 绘制中间的小珠盘
		canvas.drawBitmap(beadBoard.topImage, left, 0, paint);
		// 绘制下一轮要显示的三个珠子
		List<Bead> lists =  gameService2.getPreparedBeads();
		for (int i = 0; i < lists.size(); i++){
			Bitmap source = lists.get(i).getBitmap();
			source = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
			canvas.drawBitmap(source, left + i * beadBoard.topImage.getWidth() / 3 + 2.0f, 0, paint);
		}
		
		// 绘制当前分数 (右边最中间)
		canvas.drawText(this.getResources().getString(R.string.total_score) + beadBoard.getTotalScore(), 
				left + beadBoard.topImage.getWidth() + left / 4, 
				beadBoard.topImage.getHeight() / 2
				+ paint.getTextSize() / 2, paint);
		
		
		// 绘制珠盘
		canvas.drawBitmap(beadBoard.boardImage, 0, beadBoard.topImage.getHeight(), paint);
		
		// 绘制珠子
		for (int i = 0; i < beadBoard.beads.length; i++){
			for (int j = 0; j < beadBoard.beads.length; j++){
				Bead bead = beadBoard.beads[i][j];
				if (bead.getBitmap() != null){ // 绘制珠子
					// 获取可消珠子连接点
					List<Point> points = gameService2.getLinkPoints();
					
					// 判断是不是用户选中的珠子
					if (bead.equals(selectedBead) || points.contains(new Point(bead.x, bead.y))){
						if (isFlag){ // 正常
							canvas.drawBitmap(bead.getBitmap(), 
									i * beadBoard.gridWidth + Constant.LEFT_RIGHT_SPACE * beadBoard.scale, 
									j * beadBoard.gridHeight + Constant.TOP_BUTTOM_SPACE * beadBoard.scale
									+ beadBoard.topImage.getHeight(), 
									paint);
						}else{ // 缩小
							
							Bitmap source= bead.getBitmap();
							Bitmap temp = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
							canvas.drawBitmap(temp, 
									i * beadBoard.gridWidth + Constant.LEFT_RIGHT_SPACE * beadBoard.scale
									+ (source.getWidth() - temp.getWidth()) / 2, 
									j * beadBoard.gridHeight + Constant.TOP_BUTTOM_SPACE * beadBoard.scale
									+ beadBoard.topImage.getHeight()
									+ (source.getHeight() - temp.getHeight())/ 2, 
									paint);
						}
					}else{ // 不是用户选中的珠子
						canvas.drawBitmap(bead.getBitmap(), 
								i * beadBoard.gridWidth + Constant.LEFT_RIGHT_SPACE * beadBoard.scale, 
								j * beadBoard.gridHeight + Constant.TOP_BUTTOM_SPACE * beadBoard.scale
								+ beadBoard.topImage.getHeight(), 
								paint);
					}
				}
			}
		}
	}
	
	/** 获取珠盘实体方法 */
	public BeadBoard getBeadBoard() {
		return beadBoard;
	}
	public void setGameService(GameService2 gameService2) {
		this.gameService2 = gameService2;
	}
	public void setSelectedBead(Bead selectedBead) {
		this.selectedBead = selectedBead;
	}
	public Bead getSelectedBead(){
		return this.selectedBead;
	}

	public void setIsFlag() {
		this.isFlag = !isFlag;
		// 重新绘制
		this.postInvalidate();
	}
	// 移动珠子
	public void moveBead(Point point) {
		if (upPoint != null){
			beadBoard.beads[upPoint.x][upPoint.y].setBitmap(null);
		}
		if (!point.equals(new Point(targetBead.x, targetBead.y))){
			beadBoard.beads[point.x][point.y].setBitmap(tempBead.getBitmap());
			upPoint = point;
		}else{
			// 目的点
			beadBoard.beads[targetBead.x][targetBead.y].setBitmap(tempBead.getBitmap());
			beadBoard.beads[targetBead.x][targetBead.y].color = tempBead.color;
			upPoint = null;
			targetBead = null;
		}
		this.postInvalidate();
	}
	// 设置目的珠子
	public void setTargetBead(Bead targetBead) {
		this.targetBead = targetBead;
		// 创建tempBead来缓存selectedBead
		tempBead = new Bead();
		tempBead.setBitmap(selectedBead.getBitmap());
		tempBead.color = selectedBead.color;
		// 将选中的珠子设置为空
		selectedBead.setBitmap(null);
		selectedBead = null;
	}
	/** 显示一个珠子 */
	public void displayBead(Bead bead) {
		beadBoard.beads[bead.x][bead.y].setBitmap(bead.getBitmap());
		beadBoard.beads[bead.x][bead.y].color = bead.color;
		this.postInvalidate();
	}
	
}
