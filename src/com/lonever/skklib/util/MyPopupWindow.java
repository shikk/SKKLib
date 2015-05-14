package com.lonever.skklib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MyPopupWindow extends PopupWindow 
		 {

	private static Activity context;
 	private boolean initOrNot;
	static int notifId;
	private int viewResId;
	private int viewRootLayoutId;
	public static MyPopupWindow instance = null;

	public MyPopupWindow(Activity context,int viewResId,int viewRootLayoutId) {
		this(context, true);
		this.viewResId = viewResId;
		this.viewRootLayoutId = viewRootLayoutId;
	}
	public MyPopupWindow(Activity context1,boolean initOrNot) {
		super(context1);
		context = context1;
		instance = this;
		this.initOrNot = initOrNot;
		if (initOrNot) {
			init();
		}
	}

	public void init() {
		contentView = (RelativeLayout) LayoutInflater.from(
				context).inflate(viewResId, null);
		Resources res = context.getResources();
		// 设置PopupWindow显示和隐藏时的动画
		setAnimationStyle(android.R.style.Animation_Dialog);
		width = res.getDisplayMetrics().widthPixels;
		height = res.getDisplayMetrics().heightPixels;
		density = res.getDisplayMetrics().density;
		setWidth(width-300);
		setHeight(height-300);
		
//		RelativeLayout.LayoutParams p = null;
//		if (width>height) {
//			p = new RelativeLayout.LayoutParams((int) (width*0.6), (int) (height*0.7));
//		}else{
//			p = new RelativeLayout.LayoutParams((int) (width*0.8), (int) (height*0.7));
//		}
//		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		LinearLayout windowLayout = (LinearLayout) contentView.findViewById(viewRootLayoutId);
//		windowLayout.setLayoutParams(p);
		windowLayout.setFocusableInTouchMode(true);
		windowLayout.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				System.out.println("xonKeyxxx  code:"+keyCode +"event:"+event);
				return true;
			}
		});
		
		
		
		// 设置PopupWindow的内容view
		setContentView(contentView);
		// 设置PopupWindow外部区域是否可触摸
		setFocusable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(null);
//		setTouchInterceptor(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				/**** 如果点击了popupwindow的外部，popupwindow也会消失 ****/
//				System.out.println("xxx action:");
////				context.dispatchTouchEvent(event);
//				return false;
//			}
//
//		});
		
	}


	private float density;
	private int width;
	private int height;
	private boolean isShown;
	private WindowManager mWindowManager;
	private RelativeLayout contentView;
	/**
     * 显示弹出框在屏幕最上方
     *
     * @param context
     * @param view
     */
    public void showPopupWindow() {
        if (isShown) {
            return;
        }

        isShown = true;

        // 获取应用的Context
        Context mContext = context.getApplicationContext();
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // 设置flag

        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题

        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;

        params.gravity = Gravity.CENTER;

        mWindowManager.addView(contentView, params);


    }

    /**
     * 隐藏弹出框
     */
    public void hidePopupWindow() {
        if (isShown && null != contentView) {
            mWindowManager.removeView(contentView);
            isShown = false;
        }

    }
	public static MyPopupWindow getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

}
