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
import android.widget.Button;

import commons.CommonAnimations;
import lockscreen.localshop.com.ease.R;
import models.User;

public class Trends extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TRENDS = "Trends";

    // Create user object
    User user;
    CommonAnimations commonAnimations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        commonAnimations = new CommonAnimations(this);
        commonAnimations.setQuestiontxt();



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
            Intent intentCourseOverview = CourseOverview.makeIntent(getApplicationContext());
            startActivity(intentCourseOverview);
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
        Intent intent= new Intent(context, Trends.class);
        return intent;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // Get view context

        commonAnimations.intializeVerticalAnimation();
        commonAnimations.intializeQuestionAnimation();
        commonAnimations.intializeKokoAnimation();
        commonAnimations.initializeBottomBtn();
        // Initialize action listener for single button
        final Button btn_trends = (Button) findViewById(R.id.btn_trends);
        commonAnimations.allButtonsAnimation(btn_trends);

    }
}
