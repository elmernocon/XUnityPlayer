package com.unity3d.xample;

import com.unity3d.xplayer.ActivityEventListener;
import com.unity3d.xplayer.GameObjectListener;

public final class XampleActivity extends ActivityEventListener {

    // region Fields

    private GameObjectListener listener;

    // endregion Fields

    // region Constructor

    public XampleActivity() {
        // Create a GameObject listener named
        // 'com.unity3d.xample.XampleActivity Listener' that will
        // receive messages through its 'OnMessage(string s)' method.
        listener = new GameObjectListener(
                "com.unity3d.xample.XampleActivity Listener",
                "OnMessage");
    }

    // endregion Constructor

    // region ActivityEventListener Methods

    @Override
    public void onUserInteraction() {
        // Sends a message to the listener whenever
        // the user interacts with the app.
        listener.sendMessage("Hello from XampleActivity!");
    }

    // endregion ActivityEventListener Methods

}
