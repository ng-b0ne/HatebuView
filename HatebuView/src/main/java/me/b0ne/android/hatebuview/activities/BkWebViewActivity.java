package me.b0ne.android.hatebuview.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.fragments.BkWebViewFragment;
import me.b0ne.android.hatebuview.models.Util;

/**
 * Created by bone on 13/09/21.
 */
public class BkWebViewActivity extends ActionBarActivity {

    private String viewUrl;
    private String categoryTypeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bk_webview);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewUrl = getIntent().getStringExtra(Util.BK_WEBVIEW_URL);
        actionBar.setTitle(getIntent().getStringExtra(Util.BK_WEBVIEW_TITLE));
        actionBar.setSubtitle(viewUrl);

        categoryTypeKey = getIntent().getStringExtra(Util.KEY_BK_CATEGORY_KEY);

        BkWebViewFragment webviewFragment = new BkWebViewFragment();
        Bundle args = new Bundle();
        args.putString(Util.BK_WEBVIEW_URL, viewUrl);
        args.putString(Util.KEY_BK_CATEGORY_KEY, categoryTypeKey);
        webviewFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.webview_frame, webviewFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bk_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_to_browser:
                Uri uri = Uri.parse(viewUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.menu_share:
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("text/*");
                send.putExtra(Intent.EXTRA_TEXT, viewUrl);
                startActivity(Intent.createChooser(send, "アプリを選択"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
