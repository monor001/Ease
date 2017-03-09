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

import commons.CommonAnimations;
import controllers.Indexes;
import lockscreen.localshop.com.ease.R;
import models.User;

public class GapFillingPuzzle extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String GAP_FILLING_PUZZLE = "Gap Filling Puzzle";

    // Create user object
    User user;
    CommonAnimations commonAnimations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gap_filling_puzzle);
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
            Indexes.questionIndex--;
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
        Intent intent= new Intent(context, GapFillingPuzzle.class);
        return intent;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // Get view context

        commonAnimations.intializeVerticalAnimation();
        commonAnimations.intializeQuestionAnimation();
        commonAnimations.intializeButtonAnimation();
        commonAnimations.intializeKokoAnimation();
        commonAnimations.initializeBottomBtn();
    }
}
