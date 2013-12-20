package com.zaycev.vmusic.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.adapter.AudioAdapter;
import com.zaycev.vmusic.adapter.PlayerPageAdapter;
import com.zaycev.vmusic.background.AbstractTask;
import com.zaycev.vmusic.background.AudioListTask;
import com.zaycev.vmusic.config.Source;
import com.zaycev.vmusic.entity.Player;
import com.zaycev.vmusic.fragment.AudioListFragment;
import com.zaycev.vmusic.listener.NavigationDrawerListener;
import com.zaycev.vmusic.utils.Observer;
import com.zaycev.vmusic.utils.PlayerCallback;
import com.zaycev.vmusic.utils.ReplaceFragmentCallback;
import com.zaycev.vmusic.utils.vksdk.api.Audio;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
        implements ReplaceFragmentCallback, Observer {

    private ViewPager playerPager;
    private Player player;

    public MainActivity() {
        this.player = Player.getInstance();
    }

    @Override
    public void update(ArrayList<Audio> audioList) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragments_container, new AudioListFragment(Source.AUDIO_RECORDINGS))
                    .commit();

            setupNavigationDrawer();
            setupPlayerPager();
        }

        player.setOnPlayerStarted(new Player.OnPlayerStarted() {
            @Override
            public void onStarted() {
                ((ImageView)playerPager.findViewById(R.id.iv_track_status)).setImageResource(R.drawable.ic_action_play);
            }
        });

        player.setOnPlayerPaused(new Player.OnPlayerPaused() {
            @Override
            public void onPaused() {
                ((ImageView)playerPager.findViewById(R.id.iv_track_status)).setImageResource(R.drawable.ic_action_pause);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .remove(getFragmentManager().findFragmentById(R.id.fragments_container))
                .add(R.id.fragments_container, fragment)
                .commit();
    }

    private void setupNavigationDrawer() {
        ListView lv_left_navigation_drawer = (ListView) findViewById(R.id.lv_left_navigation_drawer);

        lv_left_navigation_drawer.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.left_menu_drawer_titles)
        ));

        lv_left_navigation_drawer.setOnItemClickListener(new NavigationDrawerListener(this));
    }

    public void setupPlayerPager() {
        playerPager = (ViewPager) findViewById(R.id.vp_player);
        playerPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        AudioListTask audioListTask = new AudioListTask(this, Source.AUDIO_RECORDINGS, new AbstractTask.OnTaskFinished<AudioListTask>() {
            @Override
            public void onFinish(AudioListTask task) {
                playerPager.setAdapter(new PlayerPageAdapter(getLayoutInflater(), task.getResult()));
            }
        });
        audioListTask.execute();

        playerPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                if (player.getPlayingTrackPosition() < i) {
                    player.playNext();
                } else if (player.getPlayingTrackPosition() > i) {
                    player.playBack();
                }
                playerPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }


    public void changePlayerPage(int position) {
        playerPager.setCurrentItem(position);
        playerPager.getAdapter().notifyDataSetChanged();
    }


    public void changePlayerPageAdapter() {
        ((PlayerPageAdapter)playerPager.getAdapter()).setAudioList(player.getPlayingAudioList());
        playerPager.getAdapter().notifyDataSetChanged();
    }
}
