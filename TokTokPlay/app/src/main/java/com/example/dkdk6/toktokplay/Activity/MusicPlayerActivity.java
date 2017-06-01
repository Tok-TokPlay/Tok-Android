package com.example.dkdk6.toktokplay.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;
import com.example.dkdk6.toktokplay.Service_Receiver.MusicService;

import java.util.ArrayList;

import static com.example.dkdk6.toktokplay.Activity.MusicListActivity.intent2;

/**
 * Created by dkdk6 on 2017-05-29.
 */

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    public static Uri playerUri;
    public static int PAUSE_CHECKING_FLAG = 0;
    private ArrayList<MusicDto> list;
    private TextView title;
    private ImageView album, previous, play, pause, next;
    private int play_flag = 0;
    private ContentResolver res;
    public static int position; //        stopService(intent2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Intent intent = getIntent();
        /*
        초기화 할 부분
         */
        title = (TextView) findViewById(R.id.title);
        album = (ImageView) findViewById(R.id.album);
        position = intent.getIntExtra("position", 0);
        list = (ArrayList<MusicDto>) intent.getSerializableExtra("playlist");
        res = getContentResolver();
        previous = (ImageView) findViewById(R.id.pre);
        play = (ImageView) findViewById(R.id.start_music);
        next = (ImageView) findViewById(R.id.next);
        play_flag = 0;
        previous.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        if(FlagControl.NOTIFICATION_OPEN==1&&FlagControl.MUSIC_PLAYING_NOW==1){
            album.setImageBitmap(FlagControl.nowalbum);
            title.setText(FlagControl.nowTitle);
        }
        playingMethods();
      /* 다음 곡 자동재생
       playerInMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (position + 1 < list.size()) {
                    position++;
                    playMusic(list.get(position));
                }
            }
        });*/
    }

    public void playingMethods() {
        if (FlagControl.ON_PLAY_LIST == 1 && FlagControl.MUSIC_PLAYING_NOW == 0) {
            Log.i("Testing:리스트", "음악재생안되고있었음");
            playMusic(list.get(position));//일단 position실행
            FlagControl.ON_PLAY_LIST = 0;
        } else if (FlagControl.ON_PLAY_LIST == 1 && FlagControl.MUSIC_PLAYING_NOW == 1) {
            Log.i("Testing:리스트", "음악재생되고있었음");
            stopService(intent2);
            playMusic(list.get(position));//일단 position실행
            FlagControl.ON_PLAY_LIST = 0;
        }
        if (FlagControl.APP_SEARCHING_CONTROL == 0 && FlagControl.MUSIC_PLAYING_NOW == 0) {
            Log.i("Testing:MUSIC_NOW", "현재음악안재생중");
            playMusic(list.get(position));//일단 position실행
            FlagControl.APP_SEARCHING_CONTROL = 0;
        } else if (FlagControl.APP_SEARCHING_CONTROL == 0 && FlagControl.MUSIC_PLAYING_NOW == 1) {
            Log.i("Testing:MUSIC_NOW", "현재음악재생중");
            stopService(intent2);
            playMusic(list.get(position));//일단 position실행
            FlagControl.APP_SEARCHING_CONTROL = 0;
        }
    }

    public void playMusic(MusicDto musicDto) {
        try {
            Log.i("Player",":::playMusic");
            //  seekBar.setProgress(0);
            title.setText(musicDto.getArtist() + " - " + musicDto.getTitle());
            String temp = musicDto.getArtist() + " - " + musicDto.getTitle();
            playerUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicDto.getId());
           /* MusicService.SERVICE_PAUSE_FLAG = 0;
            this.PAUSE_CHECKING_FLAG = 0;*/
            Bitmap bitmap = BitmapFactory.decodeFile(getCoverArtPath(Long.parseLong(musicDto.getAlbumId()), getApplication()));
            FlagControl.nowalbum=bitmap;
            FlagControl.nowTitle= temp;
            album.setImageBitmap(bitmap);
            startService(intent2); // 서비스 시작
        } catch (Exception e) {
            Log.e("SimplePlayer", e.getMessage());
        }
    }
    //앨범이 저장되어 있는 경로를 리턴합니다.
    private static String getCoverArtPath(long albumId, Context context) {
        Cursor albumCursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_music:
                if (play_flag == 0) { //곡이 재생되는 경우 -> 즉 일시정지가 수행되어야 하는 경우임
                    play.setImageResource(R.drawable.play);
                    //일시정지 플래그 걸고
                    //MusicService.SERVICE_PAUSE_FLAG = 0;
                    FlagControl.MUSIC_PAUSE = 0;
                    startService(intent2);
                    play_flag = 1;
                } else if (play_flag == 1) {
                    play.setImageResource(R.drawable.pause);
                    //MusicService.SERVICE_PAUSE_FLAG = 1;
                    FlagControl.MUSIC_PAUSE=1;
                    startService(intent2);
                    play_flag = 0;
                }
                break;
            case R.id.pre:
                MusicService.SERVICE_PAUSE_FLAG = 0;/*
                this.PAUSE_CHECKING_FLAG = 0;*/
                stopService(intent2);
                if (position - 1 >= 0) {
                    position--;
                    playMusic(list.get(position));
                }
                break;
            case R.id.next:
                MusicService.SERVICE_PAUSE_FLAG = 0;
       /*         this.PAUSE_CHECKING_FLAG = 0;*/
                stopService(intent2);
                if (position + 1 < list.size()) {
                    position++;
                    playMusic(list.get(position));
                }
                break;
        }
    }

}
