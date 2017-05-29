package com.example.dkdk6.toktokplay.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dkdk6.toktokplay.R;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2017-05-29.
 */

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    public static Uri playerUri;
    public static int PAUSE_CHECKING_FLAG=0;
    public static int OVER_CHECKING_FLAG = 0; //음악 백그라운드 중복재생 방지
    private ArrayList<MusicDto> list;
    public static MediaPlayer playerInMediaPlayer;
    private TextView title;
    private ImageView album, previous, play, pause, next;
    private SeekBar seekBar;
    boolean isPlaying = true;
    private ContentResolver res;
    private ProgressUpdate progressUpdate;
    private int position;
    private Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Intent intent = getIntent();
        playerInMediaPlayer = new MediaPlayer();
        intent2 = new Intent(
                getApplicationContext(),//현재제어권자
                MusicService.class); // 이동할 컴포넌트
        title = (TextView) findViewById(R.id.title);
        album = (ImageView) findViewById(R.id.album);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        position = intent.getIntExtra("position", 0);
        list = (ArrayList<MusicDto>) intent.getSerializableExtra("playlist");
        res = getContentResolver();
        previous = (ImageView) findViewById(R.id.pre);
        play = (ImageView) findViewById(R.id.play);
        pause = (ImageView) findViewById(R.id.pause);
        next = (ImageView) findViewById(R.id.next);
        previous.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        playMusic(list.get(position));//일단 position실행
        progressUpdate = new ProgressUpdate();
        progressUpdate.start();
/*        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//Service단에 요청
                playerInMediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playerInMediaPlayer.seekTo(seekBar.getProgress());
                if (seekBar.getProgress() > 0 && play.getVisibility() == View.GONE) {
                    playerInMediaPlayer.start();
                }
            }
        });

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

    public void playMusic(MusicDto musicDto) {
        stopService(intent2);
        try {
            seekBar.setProgress(0);
            title.setText(musicDto.getArtist() + " - " + musicDto.getTitle());
            playerUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicDto.getId());
            //Start Music//
            // mediaPlayer.start();
            seekBar.setMax(playerInMediaPlayer.getDuration());
            if (playerInMediaPlayer.isPlaying()) {
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
            } else {
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            }
            Bitmap bitmap = BitmapFactory.decodeFile(getCoverArtPath(Long.parseLong(musicDto.getAlbumId()), getApplication()));
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
            case R.id.play:
                pause.setVisibility(View.VISIBLE);
                play.setVisibility(View.GONE);
                //Service구현//
                //일시 정지 후 시작하는 경우임
                stopService(intent2);

                //  mediaPlayer.start();

                break;
            case R.id.pause:
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                startService(intent2);
                //playerInMediaPlayer.pause();
                break;
            case R.id.pre:
                if (position - 1 >= 0) {
                    position--;
                    playMusic(list.get(position));
                    seekBar.setProgress(0);
                }
                break;
            case R.id.next:
                if (position + 1 < list.size()) {
                    position++;
                    playMusic(list.get(position));
                    seekBar.setProgress(0);
                }

                break;
        }
    }


    class ProgressUpdate extends Thread {
        @Override
        public void run() {
            while (isPlaying) {
                try {
                    Thread.sleep(500);
                    if (playerInMediaPlayer != null) {
                        seekBar.setProgress(playerInMediaPlayer.getCurrentPosition());
                    }
                } catch (Exception e) {
                    Log.e("ProgressUpdate", e.getMessage());
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        if (playerInMediaPlayer != null) {
            playerInMediaPlayer.release();
            playerInMediaPlayer = null;
        }
    }
}
