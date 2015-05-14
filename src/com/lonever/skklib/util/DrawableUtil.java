package com.lonever.skklib.util;

import java.io.IOException;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

public class DrawableUtil {

	
	public static Drawable getDrawableFromAssets(Context context,String filePath){
		try { 
			return new BitmapDrawable(context.getResources(),BitmapFactory.decodeStream(context.getAssets().open(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setDrawableSelector(View view,Drawable normal,Drawable pressed,Drawable focused){
		 StateListDrawable bg = new StateListDrawable(); 
		    int[][] states = new int[6][];  
		    states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };   
		           states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };   
		           states[2] = new int[] { android.R.attr.state_enabled };   
		           states[3] = new int[] { android.R.attr.state_focused ,android.R.attr.state_window_focused};   
		           states[4] = new int[] { android.R.attr.state_window_focused };  
		           states[5] = new int[] { };  
		    
		    bg.addState(states[0], pressed); 
		    bg.addState(states[1], focused); 
		    bg.addState(states[2], normal);
		    bg.addState(states[3], focused); 
		    bg.addState(states[4], focused); 
		    bg.addState(states[5], normal); 
		   view.setBackgroundDrawable(bg); 
	}
}
