package com.example.dkdk6.toktokplay.Service_Receiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.dkdk6.toktokplay.Activity.MusicPlayerActivity;
import com.example.dkdk6.toktokplay.Activity.StartingActivity;
import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

/**
 * Created by dkdk6 on 2017-05-29.
 */

public class MusicService extends Service {
    MediaPlayer musicObject; // 음악 재생을 위한 객체
    public static int SERVICE_PAUSE_FLAG = 0;

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
//리스너에서 끝났다는 표시가 오면 서비스에서 엑티비티에 다시 신호를 줌 -> 다시 서비스를 진행한다.
        return null;
    }

    @Override
    public void onCreate() {  // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        super.onCreate();
        Log.i("영운","몰라1");
        Log.d("test", "서비스의 onCreate");
        if (musicObject == null) {
            musicObject = new MediaPlayer();
        }
    }

    public void onPause() {
        //일시정지의 가능성이 있는 상태
        Log.i("Service", "onPause");
        Log.i("영운","몰라10");
        if (FlagControl.MUSIC_PAUSE==1) { //SERVICE_PAUSE_FLAG는 Service에서 제어하기 위한
            Log.i("Service", "MUSIC_PAUSE해재");
            FlagControl.MUSIC_PLAYING_NOW=1;
            StartingActivity.startPauseBtn.setImageResource(R.drawable.starting_stop);
            musicObject.seekTo(musicObject.getCurrentPosition());
            musicObject.start();
        } else if (FlagControl.MUSIC_PLAYING_NOW==1&&FlagControl.MUSIC_PAUSE==0) {
            Log.i("Service", "MUSIC_PAUSE 걸림");
            StartingActivity.startPauseBtn.setImageResource(R.drawable.startactivity_background_top);
            musicObject.pause();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            if (FlagControl.MUSIC_PLAYING_NOW==0) { //일시정지상태가 아니다
                Log.i("Service", "onStartCommand, MUSIC_PLAY_NOW");
                Log.i("영운","몰라20");
                FlagControl.MUSIC_PLAYING_NOW=1; //현재 노래 재생중임을 알린다.
                musicObject = MediaPlayer.create(this, MusicPlayerActivity.playerUri);
                musicObject.start();
                if(FlagControl.FAKE==1) {
                    FlagControl.FAKE=0;
                    StartingActivity.startPauseBtn.setImageResource(R.drawable.startactivity_background_top);
                    onDestroy();
                }
            } else if (FlagControl.MUSIC_PLAYING_NOW==1) { //일시정지가 걸린 상태다
                Log.i("Service", "onStartCommand, MUSIC_PLAY_NOW==1");
                onPause();
            }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        StartingActivity.startPauseBtn.setImageResource(R.drawable.startactivity_background_top);
        FlagControl.MUSIC_PLAYING_NOW=0; //현재 노래 정지
        musicObject.stop(); // 음악 종료
        Log.d("test", "서비스의 onDestroy");
    }

}
