package com.example.dkdk6.toktokplay.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.dkdk6.toktokplay.R;

import java.util.logging.Handler;

/**
 * Created by dkdk6 on 2017-05-29.
 */

public class MusicService extends Service {
    MediaPlayer musicObject; // 음악 재생을 위한 객체

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
        Intent intent = new Intent(MusicService.this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intent2 = new Intent(MusicService.this, MusicPlayerActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (StartActivity.SELECT_FLAG_IN_DIRECT == 1) {
          //  musicObject=MusicPlayerActivity.playerInMediaPlayer;
            Log.i("들어오고","있는가");
           // MusicPlayerActivity.playerInMediaPlayer.start();
            musicObject = MediaPlayer.create(this, MusicPlayerActivity.playerUri);
        } else {
            musicObject = MediaPlayer.create(this, MusicActivity.uri);
        }
        musicObject.setLooping(false); // 반복재생
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
       /* Log.d("test", "서비스의 onStartCommand");
        musicObject.start(); // 노래 시작*/
        Intent intent3 = new Intent(MusicService.this, StartActivity.class);
        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intent4 = new Intent(MusicService.this, MusicPlayerActivity.class);
        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (StartActivity.SELECT_FLAG_IN_DIRECT == 1) {
            //  musicObject=MusicPlayerActivity.playerInMediaPlayer;
            Log.i("들어오고2","있는가");
            //MusicPlayerActivity.playerInMediaPlayer.start();
            //musicObject.seekTo(musicObject.getCurrentPosition());
            musicObject.start();
        } else {
            musicObject.start();
        }
     //   musicObject.setLooping(false); // 반복재생
        return super.onStartCommand(intent, flags, startId);
    }

    public void onPause(){
        musicObject.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행

        musicObject.pause(); // 음악 종료
        Log.d("test", "서비스의 onDestroy");
    }

}
