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

public class SearchingActivity extends AppCompatActivity {
    private ImageView imageView;
    private ArrayList<Integer> beatarray = new ArrayList<Integer>();
    private boolean touch = false;
    private boolean timerStart = false, ticFrag=false, stopFlag=false;
    private CountDownTimer mCountDown = null;

    private int tickCount = 0;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_activity);
        Log.i("터치0","");
        imageView = (ImageView) findViewById(R.id.imageView);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000); //0.3초동안 진동..
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Log.i("Touching","now");
                    ticFrag=true;
                    beatarray.add(1);
                    receiveBeat();
                }
                return true;
            }
        });
        Log.i("터치3","");
    }

    protected void receiveBeat() {
        Log.i("Touching","receiveB");
        timerStart=true;
        if (timerStart == true) {
            mCountDown = new CountDownTimer(20000, 50) {//1초 1000->10000
                @Override
                public void onTick(long l) {
                    imageView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction()){
                                case MotionEvent.ACTION_UP:
                                    Log.i("Touching","now3");
                                    ticFrag=false;
                                    break;
                                default:
                                    Log.i("Touching","default");
                                case MotionEvent.ACTION_DOWN:
                                    Log.i("Touching","now2");
                                    ticFrag=true;
                                    break;
                            }
                            return true;
                        }
                    });
                    if(ticFrag==false){
                        beatarray.add(0);
                    }else if(ticFrag==true){
                        beatarray.add(1);
                    }
                }
                @Override
                public void onFinish() {
                    int one =0;
                    int zero =0;
                    if(stopFlag==false){
                        stopFlag=true;
                        Log.i("Touching","receiveB");
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(100); //0.3초동안 진동
                        timerStart = false;
                        imageView.setEnabled(false);
                        Log.d("beatarry > ", "시간종료");
                        for (int k = 0; k < beatarray.size(); k++) {
                            if(beatarray.get(k)==1){
                                one++;
                            }else if(beatarray.get(k)==0){
                                zero++;
                            }
                        }
                        Log.d("beatarry one > ", "" + one);
                        Log.d("beatarry zero > ", "" + zero);
                        Log.i("Touching_beatarray",beatarray.toString());
                        sendToServer(beatarray);
                    }

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
