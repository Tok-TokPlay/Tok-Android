package com.example.dkdk6.toktokplay.Activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;
import com.example.dkdk6.toktokplay.Service_Receiver.MusicService;

import java.util.ArrayList;


public class MusicListActivity extends AppCompatActivity {
    public static Intent intent2;
    private ListView listView;
    public static ArrayList<MusicDto> list;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_CODE = 101;
    private String receiveTitle, receiveArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        Log.i("Testing2:Starting","g");
        if(FlagControl.ON_PLAY_LIST!=1){ //검색으로 온 경우만 이 과정 진행해야 한다.
            Intent rintent = getIntent();
            receiveTitle=rintent.getStringExtra("RKey_T");
            receiveArtist= rintent.getStringExtra("RKey_A");
            Log.i("진희가테스트하라고시킨내용",";"+receiveTitle+"AA"+receiveArtist);
        }

        intent2 = new Intent(getApplicationContext(), MusicService.class);
        if (FlagControl.APP_SEARCHING_CONTROL == 0&&FlagControl.ON_PLAY_LIST==0){
            Log.i("Testing3:Starting","g");
            //검색 결과 바로 재생인 경우
            Log.i("Tesing: DirectPlaying", "Checking");
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest();
            }
            getMusicList(); // 디바이스 안에 있는 mp3 파일 리스트를 조회하여 LIst를 만듭니다.
          /*  if (FlagControl.MUSIC_PLAYING_NOW != 0) {      //현재 음악 정지
                Log.i("현재 음악", "재생 중 이여서 끄고 다시 시작");
                stopService(intent2);
            }*/
            stopService(intent2);
            makeNotification();
            /*검색결과를 받아서 putExtra에서 List목록 중 찾아서 position에 넣어주면되요*/
            Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
            intent.putExtra("position", 0);
            intent.putExtra("playlist", list);
            startActivity(intent);
            finish();
        }else if(FlagControl.ON_PLAY_LIST == 1){ //앱으로 검색해서 List를 보여줘야 하는 경우라면?
            Log.i("Testing4:Starting","g");
            Log.i("Testing1","MusicList");
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest();
            }
            getMusicList(); // 디바이스 안에 있는 mp3 파일 리스트를 조회하여 LIst를 만듭니다.
            listView = (ListView) findViewById(R.id.listview);
            MyAdapter adapter = new MyAdapter(this, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    stopService(intent2);
           /*         MusicPlayerActivity.PAUSE_CHECKING_FLAG = 0;*/
                    Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
                    makeNotification();
                    Log.i("Testing7.position",""+position);
                    intent.putExtra("position", position);
                    intent.putExtra("playlist", list);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    public void makeNotification() {
        Log.i("Tesing:makeNotification", "->checking..");
        FlagControl.NOTIFICATION_OPEN=1;
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MusicPlayerActivity.class); //인텐트 생성.
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를없앤다.
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.app_icon).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("TokTok_Music 작동중").setContentText("음악을 정지하려면 누르세요!")
                .setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
        notificationManager.notify(1, builder.build()); // Notification send
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been denied by user2");
                }
                return;
            }
        }
    }

    public void getMusicList() {
        if (FlagControl.APP_SEARCHING_CONTROL == 0&&FlagControl.ON_PLAY_LIST==0) {
            int count = 0;
            list = new ArrayList<>();
            String[] searchResultTitle = {"I LOVE YOU", "Let It Go", "서쪽 하늘"};
            String[] searchResultArtist = {"2NE1", "Idina Menzel", "울랄라 세션"};
            //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
            String[] projection = {MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST
            };
            Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);
            /*
            진희언니 여기 이 밑에 While문 안에 두번째 if문에서 searchResultTitle이랑 ArtistTitle에 언니가 보내준값이 들어와야되!
             */
            while (cursor.moveToNext()) {
                if (count < searchResultTitle.length) {
                    if ((cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)).equals(receiveTitle)) && (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)).equals(receiveArtist))) {
                       Log.i("AA","여기");
                        count++;
                        MusicDto musicDto = new MusicDto();
                        musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                        musicDto.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                        musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                        musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                        list.add(musicDto);
                    }
                }
            }
            cursor.close();
        } else{
            int count = 0;
            list = new ArrayList<>();
            //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
            String[] projection = {MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST
            };
            Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);
            while (cursor.moveToNext()) {
                MusicDto musicDto = new MusicDto();
                musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                musicDto.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                list.add(musicDto);
            }
            cursor.close();
        }
    }
}
