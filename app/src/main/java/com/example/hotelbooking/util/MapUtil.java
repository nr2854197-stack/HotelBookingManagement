package com.example.hotelbooking.util;

import android.content.Context;

import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;

import java.io.File;

public final class MapUtil {

    private MapUtil() { }

    /** Call BEFORE inflating a layout that contains an osmdroid MapView. */
    public static void init(Context context) {
        IConfigurationProvider config = Configuration.getInstance();
        config.load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE));
        config.setUserAgentValue(context.getPackageName());
        File base = new File(context.getCacheDir(), "osmdroid");
        config.setOsmdroidBasePath(base);
        config.setOsmdroidTileCache(new File(base, "tiles"));
    }
}
