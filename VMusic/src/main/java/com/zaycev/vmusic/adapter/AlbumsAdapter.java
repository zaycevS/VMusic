package com.zaycev.vmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.utils.vksdk.api.AudioAlbum;

import java.util.ArrayList;

public class AlbumsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<AudioAlbum> listAudioAlbum;

    public AlbumsAdapter(Context context, ArrayList<AudioAlbum> listAudioAlbum) {
        this.listAudioAlbum = listAudioAlbum;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listAudioAlbum.size();
    }

    @Override
    public Object getItem(int position) {
        return listAudioAlbum.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_audio_album, parent, false);
        }

        AudioAlbum album = (AudioAlbum) getItem(position);

        assert view != null;
        ((TextView) view.findViewById(R.id.tv_album)).setText(album.title);

        return view;
    }
}