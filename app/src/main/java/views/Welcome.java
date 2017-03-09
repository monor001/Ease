package views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.Chapter;
import models.Course;
import models.Question;
import models.User;

import static controllers.SessionManager.IS_LOGIN;
import static lockscreen.localshop.com.ease.R.layout.welcome_slide4;

public class Welcome extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;


    // Session Manager Class
    SessionManager session;

    // User
    User user;

    /**************** Course Attributes ********************/
    // Courses Size
    int coursesSize = 3;
    String [] courses = {"SEA", "SEO", "E_BUSINESS"};
    String [] chapters = {"Chapter 1","Chapter 2","Chapter 3","Chapter 4"};
    String [] Questions = {"wegerhgt43ewfgwergweg\nsefewfwegwefwef\nwefwefwegwefwefwef",
            "Morem Morem\n" +
                    "sefewfwegwefwef\n" +
                    "wefwefwegwefwefwef",
            "Lorem Morem Horem Gorem Shorem",
            "Koolloo Boloreka !!!"};
    String [] Answers = {"Hay", "Yah"};
    /**************** Course Attributes ********************/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Checking for first time launch - before calling setContentView()
        session = new SessionManager(this);

        Boolean x = session.pref.getBoolean(IS_LOGIN, true);
        if(x){
            // Set Indexes
            //Indexes.maxNumQuestions = Questions.length;


            launchMainScreen();
            finish();
        }else{

            // Set course
            setCourse ();

            viewPager = (ViewPager) findViewById(R.id.welcome_view_pager);
            dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
            btnSkip = (Button) findViewById(R.id.skipButton);

            // layouts of all welcome sliders
            // add few more layouts if you want
            layouts = new int[]{
                    R.layout.welcome_slide1,
                    R.layout.welcome_slide2,
                    R.layout.welcome_slide3,
                    welcome_slide4};

            // adding bottom dots
            addBottomDots(0);

            // making notification bar transparent
            changeStatusBarColor();

            myViewPagerAdapter = new MyViewPagerAdapter();
            viewPager.setAdapter(myViewPagerAdapter);
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.setUserId(0);
                    Exclude ex = new Exclude();
                    Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                    String json_user = gson.toJson(user);
                    session.creatLoginSession(user.getUserId(), true, user.getUserName(), user.getEmail(), json_user);
                    launchHomeScreen();
                }
            });
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }



    }


    void setCourse (){

        // Initial required attributes
        user = new User();

        // Max Questions Number
        Indexes.maxNumQuestions = Questions.length;

        // Initialize user courses and chapters
        for (int courseCounter = 0; courseCounter < courses.length
                ; courseCounter++ ) {
            /* Push course data here*/
            Course course = new Course(courseCounter, courses[courseCounter], false, 0);
            user.getUserCourseslist().put(course.getCourseName(), course);
            for (int chapterCounter = 0; chapterCounter < chapters.length; chapterCounter++) {
                /* Push chapter data here*/
                Chapter chapter = new Chapter(chapterCounter, chapters[chapterCounter], 0, false);
                // Add questions
                for (int questionNum = 0; questionNum < Questions.length; questionNum++) {
                    Question question = new Question(questionNum, Questions[questionNum], Answers[0], null);
                    chapter.getQuestionList().add(question);
                }

                // Set course success percentage
                chapter.setCourseSuccessPercentage((int) (chapter.getQuestionList().size() * 60) / 100);
                // Activate first chapter
                if (chapterCounter == 0) {
                    chapter.setChapterActivate(true);
                }

                user.getUserCourseslist().get(course.getCourseName())
                        .getChapters().put(chapter.getChapterName(), chapter);
            }
        }
    }



    void setDataCollectionButtons (View v) {
        Button button = (Button) v;
        LinearLayout ONOFFLinearLayout = (LinearLayout) v.getParent();

        /*if (button.getText().equals("AN")){
            button.setTextColor(getResources().getColor(R.color.DarkBlue));
            button.setBackgroundResource(R.color.White);
            AUS_Welcome.setTextColor(getResources().getColor(R.color.White));
            AUS_Welcome.setBackgroundResource(R.drawable.strock);

        }else if (button.getText().equals("AUS")){
            AUS_Welcome.setTextColor(getResources().getColor(R.color.DarkBlue));
            AUS_Welcome.setBackgroundResource(R.color.White);
            AN_Welcome.setTextColor(getResources().getColor(R.color.White));
            AN_Welcome.setBackgroundResource(R.drawable.strock);
        }*/
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
       // prefManager.setFirstTimeLaunch(false);

        Intent intentWelcome = PreChooseCourse.makeIntent(Welcome.this);
        startActivity(intentWelcome);
        finish();
    }

    private void launchMainScreen() {
        Intent intentWelcome = HomeActivity.makeIntent(Welcome.this);
        startActivity(intentWelcome);
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
               // btnNext.setText(getString(R.string.start));
                btnSkip.setText("Start");
                // Home Page Call
                //launchHomeScreen();
            } else {
                // still pages are left
               // btnNext.setText(getString(R.string.next));
                btnSkip.setText("Skip");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
