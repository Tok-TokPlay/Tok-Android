package com.example.dkdk6.toktokplay.Activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

public class StartingActivity extends AppCompatActivity {
    private Context mContext = this;
    private ImageButton onBtn, offBtn, startMusic, goToSearching;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_CODE = 101;
    public static int SELECT_FLAG_IN_DIRECT=0; //음악 플레이 종류 인식
    public static int SELECT_FLAG_IN_SEARCHING=0;
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);
        Intent in = new Intent(this, MusicActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MusicPlayerActivity.class); //인텐트 생성.
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를없앤다.
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent는 일회용 인텐트 같은 개념입니다.
       /* FLAG_UPDATE_CURRENT - > 만일 이미 생성된 PendingIntent가 존재 한다면, 해당 Intent의 내용을 변경함.

                FLAG_CANCEL_CURRENT - .이전에 생성한 PendingIntent를 취소하고 새롭게 하나 만든다.

                FLAG_NO_CREATE -> 현재 생성된 PendingIntent를 반환합니다.

        FLAG_ONE_SHOT - >이 플래그를 사용해 생성된 PendingIntent는 단 한번밖에 사용할 수 없습니다*/
        builder.setSmallIcon(R.drawable.logo).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("TokTok_Music").setContentText("음악을 정지하려면 누르세요!")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
        //해당 부분은 API 4.1버전부터 작동합니다.

//setSmallIcon - > 작은 아이콘 이미지

//setTicker - > 알람이 출력될 때 상단에 나오는 문구.

//setWhen -> 알림 출력 시간.

//setContentTitle-> 알림 제목

//setConentText->푸쉬내용

        notificationManager.notify(1, builder.build()); // Notification send

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
        onBtn= (ImageButton)findViewById(R.id.onBtn);
        offBtn= (ImageButton)findViewById(R.id.offBtn);
        startMusic = (ImageButton)findViewById(R.id.playMusic_direct);
        goToSearching = (ImageButton)findViewById(R.id.searching_direct);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECT_FLAG_IN_SEARCHING=1;
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
                SELECT_FLAG_IN_SEARCHING=1;
                Intent intent = new Intent(StartingActivity.this, SearchingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        startMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECT_FLAG_IN_SEARCHING=0;
                SELECT_FLAG_IN_DIRECT=1;
                Intent intent = new Intent(StartingActivity.this, DirectMusicListActivity.class);
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
