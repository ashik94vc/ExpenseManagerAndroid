package com.cepheuen.expensemanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by Ashik Vetrivelu on 22/08/16.
 */
public class PagerScrollView extends HorizontalScrollView {

    public PagerScrollView(Context p_context, AttributeSet p_attrs)
    {
        super(p_context, p_attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event)
    {
        if (getParent() != null) {
            switch (p_event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }

        return super.onInterceptTouchEvent(p_event);
    }

}

