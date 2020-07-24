package com.unity3d.xplayer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public final class GameObjectAllListener extends GameObjectListener {

    // region Statics

    public static final String DEFAULT_NAME = "android.app.Activity Listener";

    public static final String DEFAULT_METHOD_NAME = "OnMessage";

    // endregion Statics

    // region Constructor

    public GameObjectAllListener() {
        super(DEFAULT_NAME, DEFAULT_METHOD_NAME);
    }

    // endregion Constructor

    // region ActivityEventListener Methods

    @Override
    protected final void onSetContext(Context context) {
        sendEvent("onSetContext");
    }

    @Override
    protected final void onSetIntent(Intent intent) {
        sendEvent("onSetIntent");
    }

    @Override
    public final void onPreCreate(Bundle savedInstanceState) {
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        sendEvent("onCreate");
    }

    @Override
    public final void onPostCreate(@Nullable Bundle savedInstanceState) {
        sendEvent("onPostCreate");
    }

    @Override
    public final void onPreRestart() {
    }

    @Override
    public final void onRestart() {
        sendEvent("onRestart");
    }

    @Override
    public final void onPreStart() {
    }

    @Override
    public final void onStart() {
        sendEvent("onRestart");
    }

    @Override
    public final void onPreResume() {
    }

    @Override
    public final void onResume() {
        sendEvent("onResume");
    }

    @Override
    public final void onPostResume() {
        sendEvent("onPostResume");
    }

    @Override
    public final void onPrePause() {
    }

    @Override
    public final void onPause() {
        sendEvent("onPause");
    }

    @Override
    public final void onPreStop() {
    }

    @Override
    public final void onStop() {
        sendEvent("onStop");
    }

    @Override
    public final void onPreDestroy() {
    }

    @Override
    public final void onDestroy() {
        sendEvent("onDestroy");
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        sendEvent(new Event("onActivityResult")
                .addValueToData("requestCode", requestCode)
                .addValueToData("resultCode", resultCode));
    }

    @Override
    public final void onBackPressed() {
        sendEvent("onBackPressed");
    }

    @Override
    public final void onConfigurationChanged(Configuration configuration) {
        sendEvent("onConfigurationChanged");
    }

    @Override
    public final void onGenericMotionEvent(MotionEvent event) {
        sendEvent("onGenericMotionEvent");
    }

    @Override
    public final void onKeyDown(int keyCode, KeyEvent event) {
        sendEvent(new Event("onKeyDown")
                .addValueToData("keyCode", keyCode));
    }

    @Override
    public final void onKeyUp(int keyCode, KeyEvent event) {
        sendEvent(new Event("onKeyUp")
                .addValueToData("keyCode", keyCode));
    }

    @Override
    public final void onLowMemory() {
        sendEvent("onLowMemory");
    }

    @Override
    public final void onNewIntent(Intent intent) {
        sendEvent("onNewIntent");
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        sendEvent(new Event("onRequestPermissionsResult")
                .addValueToData("requestCode", requestCode)
                .addValuesToData("permissions", permissions)
                .addValuesToData("grantResults", grantResults));
    }

    @Override
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        sendEvent("onRestoreInstanceState");
    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        sendEvent("onSaveInstanceState");
    }

    @Override
    public final void onTrimMemory(int level) {
        sendEvent(new Event("onTrimMemory")
                .addValueToData("level", level));
    }

    @Override
    public final void onTouchEvent(MotionEvent event) {
        sendEvent("onTouchEvent");
    }

    @Override
    public final void onUserInteraction() {
        sendEvent("onUserInteraction");
    }

    @Override
    public final void onUserLeaveHint() {
        sendEvent("onUserLeaveHint");
    }

    @Override
    public final void onWindowFocusChanged(boolean hasFocus) {
        sendEvent(new Event("onWindowFocusChanged")
                .addValueToData("hasFocus", hasFocus));
    }

    // endregion ActivityEventListener Methods
}
