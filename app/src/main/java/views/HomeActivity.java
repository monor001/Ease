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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import commons.CommonCoursesLayout;
import commons.CoursesSort;
import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.Course;
import models.User;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CoursesSort {

    public static final String MAIN_ACTIVITY = "Main Activity";

    // Attributes to handle circle progress
    ArrayList<Course> courses_alltimes = new ArrayList<Course>();;
    ArrayList<Button> courses_btns_alltimes = new ArrayList<Button>();
    ArrayList<Button> courses_btns_weekly = new ArrayList<Button>();
    RelativeLayout rL_progressCircles;
    RelativeLayout courses_lL0, courses_lL1;
    int intital_value=0;

    // Create user object
    User user;

    // Create user temp object
    User userTemp;


    LinearLayout lLEnterCourse_MA;
    LinearLayout courseLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        String json_user_temp = session.pref.getString(SessionManager.JSON_TEMP, "");
        userTemp = gson.fromJson(json_user_temp, User.class);
        /*********************************************************************/

        /************************ Create selected courses view *****************************/
        // Call LinearLayout
        lLEnterCourse_MA = (LinearLayout) findViewById(R.id.lLEnterCourse_MA);

        /* Create courses view on the first creation*/
        onFirstcreate ();

        // Setup Add more courses button
        callTopicSelection();

        // Setup Flipcard Button
        callFlipCardManage ();

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
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent makeIntent(Context context) {
        Intent newIntent = new Intent(context, HomeActivity.class);
        return newIntent;
    }


    /* Show all selected courses*/
    void onFirstcreate (){
        // Call all courses to the layout
        for (String courseName : user.getUserCourseslist().keySet()){
            // Check if the course is active
            if(user.getUserCourseslist().get(courseName).isCourseActive()){
                createCourseView (courseName);
                user.getUserCourseslist().get(courseName).setDisplayed_MA(true);
            }

        }
    }

    void onChangeDisplaystatus(String courseName){
        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        /*********************************************************************/
        user.getUserCourseslist().get(courseName).setDisplayed_MA(true);
        String json_user = gson.toJson(user);
        session.editUserSession(json_user);
        createCourseView (courseName);
    }

    void createCourseView (String courseName){
        CommonCoursesLayout commonCoursesLayout = new CommonCoursesLayout(this, MAIN_ACTIVITY, courseName);
        courseLinearLayout = commonCoursesLayout.initializeCourseLayout();
        courseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout)  v;
                Intent intentCourseOverview = CourseOverview.makeIntent(HomeActivity.this);
                TextView courseName = (TextView) linearLayout.getChildAt(1);
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
                // Put course index
                startActivity(intentCourseOverview);
                //finish();
            }
        });
    }

    @Override
    public LinkedHashMap<String, Course> resortcourses(Course course, LinkedHashMap<String, Course> coursesList) {
        LinkedHashMap<String, Course> courseList_temp = new LinkedHashMap<String, Course>();
        courseList_temp.put(course.getCourseName(), course);
        for (String courseIndex_temp : coursesList.keySet()){
            if (!courseIndex_temp.equals(course.getCourseName())){
                courseList_temp.put(courseIndex_temp, coursesList.get(courseIndex_temp));
            }
        }

        return courseList_temp;
    }

    // Call More Courses activity
    void callTopicSelection(){
        Button b1Extra_MA = (Button) findViewById(R.id.newTopicBExtra_MA);
        b1Extra_MA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /************************ Call session *****************************/
                SessionManager session = new SessionManager(getApplicationContext());
                Exclude ex = new Exclude();
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                /*********************************************************************/

                String json_user = gson.toJson(user);
                session.editUserSession(json_user);

                // Edit temporary user info to keep any error away from choosing non of the courses
                userTemp = user;
                String json_user_temp = gson.toJson(userTemp);
                session.editUserSessionTemp(json_user_temp);

                Intent intentNewTopic = MoreCourses.makeIntent(HomeActivity.this);
                startActivity(intentNewTopic);
                Indexes.calledClass = MoreCourses.MORE_COURSES;
                //finish();
            }
        });
    }

    void callFlipCardManage() {
        Button flipcardbtn_MA = (Button) findViewById(R.id.flipcardbtn_MA);
        flipcardbtn_MA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /************************ Call session *****************************/
                SessionManager session = new SessionManager(getApplicationContext());
                Exclude ex = new Exclude();
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                /*********************************************************************/
                String json_user = gson.toJson(user);
                session.editUserSession(json_user);
                Intent intentNewTopic = FlipcardView.makeIntent(HomeActivity.this);
                startActivity(intentNewTopic);
                Indexes.calledClass = FlipcardView.FLIPCARD_VIEW;
                //finish();
            }
        });

    }
}
