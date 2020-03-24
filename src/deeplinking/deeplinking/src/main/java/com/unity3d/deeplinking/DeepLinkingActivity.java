package com.unity3d.deeplinking;

import android.content.Intent;

import com.unity3d.xplayer.ActivityEventListener;
import com.unity3d.xplayer.Event;
import com.unity3d.xplayer.GameObjectListener;

public class DeepLinkingActivity extends ActivityEventListener {

    // region Statics

    public static final String LOG_TAG = "DeepLinkingActivity";

    public static String extractDeepLink(Intent intent) {
        return isDeepLink(intent) ? intent.getDataString() : null;
    }

    public static boolean isDeepLink(Intent intent) {
        return intent != null && Intent.ACTION_VIEW.equals(intent.getAction());
    }

    // endregion Statics

    // region Fields

    private GameObjectListener listener;

    // endregion Fields

    // region Constructor

    public DeepLinkingActivity() {
        // Create a GameObject listener named
        // 'com.unity3d.deeplinking.DeepLinkingActivity Listener' that will
        // receive messages through its 'OnMessage(string s)' method.
        listener = new GameObjectListener(
                "com.unity3d.deeplinking.DeepLinkingActivity Listener",
                "OnMessage");
    }

    // endregion Constructor

    // region ActivityEventListener Methods

    @Override
    protected void log(String msg, int priority) {
        log(msg, priority, LOG_TAG);
    }

    @Override
    protected final void onSetIntent(Intent intent) {
        String deepLink = extractDeepLink(intent);

        if (deepLink == null || deepLink.isEmpty()) {
            return;
        }

        log("onDeepLink: " + deepLink);

        // Creates an event.
        // {
        //     "event": "onDeepLink",
        //     "data": {
        //         "deepLink": "<LINK>"
        //     }
        // }
        Event event = new Event("onDeepLink")
                .addValueToData("deepLink", deepLink);

        // Sends event to the listener.
        if (listener != null) {
            listener.sendEvent(event);
        }
    }

    // endregion ActivityEventListener Methods

}
