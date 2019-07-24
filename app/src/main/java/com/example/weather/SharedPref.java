package com.example.weather;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context _context;

    int private_mode = 0;


    private static final String PREF_NAME = "alarmManager";

    private static final String TIME_FIRST = "IsFirstTime";

public SharedPref(Context context){
    this._context = context;
    sharedPreferences = _context.getSharedPreferences(PREF_NAME , private_mode);
    editor = sharedPreferences.edit();
}

public void SetFirstTime(Boolean isFirstTime){
    editor.putBoolean(TIME_FIRST , isFirstTime);
    editor.commit();
}
    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(TIME_FIRST, true);
    }
}
