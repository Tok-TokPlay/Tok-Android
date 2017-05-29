package com.example.dkdk6.toktokplay.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dkdk6.toktokplay.R;
import com.example.dkdk6.toktokplay.Service_Receiver.ScreenService;

public class StartActivity extends AppCompatActivity {
    private Context mContext = this;
    private ImageButton onBtn, offBtn, startMusic, goToSearching;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_CODE = 101;
    public static int SELECT_FLAG_IN_DIRECT=0; //음악 플레이 종류 인식
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
        onBtn= (ImageButton)findViewById(R.id.btn1);
        offBtn= (ImageButton)findViewById(R.id.btn2);
        startMusic = (ImageButton)findViewById(R.id.playMusic_direct);
        goToSearching = (ImageButton)findViewById(R.id.searching_direct);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScreenService.class);
                Toast.makeText(mContext, "TokTokPlay 서비스가 시작됩니다.", Toast.LENGTH_SHORT).show();
                startService(intent);
                finish();
            }
        });
        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), ScreenService.class);
                Toast.makeText(mContext, "TokTokPlay 서비스가 종료됩니다.", Toast.LENGTH_SHORT).show();
                stopService(intent2);
                finish();
            }
        });
        goToSearching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, SearchingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        startMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECT_FLAG_IN_DIRECT=1;
                Intent intent = new Intent(StartActivity.this, DirectMusicListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void makeRequest(){
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE:{
                if(grantResults.length==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG,"Permission has been denied by user");
                }else {
                    Log.i(TAG,"Permission has been denied by user2");
                }
                return;
            }
        }
    }
}
