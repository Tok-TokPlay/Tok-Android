package com.example.dkdk6.toktokplay.Service_Receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.dkdk6.toktokplay.Activity.LockScreenActivity;

/*
화면이 꺼졌을 때 ACTION_SCREEN_OFF Intent -> LockScreenActivity실행
화면이 켜졌을 때 ACTION_SCREEN_ON Intent
 */

public class ScreenReceiver extends BroadcastReceiver{
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keyLock = null;
    private TelephonyManager telephonyManager = null;
    private boolean isPhoneIdle = true;
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){ //화면이 꺼졌을 때 인텐트를 받음.
            if(km==null)
                km=(KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
            if(keyLock==null)
                keyLock=km.newKeyguardLock(Context.KEYGUARD_SERVICE);
            if(telephonyManager == null){
                telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            }
            if(isPhoneIdle) {
                disableKeyguard();
                Intent i = new Intent(context, LockScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Activity에서 Start Activity를 하지 않기 때문에 필요!
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        }//여기서 ON 테스트해보기!
    }
    public void reenableKeyguare(){
        keyLock.reenableKeyguard();
    }
    public void disableKeyguard(){
        keyLock.disableKeyguard();
    }
    private PhoneStateListener phoneListener = new PhoneStateListener(){

        @Override

        public void onCallStateChanged(int state, String incomingNumber){

            switch(state){

                case TelephonyManager.CALL_STATE_IDLE :

                    isPhoneIdle = true;

                    break;

                case TelephonyManager.CALL_STATE_RINGING :

                    isPhoneIdle = false;

                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK :

                    isPhoneIdle = false;

                    break;

            }

        }

    };

}
