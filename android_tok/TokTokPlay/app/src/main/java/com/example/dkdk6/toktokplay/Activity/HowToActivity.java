package com.example.dkdk6.toktokplay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dkdk6.toktokplay.Adapter.LockScreenFragmentPagerAdapter;
import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

/**
 * Created by dkdk6 on 2017-06-01.
 */

public class HowToActivity extends AppCompatActivity {
    ImageView howto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
        howto = (ImageView) findViewById(R.id.howto);
        howto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
