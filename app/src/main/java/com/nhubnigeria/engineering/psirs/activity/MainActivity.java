package com.nhubnigeria.engineering.psirs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhubnigeria.engineering.psirs.R;
import com.nhubnigeria.engineering.psirs.fragment.ProfileFragment;
import com.nhubnigeria.engineering.psirs.fragment.LogoutFragment;
import com.nhubnigeria.engineering.psirs.utils.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // index to identify current navigation menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_LOGOUT = "logout";
    private static final String PREFS_NAME = "LoginPrefs";
    private static String CURRENT_TAG = TAG_PROFILE;
    private View navHeader;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView navHeader_Img;
    SessionManager session;

    // toolabr titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private Boolean shouldLoadProfileFragmentOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        navHeader_Img = (ImageView) navHeader.findViewById(R.id.navHeaderImg);
        LinearLayout container = (LinearLayout) navHeader.findViewById(R.id.view_container);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
        // Get user data from session
        HashMap<String, String> user = session.getUserDetails();

        //Phone number
        String phone = user.get(SessionManager.KEY_PHONE);
        // name
        String name = user.get(SessionManager.KEY_NAME);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        // initializing navigation menu
        setupNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PROFILE;
            loadProfileFragment();
        }

    }

    /**
     * Returns respected fragment that user
     * selected from the navigation menu
     */
    public void loadProfileFragment() {
        // Select appropriate navigation menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if the user select the current navigation menu again, don't
        // do anything, just close the nav drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }
        // Loading fragments with cross-fade effect
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getProfileFragment();
                FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
                fragTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
                fragTransaction.commitAllowingStateLoss();
            }
        };
        // if mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        // Closing drawer on item click
        drawer.closeDrawers();

        invalidateOptionsMenu();
    }
    private Fragment getProfileFragment() {
        switch (navItemIndex) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            default:
                return new ProfileFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setupNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the click of the navigation menu
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Check to see which item was clicked nd perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_profile:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

                    case R.id.nav_logout:
                        session.logoutUser();

                    default:
                        navItemIndex = 0;
                }
                // Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadProfileFragment();

                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code will be triggered once the drawer closes
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code will be triggered once the drawer opens
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(toggle);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadProfileFragmentOnBackPress) {
            // Checking if user is on other navigation menu rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_PROFILE;
                loadProfileFragment();
                return;
            }
        }
        super.onBackPressed();
    }

}
