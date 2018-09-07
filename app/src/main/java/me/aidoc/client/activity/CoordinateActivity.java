package me.aidoc.client.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.WindowManager;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.lcodecore.twinklingrefreshlayout.adapter.PhotoAdapter;
import com.lcodecore.twinklingrefreshlayout.beans.Photo;

import java.util.ArrayList;
import java.util.List;


public class CoordinateActivity extends AppCompatActivity {
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);

        setupRecyclerView((RecyclerView) findViewById(R.id.recyclerview));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setupRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotoAdapter();
        rv.setAdapter(photoAdapter);

        final TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);
        ProgressLayout header = new ProgressLayout(this);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setHeaderHeight(140);
        refreshLayout.setMaxHeadHeight(240);
        refreshLayout.setTargetView(rv);

        refreshCard();

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshCard();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreCard();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableOverScroll(false);
                } else {
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableOverScroll(false);
                }
            }
        });
    }

    void refreshCard() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("chest nut", R.drawable.photo1));
        photos.add(new Photo("fish", R.drawable.photo2));
        photos.add(new Photo("cat", R.drawable.photo10));
        photos.add(new Photo("guitar", R.drawable.photo3));
        photos.add(new Photo("common-hazel", R.drawable.photo4));
        photos.add(new Photo("cherry", R.drawable.photo5));
        photos.add(new Photo("flower details", R.drawable.photo6));
        photos.add(new Photo("tree", R.drawable.photo7));
        photos.add(new Photo("blue berries", R.drawable.photo8));
        photos.add(new Photo("snow man", R.drawable.photo9));
        photoAdapter.setDataList(photos);
    }

    void loadMoreCard() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("chest nut", R.drawable.photo1));
        photos.add(new Photo("fish", R.drawable.photo2));
        photos.add(new Photo("cat", R.drawable.photo10));
        photos.add(new Photo("guitar", R.drawable.photo3));
        photos.add(new Photo("common-hazel", R.drawable.photo4));
        photos.add(new Photo("cherry", R.drawable.photo5));
        photos.add(new Photo("flower details", R.drawable.photo6));
        photos.add(new Photo("tree", R.drawable.photo7));
        photos.add(new Photo("blue berries", R.drawable.photo8));
        photos.add(new Photo("snow man", R.drawable.photo9));
        photoAdapter.addItems(photos);
    }
}
