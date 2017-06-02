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

/**
 * A sample activity which shows how to use the {@link YouTubeIntents} static methods to create
 * Intents that navigate the user to Activities within the main YouTube application.
 */
public final class IntentsDemoActivity extends Activity{

  private String SONG_ID = "Lonely";
  Intent intent2 = new Intent();
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        intent2 = YouTubeIntents.createSearchIntent(IntentsDemoActivity.this, SONG_ID);
        startActivity(intent2);
      }

}
