package com.example.crazyideas.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CrazyIdeas on 2017/12/16.
 *
 */

public class MyCanvasView extends View {


    private Context context;
    private int screenWidth;
    private int screenHeight;
    private Bitmap mBitmap;
    private Canvas mCanvas;//记录以前的绘画  画布
    private Paint mPaint;  //
    private Paint mPaintBitmap;  //画布的画笔
    private Path mPath;
    private int [] paintColorSet = {
            Color.argb(255,0,0,0),//黑
            Color.argb(255,255,0,0),//红
            Color.argb(255,255,165,0),//橙
            Color.argb(255,255,255,0),//黄
            Color.argb(255,0,128,0),//绿
            Color.argb(255,0,0,255),//蓝
            Color.argb(255,128,0,128),//紫
    };
    private int paintSize = 10;  //设置画笔大小
    private int paintColor = 0;  //设置画笔颜色

    public MyCanvasView(Context context,int width, int height) {
        super(context);
        this.context = context;
        screenWidth = width;
        screenHeight = height;

        initCanvas();
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void initCanvas(){
        initPaint();
        mPaintBitmap = new Paint();//
        mBitmap = Bitmap.createBitmap(screenWidth,screenHeight, Bitmap.Config.ARGB_8888);
        ///mBitmap.eraseColor(Color.argb(0,0,0,0));
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintSize);
        mPaint.setColor(paintColorSet[paintColor]);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,0,0,mPaintBitmap);
        if (mPath != null){
            canvas.drawPath(mPath,mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x,y);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath,mPaint);
                postInvalidate();
                break;
        }
        return true;
    }

    //选择画笔大小
    public void selectPaintSize(int opt){
        paintSize = opt;
        initPaint();
    }
    //选择画笔颜色
    public void selectPaintColor(int opt){
        paintColor = opt;
        initPaint();
    }
}
