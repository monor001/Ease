package views;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.Exclude;
import controllers.Indexes;
import controllers.SessionManager;
import lockscreen.localshop.com.ease.R;
import models.User;

public class ScreenSlidePageFragment extends Fragment {


    boolean isBackVisible = false, isBackVisibleTemp=false;

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    public int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call Session
        /************************ Call session *****************************/
        SessionManager session = new SessionManager(getActivity().getApplicationContext());
        Exclude ex = new Exclude();
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String json_user = session.pref.getString(SessionManager.JSON, "");
        User user = gson.fromJson(json_user, User.class);
        /*********************************************************************/
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.activity_screen_slide_page_fragment, container, false);

        if (mPageNumber<user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().size()){
            // Front Button
            Button btn_front = (Button) rootView.findViewById(R.id.btn_front);
            btn_front.setText(user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().get(mPageNumber).getQuestion());
            btn_front.setTextColor(getResources().getColor(R.color.White));
            // Back Button
            Button btn_back = (Button) rootView.findViewById(R.id.btn_back);
            btn_back.setText(user.getUserCourseslist().get(Indexes.courseIndex).getFlipcards().get(mPageNumber).getAnswer());
            btn_back.setTextColor(getResources().getColor(R.color.White));
        }


        // Set the title view to show the page number.
        /*((TextView) rootView.findViewById(android.R.id.text1)).setText(
                getString(R.string.title_template_step, mPageNumber + 1));*/

        setActionsToFlipBtns(rootView);

        return rootView;
    }



    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    void setActionsToFlipBtns (ViewGroup rootView) {

        // Set Animators
        final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getApplicationContext(),
                R.animator.flip_right_out);

        final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getApplicationContext(),
                R.animator.flip_left_in);

        // Call flip buttons
        final Button btn_front = (Button) rootView.findViewById(R.id.btn_front);
        final Button btn_back = (Button) rootView.findViewById(R.id.btn_back);


        btn_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                if (!isBackVisible) {
                    setRightOut.setTarget(btn_front);
                    setLeftIn.setTarget(btn_back);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = true;

                } else {
                    setRightOut.setTarget(btn_back);
                    setLeftIn.setTarget(btn_front);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = false;
                }
            }
        });

    }
}
