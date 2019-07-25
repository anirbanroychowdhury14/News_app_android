package com.haerul.popularnews;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.haerul.popularnews.models.Article;

import io.realm.Realm;
import io.realm.RealmResults;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "Experiment";
    RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private RealmResults<Article> savedRealmResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        Log.i(TAG, "onCreate: before adapter creation" + "Also the recyclerView id is " + mRecyclerView.getId());
        Realm realm = Realm.getDefaultInstance();
        savedRealmResults = realm.where(Article.class).findAll();
       // MyAdapter myAdapter = new MyAdapter(savedRealmResults,this);
        adapter = new MyAdapter(savedRealmResults,this);
        Log.i(TAG, "onCreate: After adapter creation");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.i(TAG, "onCreate: After linear layout manager creation and setting");
        mRecyclerView.setAdapter(adapter);
        Log.i(TAG, "onCreate: After setAdapter");
        initListener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Home) {
            Intent intent2 = new Intent(DisplayActivity.this, MainActivity.class);
            startActivity(intent2);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void initListener(){

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(DisplayActivity.this, NewsDetailActivity.class);

                Article article = savedRealmResults.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                //intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());
                intent.putExtra("description",article.getDescription());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        DisplayActivity.this,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });

    }
}

