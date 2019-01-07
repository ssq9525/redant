package com.xby.ssq.wavlink.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.xby.ssq.wavlink.R;

/**
 * 自定义圆角textview
 */
public class RoundTextView extends AppCompatTextView {
    private int mBgColor = 0;
    private int mCornerSize = 0;
    private int curColor = 0;
    private int mPressColor = 0;
    boolean isEnable = true;


    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        this.setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackground(this.getMeasuredWidth(), this.getMeasuredHeight(), this, curColor);
        super.onDraw(canvas);
    }


    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        mBgColor = ta.getColor(R.styleable.MyTextView_normal_Color, Color.BLUE);
        mCornerSize = ta.getInt(R.styleable.MyTextView_corner_Size, 0);
        mPressColor = ta.getColor(R.styleable.MyTextView_press_Color, Color.RED);
        curColor = mBgColor;
        invalidate();
        ta.recycle();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBackground(int w, int h, View v, int color) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        double dH = (metrics.heightPixels / 100) * 1.5;
        int iHeight = (int) dH;
        iHeight = mCornerSize;
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(color);
        RectF rec = new RectF(0, 0, w, h);
        c.drawRoundRect(rec, iHeight, iHeight, paint);
        v.setBackground(new BitmapDrawable(getResources(), bmp));
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnable = enabled;
        if (isEnable) {
            curColor = mBgColor;
        } else
            curColor = Color.GRAY;
        invalidate();
        super.setEnabled(enabled);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isEnable)
            return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curColor = mPressColor;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                curColor = mBgColor;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}