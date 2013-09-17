package me.b0ne.android.hatebuview.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.internal.ac;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.adapters.DrawerMenuListAdapter;
import me.b0ne.android.hatebuview.fragments.MainContentFragment;
import me.b0ne.android.hatebuview.models.DrawerMenuItem;
import me.b0ne.android.hatebuview.models.HateBook;
import me.b0ne.android.hatebuview.models.RssItem;
import me.b0ne.android.hatebuview.models.RssRequest;

public class MainActivity extends ActionBarActivity {
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerMenuListAdapter mDrawerListAdapter;

    private static RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerListAdapter = new DrawerMenuListAdapter(this);
        String[] drawerItemList = getResources().getStringArray(R.array.hatebu_category_name);
        DrawerMenuItem item;
        for (int i = 0; i < drawerItemList.length; i++) {
            item = new DrawerMenuItem();
            item.setName(drawerItemList[i]);
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
            selectItem(0);
        }

        mQueue = Volley.newRequestQueue(this);
        String rssUrl = "http://b.hatena.ne.jp/hotentry?mode=rss";
        HateBook.getRss(this, rssUrl, responseListener);

    }

    private void selectItem(int position) {
        MainContentFragment mainContentFragment = new MainContentFragment();
        DrawerMenuItem item = mDrawerListAdapter.getItem(position);
        Bundle args = new Bundle();
//        args.putString();
//        args.putString();

        mainContentFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, mainContentFragment)
                .commit();

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
                public boolean onNavigationItemSelected(int position, long id) {
                    return true;
                }
            });
        } else {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }

        mDrawerLayout.closeDrawers();
    }

    private Response.Listener<ArrayList<RssItem>> responseListener = new Response.Listener<ArrayList<RssItem>>() {
        @Override
        public void onResponse(ArrayList<RssItem> response) {
            if (response.size() < 1) return;

            for (int i = 0; i<response.size(); i++) {
                RssItem item = response.get(i);
                Log.v("TEST", "= " + item.getTitle());
                Log.v("TEST", "= " + item.getCategory());
                Log.v("TEST", "= " + item.getDate());
                Log.v("TEST", "= " + item.getBookmarkCount());
                Log.v("TEST", "= " + item.getLink());
            }
        }
    };


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
