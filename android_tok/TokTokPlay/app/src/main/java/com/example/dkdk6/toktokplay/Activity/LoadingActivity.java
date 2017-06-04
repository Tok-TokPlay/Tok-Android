package com.example.dkdk6.toktokplay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dkdk6.toktokplay.FlagControl;
import com.example.dkdk6.toktokplay.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Handler;

/**
 * Created by dkdk6 on 2017-06-04.
 */

public class LoadingActivity extends AppCompatActivity {

    ImageView image;
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TestLog";
    final static String filename = "logfile.txt";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        image = (ImageView) findViewById(R.id.loading_image);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateanim);
        image.setAnimation(animation);
        android.os.Handler mHandler = new android.os.Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(LoadingActivity.this, StartingActivity.class);
                startActivity(start);
                finish();
            }
        }, (3 * 1000));
        WriteTextFile();

        //mOnFileWrite();
    }

    public void WriteTextFile() {

        String dirPath = getFilesDir().getAbsolutePath();
        File file = new File(dirPath);
        try {
            if (!file.exists()) {
                file.mkdir();
                Log.i("Txt", "폴더 생성 성공");
            }
            File savefile = new File(dirPath + "/tokdata.txt");
            FileInputStream fos = new FileInputStream(savefile);
            if(fos.read()==0||fos.read()==1){
                FlagControl.LOCK_ON=fos.read();
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
