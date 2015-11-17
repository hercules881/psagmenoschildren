package Misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by h.lionakis on 15/11/2015.
 */
public class Preferences {

    public static void set(Context context, String name, String key, Object value) {

        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(Boolean.class.isAssignableFrom(value.getClass()))
        editor.putBoolean(key, (Boolean) value);
        else
            editor.putInt(key, (int) value);
        editor.apply();

    }
    public static Object get(Context context, String name, String key, Object defaultValue) {

        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        if(Boolean.class.isAssignableFrom(defaultValue.getClass()))
        return preferences.getBoolean(key, (Boolean) defaultValue);
        else
            return preferences.getInt(key, (int) defaultValue);
    }
}
