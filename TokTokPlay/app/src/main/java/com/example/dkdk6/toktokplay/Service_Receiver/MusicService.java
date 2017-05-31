package com.example.dkdk6.toktokplay.Service_Receiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.dkdk6.toktokplay.Activity.MusicPlayerActivity;
import com.example.dkdk6.toktokplay.Activity.StartingActivity;

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
        Log.d("test", "서비스의 onCreate");
        if (musicObject == null) {
            musicObject = new MediaPlayer();
        }
    }

    public void onPause() {
        //일시정지의 가능성이 있는 상태
        Log.i("Pause", "들어옴");
        if (StartingActivity.flagControl.MUSIC_PAUSE==1) { //SERVICE_PAUSE_FLAG는 Service에서 제어하기 위한
            Log.i("Pause", "노래 시작상태");
            musicObject.seekTo(musicObject.getCurrentPosition());
            musicObject.start();
            //노래재생
        } else if (StartingActivity.flagControl.MUSIC_PLAYING_NOW==1||StartingActivity.flagControl.MUSIC_PAUSE==0) {
            Log.i("Pause", "노래 정지");
            StartingActivity.flagControl.MUSIC_PLAYING_NOW=1; //현재 노래 재생중임을 알린다.
            musicObject.pause();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            if (StartingActivity.flagControl.MUSIC_PLAYING_NOW==0) { //일시정지상태가 아니다
                Log.i("StartCommand", "" + MusicPlayerActivity.playerUri);
                musicObject = MediaPlayer.create(this, MusicPlayerActivity.playerUri);
                musicObject.start();
                StartingActivity.flagControl.MUSIC_PLAYING_NOW=1; //현재 노래 재생중임을 알린다.
            } else if (StartingActivity.flagControl.MUSIC_PLAYING_NOW==1/*||MusicPlayerActivity.PAUSE_CHECKING_FLAG == 1*/) { //일시정지가 걸린 상태다
                Log.i("StartCommand->onPause", "" + MusicPlayerActivity.playerUri);
                onPause();
            }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        StartingActivity.flagControl.MUSIC_PLAYING_NOW=0; //현재 노래 정지
        musicObject.stop(); // 음악 종료
        Log.d("test", "서비스의 onDestroy");
    }

}
