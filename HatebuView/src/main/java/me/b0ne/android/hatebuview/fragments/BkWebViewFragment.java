package me.b0ne.android.hatebuview.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.models.Util;

/**
 * Created by bone on 13/09/21.
 */
public class BkWebViewFragment extends Fragment {

    private WebView mWebView;
    private ProgressBar loadProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bk_webview, container, false);
        loadProgressBar = (ProgressBar)view.findViewById(R.id.load_progressbar);
        mWebView = (WebView)view.findViewById(R.id.webview);
        WebChromeClient chromeClient = new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && loadProgressBar.getVisibility() == ProgressBar.GONE){
                    loadProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
                loadProgressBar.setProgress(progress);
                if(progress == 100) {
                    loadProgressBar.setVisibility(ProgressBar.GONE);
                }
            }
        };
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(chromeClient);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlString) {
                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                getActivity().setProgressBarIndeterminateVisibility(false);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String viewUrl = getArguments().getString(Util.BK_WEBVIEW_URL);

        mWebView.loadUrl(viewUrl);
    }
}
