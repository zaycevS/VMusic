package com.zaycev.vmusic.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.fragment.AudioListFragment;
import com.zaycev.vmusic.utils.Util;
import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.util.ArrayList;
import java.util.List;

public class PlayerPageAdapter extends PagerAdapter {

    private ArrayList<Audio> audioList;
    private LayoutInflater inflater;

    public PlayerPageAdapter(LayoutInflater layoutInflater, ArrayList<Audio> audios) {
        audioList = audios;
        inflater = layoutInflater;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View page = inflater.inflate(R.layout.fragment_player, null, false);
        Audio audio = audioList.get(position);

        ((TextView) page.findViewById(R.id.tv_title)).setText(audio.title);
        ((TextView) page.findViewById(R.id.tv_artist)).setText(audio.artist);
        ((TextView) page.findViewById(R.id.tv_time)).setText(Util.getDateFromStr(audio.duration));
        ((SeekBar) page.findViewById(R.id.sb_rewind)).setMax((int) (audio.duration / 1000));

        container.addView(page);
        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setAudioList(ArrayList<Audio> audios) {
        this.audioList = audios;
    }
}
