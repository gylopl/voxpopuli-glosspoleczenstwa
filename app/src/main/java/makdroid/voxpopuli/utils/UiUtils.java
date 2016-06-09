package makdroid.voxpopuli.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;

import makdroid.voxpopuli.R;

/**
 * Created by Grzecho on 18.04.2016.
 */
public class UiUtils {

    public static void setUpToolBarWithMenu(Toolbar mToolbar, AppCompatActivity activity) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_menu);
            activity.setSupportActionBar(mToolbar);
        }
    }

    public static void setUpToolBarWithBackNavigation(Toolbar mToolbar, final AppCompatActivity activity) {
        if (mToolbar != null) {
            Drawable upArrow = ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(ContextCompat.getColor(activity.getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
            mToolbar.setNavigationIcon(upArrow);
            activity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
    }

    public static void setSizeOrzelBackground(ImageView orzelBackground, AppCompatActivity activity) {
        orzelBackground.getLayoutParams().width = getBestWidthForNavigationDrawer(activity);
        orzelBackground.getLayoutParams().height = orzelBackground.getLayoutParams().width;
    }

    public static void setSizeOrzelImage(ImageView orzelBackground, AppCompatActivity activity) {
        orzelBackground.getLayoutParams().width = getBestWidthForStartOrzel(activity);
        orzelBackground.getLayoutParams().height = orzelBackground.getLayoutParams().width;
    }

    public static void setWidthNavigationDrawer(AppCompatActivity activity, NavigationView mNavigationView) {
        mNavigationView.getLayoutParams().width = getBestWidthForNavigationDrawer(activity);
    }

    public static int getBestWidthForNavigationDrawer(@NonNull final Context context) {
        final int possibleMinDrawerWidth = getScreenWidth(context) - getActionBarHeight(context);
        final int maxDrawerWidth = context.getResources().getDimensionPixelSize(R.dimen.material_drawer_width);
        return Math.min(possibleMinDrawerWidth, maxDrawerWidth);
    }

    public static int getBestWidthForPieChart(@NonNull final Context context) {
        final int possibleMinDrawerWidth = (int) (getScreenWidth(context) * 0.7);
        return possibleMinDrawerWidth;
    }

    public static int getScreenWidth(@NonNull final Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(@NonNull final Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = getThemeAttributeDimensionSize(context, android.R.attr.actionBarSize);
        if (actionBarHeight == 0) {
            actionBarHeight = context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
        }
        return actionBarHeight;
    }

    public static boolean isLandOrientation(@NonNull final Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static int getBestWidthForStartOrzel(@NonNull final Context context) {
        int widthHeight;
//        if (isLandOrientation(context))
//            widthHeight = Math.round((getScreenWidth(context) - getActionBarHeight(context)) / 2);
//        else
        widthHeight = Math.round((getScreenHeight(context)) / 4);

        return widthHeight;
    }

    /**
     * Returns the size in pixels of an attribute dimension
     *
     * @param context the context to get the resources from
     * @param attr    is the attribute dimension we want to know the size from
     * @return the size in pixels of an attribute dimension
     */
    public static int getThemeAttributeDimensionSize(@NonNull final Context context, final int attr) {
        TypedArray a = null;
        try {
            a = context.getTheme().obtainStyledAttributes(new int[]{attr});
            return a.getDimensionPixelSize(0, 0);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public static boolean isTwoPaneMode(AppCompatActivity activity) {
        return activity.findViewById(R.id.base_detail_container) != null;
    }

    public static void setTwoPaneLayout(AppCompatActivity activity) {
        if (isTwoPaneMode(activity)) {
            View secondPane = activity.findViewById(R.id.base_detail_container);
            secondPane.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                    0, FrameLayout.LayoutParams.MATCH_PARENT, 2.0f);
            secondPane.setLayoutParams(param1);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0, FrameLayout.LayoutParams.MATCH_PARENT, 1.0f);
            View firstPane = activity.findViewById(R.id.base_container);
            firstPane.setLayoutParams(param);
        }
    }

    public static void setOnePaneLayout(AppCompatActivity activity) {
        if (isTwoPaneMode(activity)) {
            View secondPane = activity.findViewById(R.id.base_detail_container);
            secondPane.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT, 1.0f);
            View firstPane = activity.findViewById(R.id.base_container);
            firstPane.setLayoutParams(param);
        }
    }

    public static SimpleDateFormat getIso8601Format() {
        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return iso8601Format;
    }
}
