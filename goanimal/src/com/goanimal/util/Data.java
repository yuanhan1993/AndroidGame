/**
 * 
 */
package com.goanimal.util;

import android.app.Application;

/**
 * @author 遥指天下
 *
 */
public class Data extends Application{
	private Boolean isSound=false;//判断音效
	public Boolean get_isSound(){
		return isSound;
	}
	public void set_isSound(Boolean isSound){
		this.isSound=isSound;
	}

}
