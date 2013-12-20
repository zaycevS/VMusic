package com.zaycev.vmusic.config;

import android.app.Application;

import com.zaycev.vmusic.entity.Account;
import com.zaycev.vmusic.utils.vksdk.api.Api;

public class VMusicApp extends Application {

    private static VMusicApp applicationContext;
    private static Api vkAPI;

    public VMusicApp() {
        applicationContext = this;
    }

    public static VMusicApp getContext() {
        if (applicationContext == null)
            synchronized (VMusicApp.class) {
                if (applicationContext == null)
                    applicationContext = new VMusicApp();
            }
        return applicationContext;
    }

    public static Api getVkApi() {
        if (vkAPI == null)
            synchronized (Account.class) {
                if (vkAPI == null) {
                    vkAPI = new Api(Account.getAccessToken(), Constants.API_ID);
                }
            }
        return vkAPI;
    }
}
