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
    //日志标记
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
            //获得文本内容文本内容不可以修改,平切判断当前当前内容是否为null
            final String _tvContent = TextUtils.isEmpty(getText()) ? "" : getText().toString();
            //获取文本长度
            final int _tvLenght = _tvContent.length();
            //设置文本宽度
            textPaint.getTextBounds(_tvContent, 0, _tvLenght, rect);
            //设置文本大小
            textPaint.setTextSize(getTextSize());
            //设置文本颜色
            textPaint.setColor(getCurrentTextColor());
            //获取行数据
            getTextContentData(_layout);
            //获得行高
            int _lineHeight = -rect.top + rect.bottom;
            //初始化布局
            initLayout(_layout);
            //设置布局区域
            int[] _area = getWidthAndHeigt(layoutWidth,layoutHeight, _layout.getLineCount(),_lineHeight);
            //设置布局
            setMeasuredDimension(_area[0],_area[1]);
        }
    }

    /**
     * 初始化化布局高度
     * @param _layout
     */
    private void initLayout(Layout _layout) {
        //获得布局大小
        if (layoutWidth < -2) {
            layoutWidth = _layout.getWidth();
        }
        if(layoutHeight < -2){
            layoutHeight = _layout.getHeight();
        }
    }

    /**
     * 获取布局数据
     * @param pWidth
     * @param pHeight
     * @return 返回宽高数组
     */
    private int[] getWidthAndHeigt(int pWidth, int pHeight,int pLineCount, int pLineHeight){
        int[] _area = {
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        };
        //判断布局是否适配
        if(pWidth == ViewGroup.LayoutParams.MATCH_PARENT && pHeight == ViewGroup.LayoutParams.MATCH_PARENT){
            _area[0] = ViewGroup.LayoutParams.MATCH_PARENT;
            _area[1] = ViewGroup.LayoutParams.MATCH_PARENT;
        }else if(pWidth == ViewGroup.LayoutParams.MATCH_PARENT && pHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            _area[0] = ViewGroup.LayoutParams.MATCH_PARENT;
            _area[1] = ViewGroup.LayoutParams.WRAP_CONTENT;
        }else if(pWidth == ViewGroup.LayoutParams.WRAP_CONTENT && pHeight == ViewGroup.LayoutParams.WRAP_CONTENT){
            _area[0] = ViewGroup.LayoutParams.WRAP_CONTENT;
            _area[1] = ViewGroup.LayoutParams.WRAP_CONTENT;
        }else{
            _area[0] = pWidth-rect.left;
            _area[1] = pLineHeight * pLineCount;
        }
        return _area;
    }

    /**
     * 获取行数据
     * @param _layout 文本布局对象（注：该布局其实使用的是Layout子类对象StaticLayout）
     */
    private void getTextContentData(Layout _layout) {
        //初始化航速数据
        lineContents = new String[_layout.getLineCount()];
        //获得每行数据
        for (int i = 0; i < _layout.getLineCount(); i++) {
            int _start = _layout.getLineStart(i);
            int _end = _layout.getLineEnd(i);
            lineContents[i] = getText().subSequence(_start, _end).toString();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //循环获取每行数据内容
        for (int i = 0; i < lineContents.length; i++) {
            //获得数据
            String _drawContent = lineContents[i];
            Log.e(TAG, "LINE[" + (i + 1) + "]=" + _drawContent);
            //绘制每行数据
            canvas.drawText(_drawContent, 0, -rect.top + (-rect.top + rect.bottom) * i, textPaint);
        }
    }
}
