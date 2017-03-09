package views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.Exclude;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.User;

public class SignIn extends AppCompatActivity {

    // Initialize current activity
    public static final String SIGN_IN = "SignIn";

    // Create SignIn Attributes
    EditText nameSignin,passwordSignin;
    Button btnSignIn;
    String nameSignin_s,passwordSignin_s;

    // Create user object
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        user = gson.fromJson(json_user, User.class);
        /*********************************************************************/

        // Call Layout Attributes
        btnSignIn = (Button) findViewById(R.id.signinBtn);
        nameSignin = (EditText) findViewById(R.id.signinName);
        passwordSignin = (EditText) findViewById(R.id.signinPW);

        // Check login
        if(session.checkLogin()){
            //ToDo a call for another view
            Intent intentMain = HomeActivity.makeIntent(SignIn.this);
            startActivity(intentMain);
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation(){
        nameSignin_s=nameSignin.getText().toString().trim();
        passwordSignin_s=passwordSignin.getText().toString().trim();

        if(!validate()){
            Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();
        }else {
            onSigninSucess();
        }
    }

    public void onSigninSucess(){
        //ToDo a call for another view
        Intent intentMain = HomeActivity.makeIntent(SignIn.this);
        startActivity(intentMain);
    }


    public boolean validate(){
        boolean validate=true;
        if (nameSignin_s.isEmpty()){
            nameSignin.setError("Please enter a name");
            validate=false;
        }
        if (nameSignin_s.length()>32){
            nameSignin.setError("The name should not be more than 32 charcter");
            validate=false;
        }
        if(passwordSignin_s.isEmpty()){
            passwordSignin.setError("Please enter a password");
            validate=false;
        }
        return validate;
    }

    // Call SignIn Intent
    public static Intent makeIntent(Context context) {
        Intent newIntent = new Intent(context, SignIn.class);
        return newIntent;
    }
}
