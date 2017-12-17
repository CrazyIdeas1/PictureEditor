package com.example.crazyideas.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private DrawPath dp;
    private List<DrawPath> savePath; //保存路径
    private List<DrawPath> deletePath; //保存删除的路径

    //private boolean isMove = false;     //判断手指是否移动
    private static final float MIN_MOVE_DISTANCE = 2;
    private float mX,mY;//临时坐标 记录作用

    //存储以前的路径
    class DrawPath{
        public Path path;
        public Paint paint;
    }
    public MyCanvasView(Context context,int width, int height) {
        super(context);
        this.context = context;
        screenWidth = width;
        screenHeight = height;
        savePath = new ArrayList<>();
        deletePath = new ArrayList<>();
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

    private static final String TAG = "isMove";
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Log.d(TAG, "onTouchEvent:按下 "+ isMove);
                mPath = new Path();
                dp = new DrawPath();
                dp.path = mPath;
                dp.paint = mPaint;
                mX = x;
                mY = y;
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d(TAG, "onTouchEvent:移动1 "+ isMove);
                mPath.lineTo(x,y);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                //Log.d(TAG, "onTouchEvent: 抬起"+ isMove);
                //isMove = false;
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx > MIN_MOVE_DISTANCE || dy > MIN_MOVE_DISTANCE){
                    Log.d(TAG, "onTouchEvent: 抬起进入");
                    mCanvas.drawPath(mPath,mPaint);
                    savePath.add(dp);  //存储路径
                    mPath = null;   //需要设置为空如果不设置为空  最后一个路径 撤回不了，为什么呢？
                    // 因为不设置为 NULL 下面的postInvalidate（）会把路径再画一遍 而它没有保存在 savePath里面 所以删除不了
                    postInvalidate();
                }
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
    //实现撤销功能
    public void revoke(){
        if (savePath != null && savePath.size() > 0){
            DrawPath drawPath = savePath.get(savePath.size()-1);
            deletePath.add(drawPath);
            savePath.remove(savePath.size()-1);
            againOnDrawBitmap();
        }
    }
    //重画把保存的路径画到画布上
    private void againOnDrawBitmap(){
        initCanvas();//清空画布
        Iterator<DrawPath> iterator = savePath.iterator();
        while(iterator.hasNext()){
            DrawPath drawPath = iterator.next();
            mCanvas.drawPath(drawPath.path,drawPath.paint);
        }
        postInvalidate();
    }
}
