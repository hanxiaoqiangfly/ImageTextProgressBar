package com.hanxiaoqiang.imagetextprogressbarlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @author 韩晓强
 * @date 2018/8/22
 * @describe 百分比+背景图标进度条
 * 背景图片请严格使用不留空白的切图，否则影响体验，如果非要使用(可能美工不给你切图)你也可以修改代码调整
 * @e-mail:897971804@qq.com
 */
public class ImageTextProgressBar extends RelativeLayout {
    private static final String TAG = "ImageTextProgressBar";
    //文本背景图
    private Drawable textBackground;
    //百分比文字颜色
    private int textColor;
    //百分比文字大小
    private int textSize = 15;
    //百分比文字内容
    private String textContent;
    //进度条自定义背景，背景图片请严格使用不留空白的切图
    private Drawable progressDrawable;
    //进度条当前进度progress
    private int progress;
    private int MAX = 100;

    private View mView;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ImageTextProgressBar(Context context) {
        this(context, null, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ImageTextProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ImageTextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextProgressBar);
        textBackground = a.getDrawable(R.styleable.ImageTextProgressBar_itpbTextBackground);
        textColor = a.getColor(R.styleable.ImageTextProgressBar_itpbTextColor, Color.WHITE);
        textSize = a.getDimensionPixelSize(R.styleable.ImageTextProgressBar_itpbTextSize, textSize);
        textContent = a.getString(R.styleable.ImageTextProgressBar_itpbTextContent);

        progressDrawable = a.getDrawable(R.styleable.ImageTextProgressBar_itpbProgressDrawable);

        progress = a.getInteger(R.styleable.ImageTextProgressBar_itpbProgress, 0);
        a.recycle();
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        if (mView == null) {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.image_text_pb, null);
            mProgressBar = (ProgressBar) mView.findViewById(R.id.pb_progressbar);
            mTextView = (TextView) mView.findViewById(R.id.pb_precent);
            if (textBackground != null) {
                mTextView.setBackground(textBackground);
            }
            //设置文本
            mTextView.setTextColor(textColor);
            mTextView.setTextSize(textSize);
            mTextView.setText(textContent);
            //进度条
           setProgress(progress);
            if (progressDrawable != null) {
                mProgressBar.setProgressDrawable(progressDrawable);
            }
            addView(mView);
        }
    }

    public void setProgress(final int precent) {
        if (precent < 0 || precent > MAX) {
            return;
        }
        ViewTreeObserver vto2 = mProgressBar.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mProgressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = mProgressBar.getWidth();
                int tvWidth = mTextView.getMeasuredWidth();
                mProgressBar.setProgress(precent);
                mTextView.setText(precent + "%");
                float offsetX = (float) (mProgressBar.getProgress() * 1.0 / mProgressBar.getMax() * width);
                int maxOffset = width - tvWidth;
                int minOffset = tvWidth / 2;
                Log.i(TAG, "width: "+width);
                //防止背景图片移出屏幕外，所以加此判断
                if (offsetX < minOffset) {
                    offsetX = 0;
                } else if (offsetX >= minOffset && offsetX <= maxOffset) {
                    offsetX -= tvWidth / 2;
                } else if (offsetX > maxOffset) {
                    offsetX = maxOffset;
                }
                mTextView.setTranslationX(offsetX);
            }
        });
    }

    public int getProgress() {
        return mProgressBar.getProgress();
    }

    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
