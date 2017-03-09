package commons;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.Flipcard;
import models.User;
import views.CourseOverview;
import views.FlipManager;

//import lockscreen.localshop.com.dashboard.CourseOverview;
//import lockscreen.localshop.com.dashboard.FlipManager;

/**
 * Created by asus on 22.02.2017.
 */

public class CommonAnimations {

    Activity activity;
    FrameLayout verticallineframe;
    TextView questiontxt;
    GridLayout btn_grid;
    ImageView koko;
    Button bottomBtn;
    FlipManager flipManager;

    public Handler handler;
    public Runnable runnable;

    public CommonAnimations(Activity activity) {
        this.activity = activity;
        flipManager = new FlipManager();
    }


    public void intializeVerticalAnimation() {
        // Initialize verticallineframe animation
        verticallineFrameAnimation ();

    }
    public void intializeQuestionAnimation() {
        // Initialize Question Text Animation animation
        QuestionTextAnimation ();
    }


    public void intializeButtonAnimation() {
        btn_grid = (GridLayout) activity.findViewById(R.id.btn_grid);
        // Initialize all buttons animation
        for (int btns_counter = 0; btns_counter < btn_grid.getChildCount(); btns_counter++){
            Button button = (Button) btn_grid.getChildAt(btns_counter);
            allButtonsAnimation (button);
        }

    }

    public void intializeKokoAnimation() {
        btn_grid = (GridLayout) activity.findViewById(R.id.btn_grid);
        // Initialize all buttons animation
        kokoAnimation ();
    }

    public void initializeBottomBtn(){
        bottomBtn = (Button) activity.findViewById(R.id.bottom_btn);
        allButtonsAnimation(bottomBtn);
    }

    public void initializeCircleProgress(ProgressBar progressCircle, int currentProgress){
        circleProgressAnimation (progressCircle, currentProgress);
    }

    public void initializeTextProgress(TextView progressText, int currentProgress){
        circleTextAnimation (progressText, currentProgress);
    }

    void verticallineFrameAnimation (){
        verticallineframe = (FrameLayout) activity.findViewById(R.id.verticallinefram);

        float y=verticallineframe.getY();
        // Top drop animation
        ObjectAnimator moveAnim1 = ObjectAnimator.ofFloat
                (verticallineframe, "Y", ConvertFromPixelToDP.convertPixelsToDp
                        (verticallineframe.getY(),activity.getApplicationContext()),
                        ConvertFromPixelToDP.convertPixelsToDp
                                (verticallineframe.getY()+180, activity.getApplicationContext()));
        moveAnim1.setInterpolator(new AccelerateDecelerateInterpolator());
        moveAnim1.setDuration(1500);
        moveAnim1.start();
    }

