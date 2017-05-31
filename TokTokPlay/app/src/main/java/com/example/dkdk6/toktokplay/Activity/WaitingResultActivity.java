package com.example.dkdk6.toktokplay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

/**
 * Created by dkdk6 on 2017-05-30.
 */
/*
임의로 만들어놨어요 서버에서 결과 받아오는데 오래걸리면 이 페이지를 사용하도록 하세요~
 */
public class WaitingResultActivity extends AppCompatActivity {
    private ImageView image;
    int set = 0;
    private View view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_waiting_layout);
        image = (ImageView) findViewById(R.id.title_image);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateanim);
        image.setAnimation(animation);
        view = (View)findViewById(R.id.testing_result);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FlagControl.APP_SEARCHING_CONTROL == 0) {
                    //APP 검색 한 경우
                    Intent PlaymusicIntent = new Intent(WaitingResultActivity.this, youtubeTesting.class);
                    startActivity(PlaymusicIntent);
                    finish();
                } else if (FlagControl.APP_SEARCHING_CONTROL == 1) {
                    //잠금화면에서 검색을 한 경우
                    Intent PlaymusicIntent = new Intent(WaitingResultActivity.this, MusicListActivity.class);
                    startActivity(PlaymusicIntent);
                    finish();
                }
            }
        });


    }
}
