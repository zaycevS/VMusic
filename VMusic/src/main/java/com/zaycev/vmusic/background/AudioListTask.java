package com.zaycev.vmusic.background;

import android.content.Context;

import com.zaycev.vmusic.activity.MainActivity;
import com.zaycev.vmusic.config.Source;
import com.zaycev.vmusic.config.VMusicApp;
import com.zaycev.vmusic.entity.Account;
import com.zaycev.vmusic.entity.Player;
import com.zaycev.vmusic.entity.UserActive;
import com.zaycev.vmusic.utils.vksdk.api.Audio;
import com.zaycev.vmusic.utils.vksdk.api.KException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class AudioListTask extends AbstractTask<Void, Void, ArrayList<Audio>> {

    private Source musicFrom;

    public AudioListTask(Context context, Source musicFrom, OnTaskFinished onTaskFinished) {
        super(context, onTaskFinished);
        this.musicFrom = musicFrom;
    }

    @Override
    protected ArrayList<Audio> inBackgroundMethod() {
        ArrayList<Audio> audioList = new ArrayList<>();
        try {
            switch (musicFrom) {
                case AUDIO_RECORDINGS:
                    audioList = VMusicApp.getVkApi().getAudio(Account.getUserID(), null, null, null, null, null);
                    break;
                case FRIEND_RECORD:
                    audioList = VMusicApp.getVkApi().getAudio(UserActive.getID(), null, null, null, null, null);
                    break;
                case ALBUM_RECORD:
                    audioList = VMusicApp.getVkApi().getAudio(UserActive.getID(), null, UserActive.getAlbumID(), null, null, null);
                    break;
            }
        } catch (IOException | JSONException | KException e) {
            e.printStackTrace();
        }
        return audioList;
    }
}
