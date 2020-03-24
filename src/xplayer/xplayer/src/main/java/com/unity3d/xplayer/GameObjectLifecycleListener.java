package com.unity3d.xplayer;

import android.os.Bundle;

public final class GameObjectLifecycleListener extends GameObjectListener {

    // region Statics

    public static final String DEFAULT_NAME = "android.app.Activity Lifecycle Listener";

    public static final String DEFAULT_METHOD_NAME = "OnMessage";

    // endregion Statics

    // region Constructor

    public GameObjectLifecycleListener() {
        super(DEFAULT_NAME, DEFAULT_METHOD_NAME);
    }

    // endregion Constructor

    // region ActivityEventListener Methods

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        sendEvent("onCreate");
    }

    @Override
    public final void onRestart() {
        sendEvent("onRestart");
    }

    @Override
    public final void onStart() {
        sendEvent("onRestart");
    }

    @Override
    public final void onResume() {
        sendEvent("onResume");
    }

    @Override
    public final void onPause() {
        sendEvent("onPause");
    }

    @Override
    public final void onStop() {
        sendEvent("onStop");
    }

    @Override
    public final void onDestroy() {
        sendEvent("onDestroy");
    }

    // endregion ActivityEventListener Methods

}
