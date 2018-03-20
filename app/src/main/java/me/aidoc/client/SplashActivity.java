package me.aidoc.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.aidoc.client.adapter.MyPagerAdapter;
import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.base.MyApplication;
import me.aidoc.client.entity.resp.CurrentResp;
import me.aidoc.client.mvp.common.CommonModel;
import me.aidoc.client.mvp.mine.login.LoginActivity;
import me.aidoc.client.mvp.main.MainActivity;
import me.aidoc.client.util.DensityUtil;
import me.aidoc.client.util.MyConstants;
import me.aidoc.client.util.SPUtil;
import me.aidoc.client.util.Store;
import me.aidoc.client.util.UserUtil;

public class SplashActivity extends Activity {
    private int[] imgIds = new int[]{R.drawable.splash_1, R.drawable.splash_2,
            R.drawable.splash_3, R.drawable.splash_4};
    ViewPager viewPager;
    private LinearLayout llGuide;
    private View vwDetailsDot;
    private TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        String preferences = (String) SPUtil.get(this, MyConstants.IS_FIRST, "");

        if (TextUtils.isEmpty(preferences)) {
            // 判断是不是第一次启动

            tvNext.setOnClickListener(v -> {
                SPUtil.put(this, MyConstants.IS_FIRST, "NoFirst");
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            });
            //初始化点
            initDots(imgs);

            viewPager.setAdapter(new MyPagerAdapter(imgs));

            initEvent(imgs);
            moveDots(imgs);

            tvNext.setVisibility(View.VISIBLE);
           /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    tvNext.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });*/

        } else {
            // TODO 从服务器获得语言
            CommonModel.getCurrentUserInfo(new HttpUtils.OnResultListener<CurrentResp>() {
                @Override
                public void onSuccess(CurrentResp currentResp) {
                    // TODO
                    if (currentResp != null) {
                        try {
                            if (Store.getLanguage() != currentResp.getLanguage()) {//语言不相同
                                final String[] locals = {"zh_CN", "en"};
                                Store.setLanguageLocal(MyApplication.getContext(), locals[currentResp.getLanguage()]);
                                // TODO 重启
                                Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                // 杀掉进程
                                Process.killProcess(Process.myPid());
                                System.exit(0);
                            } else {
                                new Handler().postDelayed(() -> {
                                    if (UserUtil.getUser() == null) {
                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    } else
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                }, 1000);
                            }
                        } catch (Exception e) {
                            new Handler().postDelayed(() -> {
                                if (UserUtil.getUser() == null) {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                } else
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }, 1000);
                        }
                    }
                }

                @Override
                public void onError(Throwable error, String msg) {
                    new Handler().postDelayed(() -> {
                        if (UserUtil.getUser() == null) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        } else
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }, 1000);
                }
            });
        }
    }

    /**
     * 初始化数据
     */
    List<ImageView> imgs = new ArrayList<ImageView>();

    private void initData() {
//初始化数据
        for (int i = 0; i < imgIds.length; i++) {
            ImageView img = new ImageView(this);
            img.setBackgroundResource(imgIds[i]);
            imgs.add(img);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvNext = findViewById(R.id.tvNext);
        //进入了引导页面加载框
        viewPager = findViewById(R.id.vp_guide);
        //父容器
        llGuide = findViewById(R.id.ll_splash);
        //点
        vwDetailsDot = findViewById(R.id.vw_details_dot);
    }

    /**
     * 初始化小点
     */
    private void initDots(List<ImageView> list) {
        for (int i = 0; i < list.size(); i++) {
            //添加底部灰点
            View v = new View(getApplicationContext());
            v.setBackgroundResource(R.drawable.splash_unfill_dot);
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dp2px(this, 10), DensityUtil.dp2px(this, 10));
            if (i != 0)
                params.leftMargin = 20;
            v.setLayoutParams(params);
            llGuide.addView(v);
        }
    }


    private int diatance;

    /**
     * 测量点之间的距离
     *
     * @param list
     */
    private void initEvent(final List<ImageView> list) {
        /**
         * 当底部红色小圆点加载完成时测出两个小灰点的距离，便于计算后面小红点动态移动的距离
         */
        vwDetailsDot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (list.size() > 1) {
                    diatance = llGuide.getChildAt(1).getLeft() - llGuide.getChildAt(0).getLeft();
                }
            }
        });
    }

    /**
     * 移动小点
     *
     * @param orderGoodsImgs 图片的个数
     */
    private void moveDots(final List<ImageView> orderGoodsImgs) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //测出页面滚动时小红点移动的距离，并通过setLayoutParams(params)不断更新其位置
                position = position % orderGoodsImgs.size();
                float leftMargin = diatance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwDetailsDot.getLayoutParams();
                params.leftMargin = Math.round(leftMargin);
                vwDetailsDot.setLayoutParams(params);
                Log.d("红点在这", leftMargin + "");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
