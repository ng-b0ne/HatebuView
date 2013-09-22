package me.b0ne.android.hatebuview.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.fragments.BkWebViewFragment;
import me.b0ne.android.hatebuview.models.Util;

/**
 * Created by bone on 13/09/21.
 */
public class BkWebViewActivity extends ActionBarActivity {

    private String viewUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setSupportProgress(50);
        setSupportProgressBarIndeterminate(true);
        setContentView(R.layout.bk_webview_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewUrl = getIntent().getStringExtra(Util.BK_WEBVIEW_URL);

        BkWebViewFragment webviewFragment = new BkWebViewFragment();
        Bundle args = new Bundle();
        args.putString(Util.BK_WEBVIEW_URL, viewUrl);
        webviewFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
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
                send.setType("text/plain");
                send.putExtra(Intent.EXTRA_TEXT, viewUrl);
                startActivity(Intent.createChooser(send, "アプリを選択"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
