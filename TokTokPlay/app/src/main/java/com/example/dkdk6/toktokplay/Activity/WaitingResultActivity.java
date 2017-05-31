package com.example.dkdk6.toktokplay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dkdk6.toktokplay.R;

/**
 * Created by dkdk6 on 2017-05-30.
 */
/*
임의로 만들어놨어요 서버에서 결과 받아오는데 오래걸리면 이 페이지를 사용하도록 하세요~
 */
public class WaitingResultActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_waiting_layout);
        if(StartingActivity.flagControl.APP_SEARCHING_CONTROL==0){
            //APP 검색 한 경우
            Intent PlaymusicIntent = new Intent(WaitingResultActivity.this, youtubeTesting.class);
            startActivity(PlaymusicIntent);
            finish();
        }else if(StartingActivity.flagControl.APP_SEARCHING_CONTROL==1){
            //잠금화면에서 검색을 한 경우
            Intent PlaymusicIntent = new Intent(WaitingResultActivity.this, MusicListActivity.class);
            startActivity(PlaymusicIntent);
            finish();
        }

    }
}
