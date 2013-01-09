package com.lugar.steelbird.ui.widjets;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.lugar.steelbird.Config;
import com.lugar.steelbird.mathengine.Helicopter;

public class ControlImageView extends ImageView {

    private Helicopter mHelicopter;

    private float mControlCenter;

    public ControlImageView(Context context) {
        super(context);
    }

    public ControlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ControlImageView(Context context, AttributeSet attrs, int defStyle) {
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
                final float offsetX = mControlCenter - ev.getX();
                final float offsetY = mControlCenter - ev.getY();
                mHelicopter.setNextPoint(new PointF(mHelicopter.posX() - Config.CAMERA_WIDTH * offsetX,
                        mHelicopter.posY() - Config.CAMERA_WIDTH * offsetY));
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
