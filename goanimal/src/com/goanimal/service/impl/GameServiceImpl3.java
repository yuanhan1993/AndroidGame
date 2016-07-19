/**
 * 
 */
package com.goanimal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Point;

import com.goanimal.arithmetic.PathArithmetic;
import com.goanimal.arithmetic.ScanArithmetic;
import com.goanimal.domain.Bead;
import com.goanimal.domain.BeadBoard;
import com.goanimal.service.GameService3;
import com.goanimal.util.BitmapUtil3;
import com.goanimal.util.Constant;

/**
 * @author 遥指天下
 *
 */
public class GameServiceImpl3 implements GameService3{
	/** Activity上下文 */
	private Context context;
	/** 珠盘实体 */
	private BeadBoard beadBoard;
	/** 定义随机对象 */
	private Random random = new Random();
	/** 定义下一轮要显示的三个珠子 */
	private List<Bead> preparedBeads = new ArrayList<Bead>();
	/** 定义缓存分数的属性 */
	private int perScore = 0;
	/** 定义可消珠子的点的集合 */
	private List<Point> linkPoints = new ArrayList<Point>();
	
	public GameServiceImpl3(Context context, BeadBoard beadBoard) {
		this.context = context;
		this.beadBoard = beadBoard;
		// 调用初始化的方法,初始化珠盘上的五个珠子
		this.init();
	}
	
	
	/** 获取下一轮要显示的三个珠子 */
	public List<Bead> getPreparedBeads(){
		return preparedBeads;
	}
	/** 生成下一轮要显示的三个珠子 */
	private void setPreparedBeads(){
		preparedBeads.clear(); // 清空
		for (int i = 0; i < Constant.PER_NUM; i++){
			Bead bead = BitmapUtil3.randomBead(context, beadBoard.scale);
			preparedBeads.add(bead);
		}
	}
	/**
	 * 根据用户点击的坐标获取相对应的珠子
	 * @param x 坐标
	 * @param y 坐标
	 * @return Bead
	 */
	public Bead getSelectedBead(float x, float y){
		// 对y坐标进行有效性的判断
		if (y < beadBoard.topImage.getHeight() + Constant.TOP_BUTTOM_SPACE * beadBoard.scale){
			return null;
		}
		if (y > (beadBoard.topImage.getHeight() + beadBoard.boardImage.getHeight() 
				- Constant.TOP_BUTTOM_SPACE * beadBoard.scale)){
			return null;
		}
		// 把x、y坐标转化成二维数组中的i与j
		int i = Float.valueOf((x - Constant.LEFT_RIGHT_SPACE * beadBoard.scale) / beadBoard.gridWidth).intValue();
		int j = Float.valueOf((y - beadBoard.topImage.getHeight() 
				- Constant.TOP_BUTTOM_SPACE * beadBoard.scale) / beadBoard.gridHeight).intValue();
		
		// 对i的有效性放松
		if (i >= 9) i = Constant.BEAD_SIZE - 1;
		
		if (i >= 0 && i < Constant.BEAD_SIZE && j >= 0 && j < Constant.BEAD_SIZE){
			return beadBoard.beads[i][j];
		}
		return null;
	}
	
