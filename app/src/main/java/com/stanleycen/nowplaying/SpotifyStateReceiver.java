package com.stanleycen.nowplaying;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by scen on 10/18/15.
 */

public class SpotifyStateReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "SpotifyStateReceiver";

    private static final String METADATA_CHANGED_INTENT = "metadatachanged";
    private static final String PLAYBACK_CHANGED_INTENT = "playbackstatechanged";
    private static final String QUEUE_CHANGED_INTENT = "queuechanged";

    // in millis
    private static final long DEBOUNCE_TIME = 100;

    // Last broadcast in millis, used for debouncing.
    private static long lastWidgetUpdateBroadcast = -1;

    private boolean isMetadataBroadcast(String action) {
        return action.toLowerCase().contains(METADATA_CHANGED_INTENT);
    }

    private boolean isPlaybackBroadcast(String action) {
        return action.toLowerCase().contains(PLAYBACK_CHANGED_INTENT);
    }

    private boolean isQueueBroadcast(String action) {
        return action.toLowerCase().contains(QUEUE_CHANGED_INTENT);
    }

    private void refreshWidgets(Context context) {
        Intent intent = new Intent(context, SpotifyWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{0});
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, ">>>>>>>" + action);
        Bundle extras = intent.getExtras();

        //TODO: we might have to debounce the function that updates the widget.

        for (String key : extras.keySet()) {
            Log.i(DEBUG_TAG, key + ": " + extras.get(key).toString());
        }

        boolean shouldUpdateWidget = false;

        if (isMetadataBroadcast(action) || isPlaybackBroadcast(action)) {
            NowPlayingState.beginTransaction();

            boolean curPlaystate = NowPlayingState.getPlaystate(context);
            String curTrack = NowPlayingState.getTrackname(context);
            String curAlbum = NowPlayingState.getAlbum(context);
            String curArtist = NowPlayingState.getArtist(context);

            try {
                if (extras.containsKey("playing") || extras.containsKey("playState")) {
                    boolean playing = extras.getBoolean("playing", false);
                    boolean playState = extras.getBoolean("playstate", false);

                    if (playing != playState) {
                        // wtf?
                        Toast.makeText(context, "Spotify: playing != playState", Toast.LENGTH_SHORT).show();
                    }

                    boolean newPlaystate = playing || playState;
                    NowPlayingState.setPlaystate(context, newPlaystate);
                    shouldUpdateWidget |= curPlaystate != newPlaystate;
                }

                String track = extras.getString("track", null);
                if (track != null) {
                    NowPlayingState.setTrackname(context, track);
                    shouldUpdateWidget |= !track.equals(curTrack);
                }

                String album = extras.getString("album", null);
                if (album != null) {
                    NowPlayingState.setAlbum(context, album);
                    shouldUpdateWidget |= !album.equals(curAlbum);
                }

                String artist = extras.getString("artist", null);
                if (artist != null) {
                    NowPlayingState.setArtist(context, artist);
                    shouldUpdateWidget |= !artist.equals(curArtist);
                }
            } finally {
                NowPlayingState.endTransaction();
            }

            if (shouldUpdateWidget) {
                refreshWidgets(context);
            }
        } else {
            Log.w(DEBUG_TAG, "Unknown action " + action);
        }
    }
}
