package com.zaycev.vmusic.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.zaycev.vmusic.adapter.AlbumsAdapter;
import com.zaycev.vmusic.background.AbstractTask;
import com.zaycev.vmusic.background.AlbumsTask;
import com.zaycev.vmusic.config.Source;
import com.zaycev.vmusic.entity.UserActive;
import com.zaycev.vmusic.utils.ReplaceFragmentCallback;
import com.zaycev.vmusic.utils.vksdk.api.AudioAlbum;

public class AlbumsFragment extends ListFragment implements AdapterView.OnItemClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AlbumsTask albumsTask = new AlbumsTask(getActivity(), new AbstractTask.OnTaskFinished<AlbumsTask>() {
            @Override
            public void onFinish(AlbumsTask task) {
                setListAdapter(new AlbumsAdapter(getActivity(), task.getResult()));
            }
        });
        albumsTask.execute();

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AudioAlbum album = (AudioAlbum) parent.getItemAtPosition(position);
        UserActive.setAlbumID(album.album_id);
        ((ReplaceFragmentCallback) getActivity()).replaceFragment(new AudioListFragment(Source.ALBUM_RECORD));
    }
}