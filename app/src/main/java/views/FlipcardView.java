package views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;

import commons.CommonCoursesLayout;
import commons.CoursesSort;
import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.Course;
import models.User;

public class FlipcardView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CoursesSort {

    public static final String FLIPCARD_VIEW = "Flipcard View";


    // Create user object
    User user;

    // Create Common Courses Layout object
    CommonCoursesLayout commonCoursesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipcard_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        /*********************************************************************/


        // Sort Courses
        // Resort courses depends on/off courses
        user.setUserCourseslist(resortcourses(
                user.getUserCourseslist().get(Indexes.courseIndex),
                user.getUserCourseslist()
        ));


        /************************ Create All courses view *****************************/
        onCreateCourse ();
        /*courseSelectionFinish ();*/
        /*********************************************************************/

        /************************ Initialize OFF All courses *****************************/
        // offAllSelection ();
        /*********************************************************************/


        /************************ Drawer *****************************/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*********************************************************************/
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
           /* checkFlipcardSelection ();*/
            Indexes.calledClass = HomeActivity.MAIN_ACTIVITY;
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
        Intent newIntent = new Intent(context, FlipcardView.class);
        return newIntent;
    }

    void onCreateCourse () {

        for (String courseName : user.getUserCourseslist().keySet()){
            commonCoursesLayout = new CommonCoursesLayout(this, FLIPCARD_VIEW, courseName);
            LinearLayout courseLinearLayout = commonCoursesLayout.initializeCourseLayout();
            courseLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout = (LinearLayout)  v;

                    /**************** Take care if you changed flipcard course layout *****************/

                    TextView courseName = (TextView) linearLayout.getChildAt(0); // Change this number

                    /*******************************************************************************/

                    // Put course index
                    Indexes.courseIndex = courseName.getText().toString();

                    // Resort courses depends on user clicks
                    user.setUserCourseslist(resortcourses(
                            user.getUserCourseslist().get(Indexes.courseIndex),
                            user.getUserCourseslist()
                    ));


                    // Save session
                    /************************ Call session *****************************/
                    SessionManager session = new SessionManager(getApplicationContext());
                    Exclude ex = new Exclude();
                    Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                    /*********************************************************************/
                    String json_user = gson.toJson(user);
                    session.editUserSession(json_user);

                    Intent intentFlipManager = FlipManager.makeIntent(FlipcardView.this);
                    startActivity(intentFlipManager);
                    //finish();
                }
            });
        }
    }

   /* void courseSelectionFinish (){
        Button finish_postCS = (Button) findViewById(R.id.finish_postCS);
        finish_postCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//*checkFlipcardSelection
                        ();*//*
            }
        });
    }*/

   /* void checkFlipcardSelection () {
        *//************************ Call session *****************************//*
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        *//*********************************************************************//*
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        // Number of Courses
        int offCourseCounter=0;
        for (String courseName : user.getUserCourseslist().keySet()){
            if (!user.getUserCourseslist().get(courseName).isFlipcardsActive()){
                offCourseCounter++;
            }
        }
        // Number of Courses
        if(offCourseCounter!=user.getUserCourseslist().size()){
            if (!user.getUserCourseslist().isEmpty() && user.getUserCourseslist()!=null) {
                // Go to the next view
                json_user = gson.toJson(user);
                session.editUserSession(json_user);
                Intent intentMainActivity = FlipManager.makeIntent(FlipcardView.this);
                startActivity(intentMainActivity);
                finish();
            }
        }else{
            json_user = gson.toJson(user);
            session.editUserSession(json_user);
            Context context = getApplicationContext();
            Toast.makeText(context,
                    "You have no active flipcard course", Toast.LENGTH_LONG).show();
            Intent intentMainActivity = HomeActivity.makeIntent(FlipcardView.this);
            startActivity(intentMainActivity);
            finish();
        }
    }*/

    /*private void offAllSelection() {

        Button btnOffAll = (Button) findViewById(R.id.offAll);

        btnOffAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//************************ Call session *****************************//*
                SessionManager session = new SessionManager(getApplicationContext());
                Exclude ex = new Exclude();
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user = session.pref.getString(SessionManager.JSON, "");
                user = gson.fromJson(json_user, User.class);
                *//*********************************************************************//*

                // Get parent LinearLayout
                LinearLayout lLEnterCourse_MA = (LinearLayout) findViewById(R.id.lLEnterCourse_MA);

                for (int linearChildrenCount = 0; linearChildrenCount < lLEnterCourse_MA.getChildCount()
                        ; linearChildrenCount++){
                    // Get LinearLayout parent
                    LinearLayout parentLinearlayout = (LinearLayout) lLEnterCourse_MA
                            .getChildAt(linearChildrenCount);

                    *//**************** Take care if you changed flipcard course layout *****************//*

                    // Get Course name from LinearLayout parent
                    TextView coursenameLinearlayout = (TextView) parentLinearlayout
                            .getChildAt(0);// Change this number

                    *//*******************************************************************************//*

                    *//**************** Take care if you changed flipcard course layout *****************//*

                    LinearLayout onOffLinearlayout = (LinearLayout) parentLinearlayout
                            .getChildAt(2);// Change this number

                    *//*******************************************************************************//*


                    for (int btnCount = 0; btnCount < onOffLinearlayout.getChildCount()
                        ; btnCount++){

                        Button btnOnOff = (Button) onOffLinearlayout.getChildAt(btnCount);

                        if (btnOnOff.getText().toString().equals("AN")){
                            btnOnOff.setTextColor(getResources().getColor(R.color.White));
                            btnOnOff.setBackgroundResource(R.drawable.strock);
                            user.getUserCourseslist().get(coursenameLinearlayout.getText().toString()).setFlipcardsActive(false);
                            json_user = gson.toJson(user);
                            session.editUserSession(json_user);
                        }
                        else if (btnOnOff.getText().toString().equals("AUS")){
                            btnOnOff.setTextColor(getResources().getColor(R.color.DarkBlue));
                            btnOnOff.setBackgroundResource(R.color.White);
                        }
                    }
                }
            }
        });



    }
*/
    @Override
    public LinkedHashMap<String, Course> resortcourses(Course course, LinkedHashMap<String, Course> coursesList) {
        LinkedHashMap<String, Course> courseList_temp = new LinkedHashMap<String, Course>();
        for (String courseIndex_temp : coursesList.keySet()){
            if (coursesList.get(courseIndex_temp).isFlipcardsActive()){
                courseList_temp.put(courseIndex_temp, coursesList.get(courseIndex_temp));
            }
        }
        for (String courseIndex_temp : coursesList.keySet()){
            if (!coursesList.get(courseIndex_temp).isFlipcardsActive()){
                courseList_temp.put(courseIndex_temp, coursesList.get(courseIndex_temp));
            }
        }

        return courseList_temp;
    }
}
