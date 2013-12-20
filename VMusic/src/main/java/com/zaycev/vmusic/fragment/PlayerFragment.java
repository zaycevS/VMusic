//package com.zaycev.vmusic.fragment;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.zaycev.vmusic.R;
//import com.zaycev.vmusic.entity.Player;
//import com.zaycev.vmusic.utils.Util;
//
//public class PlayerFragment extends Fragment {
//
//    private SeekBar sb_rewind;
//    private TextView tv_track_time;
//    private ImageView iv_track_status;
//    private TextView tv_title;
//    private TextView tv_artist;
//
//    private Player player = Player.getInstance();
//    private Handler handler = new Handler();
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_player, container, false);
//
//        player.setOnPlayerStarted(new Player.OnPlayerStarted() {
//            @Override
//            public void onStarted() {
//                iv_track_status.setImageResource(R.drawable.ic_action_play);
//                progressUpdate();
//            }
//        });
//
//        player.setOnPlayerPaused(new Player.OnPlayerPaused() {
//            @Override
//            public void onPaused() {
//                iv_track_status.setImageResource(R.drawable.ic_action_pause);
//            }
//        });
//
//        return view;
//    }
//
//    public void progressUpdate() {
//        if (player.isPlaying()) {
//            sb_rewind.setProgress(player.getCurrentPosition());
//            tv_track_time.setText(Util.getDateFromStr((player.getDuration() - player.getCurrentPosition())));
//
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    progressUpdate();
//                }
//            };
//            handler.postDelayed(runnable, 1000);
//        }
//    }
//
//
//}