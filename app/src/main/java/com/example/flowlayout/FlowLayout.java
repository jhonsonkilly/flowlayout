package com.example.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        View child = null;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {

        }
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);

            if (child.getVisibility() != View.GONE) {
                final LayoutParams lp = child.getLayoutParams();


                final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                        getPaddingLeft() + getPaddingRight(), lp.width);
                final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                        getPaddingBottom() + getPaddingTop(), lp.height);

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int width = 0;//文字的宽度
            int height = 0;//一行的最大高度（不累加）
            int lineHeight = 0;//每一行的行高

            Log.i("FlowLayout", l + "  " + l + "  " + r + "  " + b);
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view.getMeasuredWidth() + width < getMeasuredWidth() - getPaddingRight()) {
                    view.layout(l + width + getPaddingLeft(),
                            t + lineHeight,
                            view.getMeasuredWidth() + width + getPaddingLeft(),
                            view.getMeasuredHeight() + t + lineHeight);
                    width = width + view.getMeasuredWidth();
                    height = Math.max(height, view.getMeasuredHeight());

                } else {
                    width = 0;
                    view.layout(l + width + getPaddingLeft(),
                            t + lineHeight + height,
                            view.getMeasuredWidth() + width + getPaddingLeft(),
                            t + lineHeight + height + view.getMeasuredHeight());
                    lineHeight = lineHeight + height;
                    width = width + view.getMeasuredWidth();
                    height=0;
                }
            }


        }
    }


}
