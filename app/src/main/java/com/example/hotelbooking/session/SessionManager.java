package com.example.hotelbooking.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    /** Logging in with this email opens the owner/admin dashboard (demo-only rule). */
    public static final String ADMIN_EMAIL = "admin@localstay.com";

    private static final String PREFS_NAME = "localstay_session";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(String email, String name) {
        prefs.edit().putString(KEY_EMAIL, email).putString(KEY_NAME, name).apply();
    }

    public String getEmail() { return prefs.getString(KEY_EMAIL, null); }

    public String getName() { return prefs.getString(KEY_NAME, "Guest"); }

    public boolean isLoggedIn() { return getEmail() != null; }

    public boolean isAdmin() { return ADMIN_EMAIL.equalsIgnoreCase(getEmail()); }

    public void logout() { prefs.edit().clear().apply(); }
}
