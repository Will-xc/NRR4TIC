package com.example.simplenews;

import android.content.res.ColorStateList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.simplenews.gson.Colltects;
import com.example.simplenews.gson.Data;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BrowseNewsActivity extends AppCompatActivity {

    private DrawerLayout browseDL;
    private WebView webView;
    private FloatingActionButton FAB;
    private int num;
    private String title;
    private String date;
    private String author_name;
    private String pic_url;
    private String content_url;
    private List<Colltects> news;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_news);
        //接收传来的数据
        title = getIntent().getStringExtra("title");
        author_name = getIntent().getStringExtra("author_name");
        date = getIntent().getStringExtra("date");
        pic_url = getIntent().getStringExtra("pic_url");
        content_url = getIntent().getStringExtra("content_url");
        num = getIntent().getIntExtra("num",1);
        //初始化布局
        Toolbar toolbar = (Toolbar) findViewById(R.id.browse_toolbar);
        setSupportActionBar(toolbar);
        browseDL = (DrawerLayout) findViewById(R.id.browse_drawer);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView = (WebView)findViewById(R.id.webView);
        FAB = (FloatingActionButton) findViewById(R.id.float_collection);
        if (num%2==0){
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimary);
            FAB.setBackgroundTintList(colorStateList);
        }
        //收藏
        news = DataSupport.findAll(Colltects.class);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num%2==1){
                    ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimary);
                    FAB.setBackgroundTintList(colorStateList);
                    num++;
                    for (Colltects conew:news){
                        if (title.equals(conew.getTitle())&&author_name.equals(conew.getAuthor_name())){
                            flag = false;
                            Toast.makeText(BrowseNewsActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (flag){
                        Colltects coll = new Colltects(title,author_name,date,content_url);
                        coll.save();
                        Toast.makeText(BrowseNewsActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(),R.color.colorAccent);
                    FAB.setBackgroundTintList(colorStateList);
                    num++;
                    DataSupport.deleteAll(Colltects.class,"title = ? and author_name = ?",title,author_name);
                    Toast.makeText(BrowseNewsActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });
        webView.loadUrl(content_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
