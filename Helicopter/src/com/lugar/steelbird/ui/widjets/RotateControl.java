package com.lugar.steelbird.ui.widjets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.lugar.steelbird.mathengine.Helicopter;

import org.andengine.util.math.MathUtils;

public class RotateControl extends ImageView {

    private Helicopter mHelicopter;

    private float mControlCenter;

    public RotateControl(Context context) {
        super(context);
    }

    public RotateControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mControlCenter = getWidth() / 2;
                break;
            case MotionEvent.ACTION_MOVE:
                final float directionX = mControlCenter - ev.getX();
                final float directionY = mControlCenter - ev.getY();
                final float rotationAngle = MathUtils.atan2(directionY, directionX);
                mHelicopter.setAngle(MathUtils.radToDeg(rotationAngle) - 90);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mHelicopter.setNextPoint(mHelicopter.getPoint());
                break;
        }
        return true;
    }

    public void setHelicopter(Helicopter helicopter) {
        mHelicopter = helicopter;
    }
}
