package views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.User;

public class FlipManager extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int NUM_PAGES = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    public static final String FLIP_MANAGER = "Flip Manager";
    boolean isBackVisible = false, isBackVisibleTemp=false;

    // Current Card Position
    static int currentCardPosition = 0;

    // Create user object
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        /*********************************************************************/

        // Set course name
        TextView courseName_FC = (TextView) findViewById(R.id.courseName_FC);
        courseName_FC.setText(Indexes.courseIndex);

        // Set cards number
        if (user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size()==0){
            TextView cardNumber = (TextView) findViewById(R.id.cardNumber);
            cardNumber.setText("0 / 0");
            NUM_PAGES=0;
        }else{
            NUM_PAGES =user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size();
        }
        // Set view pager slider listner
        ViewPager pager = (ViewPager)  findViewById(R.id.pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TextView cardNumber = (TextView) findViewById(R.id.cardNumber);
                cardNumber.setText(position+1 + " / " + NUM_PAGES);
                currentCardPosition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();

            }
        });

        // Remove A Card
        removeCard();


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
            SessionManager session = new SessionManager(getApplicationContext());
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String json_user = gson.toJson(user);
            session.editUserSession(json_user);
            /*********************************************************************/
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
        Intent intent = new Intent(context, FlipManager.class);
        return intent;
    }

    private void removeCard() {
        Button bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call Session
                SessionManager session = new SessionManager(getApplicationContext());
                Exclude ex = new Exclude();
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user = session.pref.getString(SessionManager.JSON, "");
                User user = gson.fromJson(json_user, User.class);
                if (user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size()==0) {
                    TextView cardNumber = (TextView) findViewById(R.id.cardNumber);
                    cardNumber.setText("0 / 0");
                }
                else  {
                    user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().remove(currentCardPosition);
                    NUM_PAGES =user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size();
                    ViewPager pager = (ViewPager)  findViewById(R.id.pager);
                    Object objectobject = mPagerAdapter.instantiateItem(pager, currentCardPosition);
                    mPagerAdapter.destroyItem(pager, currentCardPosition, objectobject);
                    pager.setAdapter(mPagerAdapter);
                    pager.setCurrentItem(currentCardPosition);

                    if (user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size()==0) {
                        TextView cardNumber = (TextView) findViewById(R.id.cardNumber);
                        cardNumber.setText("0 / 0");
                    }
                }

                // Edit Session
                Gson gson1 = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user1 = gson1.toJson(user);
                session.editUserSession(json_user1);

            }
        });
    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //currentCardPosition = position;

            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem((ViewPager)container, position, object);
            // Remove viewpager_item.xml from ViewPager
            /*container.removeView((LinearLayout) object);*/
            int maxcards = getCount()+1;
            if (position >= maxcards) {
                FragmentManager manager = ((Fragment) object).getFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((Fragment) object);
                trans.commit();
            }
            notifyDataSetChanged();
        }

        /*// Delete a page at a `position`
        public void deletePage(int position)
        {
            // Remove the corresponding item in the data set
            //page_indexes.remove(position);
            // Notify the adapter that the data set is changed
            notifyDataSetChanged();
        }*/
    }
}
