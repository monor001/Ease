package commons;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import lockscreen.localshop.com.ease.R;

/**
 * Created by Elsayed on 20.02.2017.
 */

public class CommonChooseCourseView {


    public void createCourseTextview (Context context, LinearLayout sLL0, String courseName){

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textviewparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setText(courseName);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setBackgroundResource(R.drawable.strock);
        textView.setPadding
                (ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTextPaddingLeft), context)
                        , ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTextPaddingTop), context)
                        , ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTextPaddingRight), context)
                        , ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTextPaddingBottom), context));// Add to value resource

        textviewparams.setMargins(0,0,0, ConvertFromPixelToDP.convertPixelsToDp
                (context.getResources().getInteger(R.integer.courseTextMarginBottom), context));
        textView.setLayoutParams(textviewparams);
        sLL0.addView(textView);
    }
    public void createCourseToggleButton (Context context, LinearLayout sLL1, Boolean courseStatus){
        ToggleButton togglebutton = new ToggleButton(context);
        LinearLayout.LayoutParams toggleButtonparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        togglebutton.setChecked(courseStatus);

        if(courseStatus){
            togglebutton.setTextColor(Color.parseColor("#FF0A2247"));
            togglebutton.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            togglebutton.setTextColor(Color.parseColor("#ffffff"));
            togglebutton.setBackgroundResource(R.drawable.strock);
        }
        togglebutton.setPadding(ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTogglePaddingLeft), context)
                , ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTogglePaddingTop), context)
                , ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTogglePaddingRight), context)
                , ConvertFromPixelToDP.convertPixelsToDp(context.getResources().getInteger(R.integer.courseTogglePaddingBottom), context));// Add to value resource
        togglebutton.setMinimumHeight(0);
        togglebutton.setMinHeight(0);
        toggleButtonparams.setMargins(0,0,0, ConvertFromPixelToDP.convertPixelsToDp
                (context.getResources().getInteger(R.integer.courseToggleMarginBottom), context));
        togglebutton.setLayoutParams(toggleButtonparams);
        sLL1.addView(togglebutton);
    }


}
