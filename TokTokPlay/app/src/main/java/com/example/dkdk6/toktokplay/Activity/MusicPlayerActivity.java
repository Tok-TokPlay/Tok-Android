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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dkdk6.toktokplay.R;
import com.example.dkdk6.toktokplay.Service_Receiver.MusicService;

import java.util.ArrayList;

import static com.example.dkdk6.toktokplay.Activity.DirectMusicListActivity.intent2;

/**
 * Created by dkdk6 on 2017-05-29.
 */

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    public static Uri playerUri;
    public static int PAUSE_CHECKING_FLAG=0;
    public static int OVER_CHECKING_FLAG = 0; //음악 백그라운드 중복재생 방지
    private ArrayList<MusicDto> list;
    private TextView title;
    private ImageView album, previous, play, pause, next;
    private int play_flag=0;
    private SeekBar seekBar;
    boolean isPlaying = true;
    private ContentResolver res;
    public static int position; //        stopService(intent2);
  //  public static Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Intent intent = getIntent();
     //   intent2 = new Intent(getApplicationContext(),MusicService.class); // 이동할 컴포넌트
        title = (TextView) findViewById(R.id.title);
        album = (ImageView) findViewById(R.id.album);
        position = intent.getIntExtra("position", 0);
        list = (ArrayList<MusicDto>) intent.getSerializableExtra("playlist");
        res = getContentResolver();
        previous = (ImageView) findViewById(R.id.pre);
        play = (ImageView) findViewById(R.id.start_music);
        next = (ImageView) findViewById(R.id.next);
        play_flag=0;
        previous.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        if(this.PAUSE_CHECKING_FLAG==0){
            playMusic(list.get(position));//일단 position실행
        }

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

   */
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

    public void playMusic(MusicDto musicDto) {
        try {
          //  seekBar.setProgress(0);
            title.setText(musicDto.getArtist() + " - " + musicDto.getTitle());
            playerUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + musicDto.getId());
/*            //Start Music//
            // mediaPlayer.start();
            //seekBar.setMax(playerInMediaPlayer.getDuration());
            if (playerInMediaPlayer.isPlaying()) {
               // play.setVisibility(View.GONE);
                //pause.setVisibility(View.VISIBLE);
            } else {
               // play.setVisibility(View.VISIBLE);
               // pause.setVisibility(View.GONE);
            }*/
            MusicService.SERVICE_PAUSE_FLAG=0;
            this.PAUSE_CHECKING_FLAG=0;
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
            case R.id.start_music:
                if(play_flag==0){ //곡이 재생되는 경우 -> 즉 일시정지가 수행되어야 하는 경우임
                    play.setImageResource(R.drawable.play);
                    //일시정지 플래그 걸고
                    MusicService.SERVICE_PAUSE_FLAG=0;
                    startService(intent2);
                    play_flag=1;
                }else if(play_flag==1){
                    play.setImageResource(R.drawable.pause);
                    MusicService.SERVICE_PAUSE_FLAG=1;
                    startService(intent2);
                    play_flag=0;
                }
                //Service구현//
                //일시 정지 후 시작하는 경우임

                //startService(intent2);
                //  mediaPlayer.start();

                break;
            case R.id.pre:
                MusicService.SERVICE_PAUSE_FLAG=0;
                this.PAUSE_CHECKING_FLAG=0;
                stopService(intent2);
                if (position - 1 >= 0) {
                    position--;
                    playMusic(list.get(position));
                }
                break;
            case R.id.next:
                MusicService.SERVICE_PAUSE_FLAG=0;
                this.PAUSE_CHECKING_FLAG=0;
                stopService(intent2);
                if (position + 1 < list.size()) {
                    position++;
                    playMusic(list.get(position));
                }
                break;
        }
    }

}
