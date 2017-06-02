package com.example.dkdk6.toktokplay.Service_Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dkdk6.toktokplay.Service_Receiver.ScreenService;

/**
 * Created by dkdk6 on 2017-05-08.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent i = new Intent(context, ScreenService.class);
            context.startService(i);
        }
    }

}
