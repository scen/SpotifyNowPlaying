package com.stanleycen.nowplaying;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by scen on 10/18/15.
 */

public class SpotifyStateReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "SpotifyStateReceiver";

    private static final String METADATA_CHANGED_INTENT = "metadatachanged";
    private static final String PLAYBACK_CHANGED_INTENT = "playbackstatechanged";
    private static final String QUEUE_CHANGED_INTENT = "queuechanged";

    private boolean isMetadataBroadcast(String action) {
        return action.toLowerCase().contains(METADATA_CHANGED_INTENT);
    }

    private boolean isPlaybackBroadcast(String action) {
        return action.toLowerCase().contains(PLAYBACK_CHANGED_INTENT);
    }

    private boolean isQueueBroadcast(String action) {
        return action.toLowerCase().contains(QUEUE_CHANGED_INTENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, ">>>>>>>" + action);
        Bundle extras = intent.getExtras();

        for (String key : extras.keySet()) {
            Log.i(DEBUG_TAG, key + ": " + extras.get(key).toString());
        }

        if (isMetadataBroadcast(action)) {
            Log.i(DEBUG_TAG, action);
        } else if (isPlaybackBroadcast(action)) {

        } else if (isQueueBroadcast(action)) {

        } else {
            Log.w(DEBUG_TAG, "Unknown action " + action);
        }
    }
}