    public void setQuestiontxt () {
        //Set Question Text
        SessionManager session = new SessionManager(activity.getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        User user = gson.fromJson(json_user, User.class);

        questiontxt= (TextView) activity.findViewById(R.id.questiontxt);

        questiontxt.setText(user.getUserCourseslist().get(Indexes.courseIndex).
                getChapters().get(Indexes.chapterIndex).getQuestionList()
                .get(Indexes.questionIndex).getQuestion());
    }

    void QuestionTextAnimation (){


        // Down lift animation
        ObjectAnimator moveAnim1 = ObjectAnimator.ofFloat(questiontxt, "Y", ConvertFromPixelToDP.convertPixelsToDp
                (questiontxt.getY(),activity.getApplicationContext()), ConvertFromPixelToDP.convertPixelsToDp
                (questiontxt.getY()-10,activity.getApplicationContext()));
        moveAnim1.setInterpolator(new BounceInterpolator());
        moveAnim1.setDuration(4000);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setDuration(1000);
        alphaAnimation.setStartOffset(700);
        alphaAnimation.setFillAfter(true);
        moveAnim1.start();
        questiontxt.startAnimation(alphaAnimation);
    }

    public void allButtonsAnimation (final Button button) {
        koko = (ImageView) activity.findViewById(R.id.kokoDrag);
            button.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()){
                        case DragEvent.ACTION_DRAG_ENTERED:
                            ValueAnimator colorAnimation = ValueAnimator.ofObject
                                    (new ArgbEvaluator(), 0, activity.getResources().getColor(R.color.White));
                            colorAnimation.setDuration(500); // milliseconds
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    button.setBackgroundColor((int) animator.getAnimatedValue());
                                    button.setTextColor(activity.getResources().getColor(R.color.DarkBlue));
                                }

                            });
                            colorAnimation.start();
                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
                            ValueAnimator colorAnimation2 = ValueAnimator.ofObject
                                    (new ArgbEvaluator(), activity.getResources().getColor(R.color.White), 0);
                            colorAnimation2.setDuration(500); // milliseconds
                            colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    button.setBackgroundColor((int) animator.getAnimatedValue());
                                    button.setBackgroundResource(R.drawable.strock);
                                    button.setTextColor(activity.getResources().getColor(R.color.White));
                                }

                            });
                            colorAnimation2.start();
                            break;

                        case DragEvent.ACTION_DROP:
                            if(button==button.findViewById(R.id.bottom_btn)){
                                // Open session
                                SessionManager session = new SessionManager(activity.getApplicationContext());
                                Exclude ex = new Exclude();
                                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                                String json_user = session.pref.getString(SessionManager.JSON, "");
                                User user = gson.fromJson(json_user, User.class);
                                TextView textView = (TextView) activity.findViewById(R.id.questiontxt);
                                String question = textView.getText().toString();
                                Flipcard flipcard = new Flipcard(user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size(), question,
                                                user.getUserCourseslist().get(Indexes.courseIndex).getChapters()
                                                        .get(Indexes.chapterIndex).getQuestionList()
                                                        .get(Indexes.questionIndex).getRightAnswer());
                                user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().add(flipcard);

                                Toast.makeText(activity.getApplicationContext(),
                                        "A card has been added", Toast.LENGTH_LONG).show();

                                // Move forward the questions
                                // Move forward for another question in the same chapter
                                int temp_questionNum =
                                        user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                                                get(Indexes.chapterIndex)
                                                .getNumQuestion()+1;

                                user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                                        get(Indexes.chapterIndex)
                                        .setNumQuestion(temp_questionNum);

                                // Call session to hold resolved puzzle flag
                                session.editResolvedPuzzle(false);
                                session.editAddedPuzzleToFlipcard(true);
                                json_user = gson.toJson(user);
                                session.editUserSession(json_user);
                                Intent intentSelectionPuzzle = CourseOverview.makeIntent(activity.getApplicationContext());
                                activity.startActivity(intentSelectionPuzzle);
                                // Loop with CourseOverview
                                activity.finish();

                            }else{
                                // Loop with CourseOverview

                                // Call session to hold resolved puzzle flag
                                SessionManager session2 = new SessionManager(activity.getApplicationContext());
                                session2.editResolvedPuzzle(true);
                                Exclude ex2 = new Exclude();
                                Gson gson2 = new GsonBuilder().addDeserializationExclusionStrategy(ex2).addSerializationExclusionStrategy(ex2).create();
                                String json_user2 = session2.pref.getString(SessionManager.JSON, "");
                                User user;
                                user = gson2.fromJson(json_user2, User.class);
                                // Correct answer
                                user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                                        get(Indexes.chapterIndex).setNumberofCorrectAnswers(user.getUserCourseslist().
                                        get(Indexes.courseIndex).getChapters().
                                        get(Indexes.chapterIndex).getNumberofCorrectAnswers()+1);

                                // Move forward for another question in the same chapter
                                int temp_questionNum =
                                        user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                                                get(Indexes.chapterIndex)
                                                .getNumQuestion()+1;

                                user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                                        get(Indexes.chapterIndex)
                                        .setNumQuestion(temp_questionNum);

                                json_user2 = gson2.toJson(user);
                                session2.editUserSession(json_user2);
                                activity.finish();
                                Intent intentSelectionPuzzle = CourseOverview.makeIntent(activity.getApplicationContext());
                                activity.startActivity(intentSelectionPuzzle);
                            }
                            break;

                        case DragEvent.ACTION_DRAG_ENDED:
                            float x=1;
                            koko.setAlpha(x);
                            break;

                    }

                    return true;
                }
            });
    }

    private void kokoAnimation() {
        koko = (ImageView) activity.findViewById(R.id.kokoDrag);
        koko.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ClipData data =  ClipData.newPlainText("","");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadowBuilder, v, 0);
                        float x=0;
                        koko.setAlpha(x);
                        break;
                }

                return true;
            }
        });
    }


    private void circleProgressAnimation(ProgressBar progressCircle, int currentProgress) {
        ObjectAnimator animation1 = ObjectAnimator.ofInt (progressCircle, "progress", 0, currentProgress); // see this max value coming back here, we animale towards that value
        animation1.setDuration (2000); //in milliseconds
        animation1.setInterpolator (new DecelerateInterpolator());
        animation1.start ();

    }

    private void circleTextAnimation(final TextView progressText, final int currentProgress) {
        handler = new Handler();
        runnable = new Runnable() {
            int progress_value =0;
            @Override
            public void run() {
                if(progress_value < currentProgress){
                    progress_value++;
                    progressText.setText(""+ progress_value);
                }
                handler.postDelayed(this, 0);
            }
        };

        handler.post(runnable);
    }

}