	/**
	 * 获取两个珠子可走的线路
	 * @param selectedBead 选中的珠子
	 * @param targetBead 目标的珠子
	 * @return 可走线路点的集合
	 */
	public List<Point> getPath(Bead selectedBead, Bead targetBead){
		if (selectedBead == null || targetBead == null){
			return null;
		}
		Point from = new Point(selectedBead.x, selectedBead.y);
		Point to = new Point(targetBead.x, targetBead.y);
		// 从起点到终点
		List<Point> points = PathArithmetic.getInstance().getPath(from, to, beadBoard.beads);
		if (points.size() <= 5){
			// 反转集合中的元素
			Collections.reverse(points);
			return points;
		}else{
			// 从终点到起点
			List<Point> tempPoints = PathArithmetic.getInstance().getPath(to, from, beadBoard.beads);
			if (points.size() < tempPoints.size()){
				// 反转集合中的元素
				Collections.reverse(points);
				return points;
			}else{
				tempPoints.remove(from);
				tempPoints.add(to);
				return tempPoints;
			}
		}
	}
	/**
	 * 获取要显示的三个珠子
	 * @return 珠子集合
	 */
	public List<Bead> getDisplayBeads(){
		// 预加载的三个珠子 (小珠盘上的三个珠子)
		List<Bead> preparedBeads = this.getPreparedBeads();
		// 获取珠盘上所有的空珠子
		List<Bead> emptyBeads = this.getEmptyBeads();
		// 判断珠盘上空珠子的数量 (不能小于3)
		if (emptyBeads.size() < 3){
			return null;
		}
		List<Bead> lists = new ArrayList<Bead>();
		for (Bead preBead : preparedBeads){
			int cursor = random.nextInt(emptyBeads.size());
			Bead bead = emptyBeads.remove(cursor);
			// 组合两个珠子
			preBead.x = bead.x;
			preBead.y = bead.y;
			lists.add(preBead);
		}
		// 重新加载三个珠子(下一轮)
		this.setPreparedBeads();
		return lists;
	}
	/**
	 * 扫描珠子的方法
	 * @return
	 */
	public boolean scanBead(int scanType){
		linkPoints.clear(); // 清空集合
		perScore = 0; // 清空分数
		// 四个方位的扫描
		List<List<Point>> lists = ScanArithmetic.scan(beadBoard.beads);
		// 判断是否有珠子可消
		if (lists.isEmpty()){
			return false;
		}
		// 统计一下一共有多少个珠子可以消
		int count = 0;
		// 记录可消珠子的点
		for (List<Point> points : lists){
			for (Point point : points){
				if (!linkPoints.contains(point)){
					linkPoints.add(point);
				}
			}
			count += points.size();
		}
		
		// 计算出分数 (一个珠子2分) 消的分数 * 方位
		if (scanType == Constant.SCAN_TYPE_1){
			// 用户走珠子后消得就算分数
			perScore = count * 2 * lists.size();
		}
		return true;
	}
	
	/** 获取分数(每次的得分) */
	public int getPerScore(){
		return this.perScore;
	}
	/** 获取可消珠子的连接点 */
	public List<Point> getLinkPoints(){
		return this.linkPoints;
	}
	/** 设置游戏累计分数 */
	public void setTotalScore(){
		beadBoard.setTotalScore(this.perScore);
	}
	/**
	 * 清除可消珠子
	 */
	public void clearBead(){
		for (Point p : linkPoints){
			beadBoard.beads[p.x][p.y].setBitmap(null);
		}
		linkPoints.clear();
	}
	/** 获取珠盘上所有的空珠子 */
	public List<Bead> getEmptyBeads(){
		List<Bead> emptyBeads = new ArrayList<Bead>();
		for (int i = 0; i < beadBoard.beads.length; i++){
			for (int j = 0; j < beadBoard.beads.length; j++){
				Bead bead = beadBoard.beads[i][j];
				// 只要珠子实体没有位图,那就是一个空珠
				if (bead.getBitmap() == null){
					emptyBeads.add(bead);
				}
			}
		}
		return emptyBeads;
	}
	/**
	 * 游戏重新开始的方法
	 */
	public void reset(){
		for (int i = 0; i < beadBoard.beads.length; i++){
			for (int j = 0; j < beadBoard.beads.length; j++){
				beadBoard.beads[i][j].setBitmap(null);
			}
		}
		this.perScore = 0;
		this.setTotalScore();
		this.init();
	}
	
	


	/** 初始化的方法 */
	private void init(){
		// 获取所有的空珠子
		List<Bead> emptyBeads = this.getEmptyBeads();
		// 初始化五个珠子
		for (int i = 0; i < Constant.INIT_NUM; i++){
			Bead temp = BitmapUtil3.randomBead(context, beadBoard.scale);
			// 在珠盘上所有空珠子的地方随机获取一个珠子
			int cursor = random.nextInt(emptyBeads.size());
			Bead bead = emptyBeads.remove(cursor);
			bead.setBitmap(temp.getBitmap());
			bead.color = temp.color;
		}
		// 生成下一轮要显示的三个珠子
		this.setPreparedBeads();
	}

}
