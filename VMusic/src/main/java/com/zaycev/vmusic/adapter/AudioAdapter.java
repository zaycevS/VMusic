package com.zaycev.vmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.utils.Util;
import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.util.ArrayList;

public class AudioAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Audio> listAudio;

    public AudioAdapter(Context context, ArrayList<Audio> listAudio) {
        this.listAudio = listAudio;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public int getCount() {
        return listAudio.size();
    }

    @Override
    public Object getItem(int position) {
        return listAudio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_audio, parent, false);
        }

        Audio audio = (Audio) getItem(position);

        assert view != null;
        if (position < 9)
            ((TextView) view.findViewById(R.id.tv_track_number)).setText("0" + ++position);
        else
            ((TextView) view.findViewById(R.id.tv_track_number)).setText("" + ++position);
        ((TextView) view.findViewById(R.id.tv_title)).setText(audio.title);
        ((TextView) view.findViewById(R.id.tv_artist)).setText(audio.artist);
        ((TextView) view.findViewById(R.id.tv_time)).setText(Util.getDateFromStr(audio.duration));

        return view;
    }
}