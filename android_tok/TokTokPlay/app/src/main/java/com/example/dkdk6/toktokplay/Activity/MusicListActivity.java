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
    public ArrayList<MusicDto> list;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_CODE = 101;
    private ArrayList<String> serverTitle = new ArrayList<String>();
    private ArrayList<String> serverArtist = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        intent2 = new Intent(getApplicationContext(), MusicService.class);
        Log.i("Testing2:Starting", "g");
        Intent rintent2 = getIntent();
        int check = rintent2.getIntExtra("Starting",0);
        if(check==10){
            fakeStop();
        }
        if (FlagControl.APP_SEARCHING_CONTROL == 0) { //검색으로 온 경우만 이 과정 진행해야 한다.
            Intent rintent = getIntent();
            serverTitle = rintent.getStringArrayListExtra("RKey_T");
            serverArtist = rintent.getStringArrayListExtra("RKey_A");
/*            receiveTitle = rintent.getStringExtra("RKey_T");
            receiveArtist = rintent.getStringExtra("RKey_A");*/
            Log.i("진희가테스트하라고시킨내용", ";"+""+serverTitle.size());
        }
        intent2 = new Intent(getApplicationContext(), MusicService.class);
        if (FlagControl.APP_SEARCHING_CONTROL == 0) {
            stopService(intent2); //노래를 끈다.
            Log.i("Testing3:Starting", "g");
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
            makeNotification();
            listView = (ListView) findViewById(R.id.listview);
            MyAdapter adapter = new MyAdapter(this, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    stopService(intent2);
                    Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
                    makeNotification();
                    Log.i("Testing7.position", "" + position);
                    intent.putExtra("position", position);
                    Log.i("MusicName",list.get(position).getTitle());
                    intent.putExtra("playlist", list);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (FlagControl.ON_PLAY_LIST == 1 && FlagControl.APP_SEARCHING_CONTROL == -1) { //앱으로 검색해서 List를 보여줘야 하는 경우라면?
            Log.i("Testing4:Starting", "g");
            Log.i("Testing1", "MusicList");
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
                    Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
                    makeNotification();
                    Log.i("Testing7.position", "" + position);
                    intent.putExtra("position", position);
                    Log.i("MusicName",list.get(position).getTitle());
                    intent.putExtra("playlist", list);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    public void fakeStop(){
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
        Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
        intent.putExtra("Fakeposition", 10);
        Log.i("MusicName",list.get(0).getTitle());
        intent.putExtra("playlist", list);
        startActivity(intent);
        finish();
    }
    public void makeNotification() {
        Log.i("Tesing:makeNotification", "->checking..");
        FlagControl.NOTIFICATION_OPEN = 1;
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), StartingActivity.class); //인텐트 생성.
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를없앤다.
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.app_icon).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("TokTok_Music 작동중").setContentText("어플을 다시 실행하려면 누르세요!")
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
        if (FlagControl.APP_SEARCHING_CONTROL == 0 /*&& FlagControl.ON_PLAY_LIST == 0*/) {
            list = new ArrayList<>();
            list.clear();
            //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
            String[] projection = {MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST
            };
            Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);
            while (cursor.moveToNext()) {
                for(int i=0; i<serverTitle.size();i++){
                    if ((cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)).contains(serverTitle.get(i))) && (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)).contains(serverArtist.get(i)))) {
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
        } else {
            Log.i("Testing11","들어옴");
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
    @Override
    public void onStop(){
        super.onStop();
        if(FlagControl.MUSIC_PLAYING_NOW==0){
            StartingActivity.startPauseBtn.setImageResource(R.drawable.startactivity_background_top);
        }else if(FlagControl.MUSIC_PLAYING_NOW==1){
            StartingActivity.startPauseBtn.setImageResource(R.drawable.starting_stop);
        }
    }

}
