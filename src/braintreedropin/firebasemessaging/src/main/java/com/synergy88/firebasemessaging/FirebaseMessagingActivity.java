package com.synergy88.firebasemessaging;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.MessageForwardingService;
import com.unity3d.player.UnityPlayer;
import com.unity3d.xplayer.ActivityEventListener;

public class FirebaseMessagingActivity extends ActivityEventListener {

    // region Statics

    public static final String LOG_TAG = "FirebaseMessagingActivity";

    // endregion Statics

    // region Constructor

    public FirebaseMessagingActivity() {
    }

    // endregion Constructor

    // region ActivityEventListener Methods

    @Override
    protected void log(String msg, int priority) {
        log(msg, priority, LOG_TAG);
    }

    @Override
    public void onPreCreate(Bundle savedInstanceState) {
        try {
            // Dispose of the mUnityPlayer when restarting the app.
            //
            // This ensures that when the app starts up again it does not start with stale data.
            getActivity().quitUnityPlayer();
        } catch (Exception ex) {
            log("Failed to quit UnityPlayer. " + ex.getMessage(), Log.ERROR);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        try {
            // Workaround for when a message is sent containing both a Data and Notification payload.
            //
            // When the app is in the background, if a message with both a data and notification payload is
            // received the data payload is stored on the Intent passed to onNewIntent. By default, that
            // intent does not get set as the Intent that started the app, so when the app comes back online
            // it doesn't see a new FCM message to respond to. As a workaround, we override onNewIntent so
            // that it sends the intent to the MessageForwardingService which forwards the message to the
            // FirebaseMessagingService which in turn sends the message to the application.
            String action = MessageForwardingService.ACTION_REMOTE_INTENT;
            Intent message = new Intent(getContext(), MessageForwardingService.class);
            message.setAction(action);
            message.putExtras(intent);
            message.setData(intent.getData());
            UnityPlayer.currentActivity.startService(message);
            log("Started 'MessageForwardingService'.");
        } catch (Exception ex) {
            log("Failed to start 'MessageForwardingService'. " + ex.getMessage(), Log.ERROR);
        }
    }

    // endregion ActivityEventListener Methods

}
