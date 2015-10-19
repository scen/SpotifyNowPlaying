package com.stanleycen.nowplaying;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by scen on 10/18/15.
 */

public class NowPlayingState {
    private static final String PREFS_FILE = "PREFS";

    private static final String TRACKNAME_KEY = "trackname";
    private static final String ARTIST_KEY = "artist";
    private static final String ALBUM_KEY = "album";
    private static final String PLAYING_KEY = "is_playing";

    private static final ReentrantLock lock = new ReentrantLock();

    public static void beginTransaction() {
        lock.lock();
    }

    public static void endTransaction() {
        lock.unlock();
    }

    private static void set(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILE,
                                                                       Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void set(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILE,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getString(key, "N/A");
    }

    private static boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
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

    public static void setPlaystate(Context context, boolean isPlaying) {
        set(context, PLAYING_KEY, isPlaying);
    }

    public static boolean getPlaystate(Context context) {
        return getBoolean(context, PLAYING_KEY);
    }

    public static String getTrackname(Context context) {
        return getString(context, TRACKNAME_KEY);
    }

    public static String getArtist(Context context) {
        return getString(context, ARTIST_KEY);
    }

    public static String getAlbum(Context context) {
        return getString(context, ALBUM_KEY);
    }
}
