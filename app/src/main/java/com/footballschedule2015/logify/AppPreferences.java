package com.footballschedule2015.logify;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Scott on 10/28/2015.
 */
public class AppPreferences {

    public static final String GAMES_PLAYED = "gamesPlayed";

    public static boolean showGamesPlayed(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean(GAMES_PLAYED,true);
    }

    public static void setShowGamesPlayed(Context context,boolean status){
        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit().putBoolean(GAMES_PLAYED,status).commit();
    }
}
