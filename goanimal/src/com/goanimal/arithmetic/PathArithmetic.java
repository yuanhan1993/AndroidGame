package com.goanimal.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.goanimal.domain.Bead;

import android.graphics.Point;
import android.util.Log;

/**
 * 路径搜索的扫描算法
 * @version 1.0
 */
public class PathArithmetic {
	
	/** 记录扫描已经经过的点 */
	private List<Point> invalidPoints;
	/** 记录有效路径的点 */
	private List<Point> pathPoints;
	
	// 单例模式
	private static PathArithmetic pathArithmetic = new PathArithmetic();
	private PathArithmetic(){}
	public static PathArithmetic getInstance(){
		return pathArithmetic; 
	}
	
	public List<Point> getPath(Point from, Point to, Bead[][] beads){
		invalidPoints = new ArrayList<Point>();
		pathPoints = new ArrayList<Point>();
		isLink(from, to, beads);
		return pathPoints;
	}
	
	/**
	 * 宽度优先搜索方法
	 * @param from 开始点
	 * @param to 结束点
	 * @param beads 珠子二维数组
	 * @return
	 */
	private boolean isLink(Point from, final Point to, Bead[][] beads) {
		// 第一步：记录走过的点
		invalidPoints.add(from);
		// 第二步：获取上、右、左、下四个点。
		Point[] points = {
			new Point(from.x, from.y - 1), 
			new Point(from.x, from.y + 1),
			new Point(from.x - 1, from.y),
			new Point(from.x + 1, from.y)
		};
		// 第三步：判断四个点是否有效或者是目的点。
		List<Point> temp = new ArrayList<Point>();
		for (Point p : points){
			// 是不是到了目地点
			if (p.equals(to)){
				pathPoints.add(p);
				return true;
			}
			if (isCheck(p,  beads)){
				temp.add(p);
			}
		}
		// 第四步：判断有效点是否全部占完。
		if (temp.isEmpty()) return false;
		
		// 第五步：对有效点进按最短路径排序。
		Collections.sort(temp, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				double r1 = Math.sqrt((p1.x - to.x) * (p1.x - to.x) + (p1.y - to.y) * (p1.y - to.y));
				double r2 = Math.sqrt((p2.x - to.x) * (p2.x - to.x) + (p2.y - to.y) * (p2.y - to.y));
				return r1 < r2 ? -1 : 0;
			}
		});
		// 第六步：递归找出有效点及到搜索到目的点或有效点全部搜索完毕。
		for (Point p : temp){
			boolean flag = isLink(p, to, beads);
			if (flag){
				pathPoints.add(p);
				return true;
			}
		}
		return false;
	}
	/**
	 * 查检点的有效性
	 * @param point
	 * @param beads
	 * @return
	 */
	private boolean isCheck(Point point, Bead[][] beads) {
		if (invalidPoints.contains(point)){
			return false;
		}
		return (point.x >= 0 && point.x < beads.length && point.y >= 0 && point.y < beads.length 
				&& beads[point.x][point.y].getBitmap() == null);
		
	}
}
