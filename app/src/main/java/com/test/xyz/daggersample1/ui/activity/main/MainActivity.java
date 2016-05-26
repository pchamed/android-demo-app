package com.test.xyz.daggersample1.ui.activity.main;

import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.ui.fragment.main.MainFragment;
import com.test.xyz.daggersample1.ui.fragment.repolist.RepoListFragment;
import com.test.xyz.daggersample1.ui.navdrawer.FragmentDrawer;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private final int MAIN_FRAG = 0;
    private final int REPO_LIST_FRAG = 1;
    private final int FRAGMENT_COUNT = REPO_LIST_FRAG + 1;
    private final String CURRENT_FRAGMENT = "currentFragment";

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

    private int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        hideAllFragments();

        // display the first navigation drawer view on app launch
        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(CURRENT_FRAGMENT);
        }

        showFragment(currentFragment);
    }

    private void hideAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragments[0] = fragmentManager.findFragmentById(R.id.main_frag);
        fragments[1] = fragmentManager.findFragmentById(R.id.repolist_frag);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.length; ++i) {
            fragmentTransaction.hide(fragments[i]);
        }

        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        /*
        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        showFragment(position);
    }

    private void showFragment(int fragmentIndex) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        String title = "";

        switch (fragmentIndex) {
            case MAIN_FRAG:
                title = getString(R.string.nav_item_main);
                break;
            case REPO_LIST_FRAG:
                title = getString(R.string.nav_item_repo_list);
                break;
            default:
                break;
        }

        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }

        transaction.commit();
        currentFragment = fragmentIndex;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        savedState.putInt(CURRENT_FRAGMENT, currentFragment);
        super.onSaveInstanceState(savedState);
    }
}