package com.unity3d.xplayer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class ActivityEventListener {

    // region Statics

    private static final int LOG_PRIORITY = Log.INFO;

    private static final String LOG_TAG = "ActivityEventListener";

    // endregion Statics

    // region Fields

    private Context context;

    private Intent intent;

    // endregion Fields

    // region Properties

    protected final Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
        onSetContext(context);
    }

    protected void onSetContext(Context context) {}

    protected final Intent getIntent() {
        return intent;
    }

    protected void setIntent(Intent intent) {
        this.intent = intent;
        onSetIntent(intent);
    }

    protected void onSetIntent(Intent intent) {}

    // endregion Properties

    // region Methods

    protected void log(String msg, int priority, String tag) {
        Log.println(priority, tag, msg);
    }

    protected void log(String msg, int priority) {
        log(msg, priority, LOG_TAG);
    }

    protected void log(String msg) {
        log(msg, LOG_PRIORITY);
    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onRestart() {

    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onBackPressed() {

    }

    public void onConfigurationChanged(Configuration configuration) {

    }

    public void onGenericMotionEvent(MotionEvent event) {

    }

    public void onKeyDown(int keyCode, KeyEvent event) {

    }

    public void onKeyUp(int keyCode, KeyEvent event) {

    }

    public void onLowMemory() {

    }

    public void onNewIntent(Intent intent) {

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onTrimMemory(int level) {

    }

    public void onTouchEvent(MotionEvent event) {

    }

    public void onUserInteraction() {

    }

    public void onUserLeaveHint() {

    }

    public void onWindowFocusChanged(boolean hasFocus) {

    }

    // endregion Methods
}
