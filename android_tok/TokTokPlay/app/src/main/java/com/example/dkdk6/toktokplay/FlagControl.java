package com.example.dkdk6.toktokplay;

import android.content.Intent;
import android.graphics.Bitmap;

import com.example.dkdk6.toktokplay.Activity.MusicDto;
import com.example.dkdk6.toktokplay.Service_Receiver.MusicService;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2017-05-30.
 */

public class FlagControl {
    public static int LOCK_ON=0;
    public static int APP_SEARCHING_CONTROL=-1;
    public static int MUSIC_PLAYING_NOW=0;
    public static int ON_PLAY_LIST=0;
    public static int MUSIC_PAUSE=0;
    public static int NOTIFICATION_OPEN=0;
    public static Bitmap nowalbum;
    public static String nowTitle;
    public static String receiveTitle = "I LOVE YOU";
    public static String receiveArtist = "2NE1";
    public static String musicKey;
}
