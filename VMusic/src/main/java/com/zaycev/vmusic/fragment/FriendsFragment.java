package com.zaycev.vmusic.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.zaycev.vmusic.adapter.FriendAdapter;
import com.zaycev.vmusic.background.AbstractTask;
import com.zaycev.vmusic.background.FriendsTask;
import com.zaycev.vmusic.config.Source;
import com.zaycev.vmusic.entity.UserActive;
import com.zaycev.vmusic.utils.ReplaceFragmentCallback;
import com.zaycev.vmusic.utils.vksdk.api.User;

public class FriendsFragment extends ListFragment implements AdapterView.OnItemClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FriendsTask friendListTask = new FriendsTask(getActivity(), new AbstractTask.OnTaskFinished<FriendsTask>() {
            @Override
            public void onFinish(FriendsTask task) {
                setListAdapter(new FriendAdapter(getActivity(), task.getResult()));
            }
        });
        friendListTask.execute();

        getListView().setOnItemClickListener(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User friend = (User) getListAdapter().getItem(i);
        UserActive.setID(friend.uid);
        ((ReplaceFragmentCallback) getActivity()).replaceFragment(new AudioListFragment(Source.FRIEND_RECORD));
    }
}