package com.exoplayerhelloworld;

import com.facebook.react.uimanager.ThemedReactContext;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ExoPlaybackException;

import android.util.Log;
import android.net.Uri;

// import java.net.URI;
import java.io.File;
import java.io.IOException;

public class ExoPlayerView extends PlayerView implements Player.EventListener {
    SimpleExoPlayer mPlayer;
    PlayerView mPlayerView;
    private final String TAG = "ExoPlayerTest";

    ExoPlayerView(ThemedReactContext context){
        super(context);
        mPlayerView = this;

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "exoplayerHelloWorld"));
        // String path = "file:///android_asset/bigbuckbunny.mpd";

        // Uri uri = Uri.parse(path);
        // Uri uri = Uri.fromFile(new File("//android_asset/client_manifest-common_init.mpd"));

        DashMediaSource dashMediaSource = new DashMediaSource(uri, dataSourceFactory,
            new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        mPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        mPlayerView.setPlayer(mPlayer);
        mPlayer.prepare(dashMediaSource);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error){

        switch (error.type) {
            case ExoPlaybackException.TYPE_SOURCE:
                Log.d(TAG, "TYPE_SOURCE: " + error.getSourceException().getMessage());
                break;

            case ExoPlaybackException.TYPE_RENDERER:
                Log.d(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                break;

            case ExoPlaybackException.TYPE_UNEXPECTED:
                Log.d(TAG, "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState){
        Log.d(TAG, "PlayWhenReady: " + Boolean.toString(playWhenReady));
        Log.d(TAG, "PlayBack State Constant: " + Integer.toString(playbackState));
        // STATE_BUFFERING	2
        // STATE_ENDED	4
        // STATE_IDLE	1
        // STATE_READY	3
    }

    @Override
    public void onLoadingChanged(boolean isLoading){
        Log.d(TAG, "isLoadingSource: " + Boolean.toString(isLoading));
    }
}
