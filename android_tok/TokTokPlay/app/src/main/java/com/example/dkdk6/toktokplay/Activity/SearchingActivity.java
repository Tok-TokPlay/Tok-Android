package com.example.dkdk6.toktokplay.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.dkdk6.toktokplay.R;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2017-05-09.
 */

public class SearchingActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView imageView;
    private ArrayList<Integer> beatarray = new ArrayList<Integer>();
    private boolean touch = false;
    private boolean timerStart = false;
    private CountDownTimer mCountDown = null;
    private int tickCount = 0;
    private boolean touchFlag=true;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_activity);
        Log.i("터치0","");
        imageView = (ImageView) findViewById(R.id.imageView);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000); //0.3초동안 진동..
        receiveBeat();
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beatarray.add(1);
                receiveBeat();
        }
        });*/
       /* imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("터치1","");
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Log.i("터치2","");
                }
                return true;
            }
        });*/
        Log.i("터치3","");
        ///error
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beatarray.add(1);
                timerStart = true;
                touch = true;
                receiveBeat();
            }
        });*/
        //수정//
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchFlag=true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            touchFlag=false;
        }
        return true;
    }
    protected void receiveBeat() {
        Log.i("Touching","receiveB");
        imageView.setOnTouchListener(this);
        if (timerStart == true) {
            mCountDown = new CountDownTimer(1000, 50) {//1초 1000->10000
                @Override
                public void onTick(long l) {
                    Log.i("Touching","receiveB");
                    imageView.setOnTouchListener(SearchingActivity.this);
                    /*imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            touch = true;
                        }
                    });

                    if (touch == true) {
                        beatarray.add(1);
                        touch = false;
                    } else if (touch == false) {
                        beatarray.add(0);
                    }*/
                    if(touchFlag==true){
                        Log.i("Touching","true");
                        beatarray.add(1);
                    }else if(touchFlag==false){
                        Log.i("Touching","False");
                        beatarray.add(0);
                    }
                }
                @Override
                public void onFinish() {
                    Log.i("Touching","receiveB");
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100); //0.3초동안 진동
                    timerStart = false;
                    imageView.setEnabled(false);
                    Log.d("beatarry > ", "시간종료");
                    for (int k = 0; k < beatarray.size(); k++) {
                        Log.d("beatarry > ", ""+beatarray.get(k));
                    }
                    sendToServer(beatarray);
                    //searchingActivity로 넘어갈 것
                }
            }.start();
        }
    }

    protected void sendToServer(ArrayList<Integer> rBeatArray) {
        //서버로 비트 전송//
        /*
        진희언니 처음 작업 여기서 하면 되요! 다음으로 WatingActivity가서 결과 기다리다가 결과를 받을 액티비티는 MusicListActivity입니다!
         */
        Intent PlaymusicIntent = new Intent(SearchingActivity.this, WaitingResultActivity.class);
        PlaymusicIntent.putIntegerArrayListExtra("arrayList",rBeatArray);
        startActivity(PlaymusicIntent);
        finish();
    }
}
