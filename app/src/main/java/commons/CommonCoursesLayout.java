package commons;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.User;
import views.FlipcardView;
import views.MoreCourses;
import views.PreChooseCourse;

//import lockscreen.localshop.com.dashboard.MoreCourses;

/**
 * Created by asus on 25.02.2017.
 */

public class CommonCoursesLayout {

    Activity activity;
    CommonAnimations commonAnimations;
    String courseName = "";
    String activityName = "";
    SessionManager session;
    Gson gson;
    String json_user;
    Exclude ex;
    User user;

    public CommonCoursesLayout(Activity activity, String activityName, String courseName) {
        this.activity = activity;
        this.activityName = activityName;
        this.courseName = courseName;
        commonAnimations = new CommonAnimations(activity);
        session = new SessionManager(activity);
    }

    public LinearLayout initializeCourseLayout (){

        // Call session
        ex = new Exclude();
        gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);

        // Temporarly LinearLayout
        LinearLayout linearlayoutONOFF;

        LinearLayout lLEnterCourse_MA = (LinearLayout) activity.findViewById(R.id.lLEnterCourse_MA);

        // Create vertical LinearLayout
        LinearLayout verticalLinearLayout = initializeLinearLayout ();

        // Create RelativeLayout to contain Circle Progress
        RelativeLayout relaticeCircleLayout = initializeRelativLayout ();

        // Create Progress Circle
        ProgressBar progressCircle = initializeProgressCircle ();

        // Create Textview Progress in Numbers
        TextView progressInNumbers = initializeTextviewProgress ();

        // Add progress proprties to the relativelayout
        relaticeCircleLayout.addView(progressCircle);
        relaticeCircleLayout.addView(progressInNumbers);

        // Create Textview Progress in Numbers
        TextView course = initializeTextviewcourseName (courseName);

        // Create Textview Progress in Numbers
        TextView courseDiscription = initializeTextviewcourseDiscription ();

        // Add course properties to the LinearLayout (To add progress circle)
        if (!activityName.
                equals(PreChooseCourse.PRE_CHOOSE_COURSE)){
            if (!activityName.
                    equals(FlipcardView.FLIPCARD_VIEW)){
                verticalLinearLayout.addView(relaticeCircleLayout);
            }
        }


        verticalLinearLayout.addView(course);
        verticalLinearLayout.addView(courseDiscription);

        // Check if the called view is the view for choosing courses !!
        if (activityName.
                equals(MoreCourses.MORE_COURSES) || activityName.
                equals(PreChooseCourse.PRE_CHOOSE_COURSE)){
            // Intialize On/Off Buttons
            linearlayoutONOFF = initializeOnOffButtons ();
            verticalLinearLayout.addView(linearlayoutONOFF);

        }

        // Check if the called view is the view for Flipcards !!
       /* if (activityName.
                equals(FlipcardView.FLIPCARD_VIEW)){
            // Intialize On/Off Buttons
            linearlayoutONOFF = initializeOnOffButtons ();
            verticalLinearLayout.addView(linearlayoutONOFF);

        }*//* if (activityName.
                equals(FlipcardView.FLIPCARD_VIEW)){
            // Intialize On/Off Buttons
            linearlayoutONOFF = initializeOnOffButtons ();
            verticalLinearLayout.addView(linearlayoutONOFF);

        }*/

        // Add vertical LinearLayout to Horizontal LinearLayout
        lLEnterCourse_MA.addView(verticalLinearLayout);

