package me.b0ne.android.hatebuview.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.adapters.DrawerMenuListAdapter;
import me.b0ne.android.hatebuview.fragments.BookmarkListFragment;
import me.b0ne.android.hatebuview.fragments.MainContentFragment;
import me.b0ne.android.hatebuview.models.AppData;
import me.b0ne.android.hatebuview.models.DrawerMenuItem;
import me.b0ne.android.hatebuview.models.Util;

public class MainActivity extends ActionBarActivity {
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerMenuListAdapter mDrawerListAdapter;
    private Button appSettingBtn;
    private int mDrawerNow = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        Uri uri = intent.getData();

//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeButtonEnabled(true);

        appSettingBtn = (Button)findViewById(R.id.app_setting);

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

        appSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), AppPreferenceActivity.class);
                startActivity(intent);
            }
        });

        // アプリ起動時の表示
        if (savedInstanceState == null) {
            selectItem(Util.getStartPageType(this));
        } else {
            mDrawerNow = savedInstanceState.getInt(Util.KEY_DRAWER_POSITION,
                    Util.getStartPageType(this));
            setActionBarProcess(mDrawerNow);
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Util.KEY_DRAWER_POSITION, mDrawerNow);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState);

        mDrawerNow = savedInstanceState.getInt(Util.KEY_DRAWER_POSITION,
                Util.getStartPageType(this));
    }

    private void selectItem(int position) {

        if (mDrawerNow == position) {
            mDrawerLayout.closeDrawers();
            return;
        }
        mDrawerNow = position;
        mDrawerList.setItemChecked(position, true);
        setActionBarProcess(position);

        if (position > 0) {
            DrawerMenuItem item = mDrawerListAdapter.getItem(position);
            replaceBookmarkFragment(item, null);
        } else {
            MainContentFragment mainContentFragment = new MainContentFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content_frame, mainContentFragment)
                    .commit();
        }

        mDrawerLayout.closeDrawers();
    }

    private void setActionBarProcess(int position) {
        final DrawerMenuItem item = mDrawerListAdapter.getItem(position);
        ActionBar actionBar = getSupportActionBar();
        setActionbarTitleName(item.getName());

        if (position > 2) {
            String[] spinnerItems = new String[]{"[ 人気 ]", "[ 新規 ]"};
            ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(this,
                    R.layout.navigation_mode_list_item, spinnerItems);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            actionBar.setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(int naviPosition, long id) {
                    String type = (naviPosition == 1) ? Util.CATEGORY_TYPE_ENTRYLIST
                            : Util.CATEGORY_TYPE_HOTENTRY;
                    setActionbarTitleName(item.getName());
                    replaceBookmarkFragment(item, type);
                    return true;
                }
            });
        } else {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
    }

    private void setActionbarTitleName(String _title) {
        getSupportActionBar().setTitle(_title);
        getSupportActionBar().setSubtitle(null);
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
        args.putInt(Util.KEY_DRAWER_POSITION, mDrawerNow);
        bookmarkListFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
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
        String viewUrl = AppData.get(this, Util.KEY_NOW_SHOW_WEBVIEW_URL);
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(getApplicationContext(), AppPreferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_to_browser:
                Uri uri = Uri.parse(viewUrl);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.menu_share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, viewUrl);
                startActivity(Intent.createChooser(intent, "アプリを選択"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuToBrowser =  menu.findItem(R.id.menu_to_browser);
        MenuItem menuShare =  menu.findItem(R.id.menu_share);
        View webviewFrame = findViewById(R.id.bk_webview_frame);
        View webviewLayout = findViewById(R.id.webview);
        boolean isDualPaneWebview = webviewFrame != null
                && webviewLayout != null
                && webviewFrame.getVisibility() == View.VISIBLE;
        if (isDualPaneWebview) {
            menuToBrowser.setVisible(true);
            menuShare.setVisible(true);
        } else {
            menuToBrowser.setVisible(false);
            menuShare.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
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
