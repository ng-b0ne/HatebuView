package me.b0ne.android.hatebuview.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.adapters.DrawerMenuListAdapter;
import me.b0ne.android.hatebuview.fragments.BookmarkListFragment;
import me.b0ne.android.hatebuview.fragments.MainContentFragment;
import me.b0ne.android.hatebuview.models.DrawerMenuItem;
import me.b0ne.android.hatebuview.models.Util;

public class MainActivity extends ActionBarActivity {
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerMenuListAdapter mDrawerListAdapter;

    private static RequestQueue mQueue;

    private String mRssUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerListAdapter = new DrawerMenuListAdapter(this);
        String[] bkNameList = getResources().getStringArray(R.array.hatebu_category_name);
        String[] bkHotentryUrlList = getResources().getStringArray(R.array.hatebu_category_hotentry_rssurl);
        String[] bkEntrylistUrlList = getResources().getStringArray(R.array.hatebu_category_entrylist_rssurl);
        String[] bkCategoryKey = getResources().getStringArray(R.array.hatebu_category_key);
        DrawerMenuItem item;
        for (int i = 0; i < bkNameList.length; i++) {
            item = new DrawerMenuItem();
            item.setName(bkNameList[i]);
            item.setKey(bkCategoryKey[i]);
            item.setHotentryUrl(bkHotentryUrlList[i]);
            item.setEntrylistUrl(bkEntrylistUrlList[i]);
            mDrawerListAdapter.add(item);
        }
        mDrawerList.setAdapter(mDrawerListAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                // getSupportActionBar().setTitle(mTitle);
            }
            public void onDrawerOpened(View drawerView) {
                // getSupportActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(1);
        }

    }

    private void selectItem(final int position) {
        final DrawerMenuItem item = mDrawerListAdapter.getItem(position);

        mDrawerList.setItemChecked(position, true);
        setTitle(item.getName());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(item.getName());

        if (position > 2) {
            String[] spinnerItems = new String[]{"　人気　", "　新規　"};
            ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(this,
                    R.layout.navigation_mode_list_item, spinnerItems);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            actionBar.setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(int naviPosition, long id) {
                    String type = (naviPosition == 1) ? Util.CATEGORY_TYPE_ENTRYLIST
                            : Util.CATEGORY_TYPE_HOTENTRY;
                    replaceBookmarkFragment(item, type);
                    return true;
                }
            });
        } else {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }

        if (position > 0) {
            replaceBookmarkFragment(item, null);
        } else {
            MainContentFragment mainContentFragment = new MainContentFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content_frame, mainContentFragment)
                    .commit();
        }

        mDrawerLayout.closeDrawers();
    }

    private void replaceBookmarkFragment(DrawerMenuItem item, String type) {
        if (type == null || type.equals("")) {
            type = Util.CATEGORY_TYPE_HOTENTRY;
        }
        Bundle args = new Bundle();
        BookmarkListFragment bookmarkListFragment = new BookmarkListFragment();
        if (type.endsWith(Util.CATEGORY_TYPE_HOTENTRY)) {
            args.putString(Util.KEY_BK_RSS_URL, item.getHotentryUrl());
        } else {
            args.putString(Util.KEY_BK_RSS_URL, item.getEntrylistUrl());
        }
        args.putString(Util.KEY_BK_CATEGORY_NAME, item.getName());
        args.putString(Util.KEY_BK_CATEGORY_KEY, item.getKey());
        args.putString(Util.KEY_BK_CATEGORY_TYPE, type);
        bookmarkListFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, bookmarkListFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
