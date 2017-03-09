package controllers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by asus on 27.02.2017.
 */

public class OnSlidingTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSlidingTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListner());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListner extends GestureDetector.SimpleOnGestureListener {

        private static final int SLIDE_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            int x=0;
            int y=0;
            x=x+y;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            try {
                float deltaY = e2.getY() - e1.getY();
                float deltaX = e2.getX() - e1.getX();

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        if (deltaX > 0) {
                            // the user made a sliding right gesture
                            return onSlideRight();
                        } else {
                            // the user made a sliding left gesture
                            return onSlideLeft();
                        }

                } else {
                    if (Math.abs(deltaY) > SLIDE_THRESHOLD) {
                        if (deltaY > 0) {
                            // the user made a sliding down gesture
                            return onSlideDown();
                        } else {
                            // the user made a sliding up gesture
                            return onSlideUp();
                        }
                    }
                }
            } catch (Exception exception) {
            }

            return false;
        }
    }

    public boolean onSlideRight() {
        return false;
    }

    public boolean onSlideLeft() {
        return false;
    }

    public boolean onSlideUp() {
        return false;
    }

    public boolean onSlideDown() {
        return false;
    }
}