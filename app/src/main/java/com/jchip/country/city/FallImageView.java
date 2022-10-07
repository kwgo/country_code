package com.jchip.country.city;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatImageView;

public class FallImageView extends AppCompatImageView {
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;

    public static final int CROP_TYPE_TOP = +1;
    public static final int CROP_TYPE_BOTTOM = -1;
    public static final int CROP_TYPY_FIT = 0;

    private static final int CORNER_RADIUS_DEFAULT = 10;

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_CORNER_RADIUS = "state_border_radius";

    private boolean cornerTopLeft = true;
    private boolean cornerTopRight = true;
    private boolean cornerBottomLeft = true;
    private boolean cornerBottomRight = true;
    private int type = TYPE_CIRCLE;
    private int cropType = CROP_TYPE_TOP;

    private int cornerRadius;

    private int width;
    private int radius;

    private Paint bitmapPaint;
    private RectF roundRect;
    private Matrix matrix;
    private BitmapShader bitmapShader;

    public FallImageView(Context context) {
        this(context, null);
    }

    public FallImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FallImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        matrix = new Matrix();
        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        type = array.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
        cropType = array.getInt(R.styleable.RoundImageView_crop_type, CROP_TYPY_FIT);

        cornerTopLeft = array.getBoolean(R.styleable.RoundImageView_top_left, true);
        cornerTopRight = array.getBoolean(R.styleable.RoundImageView_top_right, true);
        cornerBottomLeft = array.getBoolean(R.styleable.RoundImageView_bottom_left, true);
        cornerBottomRight = array.getBoolean(R.styleable.RoundImageView_bottom_right, true);

        cornerRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_corner_radius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS_DEFAULT,
                        getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            radius = width / 2;
            setMeasuredDimension(width, width);
        }
    }

    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmap = drawableToBitamp(drawable, cropType);
        bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        float scale = 1.0f;
        if (type == TYPE_CIRCLE) {
            scale = width * 1.0f / Math.min(bitmap.getWidth(), bitmap.getHeight());
            matrix.setScale(scale, scale);
        } else if (type == TYPE_ROUND) {
            float scaleWidth = getWidth() * 1.0f / bitmap.getWidth();
            float scaleHeight = getHeight() * 1.0f / bitmap.getHeight();
//            scale = scaleWidth != scaleHeight ? Math.max(scaleWidth, scaleHeight) : 1f;
//            if (scaleType == SCALE_TYPE_CROP) {
//                // matrix.setScale(scale, scale);
//            } else if (scaleType == SCALE_TYPE_FIT) {
//                matrix.setScale(scaleWidth, scaleHeight);
//            }
            matrix.setScale(scaleWidth, scaleHeight);
        }
        bitmapShader.setLocalMatrix(matrix);
        bitmapPaint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();

        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(roundRect, cornerRadius, cornerRadius, bitmapPaint);
            if (!cornerTopLeft) {
                canvas.drawRect(0, 0, cornerRadius, cornerRadius, bitmapPaint);
            }
            if (!cornerTopRight) {
                canvas.drawRect(roundRect.right - cornerRadius, 0, roundRect.right, cornerRadius, bitmapPaint);
            }
            if (!cornerBottomLeft) {
                canvas.drawRect(0, roundRect.bottom - cornerRadius, cornerRadius, roundRect.bottom, bitmapPaint);
            }
            if (!cornerBottomRight) {
                canvas.drawRect(roundRect.right - cornerRadius, roundRect.bottom - cornerRadius, roundRect.right, roundRect.bottom, bitmapPaint);
            }
        } else {
            canvas.drawCircle(radius, radius, radius, bitmapPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND) {
            roundRect = new RectF(0, 0, w, h);
        }
    }

    private Bitmap drawableToBitamp(Drawable drawable, int cropType) {
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bd = (BitmapDrawable) drawable;
//            return bd.getBitmap();
//        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (cropType == -1) {
            drawable.setBounds(0, -h, w, h);
        } else if (cropType == +1) {
            drawable.setBounds(0, 0, w, h + h);
        } else {
            drawable.setBounds(0, 0, w, h);
        }
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_CORNER_RADIUS, cornerRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state).getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.cornerRadius = bundle.getInt(STATE_CORNER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    public void setBorderRadius(int borderRadius) {
        this.cornerRadius = dp2px(borderRadius);
        invalidate();
    }

    public void setCropType(int cropType) {
        if (cropType != CROP_TYPE_TOP && cropType != CROP_TYPY_FIT) {
            return;
        }
        this.cropType = cropType;
        invalidate();
    }

    public void setType(int type) {
        if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
            return;
        }
        this.type = type;
        invalidate();
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }
}