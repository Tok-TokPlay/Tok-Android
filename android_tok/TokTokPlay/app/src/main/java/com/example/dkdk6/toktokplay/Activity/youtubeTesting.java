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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dkdk6.toktokplay.Adapter.SongAdapter;
import com.example.dkdk6.toktokplay.R;
import com.google.android.youtube.player.YouTubeIntents;

import java.util.ArrayList;
/*
유투브 연결 위한 임시 클래스
 */

/**
 * A sample activity which shows how to use the {@link YouTubeIntents} static methods to create
 * Intents that navigate the user to Activities within the main YouTube application.
 */
public class youtubeTesting extends AppCompatActivity {
    private ListView listView;
    ArrayList<Song> s_info_list;
    private ArrayList<String> serverTitle = new ArrayList<String>();
    private ArrayList<String> serverArtist = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        listView = (ListView)findViewById(R.id.listView);
        Intent rintent = getIntent();
        serverTitle = rintent.getStringArrayListExtra("RKey_T");
        serverArtist = rintent.getStringArrayListExtra("RKey_A");
        s_info_list = new ArrayList<Song>();
        for(int i=0; i<serverTitle.size(); i++){
            Song temp = new Song(serverTitle.get(i),serverArtist.get(i));
            s_info_list.add(temp);
        }
        SongAdapter myadapter = new SongAdapter(getApplicationContext(),R.layout.song_info, s_info_list);
        listView.setAdapter(myadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            ///리스트 클릭하면 넘어가는 곳
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2;
                intent2 = YouTubeIntents.createSearchIntent(youtubeTesting.this, s_info_list.get(position).title+s_info_list.get(position).singer);
                startActivity(intent2);
            }
        });
    }
}

