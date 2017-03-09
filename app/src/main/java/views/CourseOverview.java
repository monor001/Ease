package views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

import commons.ConvertFromPixelToDP;
import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.User;

public class CourseOverview extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String COURSE_OVERVIEW = "Course Overview";

    // Create user object
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        /*********************************************************************/

        /*********Flush*********/
        /*user.getUserCourseslist().get(Indexes.courseIndex)
                .setFlipcards(new ArrayList<Flipcard>());*/


        // Call another puzzle in case puzzle is finished
        if(session.pref.getBoolean(SessionManager.ResolvedPuzzle, true) ||
                session.pref.getBoolean(SessionManager.AddedPuzzleToFlipcard, true) ){ // Donot forget to put another exception to handle chapter level
            session.editResolvedPuzzle(false);
            if(user.getUserCourseslist().get(Indexes.courseIndex).getChapters().get(Indexes.chapterIndex)
                    .getNumQuestion() < Indexes.maxNumQuestions){
                callRandomPuzzle();
            }
        }

        // Call target layout for chapter overview
        LinearLayout lL_courseoverview = (LinearLayout) findViewById(R.id.lL_courseoverview);

        // Course Header
        RelativeLayout relativeLayoutCourseHeader = (RelativeLayout) findViewById(R.id.courseHeader);

        TextView textViewHeader = (TextView) relativeLayoutCourseHeader.getChildAt(0);

        textViewHeader.setText(Indexes.courseIndex);
        textViewHeader.setTextColor(getResources().getColor(R.color.White));

        // Create course's chapters layout
        // Put here a condition to justify the layout of the selected course
        int tempCount = 1;
        for(String chapter :
                user.getUserCourseslist().get(Indexes.courseIndex)
                        .getChapters().keySet()){
            createChaptersLayout (lL_courseoverview, chapter, tempCount);
            tempCount++;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            /************************ Call session *****************************/
            /************************ Call session *****************************/
            SessionManager session = new SessionManager(getApplicationContext());
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            /*********************************************************************/
            String json_user = session.pref.getString(SessionManager.JSON, "");
            user = gson.fromJson(json_user, User.class);
            session.editUserSession(json_user);
            /*********************************************************************/
            // Call Home Activty
            Intent intentHomeActivity = HomeActivity.makeIntent(CourseOverview.this);
            startActivity(intentHomeActivity);
            Indexes.calledClass = HomeActivity.MAIN_ACTIVITY;
            finish();

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static Intent makeIntent(Context context) {
        Intent newIntent = new Intent(context, CourseOverview.class);
        return newIntent;
    }



    void createChaptersLayout (LinearLayout lL_courseoverview, String chapter, int tempCount){

        // Create Linearlayout
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0, ConvertFromPixelToDP.convertPixelsToDp(10, this));// Add to value resource
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);

        // Create Progress Circle
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        progressBar.setIndeterminate(true);
        progressBar.setMinimumHeight(ConvertFromPixelToDP.convertPixelsToDp
                (30, this));// Add to value resource
        progressBar.setMax(100);
        progressBar.setProgress(0);

        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.circular2));
        progressBar.setIndeterminate(false);

        Drawable progressDrawable = getResources().getDrawable(R.drawable.circular2);
        progressBar.setProgressDrawable(progressDrawable);

        progressBar.setProgress(50);
        progressBar.setLayoutParams(progressBarParams);


        Button chapterbutton = null;
        Button flipcardButton = null;
        boolean flipcardCreated = false;
        // Check both if the chapter is active and the ansewrs not exceded more than 60 %
        /*if (user.getUserCourseslist().get(Indexes.courseIndex).
                getChapters().get(chapter).isChapterActivate()
                && user.getUserCourseslist().get(Indexes.courseIndex).
                getChapters().get(chapter).getNumberofCorrectAnswers() >=
                user.getUserCourseslist().get(Indexes.courseIndex).
                        getChapters().get(chapter).getCourseSuccessPercentage()){

            // Create Go to Flipcard Manager Button
          //  flipcardButton = createFlipcardButton(chapter);

        }*/  if(user.getUserCourseslist().get(Indexes.courseIndex).
                getChapters().get(chapter).isChapterActivate()){
            // Create chapter button
            chapterbutton = createChapterButton(true, chapter);

        } else if (!user.getUserCourseslist().get(Indexes.courseIndex).
                getChapters().get(chapter).isChapterActivate()){
            // Create chapter button
            chapterbutton = createChapterButton(false, chapter);
        }


        // Add previous widgets to their parent linearLayout
        linearLayout.addView(progressBar, 0);

        if(chapterbutton!=null){
            linearLayout.addView(chapterbutton, 1);
        }


        // Add linearLayout to its parent linearLayout
        lL_courseoverview.addView(linearLayout);
        // Add flipcard to the view
       /* if (flipcardCreated && flipcardButton != null){
            lL_courseoverview.addView(flipcardButton);
        }*/

    }


    Button createChapterButton (boolean active, String chapter){
        // Create Chapter Button
        Button button = new Button(this);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setPadding
                (ConvertFromPixelToDP.convertPixelsToDp(getResources().getInteger(R.integer.chapterButtonPaddingLeft), this)
                        , ConvertFromPixelToDP.convertPixelsToDp(getResources().getInteger(R.integer.chapterButtonPaddingTop), this)
                        , ConvertFromPixelToDP.convertPixelsToDp(getResources().getInteger(R.integer.chapterButtonPaddingRight), this)
                        , ConvertFromPixelToDP.convertPixelsToDp(getResources().getInteger(R.integer.chapterButtonPaddingBottom), this));// Add to value resource

        button.setMinimumHeight(ConvertFromPixelToDP.convertPixelsToDp
                (getApplicationContext().getResources().getInteger(R.integer.chapterProgressCircleMinHeight), this));// Add to value resource
        button.setMinHeight(ConvertFromPixelToDP.convertPixelsToDp
                (getApplicationContext().getResources().getInteger(R.integer.chapterProgressCircleMinHeight), this));// Add to value resource
        button.setBackgroundDrawable(null);
        button.setText(chapter);
        button.setOnClickListener(onClickListener);
        // Set button active or not
        if (active){
            button.setEnabled(active);
            button.setTextColor(getResources().getColor(R.color.White));
        }else {
            button.setEnabled(active);
            button.setTextColor(getResources().getColor(R.color.Gray));
        }

        button.setLayoutParams(buttonParams);

        return button;
    }

    Button createFlipcardButton (String chapter){
        // Create Chapter Button
        Button button = new Button(this);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(0,30, 0, 30);
        button.setPadding
                (10, 5, 10, 5);// Add to value resource

        button.setMinimumHeight(ConvertFromPixelToDP.convertPixelsToDp
                (getApplicationContext().getResources().getInteger(R.integer.chapterProgressCircleMinHeight), this));// Add to value resource
        button.setMinHeight(ConvertFromPixelToDP.convertPixelsToDp
                (getApplicationContext().getResources().getInteger(R.integer.chapterProgressCircleMinHeight), this));// Add to value resource
        button.setBackgroundDrawable(null);
        button.setText("Flip Card");
        button.setTextColor(getResources().getColor(R.color.White));
        //button.setOnClickListener(onClickListener);

        button.setLayoutParams(buttonParams);

        return button;
    }

    /*Call random puzzles activities*/
    // Set a click listener for each chapter
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /************************ Call session *****************************/
            SessionManager session = new SessionManager(getApplicationContext());
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String json_user = session.pref.getString(SessionManager.JSON, "");
            user = gson.fromJson(json_user, User.class);
            /*********************************************************************/

            Button button = (Button) v;
            Indexes.chapterIndex = button.getText().toString();
            // Initialize Questions Max Number
            Indexes.maxNumQuestions = user.getUserCourseslist().get(Indexes.courseIndex)
                    .getChapters().get(Indexes.chapterIndex).getQuestionList().size();
            /*********Flush*********/
            if(user.getUserCourseslist().get(Indexes.courseIndex).getChapters().get(Indexes.chapterIndex)
                    .getNumQuestion() >= Indexes.maxNumQuestions){
                user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                        get(Indexes.chapterIndex)
                        .setNumQuestion(0);
            }
            json_user = gson.toJson(user);
            session.editUserSession(json_user);
            callRandomPuzzle();
        }
    };

    // Call another puzzle
    void callRandomPuzzle(){

        if(user.getUserCourseslist().get(Indexes.courseIndex).getChapters().get(Indexes.chapterIndex)
                .getNumQuestion() < Indexes.maxNumQuestions){
            // Move forward for another question in the same chapter
            Indexes.questionIndex = user.getUserCourseslist().get(Indexes.courseIndex).getChapters().get(Indexes.chapterIndex)
                    .getNumQuestion();
        }else{
            /*********Flush*********/
            user.getUserCourseslist().get(Indexes.courseIndex).getChapters().
                    get(Indexes.chapterIndex)
                    .setNumQuestion(0);

            Indexes.questionIndex = user.getUserCourseslist().get(Indexes.courseIndex).getChapters().get(Indexes.chapterIndex)
                    .getNumQuestion();
        }

        if (Indexes.questionIndex < Indexes.maxNumQuestions){
            Random rndPuzzle = new Random();
            int puzzle_id = rndPuzzle.nextInt(3);
            switch (puzzle_id){
                case 0:
                    // Call Selection Puzzle
                    finish();
                    Intent intentSelectionPuzzle = SelectionPuzzle.makeIntent(CourseOverview.this);
                    startActivity(intentSelectionPuzzle);
                    break;
                case 1:
                    // Call Gap filling Puzzle
                    finish();
                    Intent intentGapFillingPuzzle = GapFillingPuzzle.makeIntent(CourseOverview.this);
                    startActivity(intentGapFillingPuzzle);
                    break;
                case 2:
                    // Call One Line Definitions
                    finish();
                    Intent intentTrends = Trends.makeIntent(CourseOverview.this);
                    startActivity(intentTrends);
                    break;
            }
        }else {
            Indexes.questionIndex = -1;
        }
    }
}
