package com.example.dkdk6.toktokplay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;


import com.example.dkdk6.toktokplay.Adapter.LockScreenFragmentPagerAdapter;
import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

public class LockScreenActivity extends AppCompatActivity  {
    private ViewPager viewPager;
    private LockScreenFragmentPagerAdapter adapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreenactivity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new LockScreenFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if(position == adapter.PosNavigate){
                    Intent intent = new Intent(LockScreenActivity.this,SearchingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
