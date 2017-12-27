package com.android.messaging.util;

import android.content.Context;

import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import com.android.messaging.Factory;


public class FreemeWakeLockHelper {
    private static final boolean mDebug = false;

    private String mTag;
    private PowerManager.WakeLock mWakeLock;

    /**
     * Construct a FreemeWakeLockHelper object.
     *
     * @param tag a string to use for debugging
     */
    public FreemeWakeLockHelper(final String tag) {
        if (tag != null) {
            mTag = tag;
        } else {
            mTag = "Messaging-WakeLock";
        }

        setUsesWakeLock();
    }

    public void setUsesWakeLock() {
        if (mWakeLock != null) {
            // if either of these has happened, we've already played something.
            // and our releases will be out of sync.
            throw new RuntimeException("assertion failed mWakeLock=" + mWakeLock);
        }
        final PowerManager pm = (PowerManager) Factory.get().getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, mTag);
    }

    public void acquireWakeLock() {
        if (mWakeLock != null) {
            mWakeLock.acquire();
        }
    }

    public void acquireWakeLock(long timeout) {
        if (mWakeLock != null) {
            mWakeLock.acquire(timeout);
        }
    }

    public void releaseWakeLock() {
        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }

    private static long ACQUIRE_TIME_OUT = 5000;

    public static void wakeUpScreen() {
        final FreemeWakeLockHelper helper = new FreemeWakeLockHelper(LogUtil.BUGLE_TAG);
        helper.acquireWakeLock(ACQUIRE_TIME_OUT);
    }
}

