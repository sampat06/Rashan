package com.rashan.Utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Temperory_Storage {

    public static final String LAT="LAT";
    public static final String LNG="LNG";
    public static final String ADDRESS="ADDRESS";


    public static String get_lat(Context context){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getString(LAT,"");
    }

    public static void set_lat(Context context, String set_share){
        SharedPreferences.Editor prefsEditor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor .putString(LAT, set_share).commit();

    }

    public static String get_lng(Context context){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getString(LNG,"");
    }

    public static void set_lng(Context context, String set_share){
        SharedPreferences.Editor prefsEditor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor .putString(LNG, set_share).commit();

    }public static String get_address(Context context){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getString(ADDRESS,"");
    }

    public static void set_address(Context context, String set_share){
        SharedPreferences.Editor prefsEditor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor .putString(ADDRESS, set_share).commit();

    }


}
