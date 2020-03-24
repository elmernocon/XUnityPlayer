package com.unity3d.xplayer;

import android.util.Log;

import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class GameObjectListener extends ActivityEventListener {

    // region Statics

    private static final String LOG_TAG = "GameObjectListener";

    // endregion Statics

    // region Fields

    private String name;

    private String methodName;

    // endregion Fields

    // region Constructor

    public GameObjectListener() {

    }

    public GameObjectListener(
            String name,
            String methodName
    ) {
        setName(name);
        setMethodName(methodName);
    }

    // endregion Constructor

    // region Properties

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final GameObjectListener withName(String name) {
        setName(name);
        return this;
    }

    public final String getMethodName() {
        return this.methodName;
    }

    public final void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public final GameObjectListener withMethodName(String methodName) {
        setMethodName(methodName);
        return this;
    }

    // endregion Properties

    // region Methods

    public final void sendMessage(String msg) {
        String name = getName();
        String methodName = getMethodName();

        if (name == null || name.isEmpty()) {
            Log.e(LOG_TAG, "Name is null or empty.");
            return;
        }

        if (methodName == null || methodName.isEmpty()) {
            Log.e(LOG_TAG, "MethodName is null or empty.");
            return;
        }

        if (msg == null || msg.isEmpty()) {
            Log.w(LOG_TAG, "msg is null or empty.");
        }

        UnityPlayer.UnitySendMessage(name, methodName, msg);
    }

    public void sendBooleanVariable(String name, boolean value) throws JSONException {
        sendJson(new JSONObject()
                .put(name, value));
    }

    public void sendCharVariable(String name, char value) throws JSONException {
        sendJson(new JSONObject()
                .put(name, String.valueOf(value)));
    }

    public void sendDoubleVariable(String name, double value) throws JSONException {
        sendJson(new JSONObject()
                .put(name, value));
    }

    public void sendFloatVariable(String name, float value) throws JSONException {
        sendJson(new JSONObject()
                .put(name, value));
    }

    public void sendIntVariable(String name, int value) throws JSONException {
        sendJson(new JSONObject()
                .put(name, value));
    }

    public void sendLongVariable(String name, long value) throws JSONException {
        sendJson(new JSONObject()
                .put(name, value));
    }

    public void sendStringVariable(String name, String value) {
        try {
            sendJson(new JSONObject()
                .put(name, value));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed to create JSONObject.");
        }
    }

    public void sendJson(JSONObject jsonObject) {
        sendMessage(jsonObject.toString());
    }

    public void sendEvent(String eventName) {
        sendEvent(new Event(eventName));
    }

    public void sendEvent(Event event) {
        sendMessage(event.toString());
    }

    // endregion Methods

}
