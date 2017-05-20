package com.example.dkdk6.toktokplay.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dkdk6.toktokplay.R;
import com.example.dkdk6.toktokplay.Service_Receiver.ScreenService;

public class SettingActivity extends AppCompatActivity {
    private Context mContext = this;
    private ImageButton onBtn, offBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);
        onBtn= (ImageButton)findViewById(R.id.btn1);
        offBtn= (ImageButton)findViewById(R.id.btn2);
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
    }
}
