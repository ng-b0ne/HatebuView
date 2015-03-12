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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.fragments.BookmarkListFragment;
import me.b0ne.android.hatebuview.fragments.MainContentFragment;
import me.b0ne.android.hatebuview.models.AppData;
import me.b0ne.android.hatebuview.models.Util;

public class MainActivity extends ActionBarActivity {
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mBkNameList;
    private String[] mBkHotentryUrlList;
    private String[] mBkEntryUrlList;
    private String[] mBkCategoryKey;
    private int mDrawerNow = 200;

    private static final int DRAWER_TYPE_HEADLINE = 0;
    private static final int DRAWER_TYPE_HOTENTRY = 1;
    private static final int DRAWER_TYPE_ENTRYLIST = 2;
    private static final int DRAWER_TYPE_SOCIAL = 3;
    private static final int DRAWER_TYPE_ECONOMICS = 4;
    private static final int DRAWER_TYPE_LIFE = 5;
    private static final int DRAWER_TYPE_IT = 6;
    private static final int DRAWER_TYPE_ENTERME = 7;
    private static final int DRAWER_TYPE_GAME = 8;
    private static final int DRAWER_TYPE_FUN = 9;
    private static final int DRAWER_TYPE_SETTING = 10;

    private LinearLayout mDrawerHeadline;
    private LinearLayout mDrawerHotentry;
    private LinearLayout mDrawerEntrylist;
    private LinearLayout mDrawerSocial;
    private LinearLayout mDrawerEconomics;
    private LinearLayout mDrawerLife;
    private LinearLayout mDrawerIt;
    private LinearLayout mDrawerEnterme;
    private LinearLayout mDrawerGame;
    private LinearLayout mDrawerFun;
    private LinearLayout mDrawerSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mDrawerHeadline = (LinearLayout)findViewById(R.id.drawer_item_headline);
        mDrawerHotentry = (LinearLayout)findViewById(R.id.drawer_item_hotentry);
        mDrawerEntrylist = (LinearLayout)findViewById(R.id.drawer_item_entrylist);
        mDrawerSocial = (LinearLayout)findViewById(R.id.drawer_item_social);
        mDrawerEconomics = (LinearLayout)findViewById(R.id.drawer_item_economics);
        mDrawerLife = (LinearLayout)findViewById(R.id.drawer_item_life);
        mDrawerIt = (LinearLayout)findViewById(R.id.drawer_item_it);
        mDrawerEnterme = (LinearLayout)findViewById(R.id.drawer_item_enterme);
        mDrawerGame = (LinearLayout)findViewById(R.id.drawer_item_game);
        mDrawerFun = (LinearLayout)findViewById(R.id.drawer_item_fun);
        mDrawerSetting = (LinearLayout)findViewById(R.id.drawer_item_setting);

        mBkNameList = getResources().getStringArray(R.array.hatebu_category_name);
        mBkHotentryUrlList = getResources().getStringArray(R.array.hatebu_category_hotentry_rssurl);
        mBkEntryUrlList = getResources().getStringArray(R.array.hatebu_category_entrylist_rssurl);
        mBkCategoryKey = getResources().getStringArray(R.array.hatebu_category_key);

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

        mDrawerHeadline.setOnClickListener(mDrawerOnClick);
        mDrawerHotentry.setOnClickListener(mDrawerOnClick);
        mDrawerEntrylist.setOnClickListener(mDrawerOnClick);
        mDrawerSocial.setOnClickListener(mDrawerOnClick);
        mDrawerEconomics.setOnClickListener(mDrawerOnClick);
        mDrawerLife.setOnClickListener(mDrawerOnClick);
        mDrawerIt.setOnClickListener(mDrawerOnClick);
        mDrawerEnterme.setOnClickListener(mDrawerOnClick);
        mDrawerGame.setOnClickListener(mDrawerOnClick);
        mDrawerFun.setOnClickListener(mDrawerOnClick);
        mDrawerSetting.setOnClickListener(new View.OnClickListener() {
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

    private View.OnClickListener mDrawerOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.drawer_item_headline :
                    selectItem(DRAWER_TYPE_HEADLINE);
                    break;
                case R.id.drawer_item_hotentry :
                    selectItem(DRAWER_TYPE_HOTENTRY);
                    break;
                case R.id.drawer_item_entrylist :
                    selectItem(DRAWER_TYPE_ENTRYLIST);
                    break;
                case R.id.drawer_item_social :
                    selectItem(DRAWER_TYPE_SOCIAL);
                    break;
                case R.id.drawer_item_economics :
                    selectItem(DRAWER_TYPE_ECONOMICS);
                    break;
                case R.id.drawer_item_life :
                    selectItem(DRAWER_TYPE_LIFE);
                    break;
                case R.id.drawer_item_it :
                    selectItem(DRAWER_TYPE_IT);
                    break;
                case R.id.drawer_item_enterme :
                    selectItem(DRAWER_TYPE_ENTERME);
                    break;
                case R.id.drawer_item_game :
                    selectItem(DRAWER_TYPE_GAME);
                    break;
                case R.id.drawer_item_fun :
                    selectItem(DRAWER_TYPE_FUN);
                    break;
            }
        }
    };

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
        setActionBarProcess(position);


        if (position > 0) {
            replaceBookmarkFragment(position, null);
        } else {
            MainContentFragment mainContentFragment = new MainContentFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content_frame, mainContentFragment)
                    .commit();
        }

        mDrawerLayout.closeDrawers();
    }

    private void setActionBarProcess(final int position) {
        ActionBar actionBar = getSupportActionBar();
        setActionbarTitleName(mBkNameList[position]);

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
                    setActionbarTitleName(mBkNameList[position]);
                    replaceBookmarkFragment(position, type);
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

    private void replaceBookmarkFragment(int position, String type) {
        if (type == null || type.equals("")) {
            type = Util.CATEGORY_TYPE_HOTENTRY;
        }

        Bundle args = new Bundle();
        BookmarkListFragment bookmarkListFragment = new BookmarkListFragment();
        if (type.endsWith(Util.CATEGORY_TYPE_HOTENTRY)) {
            args.putString(Util.KEY_BK_RSS_URL, mBkHotentryUrlList[position]);
        } else {
            args.putString(Util.KEY_BK_RSS_URL, mBkEntryUrlList[position]);
        }
        args.putString(Util.KEY_BK_CATEGORY_NAME, mBkNameList[position]);
        args.putString(Util.KEY_BK_CATEGORY_KEY, mBkCategoryKey[position]);
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
