package com.zaycev.vmusic.background;

import android.content.Context;

import com.zaycev.vmusic.config.VMusicApp;
import com.zaycev.vmusic.entity.Account;
import com.zaycev.vmusic.utils.vksdk.api.User;

import java.util.ArrayList;

public class FriendsTask extends AbstractTask<Void, Void, ArrayList<User>> {

    public FriendsTask(Context context, OnTaskFinished onTaskFinished) {
        super(context, onTaskFinished);
    }

    @Override
    protected ArrayList<User> inBackgroundMethod() throws Exception {
        return VMusicApp.getVkApi().getFriends(Account.getUserID(), "photo_50", null, null, null);
    }
}
