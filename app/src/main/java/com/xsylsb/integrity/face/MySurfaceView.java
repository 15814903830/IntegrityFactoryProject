package com.xsylsb.integrity.face;

import java.io.IOException;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 圆形SurfaceView
 * 这个SurfaceView 使用时 必须设置其background，可以设置全透明背景
 * @author jingyouliu
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private Paint paint;
	private Camera camera;
	private int height; // 圆的半径
	
	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MySurfaceView(Context context) {
		super(context);
		initView();
	}

	
	private void initView() {
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);


		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		height=widthSize;

		Log.e("onMeasure", "draw: widthMeasureSpec = " +widthSize + "  heightMeasureSpec = " + heightSize);

		setMeasuredDimension(widthSize, heightSize);



	}

	@Override
	public void draw(Canvas canvas) {
		Log.e("onDraw", "draw: test");
		Path path = new Path();
		path.addCircle(height / 2, height / 2, height / 2, Path.Direction.CCW);
		if(Build.VERSION.SDK_INT >= 26){
			canvas.clipPath(path);
		}else {
			canvas.clipPath(path, Region.Op.REPLACE);
		}
		super.draw(canvas);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("onDraw", "onDraw");
		super.onDraw(canvas);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e("onDraw", "surfaceCreated");
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.e("onDraw", "surfaceChanged");
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("onDraw", "surfaceDestroyed");
		if (camera != null) {
			camera.stopPreview();
			camera.release();			
		}
	}



}
