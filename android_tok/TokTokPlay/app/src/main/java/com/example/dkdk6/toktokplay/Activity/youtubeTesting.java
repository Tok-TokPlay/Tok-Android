package com.example.dkdk6.toktokplay.Activity;
/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.youtube.player.YouTubeIntents;
/*
유투브 연결 위한 임시 클래스
 */

/**
 * A sample activity which shows how to use the {@link YouTubeIntents} static methods to create
 * Intents that navigate the user to Activities within the main YouTube application.
 */
public class youtubeTesting extends AppCompatActivity {
    private String receiveTitle, receiveArtist;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String SONG_ID;
        Intent rintent = getIntent();
        receiveTitle=rintent.getStringExtra("RKey_T");
        receiveArtist= rintent.getStringExtra("RKey_A");
        SONG_ID = receiveArtist +" "+receiveTitle;
        Intent intent2;
        intent2 = YouTubeIntents.createSearchIntent(youtubeTesting.this, SONG_ID);
        startActivity(intent2);
    }
}
