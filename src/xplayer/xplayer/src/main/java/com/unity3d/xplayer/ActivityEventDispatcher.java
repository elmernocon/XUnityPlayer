package com.unity3d.xplayer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class ActivityEventDispatcher extends ActivityEventListener {

    // region Statics

    private static final String LOG_TAG = "ActivityEventDispatcher";

    // endregion Statics

    // region Fields

    private ActivityEventListener[] listeners;

    private Map listenersMap;

    // endregion Fields

    // region Constructor

    ActivityEventDispatcher(ActivityEventListener[] listeners) {
        if (listeners == null) {
            throw new NullPointerException();
        }
        this.listeners = listeners;
        Map<String, ActivityEventListener> listenersMap = new HashMap<>();
        for (ActivityEventListener listener : listeners) {
            String key = listener.getClass().getName();
            listenersMap.put(key, listener);
        }
        this.listenersMap = Collections.unmodifiableMap(listenersMap);
    }

    // endregion Constructor

    // region Methods

    final ActivityEventListener findListener(String className) {
        return this.listenersMap.containsKey(className)
                ? (ActivityEventListener) this.listenersMap.get(className)
                : null;
    }

    @Override
    protected final void log(String msg, int priority) {
        log(msg, priority, LOG_TAG);
    }

    // endregion Methods

    // region ActivityEventListener Methods

    @Override
    protected final void setContext(Context context) {
        super.setContext(context);
        for (ActivityEventListener l : this.listeners) {
            l.setContext(context);
        }
        log("setContext");
    }

    @Override
    protected final void setIntent(Intent intent) {
        super.setIntent(intent);
        for (ActivityEventListener l : this.listeners) {
            l.setIntent(intent);
        }
    }

    @Override
    protected final void onSetContext(Context context) {
        log("onSetContext");
    }

    @Override
    protected final void onSetIntent(Intent intent) {
        log("onSetIntent");
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        for (ActivityEventListener l : this.listeners) {
            l.onCreate(savedInstanceState);
        }
        log("onCreate");
    }

    @Override
    public final void onRestart() {
        for (ActivityEventListener l : this.listeners) {
            l.onRestart();
        }
        log("onRestart");
    }

    @Override
    public final void onStart() {
        for (ActivityEventListener l : this.listeners) {
            l.onStart();
        }
        log("onStart");
    }

    @Override
    public final void onResume() {
        for (ActivityEventListener l : this.listeners) {
            l.onResume();
        }
        log("onResume");
    }

    @Override
    public final void onPause() {
        for (ActivityEventListener l : this.listeners) {
            l.onPause();
        }
        log("onPause");
    }

    @Override
    public final void onStop() {
        for (ActivityEventListener l : this.listeners) {
            l.onStop();
        }
        log("onStop");
    }

    @Override
    public final void onDestroy() {
        for (ActivityEventListener l : this.listeners) {
            l.onDestroy();
        }
        log("onDestroy");
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (ActivityEventListener l : this.listeners) {
            l.onActivityResult(
                    requestCode,
                    resultCode,
                    data
            );
        }
        log("onActivityResult");
    }

    @Override
    public final void onBackPressed() {
        for (ActivityEventListener l : this.listeners) {
            l.onBackPressed();
        }
        log("onBackPressed");
    }

    @Override
    public final void onConfigurationChanged(Configuration configuration) {
        for (ActivityEventListener l : this.listeners) {
            l.onConfigurationChanged(configuration);
        }
        log("onConfigurationChanged");
    }

    @Override
    public final void onGenericMotionEvent(MotionEvent event) {
        for (ActivityEventListener l : this.listeners) {
            l.onGenericMotionEvent(event);
        }
        log("onGenericMotionEvent");
    }

    @Override
    public final void onKeyDown(int keyCode, KeyEvent event) {
        for (ActivityEventListener l : this.listeners) {
            l.onKeyDown(
                    keyCode,
                    event
            );
        }
        log("onKeyDown");
    }

    @Override
    public final void onKeyUp(int keyCode, KeyEvent event) {
        for (ActivityEventListener l : this.listeners) {
            l.onKeyUp(
                    keyCode,
                    event
            );
        }
        log("onKeyUp");
    }

    @Override
    public final void onLowMemory() {
        for (ActivityEventListener l : this.listeners) {
            l.onLowMemory();
        }
        log("onLowMemory");
    }

    @Override
    public final void onNewIntent(Intent intent) {
        for (ActivityEventListener l : this.listeners) {
            l.onNewIntent(intent);
        }
        log("onNewIntent");
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (ActivityEventListener l : this.listeners) {
            l.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults
            );
        }
        log("onRequestPermissionsResult");
    }

    @Override
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        for (ActivityEventListener l : this.listeners) {
            l.onRestoreInstanceState(savedInstanceState);
        }
        log("onRestoreInstanceState");
    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        for (ActivityEventListener l : this.listeners) {
            l.onSaveInstanceState(outState);
        }
        log("onSaveInstanceState");
    }

    @Override
    public final void onTrimMemory(int level) {
        for (ActivityEventListener l : this.listeners) {
            l.onTrimMemory(level);
        }
        log("onTrimMemory");
    }

    @Override
    public final void onTouchEvent(MotionEvent event) {
        for (ActivityEventListener l : this.listeners) {
            l.onTouchEvent(event);
        }
        log("onTouchEvent");
    }

    @Override
    public final void onUserInteraction() {
        for (ActivityEventListener l : this.listeners) {
            l.onUserInteraction();
        }
        log("onUserInteraction");
    }

    @Override
    public final void onUserLeaveHint() {
        for (ActivityEventListener l : this.listeners) {
            l.onUserLeaveHint();
        }
        log("onUserLeaveHint");
    }

    @Override
    public final void onWindowFocusChanged(boolean hasFocus) {
        for (ActivityEventListener l : this.listeners) {
            l.onWindowFocusChanged(hasFocus);
        }
        log("onWindowFocusChanged");
    }

    // endregion ActivityEventListener Methods

}
