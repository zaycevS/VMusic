package com.zaycev.vmusic.utils;

import com.zaycev.vmusic.adapter.AudioAdapter;
import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.util.ArrayList;

public interface PlayerCallback {
    public void setupPlayerPager();
    public void changeTrack(int position);
    public void changePlaylist(ArrayList<Audio> listAudio);
}
