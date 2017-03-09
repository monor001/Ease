package commons;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by asus on 25.02.2017.
 */

public class ConvertFromPixelToDP {

    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }
}
