package com.example.dkdk6.toktokplay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dkdk6.toktokplay.Activity.Song;
import com.example.dkdk6.toktokplay.R;

import java.util.ArrayList;

/**
 * Created by dkdk6 on 2017-06-12.
 */

public class SongAdapter extends BaseAdapter {
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Song> data = null;
    private LayoutInflater inflater = null;

    public SongAdapter(Context c, int l, ArrayList<Song> d) {
        this.mContext = c;
        this.layout = l;
        this.data = d;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if(convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }
        TextView tTitle = (TextView) convertView.findViewById(R.id.Title);
        TextView  tSinger= (TextView) convertView.findViewById(R.id.Singer);


        tTitle.setText(data.get(position).title);
        tSinger.setText(data.get(position).singer);

        return convertView;
    }
}