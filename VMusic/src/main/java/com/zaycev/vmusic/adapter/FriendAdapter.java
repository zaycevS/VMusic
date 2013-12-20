package com.zaycev.vmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.utils.vksdk.api.User;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<User> listFriend;

    public FriendAdapter(Context context, ArrayList<User> listFriend) {
        this.listFriend = listFriend;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listFriend.size();
    }

    @Override
    public Object getItem(int position) {
        return listFriend.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_friend, parent, false);
        }

        User friend = (User) getItem(position);

        assert view != null;
        ((TextView) view.findViewById(R.id.tv_name)).setText(friend.first_name);
        ((TextView) view.findViewById(R.id.tv_surname)).setText(friend.last_name);

        return view;
    }




}
