package com.example.dkdk6.toktokplay.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;
import com.example.dkdk6.toktokplay.Service_Receiver.ScreenService;

public class StartingActivity extends AppCompatActivity {
    private Context mContext = this;
    private ImageView titleView;
    private ImageButton onBtn, offBtn, startMusic, goToSearching;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_CODE = 101;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
        titleView = (ImageView)findViewById(R.id.title_starting);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartingActivity.this,HowToActivity.class);
                startActivity(intent);
            }
        });
        onBtn = (ImageButton) findViewById(R.id.onBtn);
        offBtn = (ImageButton) findViewById(R.id.offBtn);
        startMusic = (ImageButton) findViewById(R.id.playMusic_direct);
        goToSearching = (ImageButton) findViewById(R.id.searching_direct);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Searching위치확인 Log", "" + FlagControl.APP_SEARCHING_CONTROL);
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
                FlagControl.APP_SEARCHING_CONTROL = 0;
                Log.i("Searching위치확인 Log", "" + FlagControl.APP_SEARCHING_CONTROL);
                Intent intent = new Intent(StartingActivity.this, SearchingActivity.class);
                startActivity(intent);
            }
        });
        startMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlagControl.ON_PLAY_LIST = 1;
                Intent intent = new Intent(StartingActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });
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



}
