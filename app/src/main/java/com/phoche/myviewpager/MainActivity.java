package com.phoche.myviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.phoche.adapter.MyViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener {

    // 使用本地的资源图片
    // 由于没有做优化,在运行的时候会一直,GC也可以用其他加载的图片框架加载
    // 同样可以加载网络图片
    private int[] imageResIds = {R.mipmap.img_nature1
            , R.mipmap.img_nature2
            , R.mipmap.img_nature3
            , R.mipmap.img_nature4
            , R.mipmap.img_nature5};

    private ViewPager    viewPager;
    private LinearLayout ll_point;
    private       int prePosition   = 0;  // 在轮播和拖动的时候用来记录前一个点
    private final int REPEAT_SCROLL = 1000;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            // 接收到轮播的任务,开始轮播
            // 把当前页的索引 +1 就会切换到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            // 等到时间结束后会继续切换
            sendEmptyMessageDelayed(REPEAT_SCROLL, 1200);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initEvent();

        // 初始化ViewPager的索引
        int pos = Integer.MAX_VALUE / 2 % imageResIds.length;
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - pos);

        // 发送消息开始轮播
        mHandler.sendEmptyMessageDelayed(REPEAT_SCROLL, 1200);
    }

    private void initEvent() {
        // 对ViewPager的切换进行监听
        // 主要是用来切换显示索引的点
        viewPager.setOnPageChangeListener(this);
        // 对ViewPager添加触摸监听
        // 用来对轮播控制
        viewPager.setOnTouchListener(this);
    }

    private void initData() {
        viewPager.setAdapter(new MyViewPagerAdapter(imageResIds));

        // 用来添加底部的索引点
        for (int i = 0; i < imageResIds.length; i++) {
            ImageView point = new ImageView(getApplicationContext());
            // 对点设置宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            // 点左边的margin值
            params.leftMargin = i == 0 ? 0 : 6;
            // 对点设置选择器
            point.setBackgroundResource(R.drawable.point_selector);
            // 默认将当前的索引设置为可用
            // 用setEnable()来设置点的状态
            point.setEnabled(i == 0 ? true : false);

            point.setLayoutParams(params);
            ll_point.addView(point);

        }
    }

    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);

    }


    @Override
    public void onPageSelected(int position) {
        // 将前一个点设为不可用
        ll_point.getChildAt(prePosition).setEnabled(false);
        // 得到当前的点,赋值给前一个点
        int pos = position % imageResIds.length;
        prePosition = pos;
        // 将前一个点设为可用
        ll_point.getChildAt(pos).setEnabled(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 当按下时不发送轮播任务
                mHandler.removeCallbacksAndMessages(null);
                break;

            case MotionEvent.ACTION_MOVE:
                // 当按下时不发送轮播任务
                mHandler.removeCallbacksAndMessages(null);
                break;


            case MotionEvent.ACTION_UP:
                // 当手指离开时继续开始轮播任务
                mHandler.sendEmptyMessageDelayed(REPEAT_SCROLL, 3000);
                break;
        }
        return false;
    }
}
