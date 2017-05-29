package com.example.dkdk6.toktokplay.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.dkdk6.toktokplay.R;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2017-05-09.
 */

public class SearchingActivity extends AppCompatActivity {
    private ImageView imageView;
    private ArrayList<String> beatarray = new ArrayList<String>();
    private boolean touch = false;
    private boolean timerStart = false;
    private CountDownTimer mCountDown = null;
    private int tickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        StartActivity.SELECT_FLAG_IN_DIRECT=0;
        imageView = (ImageView) findViewById(R.id.imageView);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000); //0.3초동안 진동
        ///error
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beatarray.add(new String("1"));
                timerStart = true;
                touch = true;
                receiveBeat();
            }
        });
    }

    protected void receiveBeat() {
        if (timerStart == true) {
            mCountDown = new CountDownTimer(1000, 10) {//1초
                @Override
                public void onTick(long l) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            touch = true;
                        }
                    });
                    if (touch == true) {
                        beatarray.add(new String("1"));
                        touch = false;
                    } else if (touch == false) {
                        beatarray.add(new String("0"));
                    }
                }

                @Override
                public void onFinish() {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100); //0.3초동안 진동
                    timerStart = false;
                    imageView.setEnabled(false);
                    Log.d("beatarry > ", "시간종료");
                    for (int k = 0; k < beatarray.size(); k++) {
                        Log.d("beatarry > ", beatarray.get(k));
                    }
                    sendToServer(beatarray);
                    //searchingActivity로 넘어갈 것
                }
            }.start();

        }
    }

    protected void sendToServer(ArrayList<String> rBeatArray) {
        //서버로 비트 전송//
        Intent PlaymusicIntent = new Intent(SearchingActivity.this, MusicListActivity.class);
        startActivity(PlaymusicIntent);
        finish();
    }
}