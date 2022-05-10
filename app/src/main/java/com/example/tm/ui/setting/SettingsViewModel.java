package com.example.tm.ui.setting;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class SettingsViewModel extends AndroidViewModel {

    public static final String SETTINGS = "Settings";

    public static final String THEME = "Theme";

    public static final String DARK_THEME = "Dark";
    public static final String WHITE_THEME = "Light";

    public static final String LANGUAGE = "Language";

    public static final String EN_LANGUAGE = "en";
    public static final String RU_LANGUAGE = "ru";

    private SharedPreferences settingsPreferences;
    private SharedPreferences.Editor editor;

    public SettingsViewModel ( @NonNull Application application ) {
        super ( application );
        settingsPreferences = getApplication ().getSharedPreferences ( SETTINGS, Context.MODE_PRIVATE );
        editor = settingsPreferences.edit ();
    }

    public void setTheme(String theme){
        editor.putString ( THEME, theme );
        editor.apply ();
    }
    public String getTheme(){
        return settingsPreferences.getString(THEME,WHITE_THEME);
    }

    public void setLanguage(String language){
        editor.putString ( LANGUAGE, language );
        editor.apply ();
    }

    public String getLanguage(){
        return settingsPreferences.getString ( LANGUAGE, EN_LANGUAGE );
    }

    public SharedPreferences getSettingsPreferences() {
        return settingsPreferences;
    }
}