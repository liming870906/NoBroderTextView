package com.liming.nobroder.nopaddingtextview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

public class NoPaddingTextView extends AppCompatTextView {
    private final String TAG = NoPaddingTextView.class.getSimpleName();

    //文本画笔
    private TextPaint textPaint;
    //绘制矩形
    private Rect rect;
    //默认宽度
    private int layoutWidth = -3;
    private int layoutHeight = -3;
    //获得每行数据
    private String[] lineContents;


    public NoPaddingTextView(Context context) {
        super(context);
        init();
    }

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoPaddingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //声明画笔对象
        textPaint = new TextPaint();
        //声明矩形绘制对象
        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout _layout = getLayout();
        if (_layout != null) {
            Log.e(TAG, "Layout:Wight = " + _layout.getWidth() + " <> Layout_Height = " + _layout.getHeight());
            //获得文本内容文本内容不可以修改,平切判断当前当前内容是否为null
            final String _tvContent = TextUtils.isEmpty(getText()) ? "" : getText().toString();
            //获取文本长度
            final int _tvLenght = _tvContent.length();
            //设置文本宽度
            textPaint.getTextBounds(_tvContent, 0, _tvLenght, rect);
            Log.e(TAG, "Layout:Rect = " + rect.toString());
            //设置文本大小
            textPaint.setTextSize(getTextSize());
            //设置文本颜色
            textPaint.setColor(getCurrentTextColor());
            //初始化航速数据
            lineContents = new String[_layout.getLineCount()];
            //获得每行数据
            for (int i = 0; i < _layout.getLineCount(); i++) {
                int _start = _layout.getLineStart(i);
                int _end = _layout.getLineEnd(i);
                lineContents[i] = getText().subSequence(_start, _end).toString();
            }
            //获得行高
            int _lineHeight = -rect.top + rect.bottom;
            //获得布局大小
            if (layoutWidth < -2) {
                layoutWidth = _layout.getWidth();
            }
            if(layoutWidth == ViewGroup.LayoutParams.MATCH_PARENT){
                //重新设置布局尺寸
                setMeasuredDimension(ViewGroup.LayoutParams.MATCH_PARENT,_lineHeight * _layout.getLineCount());
            }else if(layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT){
                //重新设置布局尺寸
                setMeasuredDimension(ViewGroup.LayoutParams.WRAP_CONTENT,_lineHeight * _layout.getLineCount());
            }else{
                //重新设置布局尺寸
                setMeasuredDimension(layoutWidth-rect.left,_lineHeight * _layout.getLineCount());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw()=======================");
        Log.e(TAG, "getLineSpacingExtra() = " + getLineSpacingExtra());
        Log.e(TAG, "getPaint().getFontMetricsInt(null) = " + getPaint().getFontMetricsInt(null));
        for (int i = 0; i < lineContents.length; i++) {
            String _drawContent = lineContents[i];
            Log.e(TAG, "LINE[" + (i + 1) + "]=" + _drawContent);
            canvas.drawText(_drawContent, 0, -rect.top + (-rect.top + rect.bottom) * i, textPaint);
        }
    }
}
