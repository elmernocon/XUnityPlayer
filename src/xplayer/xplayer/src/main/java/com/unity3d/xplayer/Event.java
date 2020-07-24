package com.unity3d.xplayer;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Event {

    // region Statics

    private static final String LOG_TAG = "Event";

    private static final String EVENT = "event";

    private static final String DATA = "data";

    public static int INDENT_SPACES = 4;

    // endregion Statics

    // region Fields

    private JSONObject name;

    private JSONObject data;

    // endregion Fields

    // region Constructor

    public Event(String name, JSONObject data) {
        this.name = new JSONObject();
        try {
            this.name.put(EVENT, name);
        } catch (JSONException e) {
            catchJSONPutException(EVENT, e);
        }
        if (data != null) {
            this.data = data;
            addDataToEvent();
        }
    }

    public Event(String name) {
        this(name, null);
    }

    // endregion Constructor

    // region Methods

    public Event addValueToData(String name, boolean value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, char value) {
        initData();
        try {
            this.data.put(name, Character.toString(value));
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, double value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, float value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, int value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, long value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, String value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, JSONArray value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValueToData(String name, JSONObject value) {
        initData();
        try {
            this.data.put(name, value);
        } catch (JSONException e) {
            catchJSONPutException(name, e);
        }
        return this;
    }

    public Event addValuesToData(String name, boolean[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (boolean v : values) {
            array.put(v);
        }
        return addValueToData(name, array);
    }

    public Event addValuesToData(String name, char[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (char v : values) {
            array.put(String.valueOf(v));
        }
        return addValueToData(name, array);
    }

    public Event addValuesToData(String name, double[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (double v : values) {
            try {
                array.put(v);
            } catch (JSONException e) {
                catchJSONPutException(String.valueOf(v), e);
            }
        }
        return addValueToData(name, array);
    }

    public Event addValuesToData(String name, float[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (float v : values) {
            try {
                array.put(v);
            } catch (JSONException e) {
                catchJSONPutException(String.valueOf(v), e);
            }
        }
        return addValueToData(name, array);
    }

    public Event addValuesToData(String name, int[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (int v : values) {
            array.put(v);
        }
        return addValueToData(name, array);
    }

    public Event addValuesToData(String name, long[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (long v : values) {
            array.put(v);
        }
        return addValueToData(name, array);
    }

    public Event addValuesToData(String name, String[] values) {
        initData();
        JSONArray array = new JSONArray();
        for (String v : values) {
            array.put(v);
        }
        return addValueToData(name, array);
    }

    @NonNull
    @Override
    public String toString() {
        return toString(INDENT_SPACES);
    }

    public String toString(int indentSpaces) {
        try {
            return this.name.toString(indentSpaces);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return "";
    }

    private void initData() {
        if (this.data != null) {
            return;
        }
        this.data = new JSONObject();
        addDataToEvent();
    }

    private void addDataToEvent() {
        try {
            this.name.put(DATA, data);
        } catch (JSONException e) {
            catchJSONPutException(DATA, e);
        }
    }

    private void catchJSONPutException(String name, JSONException e) {
        Log.e(LOG_TAG, "Failed to put '" + name + "' value.", e);
    }

    // endregion Methods

}
