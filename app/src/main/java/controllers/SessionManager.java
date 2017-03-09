package controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import views.SignIn;


/**
 * Created by Elsayed on 13.02.2017.
 */

public class SessionManager {
    // Shared Preferences
    public SharedPreferences pref;

    // Editor for shared preferences
    public SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "Installed";

    // All shared preferences key
    public static final String IS_LOGIN = "IsLoggedIn";

    // User id (The variable could be accessed publicly)
    public static final String ID = "id";

    // User name (The variable could be accessed publicly)
    public static final String KEY_NAME = "name";

    // Email address (The variable could be accessed publicly)
    public static final String KEY_EMAIL = "email";

    // Gson object
    public static final String JSON = "json";

    // Gson object
    public static final String JSON_TEMP = "jsontemp";

    // View Name
    public static final String VIEWNAME = "ViewName";

    // Random Puzzle ID
    public static final String RandomPuzzle = "puzzle id";

    // Puzzle resolved
    public static final String ResolvedPuzzle = "resolved puzzle";

    // Puzzle added to flipcard
    public static final String AddedPuzzleToFlipcard = "puzzle added to flipcard";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Create login session
     * */
    public void creatLoginSession (int id, Boolean isLoggedIn,String name, String email, String user){

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing id in pref
        editor.putInt(ID, id);
        // Put ViewName
        editor.putString(VIEWNAME, "Welcome");
        // Put Puzzle ID
        editor.putInt(RandomPuzzle, 0);
        // Resolved Puzzle
        editor.putBoolean(ResolvedPuzzle, false);
        // Puzzle added to flipcard
        editor.putBoolean(AddedPuzzleToFlipcard, false);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        // Storing Gson object in pref
        editor.putString(JSON, user);
        // commit changes
        editor.apply();


    }

    /**
     * Edit Login session
     * */
    public void editLoginSession (Boolean isLoggedIn){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        // commit changes
        editor.commit();
    }

    /**
     * Edit View Name session
     * */
    public void editViewNameSession (String viewName){
        // Storing login value as TRUE
        editor.putString(VIEWNAME, viewName);
        // commit changes
        editor.commit();
    }

    /**
     * Edit Puzzle ID session
     * */
    public void editPuzzleIDSession (int puzzleID){
        // Storing login value as TRUE
        editor.putInt(RandomPuzzle, puzzleID);
        // commit changes
        editor.commit();
    }

    /**
     * Edit Resolved Puzzle session
     * */
    public void editResolvedPuzzle (boolean resolvedPuzzle){
        // Storing login value as TRUE
        editor.putBoolean(ResolvedPuzzle, resolvedPuzzle);
        // commit changes
        editor.commit();
    }

    /**
     * Edit Resolved Puzzle session
     * */
    public void editAddedPuzzleToFlipcard (boolean addedPuzzleToFlipcard){
        // Storing login value as TRUE
        editor.putBoolean(ResolvedPuzzle, addedPuzzleToFlipcard);
        // commit changes
        editor.commit();
    }


    /**
     * Edit Name session
     * */
    public void editNameSession (String name){
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing email in pref
        // commit changes
        editor.commit();
    }

    /**
     * Edit Email session
     * */
    public void editEmailSession (String email){
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        // commit changes
        editor.commit();
    }

    /**
     * Edit User session
     * */
    public void editUserSession (String user){
        // Storing Gson object in pref
        editor.putString(JSON, user);
        // commit changes
        editor.commit();
    }


    /**
     * Edit User session temp
     * */
    public void editUserSessionTemp (String userTemp){
        // Storing Gson object in pref
        editor.putString(JSON_TEMP, userTemp);
        // commit changes
        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public Boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent intent = new Intent(context, SignIn.class);

            //Close all activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //Add new flag to start new activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(intent);
        }

        return true;
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Loing Activity
        Intent intent = new Intent(context, SignIn.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(intent);
    }
}
