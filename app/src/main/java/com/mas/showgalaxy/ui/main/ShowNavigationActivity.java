package com.mas.showgalaxy.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.navigation.AboutFragment;
import com.mas.showgalaxy.ui.navigation.SearchFragment;
import com.mas.showgalaxy.ui.navigation.ShowCategoryFragment;

import java.util.Hashtable;


public class ShowNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Hashtable<Integer, Fragment> mFragmentTable = new Hashtable<Integer, Fragment>();
    private int mCurrentFragmentId = -1;
    private Fragment mCurFragment = null;
    private NavigationView mNavigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        initFragment();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        if(isNeedShowSearchView()) {
            searchItem.setVisible(true);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            setSearchViewListener(searchView);
        }else
            searchItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switchFragment(itemId);
        setActionBar(itemId);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setSearchViewListener(final SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((SearchFragment)mCurFragment).startSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() == 0)
                    ((SearchFragment)mCurFragment).clearSearchResults();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener(){

            @Override
            public boolean onClose() {
                ((SearchFragment)mCurFragment).clearSearchResults();
                return false;
            }
        });
    }

    private void initFragment(){
        int initFragmentId = getInitFragementId();
        setMenuItemChecked(initFragmentId);
        switchFragment(initFragmentId);
        setActionBar(initFragmentId);
    }

    private int getInitFragementId(){
        return R.id.mn_movie;
    }

    private boolean isNeedShowSearchView(){
        return mCurrentFragmentId == R.id.mn_search;
    }

    /**
     * Responses to navigation click event, switch to different fragment as required
     *
     */
    private void switchFragment(int fragmentIdToShow) {
        mCurrentFragmentId = fragmentIdToShow;
        invalidateOptionsMenu();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment fragment = mFragmentTable.get(fragmentIdToShow);
        if(fragment == null) {
            fragment = createFragment(fragmentIdToShow);
            mFragmentTable.put(fragmentIdToShow, fragment);
        }

        // If fragment not change, no need to transaction
        if(fragment == mCurFragment)
            return;

        if (mCurFragment != null)
            transaction.hide(mCurFragment);

        if (fragment.isAdded()) {
            transaction.show(fragment);
        }else {
            transaction.add(R.id.ll_nav_fragment, fragment);
        }
        mCurFragment = fragment;

        transaction.commit();
    }

    private Fragment createFragment(int fragmentId){
        Fragment fragment = null;
        switch(fragmentId){
            case R.id.mn_movie:
                fragment = ShowCategoryFragment.newInstance(DataQuery.MOVIE);
                break;
            case R.id.mn_tvshow:
                fragment = ShowCategoryFragment.newInstance(DataQuery.TV_SHOW);
                break;
            case R.id.mn_search:
                fragment = new SearchFragment();
                break;
            case R.id.mn_about:
                fragment = new AboutFragment();
                break;
        }
        return fragment;
    }

    private void setMenuItemChecked(int fragmentId){
        int menuItemId = 0;
        switch (fragmentId){
            case R.id.mn_movie:
                menuItemId = 0;
                break;
            case R.id.mn_tvshow:
                menuItemId = 1;
                break;
            case R.id.mn_search:
                menuItemId = 2;
                break;
            case R.id.mn_about:
                menuItemId = 3;
                break;
        }
        mNavigationView.getMenu().getItem(menuItemId).setChecked(true);
    }

    private void setActionBar(int fragmentId){
        switch (fragmentId){
            case R.id.mn_movie:
                getSupportActionBar().setTitle(R.string.menu_movie);
                break;
            case R.id.mn_tvshow:
                getSupportActionBar().setTitle(R.string.menu_tvshow);
                break;
            case R.id.mn_search:
                getSupportActionBar().setTitle(R.string.menu_search);
                break;
            case R.id.mn_about:
                getSupportActionBar().setTitle(R.string.menu_about);
                break;
        }
    }
}
