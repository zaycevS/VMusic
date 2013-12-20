package com.zaycev.vmusic.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.activity.MainActivity;
import com.zaycev.vmusic.adapter.AudioAdapter;
import com.zaycev.vmusic.background.AbstractTask;
import com.zaycev.vmusic.background.AudioListTask;
import com.zaycev.vmusic.config.Source;
import com.zaycev.vmusic.entity.Player;
import com.zaycev.vmusic.utils.Observable;
import com.zaycev.vmusic.utils.Observer;
import com.zaycev.vmusic.utils.ReplaceFragmentCallback;
import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.util.ArrayList;
import java.util.List;

public class AudioListFragment extends ListFragment
        implements Observable, AdapterView.OnItemClickListener {

    private List<Observer> observers;
    private ArrayList<Audio> audioList;
    private Source musicSource;
    private Player player;

    public AudioListFragment(Source musicSource) {
        this.musicSource = musicSource;
        this.audioList = new ArrayList<>();
        this.player = Player.getInstance();
        this.observers = new ArrayList<>();
        Player.getInstance().setObserver(this);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(audioList);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AudioListTask audioListTask = new AudioListTask(getActivity(), musicSource, new AbstractTask.OnTaskFinished<AudioListTask>() {
            @Override
            public void onFinish(AudioListTask task) {
                audioList = task.getResult();
                notifyObservers();
                setListAdapter(new AudioAdapter(getActivity(), audioList));
            }
        });
        audioListTask.execute();

        setHasOptionsMenu(true);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_audio, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ((ReplaceFragmentCallback) getActivity()).replaceFragment(new AlbumsFragment());
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(player.isNewPlaylist()) {
            player.replaceAudioList();
            ((MainActivity)getActivity()).changePlayerPageAdapter();
        }
        if(player.getCurrentPosition() != position) {
            ((MainActivity)getActivity()).changePlayerPage(position);
        }
        player.play(position);
    }
}