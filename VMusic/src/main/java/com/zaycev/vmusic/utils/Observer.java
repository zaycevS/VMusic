package com.zaycev.vmusic.utils;

import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.util.ArrayList;

public interface Observer {
    void update (ArrayList<Audio> audioList);
}