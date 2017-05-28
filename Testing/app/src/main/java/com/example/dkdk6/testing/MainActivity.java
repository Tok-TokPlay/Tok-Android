package com.example.dkdk6.testing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    // MediaPlayer mMediaPlayer; // 음악 재생을 위한 객체
    int pos; // 재생 멈춘 시점
    private Button bStart;
    private Button bPause;
    private Button bRestart;
    private Button bStop;
    SeekBar sb; // 음악 재생위치를 나타내는 시크바
    boolean isPlaying = false; // 재생중인지 확인할 변수
    private MediaService mService; //서비스 클래스

    //서비스 커넥션 선언.
    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            MediaService.MediaServiceBinder binder = (MediaService.MediaServiceBinder) service;
            mService.registerCallback(mCallback); //콜백 등록
        }
    };

    //서비스에서 아래의 콜백 함수를 호출하며, 콜백 함수에서는 액티비티에서 처리할 내용 입력
    private MediaService.ICallback mCallback = new MediaService.ICallback() {
        public void recvData() {
        }
    };

    //서비스 시작.
    public void startServiceMethod() {
        Intent Service = new Intent(this, MediaService.class);
        bindService(Service, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
