package com.stanleycen.nowplaying;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by scen on 10/18/15.
 */

public class NowPlayingState {
    private static final String PREFS_FILE = "PREFS";

    private static final String TRACKNAME_KEY = "trackname";
    private static final String ARTIST_KEY = "artist";
    private static final String ALBUM_KEY = "album";

    private static void set(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILE,
                                                                       Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String get(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getString(key, "N/A");
    }

    public static void setTrackname(Context context, String track) {
        set(context, TRACKNAME_KEY, track);
    }

    public static void setArtist(Context context, String artist) {
        set(context, ARTIST_KEY, artist);
    }

    public static void setAlbum(Context context, String album) {
        set(context, ALBUM_KEY, album);
    }

    public static String getTrackname(Context context) {
        return get(context, TRACKNAME_KEY);
    }

    public static String getArtist(Context context) {
        return get(context, ARTIST_KEY);
    }

    public static String getAlbum(Context context) {
        return get(context, ALBUM_KEY);
    }
}
