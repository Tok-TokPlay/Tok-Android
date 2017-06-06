package com.example.dkdk6.toktokplay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

import java.util.ArrayList;

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
    private ArrayList<Integer> beatarray = new ArrayList<Integer>();
    /*    String[] searchResultTitle = {"I LOVE YOU", "Let It Go", "서쪽 하늘"};
        String[] searchResultArtist = {"2NE1", "Idina Menzel", "울랄라 세션"};*/
    String tempResult_T, tempResult_A;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_waiting_layout);
        Intent intent = getIntent();
        beatarray = intent.getIntegerArrayListExtra("arrayList");
        image = (ImageView) findViewById(R.id.title_image);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateanim);
        image.setAnimation(animation);
        //진희테스트용
        ClientConnect cc = new ClientConnect("165.194.17.28", beatarray);//////
        android.os.Handler mHandler2 = new android.os.Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("DB_Testing2_waitAct",FlagControl.musicKey);
                //받은 다음 재상이 디비에 String정보를 다시 받아서 (Static 변수로) Intent Gogo putExtra해주면됨.
                //도경테스트용
                //여기에 가수, 곡명 받아와짐
                tempResult_T = FlagControl.receiveTitle;
                tempResult_A = FlagControl.receiveArtist;
                if (!FlagControl.musicKey.equals(null)) {
                    Log.d("DB_Testing3_waitAct", FlagControl.receiveTitle);
                    Log.d("DB_Testing3_waitAct", FlagControl.receiveArtist);
                    tempResult_T = FlagControl.receiveTitle;
                    tempResult_A = FlagControl.receiveArtist;
                }
                if (FlagControl.APP_SEARCHING_CONTROL == 1) {
                    Log.d("여기",tempResult_A);
                    //APP 검색 한 경우
                    Intent PlaymusicIntent = new Intent(WaitingResultActivity.this, youtubeTesting.class);
                    PlaymusicIntent.putExtra("RKey_T", tempResult_T);
                    PlaymusicIntent.putExtra("RKey_A", tempResult_A);
                    startActivity(PlaymusicIntent);
                    finish();
                } else if (FlagControl.APP_SEARCHING_CONTROL == 0) {
                    Log.i("LogTesting",""+FlagControl.APP_SEARCHING_CONTROL);
                    //잠금화면에서 검색을 한 경우
                    Log.d("RKEY",tempResult_A);
                    Intent PlaymusicIntent = new Intent(WaitingResultActivity.this, MusicListActivity.class);
                    PlaymusicIntent.putExtra("RKey_T", tempResult_T);
                    PlaymusicIntent.putExtra("RKey_A", tempResult_A);
                    startActivity(PlaymusicIntent);
                    finish();
                }
            }
        }, (1 * 1000)); //나중에바꿔
    }
}