        return verticalLinearLayout;
    }


    public LinearLayout initializeLinearLayout (){
        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams
                (180, LinearLayout.LayoutParams.MATCH_PARENT);// Add to value resources
        linearLayout.setBackgroundResource(R.drawable.strock);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayoutParams.setMargins(0,0,10,0);// Add to value resources
        linearLayout.setClickable(true);
        linearLayout.setLayoutParams(linearLayoutParams);

        /*linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exclude ex = new Exclude();
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user = gson.toJson(user);
                session.editUserSession(json_user);
                Intent intentNewTopic = CourseOverview.makeIntent(activity.getApplicationContext());
                TextView courseName = (TextView) courseLinearLayout.getChildAt(1);
                ClickableController.courseName = courseName.getText().toString();
                startActivity(intentNewTopic);
            }
        });*/

        return linearLayout;
    }

    // Intialize relativelayout to contain circle progress
    public RelativeLayout initializeRelativLayout (){
        // Create a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams rlprams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlprams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        relativeLayout.setGravity(Gravity.CENTER_VERTICAL);
        relativeLayout.setLayoutParams(rlprams);
        return relativeLayout;
    }

    public ProgressBar initializeProgressCircle (){

        // Create Progress Circle
        ProgressBar progressBar = new ProgressBar(activity, null, android.R.attr.progressBarStyleHorizontal);
        RelativeLayout.LayoutParams progressBarParams = new RelativeLayout.LayoutParams
                (ConvertFromPixelToDP.convertPixelsToDp(90,activity),
                        ConvertFromPixelToDP.convertPixelsToDp(90,activity)); // Add to value resources
        progressBarParams.setMargins(0,30,0,0);// Add to value resources
        progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        progressBar.setIndeterminate(true);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        progressBar.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.circular));
        progressBar.setIndeterminate(false);

        Drawable progressDrawable = activity.getResources().getDrawable(R.drawable.circular);
        progressBar.setProgressDrawable(progressDrawable);
        // set progress
        progressBar.setProgress(100);// set progress
        progressBar.setLayoutParams(progressBarParams);

        // Intialize Progress Circle Animation
        commonAnimations.initializeCircleProgress(progressBar, 100);// set progress

        return progressBar;
    }
    private TextView initializeTextviewProgress() {

        TextView textView = new TextView(activity);
        RelativeLayout.LayoutParams textviewParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textviewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        textView.setGravity(Gravity.CENTER);
        textView.setText("0");
        textView.setTextColor(activity.getResources().getColor(R.color.White));
        textView.setLayoutParams(textviewParams);
        commonAnimations.initializeTextProgress(textView, 100);// set progress
        return textView;
    }

    private TextView initializeTextviewcourseName(String courseName) {
        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams textviewParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textviewParams.setMargins(0,0,0,0);// Add to value resources
        textView.setPadding(10,0,0,0);// Add to value resources
        textView.setLayoutParams(textviewParams);
        textView.setText(courseName);
        textView.setTextColor(activity.getResources().getColor(R.color.White));

        return  textView;
    }

    private TextView initializeTextviewcourseDiscription() {
        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams textviewParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textviewParams.weight = 1;
        textviewParams.setMargins(0,0,0,0);// Add to value resources
        textView.setPadding(10,0,0,0);// Add to value resources
        textView.setLayoutParams(textviewParams);
        textView.setText("Lorem Lorem Lorem Lorem Lorem");
        textView.setTextColor(activity.getResources().getColor(R.color.White));
        return  textView;
    }

    public LinearLayout initializeOnOffButtons() {

        //Initialize horizontal LinearLayout
        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);// Add to value resources
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(linearLayoutParams);

        // Initialize On Button
        final Button onButton = new Button(activity);
        onButton.setText("AN");
        /******* PreChooseCourse view on/off buttons *********/
        if (activityName.equals(PreChooseCourse.PRE_CHOOSE_COURSE)){
            onButton.setTextColor(activity.getResources().getColor(R.color.White));
            onButton.setBackgroundResource(R.drawable.strock);
        }

        // Initialize Off Button
        final Button offButton = new Button(activity);
        offButton.setText("AUS");
        if (activityName.equals(PreChooseCourse.PRE_CHOOSE_COURSE)){
            offButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
            offButton.setBackgroundResource(R.color.White);
        }
        /*******************************************/


        /******* Flipcard view on/off buttons *********/
        else if (activityName.equals(FlipcardView.FLIPCARD_VIEW)){
            if (user.getUserCourseslist().get(courseName).isFlipcardsActive()){
                // Change ON Button
                onButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                onButton.setBackgroundResource(R.color.White);

                // Change OFF Button
                offButton.setTextColor(activity.getResources().getColor(R.color.White));
                offButton.setBackgroundResource(R.drawable.strock);
            }else{
                // Change ON Button
                onButton.setTextColor(activity.getResources().getColor(R.color.White));
                onButton.setBackgroundResource(R.drawable.strock);

                // Change OFF Button
                offButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                offButton.setBackgroundResource(R.color.White);
            }
        }
        /*******************************************/

        /******* MoreCourses view on/off buttons *********/
        else if (activityName.equals(MoreCourses.MORE_COURSES)){
            // ON
            if (user.getUserCourseslist().get(courseName).isCourseActive()){
                // Change ON Button
                onButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                onButton.setBackgroundResource(R.color.White);

                // Change OFF Button
                offButton.setTextColor(activity.getResources().getColor(R.color.White));
                offButton.setBackgroundResource(R.drawable.strock);
            }else{
                // Change ON Button
                onButton.setTextColor(activity.getResources().getColor(R.color.White));
                onButton.setBackgroundResource(R.drawable.strock);

                // Change OFF Button
                offButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                offButton.setBackgroundResource(R.color.White);
            }
        }
        /*******************************************/

        // Set listners to ON/OFF Buttons
        // ON
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call session
                ex = new Exclude();
                gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                json_user = session.pref.getString(SessionManager.JSON, "");
                user = gson.fromJson(json_user, User.class);
                // Change ON Button
                onButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                onButton.setBackgroundResource(R.color.White);


                // Check if its add more courses
                if (!Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    user.getUserCourseslist().get(courseName).setCourseActive(true);
                    onButton.setActivated(true);
                }else if (Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    user.getUserCourseslist().get(courseName).setFlipcardsActive(true);
                    onButton.setActivated(true);
                }


                // Change OFF Button
                offButton.setTextColor(activity.getResources().getColor(R.color.White));
                offButton.setBackgroundResource(R.drawable.strock);

                // Check if its add more courses
                if (!Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    offButton.setActivated(false);
                }else if (Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    offButton.setActivated(false);
                }

                // Save session
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user = gson.toJson(user);
                session.editUserSession(json_user);

            }
        });
        //OFF
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call session
                ex = new Exclude();
                gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                json_user = session.pref.getString(SessionManager.JSON, "");
                user = gson.fromJson(json_user, User.class);
                // Change OFF Button
                offButton.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                offButton.setBackgroundResource(R.color.White);


                // Check if its add more courses
                if (!Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    user.getUserCourseslist().get(courseName).setCourseActive(false);
                    offButton.setActivated(true);
                }else if (Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    user.getUserCourseslist().get(courseName).setFlipcardsActive(false);
                    offButton.setActivated(true);
                }

                // Save session
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user = gson.toJson(user);
                session.editUserSession(json_user);

                // Change ON Button
                onButton.setTextColor(activity.getResources().getColor(R.color.White));
                onButton.setBackgroundResource(R.drawable.strock);

                // Check if its add more courses
                if (!Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    onButton.setActivated(false);
                }else if (Indexes.calledClass.equals(FlipcardView.FLIPCARD_VIEW)){
                    onButton.setActivated(false);
                }

            }
        });

        // Add buttons to the created Linearlayout
        linearLayout.addView(onButton);
        linearLayout.addView(offButton);

        return linearLayout;
    }

}
