package views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import commons.CommonChooseCourseView;
import commons.CommonCoursesLayout;
import controllers.Exclude;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.User;

public class PreChooseCourse extends AppCompatActivity {

    // Initialize current activity
    public static final String PRE_CHOOSE_COURSE = "Pre Choose Course";

    // Create course overview
    CommonChooseCourseView commonChooseCourseView;
    CommonCoursesLayout commonCoursesLayout;

    // Create user object
    User user;

    // Temp attributes
    int coursesSize = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_choose_course);
        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        /*********************************************************************/

        commonChooseCourseView = new CommonChooseCourseView();

        // Create Pre-courses overview
        onCreateCourse ();

        // Set Finish Button
        courseSelectionFinish ();
    }

    void onCreateCourse () {

        for (String courseName : user.getUserCourseslist().keySet()){
            commonCoursesLayout = new CommonCoursesLayout(this, PRE_CHOOSE_COURSE, courseName);
            commonCoursesLayout.initializeCourseLayout();
        }

    }


    void courseSelectionFinish (){
        // Initial data


        Button finish_preCS = (Button) findViewById(R.id.finish_preCS);

        // Get all assigned courses by the user
        finish_preCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call session
                /************************ Call session *****************************/
                SessionManager session = new SessionManager(getApplicationContext());
                Exclude ex = new Exclude();
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                String json_user = session.pref.getString(SessionManager.JSON, "");
                user = gson.fromJson(json_user, User.class);
                /*********************************************************************/
                int offCourseCounter=0;
                for (String courseName : user.getUserCourseslist().keySet()){
                    if (!user.getUserCourseslist().get(courseName).isCourseActive()){
                        offCourseCounter++;
                    }
                }
                if(offCourseCounter!=3){
                    if (!user.getUserCourseslist().isEmpty() && user.getUserCourseslist()!=null) {
                        // Go to the next view
                        user.setUserId(0);
                        /************************ Save session *****************************/
                        ex = new Exclude();
                        gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
                        json_user = gson.toJson(user);
                        session.creatLoginSession(user.getUserId(), false, user.getUserName(), user.getEmail(), json_user);
                        /*********************************************************************/
                        Intent intentSignIn = SignIn.makeIntent(PreChooseCourse.this);
                        startActivity(intentSignIn);
                        finish();
                    }
                }else{
                    Context context = getApplicationContext();
                    Toast.makeText(context,
                            "Sorry you cannot continue, at least select one course", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    // Call PreChooseCourse Intent
    public static Intent makeIntent(Context context) {
        Intent newIntent = new Intent(context, PreChooseCourse.class);
        return newIntent;
    }
}
