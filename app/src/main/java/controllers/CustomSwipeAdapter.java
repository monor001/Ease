package controllers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * Created by asus on 28.02.2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.flipcard, container, false);
        Button button = (Button) item_view.findViewById(R.id.btn_front);
        container.addView(item_view);

        return item_view;*/
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
