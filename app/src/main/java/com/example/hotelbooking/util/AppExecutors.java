package com.example.hotelbooking.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Small helper for running database work off the main thread and posting the
 * result back to it. This is Java's equivalent of what Kotlin coroutines
 * (lifecycleScope.launch + Dispatchers.IO) did in the Kotlin version.
 */
public final class AppExecutors {

    private static final ExecutorService DISK_IO = Executors.newSingleThreadExecutor();
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    private AppExecutors() {
    }

    public static void diskIO(Runnable runnable) {
        DISK_IO.execute(runnable);
    }

    public static void mainThread(Runnable runnable) {
        MAIN_THREAD.post(runnable);
    }
}
