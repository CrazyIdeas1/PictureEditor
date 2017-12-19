package com.example.crazyideas.pictureeditor;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.crazyideas.custom.MyCanvasView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FrameLayout frameLayout;

    private Button savePicture;
    private Button RevokePath;

    //设置画笔大小
    private Button paintSizeSmall;
    private Button paintSizeInside;
    private Button paintSizeBig;

    //设置画笔颜色
    private Button paintColorBreak;
    private Button paintColorRed;
    private Button paintColorOrange;
    private Button paintColorYellow;
    private Button paintColorGreen;
    private Button paintColorBlue;
    private Button paintColorPurPle;

    private MyCanvasView canvasView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initView(){
        frameLayout = findViewById(R.id.showCanvas);
        savePicture = findViewById(R.id.SavePicture);
        RevokePath = findViewById(R.id.RevokePath);
        paintSizeSmall = findViewById(R.id.paintSizeSmall);
        paintSizeBig = findViewById(R.id.paintSizeBig);
        paintSizeInside = findViewById(R.id.paintSizeInside);
        paintColorBreak = findViewById(R.id.paintColorBlack);
        paintColorRed = findViewById(R.id.paintColorRed);
        paintColorOrange = findViewById(R.id.paintColorOrange);
        paintColorYellow = findViewById(R.id.paintColorYellow);
        paintColorGreen = findViewById(R.id.paintColorGreen);
        paintColorBlue = findViewById(R.id.paintColorBlue);
        paintColorPurPle = findViewById(R.id.paintColorPurple);
    }

    private void initListener(){
        savePicture.setOnClickListener(this);
        RevokePath.setOnClickListener(this);
        paintSizeSmall.setOnClickListener(this);
        paintSizeBig.setOnClickListener(this);
        paintSizeInside.setOnClickListener(this);
        paintColorBreak.setOnClickListener(this);
        paintColorRed.setOnClickListener(this);
        paintColorOrange.setOnClickListener(this);
        paintColorYellow.setOnClickListener(this);
        paintColorGreen.setOnClickListener(this);
        paintColorBlue.setOnClickListener(this);
        paintColorPurPle.setOnClickListener(this);
    }

    private void initData(){
        /*
        //获取屏幕的宽度 和 高度
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();//此方法已经被废除
        int screenHeight = display.getHeight();//此方法已经被废除
        */
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        canvasView = new MyCanvasView(this,screenWidth,screenHeight);
        frameLayout.addView(canvasView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SavePicture:
                canvasView.savePictrue();
                break;
            case R.id.RevokePath:
                canvasView.revoke();
                break;
            case R.id.paintSizeSmall:
                canvasView.selectPaintSize(10);
                break;
            case R.id.paintSizeInside:
                canvasView.selectPaintSize(20);
                break;
            case R.id.paintSizeBig:
                canvasView.selectPaintSize(50);
                break;
            case R.id.paintColorBlack:
                canvasView.selectPaintColor(0);
                break;
            case R.id.paintColorRed:
                canvasView.selectPaintColor(1);
                break;
            case R.id.paintColorOrange:
                canvasView.selectPaintColor(2);
                break;
            case R.id.paintColorYellow:
                canvasView.selectPaintColor(3);
                break;
            case R.id.paintColorGreen:
                canvasView.selectPaintColor(4);
                break;
            case R.id.paintColorBlue:
                canvasView.selectPaintColor(5);
                break;
            case R.id.paintColorPurple:
                canvasView.selectPaintColor(6);
                break;
        }
    }
}
