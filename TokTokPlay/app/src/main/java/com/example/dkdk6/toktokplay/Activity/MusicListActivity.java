package com.example.dkdk6.toktokplay.Activity;

import android.Manifest;
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

import com.example.dkdk6.toktokplay.R;

import java.util.ArrayList;


public class MusicListActivity extends AppCompatActivity {
    private ListView listView;
    public static ArrayList<MusicDto> list;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_CODE = 101;
    /*
    현재 쓰지않고있는 Activity이다.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
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
                Intent intent = new Intent(MusicListActivity.this, MusicActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("playlist", list);
                startActivity(intent);
            }
        });
    }

    protected void makeRequest(){
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE:{
                if(grantResults.length==0||grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG,"Permission has been denied by user");
                }else {
                    Log.i(TAG,"Permission has been denied by user2");
                }
                return;
            }
        }
    }

    public void getMusicList() {
        int count=0;
        list = new ArrayList<>();
        String[] searchResultTitle = {"I LOVE YOU", "Let It Go", "서쪽 하늘"};
        String[] searchResultArtist = {"2NE1","Idina Menzel","울랄라 세션"};
        //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        while (cursor.moveToNext()) {
            if(count<searchResultTitle.length){
                if((cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)).equals(searchResultTitle[count]))&&(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)).equals(searchResultArtist[count]))){
                    Log.i("T같은 가수","입니다.");
                    count++;
                    Log.i("같은 가수","입니다.");
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
    }


}
