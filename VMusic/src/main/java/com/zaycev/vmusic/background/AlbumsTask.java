package com.zaycev.vmusic.background;

import android.content.Context;

import com.zaycev.vmusic.config.VMusicApp;
import com.zaycev.vmusic.entity.UserActive;
import com.zaycev.vmusic.utils.vksdk.api.AudioAlbum;
import com.zaycev.vmusic.utils.vksdk.api.KException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class AlbumsTask extends AbstractTask<Void, Void, ArrayList<AudioAlbum>> {

    public AlbumsTask(Context context, OnTaskFinished onTaskFinished) {
        super(context, onTaskFinished);
    }

    @Override
    protected ArrayList<AudioAlbum> inBackgroundMethod() {
        try {
            return VMusicApp.getVkApi().getAudioAlbums(UserActive.getID(), null, null);
        } catch (IOException | JSONException | KException e) {
            return new ArrayList<>();
        }
    }
}
