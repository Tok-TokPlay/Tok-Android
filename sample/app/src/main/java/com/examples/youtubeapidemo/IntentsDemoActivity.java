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

package com.examples.youtubeapidemo;

import com.google.android.youtube.player.YouTubeIntents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.examples.youtubeapidemo.adapter.DemoArrayAdapter;
import com.examples.youtubeapidemo.adapter.DemoListViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A sample activity which shows how to use the {@link YouTubeIntents} static methods to create
 * Intents that navigate the user to Activities within the main YouTube application.
 */
public final class IntentsDemoActivity extends Activity{

  // This is the value of Intent.EXTRA_LOCAL_ONLY for API level 11 and above.
  //private static final String EXTRA_LOCAL_ONLY = "android.intent.extra.LOCAL_ONLY";
 // private static final String VIDEO_ID = "-Uwjt32NvVA";
  //private static final String PLAYLIST_ID = "PLF3DFB800F05F551A";
  private String SONG_ID = "Lonely";
  //private static final String CHANNEL_ID = "UCVHFbqXqoYvEWM1Ddxl0QDg";
  private static final int SELECT_VIDEO_REQUEST = 1000;

  private List<DemoListViewItem> intentItems;

  Intent intent2 = new Intent();
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.intents_demo);
    final EditText et = (EditText) findViewById(R.id.musicTitle);
    Button search = (Button) findViewById(R.id.searchBtn);
    intent2 = YouTubeIntents.createSearchIntent(this, SONG_ID);
    search.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        SONG_ID = et.getText().toString();
        startActivity(intent2);
      }
    });
  }

}
