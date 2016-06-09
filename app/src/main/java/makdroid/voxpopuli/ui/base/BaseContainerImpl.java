package makdroid.voxpopuli.ui.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.ui.fragments.AboutFragment;
import makdroid.voxpopuli.ui.fragments.CardVotingPagerFragment;
import makdroid.voxpopuli.ui.fragments.FragmentEvent;
import makdroid.voxpopuli.ui.fragments.PreferenceFragment;
import makdroid.voxpopuli.utils.FragmentUtils;
import makdroid.voxpopuli.utils.UiUtils;

/**
 * Created by Grzecho on 18.04.2016.
 */
public class BaseContainerImpl implements BaseContainer {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.base_container)
    FrameLayout mBaseContainer;
    @Bind(R.id.orzelBackground)
    ImageView mImageViewOrzelBackground;
    private CircleImageView mUserAvatar;
    private TextView mUserName;
    private TextView mUserMail;
    @Inject
    UserPrefMaster userPrefMaster;
    private int mCurrentMenuId = R.id.nav_item_zaglosuj;

    @Override
    public void bindForActivity(AppCompatActivity activity) {
        initializeDependencyInjector(activity);
        activity.setContentView(R.layout.activity_base);
        ButterKnife.bind(this, activity);
        if (mCurrentMenuId != R.id.nav_item_zaglosuj)
            UiUtils.setOnePaneLayout(activity);
        UiUtils.setUpToolBarWithMenu(mToolbar, activity);
        UiUtils.setSizeOrzelBackground(mImageViewOrzelBackground, activity);
        setUpDrawer(activity, mNavigationView);
        setNavigationHeader();
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public int getmCurrentMenuId() {
        return mCurrentMenuId;
    }

    @Override
    public void setmCurrentMenuId(int mCurrentMenuId) {
        this.mCurrentMenuId = mCurrentMenuId;
    }

    private void initializeDependencyInjector(AppCompatActivity activity) {
        VoxPopuliApplication voxPopuliApplication = (VoxPopuliApplication) activity.getApplication();
        voxPopuliApplication.getComponent().inject(this);
    }

    private void setUpDrawer(AppCompatActivity activity, NavigationView mNavigationView) {
        UiUtils.setWidthNavigationDrawer(activity, mNavigationView);
        setupDrawerContent(activity, mNavigationView);
    }

    private void setNavigationHeader() {
        View headerLayout = mNavigationView.getHeaderView(0);
        mUserAvatar = (CircleImageView) headerLayout.findViewById(R.id.material_drawer_account_header_avatar);
        mUserName = (TextView) headerLayout.findViewById(R.id.material_drawer_account_header_name);
        mUserMail = (TextView) headerLayout.findViewById(R.id.material_drawer_account_header_email);
        setUserDetails();
    }

    private void setUserDetails() {
        if (userPrefMaster.isLoggged()) {
            mUserName.setText(userPrefMaster.getUserName());
            mUserMail.setText(userPrefMaster.getUserMail());
            if (userPrefMaster.getUserAvatarDir() != null) {
                Bitmap myBitmap = BitmapFactory.decodeFile(userPrefMaster.getUserAvatarDir());
                mUserAvatar.setImageBitmap(myBitmap);
            }
        }
    }

    private void setupDrawerContent(final AppCompatActivity activity, NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(activity, menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(AppCompatActivity activity, MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        String tag;
        switch (menuItem.getItemId()) {
            case R.id.nav_item_zaglosuj:
                fragmentClass = CardVotingPagerFragment.class;
                tag = CardVotingPagerFragment.FRAGMENT_TAG;
                UiUtils.setTwoPaneLayout(activity);
                break;
            case R.id.nav_item_wydarzenia:
                fragmentClass = FragmentEvent.class;
                tag = FragmentEvent.FRAGMENT_TAG;
                UiUtils.setOnePaneLayout(activity);
                break;
            case R.id.nav_item_ustawienia:
                fragmentClass = PreferenceFragment.class;
                tag = PreferenceFragment.FRAGMENT_TAG;
                UiUtils.setOnePaneLayout(activity);
                break;
            case R.id.nav_item_informacje:
                fragmentClass = AboutFragment.class;
                tag = AboutFragment.FRAGMENT_TAG;
                UiUtils.setOnePaneLayout(activity);
                break;
            default:
                fragmentClass = AboutFragment.class;
                tag = AboutFragment.FRAGMENT_TAG;
        }
        mCurrentMenuId = menuItem.getItemId();

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentUtils.setFragment(activity, fragment, R.id.base_container, tag);
        activity.setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

}
