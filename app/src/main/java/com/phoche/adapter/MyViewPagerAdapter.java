package com.phoche.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyViewPagerAdapter extends PagerAdapter {

    private int[] mDatas;

    public MyViewPagerAdapter(int[] imageResId) {
        this.mDatas = imageResId;

    }

    @Override
    public int getCount() {
        // 给ViewPager设置最大的索引
        // 因为ViewPager每次加载会加载当前,前一页和后一页
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 得到当前的索引
        int pos = position % mDatas.length;
        // 创建ImageView
        ImageView iv = new ImageView(container.getContext());
        // 给view设置图片
        iv.setBackgroundResource(mDatas[pos]);
        // 添加到ViewPager
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }


}