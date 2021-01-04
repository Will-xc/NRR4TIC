package com.example.simplenews;

import android.content.Intent;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.simplenews.gson.Colltects;
import com.example.simplenews.util.CollAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Collection extends AppCompatActivity {
    private DrawerLayout collDrawer;
    private List<Colltects> colltectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        //初始化布局
        Toolbar infoToolbar = (Toolbar)findViewById(R.id.coll_toolbar);
        setSupportActionBar(infoToolbar);
        collDrawer = (DrawerLayout) findViewById(R.id.coll_drawer_layout);
        ActionBar infoActionBar = getSupportActionBar();
        if (infoActionBar!=null){
            infoActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //收藏列表
        colltectsList = DataSupport.findAll(Colltects.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CollAdapter adapter = new CollAdapter(Collection.this,colltectsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        colltectsList = DataSupport.findAll(Colltects.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CollAdapter adapter = new CollAdapter(Collection.this,colltectsList);
        recyclerView.setAdapter(adapter);
    }

    //返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
