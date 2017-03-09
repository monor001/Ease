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
import android.widget.Toast;

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

public class MoreCourses extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CoursesSort {


    public static final String MORE_COURSES = "More Courses";

    // Create user object
    User user;

    // Create Common Courses Layout object
    CommonCoursesLayout commonCoursesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_courses);
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
        courseSelectionFinish ();
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
            Indexes.calledClass = HomeActivity.MAIN_ACTIVITY;
            checkCourseSelection ();
            return;
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
        Intent newIntent = new Intent(context, MoreCourses.class);
        return newIntent;
    }


    void onCreateCourse () {

        for (String courseName : user.getUserCourseslist().keySet()){
            commonCoursesLayout = new CommonCoursesLayout(this, MORE_COURSES, courseName);
            commonCoursesLayout.initializeCourseLayout();
        }
    }

    void courseSelectionFinish (){
        Button finish_postCS = (Button) findViewById(R.id.finish_postCS);
        finish_postCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCourseSelection ();
            }
        });
    }

    void checkCourseSelection () {
        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        /*********************************************************************/
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        // Number of Courses
        int offCourseCounter=0;
        for (String courseName : user.getUserCourseslist().keySet()){
            if (!user.getUserCourseslist().get(courseName).isCourseActive()){
                offCourseCounter++;
            }
        }
        // Number of Courses
        if(offCourseCounter!=user.getUserCourseslist().size()){
            if (!user.getUserCourseslist().isEmpty() && user.getUserCourseslist()!=null) {
                // Go to the next view
                json_user = gson.toJson(user);
                session.editUserSession(json_user);
                Intent intentMainActivity = HomeActivity.makeIntent(MoreCourses.this);
                startActivity(intentMainActivity);
                finish();
            }
        }else{
            // Add old user info to the current one
            String json_user_temp = session.pref.getString(SessionManager.JSON_TEMP, "");
            user = gson.fromJson(json_user_temp, User.class);
            json_user = gson.toJson(user);
            session.editUserSession(json_user);

            Context context = getApplicationContext();
            Toast.makeText(context,
                    "Sorry you cannot continue, at least select one course", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public LinkedHashMap<String, Course> resortcourses(Course course, LinkedHashMap<String, Course> coursesList) {
        LinkedHashMap<String, Course> courseList_temp = new LinkedHashMap<String, Course>();
        for (String courseIndex_temp : coursesList.keySet()){
            if (coursesList.get(courseIndex_temp).isCourseActive()){
                courseList_temp.put(courseIndex_temp, coursesList.get(courseIndex_temp));
            }
        }
        for (String courseIndex_temp : coursesList.keySet()){
            if (!coursesList.get(courseIndex_temp).isCourseActive()){
                courseList_temp.put(courseIndex_temp, coursesList.get(courseIndex_temp));
            }
        }

        return courseList_temp;
    }
}
