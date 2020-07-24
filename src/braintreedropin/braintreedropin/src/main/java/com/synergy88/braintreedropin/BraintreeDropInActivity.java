package com.synergy88.braintreedropin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.dropin.utils.PaymentMethodType;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.unity3d.player.UnityPlayer;
import com.unity3d.xplayer.ActivityEventListener;
import com.unity3d.xplayer.Event;
import com.unity3d.xplayer.GameObjectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class BraintreeDropInActivity extends ActivityEventListener {

    // region Statics

    public static final String LOG_TAG = "BraintreeDropInActivity";

    private static final int BRAINTREE_DROPIN_REQUEST = "BraintreeDropInRequest".hashCode();

    private static WeakReference<BraintreeDropInActivity> instance;

    public static BraintreeDropInActivity getInstance() {
        return instance.get();
    }

    // endregion Statics

    // region Fields

    private GameObjectListener listener;

    // endregion Fields

    // region Constructor

    public BraintreeDropInActivity() {
        // Create a GameObject listener named
        // 'com.synergy88.braintreedropin.BraintreeDropInActivity Listener' that will
        // receive messages through its 'OnMessage(string s)' method.
        listener = new GameObjectListener(
                "com.synergy88.braintreedropin.BraintreeDropInActivity Listener",
                "OnMessage");
    }

    // endregion Constructor

    // region Methods

    public void openDropIn(String clientToken) {
        Activity activity = UnityPlayer.currentActivity;

        if (activity == null) {
            onDropInError("Failed to get Activity.");
            return;
        }

        DropInRequest dropInRequest = new DropInRequest();
        dropInRequest.clientToken(clientToken);

        Intent intent = dropInRequest.getIntent(activity);

        activity.startActivityForResult(intent, BRAINTREE_DROPIN_REQUEST);
    }

    private void onDropInResult(DropInResult result) {
        Event event = new Event("onDropInResult");

        String deviceData = result.getDeviceData();
        event.addValueToData("deviceData", deviceData);

        try {
            PaymentMethodType pmt = result.getPaymentMethodType();
            String pmtName = pmt != null ? pmt.name() : "";
            String pmtCanonicalName = pmt != null ? pmt.getCanonicalName() : "";

            JSONObject paymentMethodType = new JSONObject();
            paymentMethodType.put("name", pmtName);
            paymentMethodType.put("canonicalName", pmtCanonicalName);

            event.addValueToData("paymentMethodType", paymentMethodType);
        } catch (JSONException e) {
            log(e.getMessage(), Log.ERROR);
        }

        try {
            PaymentMethodNonce pmn = result.getPaymentMethodNonce();
            String pmnNonce = pmn != null ? pmn.getNonce() : "";
            String pmnDescription = pmn != null ? pmn.getDescription() : "";
            String pmnTypeLabel = pmn != null ? pmn.getTypeLabel() : "";
            boolean pmnIsDefault = pmn != null && pmn.isDefault();

            JSONObject paymentMethodNonce = new JSONObject();

            paymentMethodNonce.put("nonce", pmnNonce);
            paymentMethodNonce.put("description", pmnDescription);
            paymentMethodNonce.put("typeLabel", pmnTypeLabel);
            paymentMethodNonce.put("isDefault", pmnIsDefault);

            event.addValueToData("paymentMethodNonce", paymentMethodNonce);

        } catch (JSONException e) {
            log(e.getMessage(), Log.ERROR);
        }

        listener.sendEvent(event);
    }

    private void onDropInError(String error) {
        listener.sendEvent(new Event("onDropInError")
                .addValueToData("error", error));
    }

    // endregion Methods

    // region ActivityEventListener Methods

    @Override
    protected void log(String msg, int priority) {
        log(msg, priority, LOG_TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        instance = new WeakReference<>(this);
    }

    @Override
    public void onDestroy() {
        instance.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BRAINTREE_DROPIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                if (result != null) {
                    onDropInResult(result);
                } else {
                    onDropInError("Failed to get result.");
                }

            } else if (resultCode == RESULT_CANCELED) {
                onDropInError("User cancelled.");
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                String errorMessage = error != null ? error.getMessage() : "error";
                onDropInError(errorMessage);
                log(errorMessage, Log.ERROR);
            }
        }
    }

    // endregion ActivityEventListener Methods

}
