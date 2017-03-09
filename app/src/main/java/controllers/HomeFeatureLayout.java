package controllers;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

/**
 * Created by asus on 27.02.2017.
 */

public class HomeFeatureLayout extends HorizontalScrollView {
    private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;

    private ArrayList mItems = null;
    private GestureDetector mGestureDetector;
    private int mActiveFeature = 0;

    public HomeFeatureLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HomeFeatureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeFeatureLayout(Context context) {
        super(context);
    }

    public void setFeatureItems(Activity activiy, ArrayList items){
        /*LinearLayout internalWrapper = (LinearLayout) activiy.findViewById(R.id.lL_parentCards);
        this.mItems = items;
        for(int i = 0; i< 5;i++){
           // LinearLayout featureLayout = (LinearLayout) View.inflate(this.getContext(),R.layout.activity_flip_manager,null);
            //...
            //Create the view for each screen in the scroll view
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(300, 230);
            layoutParams.setMargins(0, 0, 10, 0);
            relativeLayout.setLayoutParams(layoutParams);

            //Front Button
            Button btn_front = new Button(getContext());
            RelativeLayout.LayoutParams frontbtnLayoutprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            btn_front.setText("Fornt");
            btn_front.setTextColor(getResources().getColor(R.color.White));
            btn_front.setBackgroundColor(getResources().getColor(R.color.DarkBlueTransparent));
            btn_front.setLayoutParams(frontbtnLayoutprams);

            //Back Button
            Button btn_back = new Button(getContext());
            RelativeLayout.LayoutParams backbtnLayoutprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            btn_back.setText("back");
            btn_back.setTextColor(getResources().getColor(R.color.White));
            btn_back.setBackgroundColor(getResources().getColor(R.color.DarkBlueTransparent));
            btn_back.setLayoutParams(backbtnLayoutprams);



            //...

            relativeLayout.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //If the user swipes
                    if (mGestureDetector.onTouchEvent(event)) {
                        return true;
                    }
                    else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
                        int scrollX = getScrollX();
                        int featureWidth = v.getMeasuredWidth();
                        mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
                        int scrollTo = mActiveFeature*featureWidth;
                        smoothScrollTo(scrollTo, 0);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });
            mGestureDetector = new GestureDetector(new MyGestureDetector());
            relativeLayout.addView(btn_front);
            relativeLayout.addView(btn_back);
            internalWrapper.addView(relativeLayout);
        }
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //If the user swipes
                if (mGestureDetector.onTouchEvent(event)) {
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
                    int scrollX = getScrollX();
                    int featureWidth = v.getMeasuredWidth();
                    mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
                    int scrollTo = mActiveFeature*featureWidth;
                    smoothScrollTo(scrollTo, 0);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
        mGestureDetector = new GestureDetector(new MyGestureDetector());*/
    }
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                //right to left
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    int featureWidth = getMeasuredWidth();
                    mActiveFeature = (mActiveFeature < (mItems.size() - 1))? mActiveFeature + 1:mItems.size() -1;
                    smoothScrollTo(mActiveFeature*featureWidth, 0);
                    return true;
                }
                //left to right
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    int featureWidth = getMeasuredWidth();
                    mActiveFeature = (mActiveFeature > 0)? mActiveFeature - 1:0;
                    smoothScrollTo(mActiveFeature*featureWidth, 0);
                    return true;
                }
            } catch (Exception e) {
                Log.e("Fling", "There was an error processing the Fling event:" + e.getMessage());
            }
            return false;
        }
    }
}