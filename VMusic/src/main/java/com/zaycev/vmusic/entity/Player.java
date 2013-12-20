package com.zaycev.vmusic.entity;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.zaycev.vmusic.fragment.AudioListFragment;
import com.zaycev.vmusic.utils.Observer;
import com.zaycev.vmusic.utils.Util;
import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.io.IOException;
import java.util.ArrayList;

public class Player implements Observer{

    private static Player instance;

    private ArrayList<Audio> currentFragmentAudioList;
    private ArrayList<Audio> playingAudioList;
    private int playingTrackPosition;

    private MediaPlayer mediaPlayer;
    private AudioListFragment audioListObserver;

//    private MediaPlayer.OnCompletionListener onCompletion;
//    private MediaPlayer.OnBufferingUpdateListener onBuffer;
//    private OnPlayerPrepare onPlayerPrepare;
    private OnPlayerPaused onPlayerPaused;
    private OnPlayerStarted onPlayerStarted;

    public static Player getInstance() {
        if (instance == null)
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player();
                }
            }
        return instance;
    }

    private Player() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        currentFragmentAudioList = new ArrayList<>();
        playingAudioList = new ArrayList<>();
    }

    @Override
    public void update(ArrayList<Audio> audioList) {
        currentFragmentAudioList = audioList;
    }

    public void setObserver(AudioListFragment observer) {
        this.audioListObserver = observer;
        audioListObserver.registerObserver(this);
    }

//--------------------------------------------------------------------------------------------------
// Методы получения информации о текущей аудиозаписи
//--------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------
// Методы управления плеером
//--------------------------------------------------------------------------------------------------
    public void play(int trackPosition) {
        if((playingAudioList.get(playingTrackPosition).url).equals(playingAudioList.get(trackPosition).url)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                onPlayerPaused.onPaused();
            }
            else {
                mediaPlayer.start();
                onPlayerStarted.onStarted();
            }
        } else {
            playingTrackPosition = trackPosition;
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(playingAudioList.get(playingTrackPosition).url);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        onPlayerStarted.onStarted();

                    }
                });
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playNext() {
        play(playingTrackPosition + 1);
    }

    public void playBack() {
        play(playingTrackPosition - 1);
    }

    public void replaceAudioList() {
        playingAudioList = currentFragmentAudioList;
    }
//--------------------------------------------------------------------------------------------------

    public ArrayList<Audio> getPlayingAudioList() {
        return playingAudioList;
    }

    public void setPlayingAudioList(ArrayList<Audio> playingAudioList) {
        this.playingAudioList = playingAudioList;
    }

    public void setNewPlaylist(ArrayList<Audio> playlist) {
        this.currentFragmentAudioList = playlist;
    }

    public void changePlaylist() {
        playingAudioList = currentFragmentAudioList;
    }

    public boolean isNewPlaylist() {
        return !(playingAudioList == currentFragmentAudioList);
    }







    public void init() {
        try {
            mediaPlayer.setDataSource(playingAudioList.get(playingTrackPosition).url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition() / 1000;
    }

    public int getDuration() {
        return (int) playingAudioList.get(playingTrackPosition).duration;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getAudioCount() {
        return playingAudioList.size();
    }

    public int getPlayingTrackPosition() {
        return playingTrackPosition;
    }

    public String getTrackArtist() {
        return playingAudioList.get(playingTrackPosition).artist;
    }

    public String getTrackTitle() {
        return playingAudioList.get(playingTrackPosition).title;
    }

    public String getTrackTime() {
        return Util.getDateFromStr(playingAudioList.get(playingTrackPosition).duration);
    }

//    public MediaPlayer.OnCompletionListener getOnCompletion() {
//        return onCompletion;
//    }
//
//    public void setOnCompletion(MediaPlayer.OnCompletionListener onCompletion) {
//        this.onCompletion = onCompletion;
//        mediaPlayer.setOnCompletionListener(onCompletion);
//    }
//
//    public MediaPlayer.OnBufferingUpdateListener getOnBuffer() {
//        return onBuffer;
//    }
//
//    public void setOnBuffer(MediaPlayer.OnBufferingUpdateListener onBuffer) {
//        this.onBuffer = onBuffer;
//        mediaPlayer.setOnBufferingUpdateListener(onBuffer);
//    }
//
    public void setOnPlayerStarted(OnPlayerStarted onPlayerStart) {
        onPlayerStarted = onPlayerStart;
    }

    public void setOnPlayerPaused(OnPlayerPaused onPlayerPaused) {
        this.onPlayerPaused = onPlayerPaused;
    }
//
//    public void setOnPlayerPrepare(OnPlayerPrepare onPlayerPrepare) {
//        this.onPlayerPrepare = onPlayerPrepare;
//    }
//
//
//
//
    public interface OnPlayerPaused {
        public void onPaused();
    }

    public interface OnPlayerStarted {
        public void onStarted();
    }
//
//    public interface OnPlayerPrepare {
//        public void onPrepare();
//    }
}
