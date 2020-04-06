package com.coolweather.coolweather.util;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.coolweather.coolweather.MyApplication;
import com.coolweather.coolweather.R;

import java.lang.reflect.Type;

/**
 * 自定义Textview
 */

public class TextView extends View {

    private String mText;
    private int mTextSize = 15;//默认传入的是15sp
    private int mTextColor = Color.BLACK;
    private Paint mPaint;

    //在new 的时候调用
    public TextView(Context context) {
        this(context,null);
    }
    //在layout中使用
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    //在layout中使用，但是有style
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        mText = typedArray.getString(R.styleable.TextView_text);
        mTextColor = typedArray.getColor(R.styleable.TextView_textColor,mTextColor);

        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_textSize,mTextSize/*15sp*/);

        typedArray.recycle();
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    /**
     * 将sp转为dp
     * dip 与 dp
     * pixel，即像素，1px代表屏幕上的一个物理的像素点。
     * 而dp也叫dip，是device independent pixels。设备不依赖像素的一个单位。在不同的像素密度的设备上会自动适配，比如:
     * 在320x480分辨率，像素密度为160,1dp=1px
     * 在480x800分辨率，像素密度为240,1dp=1.5px
     *
     * sp：
     * 使用sp作为字体大小单位,会随着系统的字体大小改变，而dp作为单位则不会。所以建议在字体大小的数值要使用sp作为单位
     *
     * @param mSp 传入的Sp
     * @return
     */
    private int  Sp2Dp(int mSp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,mSp,getResources().getDisplayMetrics());
    }


    /**
     * 自定义view 计算宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高的mode，在xml中有时候会宽高会使用不同的方式表示，有的时候是wrap_content/match_parent 或者sp dp等
        //mode和value总共组成32位，高两位是mode，低30位为value
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取宽高的value
        //1.给定具体的值 sp dp，不需要进行处理 直接使用
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //2.如果使用的是wrap_content 需要计算，TextView大小与字体的长度的宽高 以及 字数大小有关
        if(widthMode == MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            //获取文本的rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            widthSize =bounds.width()+getPaddingLeft()+getPaddingRight();
        }

        if(heightMode == MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            //获取文本的rect
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            heightSize =bounds.height()+getPaddingTop()+getPaddingBottom();
        }

        //设置控件的宽高
        setMeasuredDimension(widthSize,heightSize);

        /*
        *
        * MeasureSpec.AT_MOST:在布局中指定了wrap_content等
        * MeasureSpec.EXACTLY:在布局中指定了确切的值 100sp 100dp. match_parent fill_parent
        * MeasureSpec.UNSPECIFIED: 尽可能的大,很少能用到 ListView ScrollView 在测量子布局的时候用UNSPECIFIED
        *
        * */
        if(widthMode == MeasureSpec.AT_MOST){

        }else if(widthMode == MeasureSpec.EXACTLY){

        }else if(widthMode == MeasureSpec.UNSPECIFIED){

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画文本
        //x 就是开始的位置
        //y 基线 baseline

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy =(fontMetricsInt.bottom - fontMetricsInt.top)/2 -fontMetricsInt.bottom;
        int baseline = getHeight()/2 + dy;
        Toast.makeText(MyApplication.getContext(),baseline+" "+getHeight()+" "+dy,Toast.LENGTH_SHORT).show();
            canvas.drawText(mText,getPaddingLeft(),baseline,mPaint);
//        //画弧
//        canvas.drawArc();
//        //画圆
//        canvas.drawCircle();
    }

    /**
     * 处理时间以及和用户交互
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);

    }
}
