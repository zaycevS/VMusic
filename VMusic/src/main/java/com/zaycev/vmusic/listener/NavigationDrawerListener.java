package com.zaycev.vmusic.listener;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.zaycev.vmusic.config.Source;
import com.zaycev.vmusic.entity.Account;
import com.zaycev.vmusic.entity.UserActive;
import com.zaycev.vmusic.fragment.AudioListFragment;
import com.zaycev.vmusic.fragment.FriendsFragment;
import com.zaycev.vmusic.utils.ReplaceFragmentCallback;

public class NavigationDrawerListener implements AdapterView.OnItemClickListener {

    private static final int MY_MUSIC = 0;
    private static final int FRIEND_MUSIC = 1;

    private Context context;

    public NavigationDrawerListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Fragment fragment = null;

        switch (position) {
            case MY_MUSIC:
                UserActive.setID(Account.getUserID());
                fragment = new AudioListFragment(Source.AUDIO_RECORDINGS);
                break;
            case FRIEND_MUSIC:
                fragment = new FriendsFragment();
                break;
        }

        ((ReplaceFragmentCallback) context).replaceFragment(fragment);
    }
}
