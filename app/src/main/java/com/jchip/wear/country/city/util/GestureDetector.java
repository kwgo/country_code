package com.jchip.wear.country.city.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.core.view.GestureDetectorCompat;

public class GestureDetector {
    private GestureDetectorCompat gestureDetector;

    public int SWIPE_MIN_DISTANCE = 120;
    //public int SWIPE_MAX_OFF_PATH = 250;
    public int SWIPE_MIN_VELOCITY = 200;
    public int SWIPE_MAX_VELOCITY = 300;

    public GestureDetector(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        ViewConfiguration vc = ViewConfiguration.get(context);
        SWIPE_MIN_DISTANCE = Math.round(vc.getScaledPagingTouchSlop() * density);
        SWIPE_MIN_VELOCITY = vc.getScaledMinimumFlingVelocity();
        SWIPE_MAX_VELOCITY = vc.getScaledMaximumFlingVelocity();

        this.gestureDetector = new GestureDetectorCompat(context, new android.view.GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY && Math.abs(velocityX) < SWIPE_MAX_VELOCITY) {
                    return GestureDetector.this.onRightToLeft(event1, event2);
                } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY && Math.abs(velocityX) < SWIPE_MAX_VELOCITY) {
                    return GestureDetector.this.onLeftToRight(event1, event2);
                } else if (event1.getY() - event2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_MIN_VELOCITY && Math.abs(velocityY) < SWIPE_MAX_VELOCITY) {
                    return GestureDetector.this.onBottomToTop(event1, event2);
                } else if (event2.getY() - event1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_MIN_VELOCITY && Math.abs(velocityY) < SWIPE_MAX_VELOCITY) {
                    return GestureDetector.this.onTopToBottom(event1, event2);
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                return GestureDetector.this.onSingleTapConfirmed(event);
            }

            @Override
            public void onLongPress(MotionEvent event) {
                GestureDetector.this.onLongPress(event);
            }

            @Override
            public boolean onDoubleTap(MotionEvent event) {
                return GestureDetector.this.onDoubleTap(event);
            }
        });
    }

    public boolean onSingleTapConfirmed(MotionEvent event) {
        // Toast.makeText(context, "Single tap", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onLongPress(MotionEvent event) {
        // Toast.makeText(context, "Long press", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onDoubleTap(MotionEvent event) {
        // Toast.makeText(context, "Double tap", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onRightToLeft(MotionEvent event1, MotionEvent event2) {
        // Toast.makeText(context, "Swipe right to left", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onLeftToRight(MotionEvent event1, MotionEvent event2) {
        // Toast.makeText(context, "Swipe left to right", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onTopToBottom(MotionEvent event1, MotionEvent event2) {
        // Toast.makeText(context, "Swipe top to bottom", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onBottomToTop(MotionEvent event1, MotionEvent event2) {
        // Toast.makeText(context, "Swipe bottom to top", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onTouchEvent(View view, MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }
}
