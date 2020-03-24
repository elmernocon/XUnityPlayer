package com.unity3d.xplayer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import com.unity3d.player.UnityPlayerActivity;

public final class XUnityPlayerActivity extends UnityPlayerActivity {

    // region Statics

    public static final int LOG_PRIORITY = Log.INFO;

    public static final String LOG_TAG = "XUnityPlayerActivity";

    public static final String LISTENER_PREFIX = "com.unity3d.listener.";

    public static ActivityEventListener createListener(String className) {

        // Get class instance from the className.
        Class cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, "Class '" + className + "' was not found.", e);
            return null;
        }

        // Check if class is a listener class.
        if (!isListenerClass(cls)) {
            Log.e(LOG_TAG, "Class '" + className + "' is not assignable from ActivityEventListener.");
            return null;
        }

        // Create an instance of the class.
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "Unable to instantiate an instance of '" + className +
                    "' using reflection due to having insufficient access.");
            return null;
        } catch (InstantiationException e) {
            Log.e(LOG_TAG, "Unable to instantiate an instance of '" + className +
                    "' due to:" +
                    " (1) the class either being an abstract class, interface, array class, primitive type, or void." +
                    " (2) the class not having a nullary constructor.");
            return null;
        }

        // Cast the object instance into an ActivityEventListener instance.
        ActivityEventListener listener;
        try {
            listener = (ActivityEventListener) instance;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, "Unable to cast an instance of '" + className +
                    "' into an instance of 'ActivityEventListener'.", e);
            return null;
        }

        return listener;
    }

    public static String[] getListenerClassNames(Context context) {
        List<String> classNames = new ArrayList<>();

        // Get packager manager.
        PackageManager packageManager = context.getPackageManager();

        // Try getting the application info from the package manager.
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG,"Failed to get the application info.", e);
        }

        if (applicationInfo != null) {

            // Get the bundle meta-data(s) from the application info.
            Bundle bundle = applicationInfo.metaData;

            if (bundle != null) {

                for (String key : bundle.keySet()) {

                    // Filter out meta-data(s) by its key (name).
                    if (!isListener(key)) {
                        continue;
                    }

                    Object value = bundle.get(key);

                    // Filter out meta-data(s) whose value is not a String.
                    if (!isString(value)) {
                        continue;
                    }

                    String className = (String) value;

                    // Filter out invalid values.
                    if (className.isEmpty()) {
                        continue;
                    }

                    classNames.add(className);
                }
            }
        }

        return classNames.toArray(new String[0]);
    }

    private static boolean isListener(String string) {
        return string.startsWith(LISTENER_PREFIX);
    }

    private static boolean isListenerClass(Class cls) {
        return ActivityEventListener.class.isAssignableFrom(cls);
    }

    private static boolean isString(Object object) {
        return object instanceof String;
    }

    // endregion Statics

    // region Fields

    private boolean isInitialized;

    private ActivityEventDispatcher dispatcher;

    // endregion Fields

    // region Constructor

    public XUnityPlayerActivity() {
        isInitialized = false;
    }

    // endregion Constructor

    // region Methods

    public final ActivityEventListener findListener(String className) {
        return dispatcher != null
                ? dispatcher.findListener(className)
                : null;
    }

    protected final void init() {
        if (isInitialized) {
            return;
        }

        log("Initialization started.");

        // Search listeners found in the AndroidManifest.xml file.
        String[] classNames = getListenerClassNames(this);

        // Create instances of the listeners.
        List<ActivityEventListener> listeners = new ArrayList<>();
        for (String className : classNames) {
            log("Found '" + className + "' class.", Log.VERBOSE);
            ActivityEventListener listener = createListener(className);
            if (listener == null) {
                continue;
            }
            log("Created '" + className + "' instance.");
            listeners.add(listener);
        }

        // Create dispatcher.
        dispatcher = new ActivityEventDispatcher(listeners.toArray(new ActivityEventListener[0]));

        isInitialized = true;

        log("Initialization finished.");
    }

    protected final void log(String msg, int priority, String tag) {
        Log.println(priority, tag, msg);
    }

    protected final void log(String msg, int priority) {
        log(msg, priority, LOG_TAG);
    }

    protected final void log(String msg) {
        log(msg, LOG_PRIORITY);
    }

    // endregion Methods

    // region Activity Methods

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (dispatcher != null) {
            dispatcher.setIntent(getIntent());
            dispatcher.setContext(this);
            dispatcher.onCreate(savedInstanceState);
        }
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
        if (dispatcher != null) {
            dispatcher.onRestart();
        }
    }

    @Override
    protected final void onStart() {
        super.onStart();
        if (dispatcher != null) {
            dispatcher.onStart();
        }
    }

    @Override
    protected final void onResume() {
        super.onResume();
        if (dispatcher != null) {
            dispatcher.onResume();
        }
    }

    @Override
    protected final void onPause() {
        super.onPause();
        if (dispatcher != null) {
            dispatcher.onPause();
        }
    }

    @Override
    protected final void onStop() {
        super.onStop();
        if (dispatcher != null) {
            dispatcher.onStop();
        }
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        if (dispatcher != null) {
            dispatcher.onStop();
        }
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dispatcher != null) {
            dispatcher.onActivityResult(
                    requestCode,
                    resultCode,
                    data
            );
        }
    }

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        if (dispatcher != null) {
            dispatcher.onBackPressed();
        }
    }

    @Override
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (dispatcher != null) {
            dispatcher.onConfigurationChanged(configuration);
        }
    }

    @Override
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        boolean value = super.onGenericMotionEvent(motionEvent);
        if (dispatcher != null) {
            dispatcher.onGenericMotionEvent(motionEvent);
        }
        return value;
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        boolean value = super.onKeyDown(keyCode, keyEvent);
        if (dispatcher != null) {
            dispatcher.onKeyDown(
                    keyCode,
                    keyEvent
            );
        }
        return value;
    }

    @Override
    public final boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        boolean value = super.onKeyUp(keyCode, keyEvent);
        if (dispatcher != null) {
            dispatcher.onKeyUp(
                    keyCode,
                    keyEvent
            );
        }
        return value;
    }

    @Override
    public final void onLowMemory() {
        super.onLowMemory();
        if (dispatcher != null) {
            dispatcher.onLowMemory();
        }
    }

    @Override
    protected final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (dispatcher != null) {
            dispatcher.setIntent(intent);
            dispatcher.onNewIntent(intent);
        }
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (dispatcher != null) {
            dispatcher.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults
            );
        }
    }

    @Override
    protected final void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (dispatcher != null) {
            dispatcher.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected final void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dispatcher != null) {
            dispatcher.onSaveInstanceState(outState);
        }
    }

    @Override
    public final void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (dispatcher != null) {
            dispatcher.onTrimMemory(level);
        }
    }

    @Override
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean value = super.onTouchEvent(motionEvent);
        if (dispatcher != null) {
            dispatcher.onTouchEvent(motionEvent);
        }
        return value;
    }

    @Override
    public final void onUserInteraction() {
        super.onUserInteraction();
        if (dispatcher != null) {
            dispatcher.onUserInteraction();
        }
    }

    @Override
    protected final void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (dispatcher != null) {
            dispatcher.onUserLeaveHint();
        }
    }

    @Override
    public final void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (dispatcher != null) {
            dispatcher.onWindowFocusChanged(hasFocus);
        }
    }

    // endregion Activity Methods

}
