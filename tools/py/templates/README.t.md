# ${project_name}

[//]: # (badges)
[![Version](https://img.shields.io/badge/version-${version}-brightgreen.svg)](${repo_link}/releases)
[![UnityVersion](https://img.shields.io/badge/unity-${unity_version}-${badge_color}.svg)](${unitywhatsnew_link}/${unity_version})
[![License](https://img.shields.io/badge/license-${license_type}-${badge_color}.svg)](${license_file})

## Description

Based on [MartinGonzalez/UnityPlayerActivity](https://github.com/MartinGonzalez/UnityPlayerActivityListener).

This project looks to solve merging multiple Android plugins that all extend
from `com.unity3d.player.UnityPlayerActivity`.

## Installation

Add the `com.unity3d.xplayer.aar` file in the `Assets/Plugins/Android` folder.

In your `AndroidManifest.xml` file replace `com.unity3d.player.UnityPlayerActivity`
with `com.unity3d.xplayer.XUnityPlayerActivity`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="${applicationId}">
    <application
        android:label="@string/app_name"
        android:icon="@mipmap/app_icon">
        <!--
            Replaced 'com.unity3d.player.UnityPlayerActivity'
                with 'com.unity3d.xplayer.XUnityPlayerActivity'.
        -->
        <activity
            android:name="com.unity3d.xplayer.XUnityPlayerActivity"
            android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

## Verifying Installation

Logging is done through the `android.util.Log` class which can be viewed using
the `LogCat` command-line tool.

Helps in checking if the listeners you've added are instantiated.

Logs by the `XUnityPlayerActivity` class.

```
I/XUnityPlayerActivity: Initialization started.
V/XUnityPlayerActivity: Found 'com.unity3d.xample.XampleActivity' class.
I/XUnityPlayerActivity: Created 'com.unity3d.xample.XampleActivity' instance.
V/XUnityPlayerActivity: Found 'com.unity3d.xplayer.GameObjectAllListener' class.
I/XUnityPlayerActivity: Created 'com.unity3d.xplayer.GameObjectAllListener' instance.
V/XUnityPlayerActivity: Found 'com.unity3d.deeplinking.DeepLinkingActivity' class.
I/XUnityPlayerActivity: Created 'com.unity3d.deeplinking.DeepLinkingActivity' instance.
I/XUnityPlayerActivity: Initialization finished.
```

> You should be able to see the listener classes created.

Logs by the `ActivityEventDispatcher` class.

```
I/ActivityEventDispatcher: setIntent
I/ActivityEventDispatcher: setContext
I/ActivityEventDispatcher: onCreate
I/ActivityEventDispatcher: onStart
I/ActivityEventDispatcher: onResume
I/ActivityEventDispatcher: onWindowFocusChanged
I/ActivityEventDispatcher: onUserInteraction
I/ActivityEventDispatcher: onUserInteraction
I/ActivityEventDispatcher: onUserInteraction
I/ActivityEventDispatcher: onPause
I/ActivityEventDispatcher: onWindowFocusChanged
I/ActivityEventDispatcher: onTrimMemory
I/ActivityEventDispatcher: onStop
I/ActivityEventDispatcher: onSaveInstanceState
I/ActivityEventDispatcher: onRestart
I/ActivityEventDispatcher: onStart
I/ActivityEventDispatcher: onResume
I/ActivityEventDispatcher: onWindowFocusChanged
I/ActivityEventDispatcher: onUserInteraction
I/ActivityEventDispatcher: onWindowFocusChanged
I/ActivityEventDispatcher: onWindowFocusChanged
I/ActivityEventDispatcher: onPause
I/ActivityEventDispatcher: onStop
I/ActivityEventDispatcher: onSaveInstanceState
I/ActivityEventDispatcher: onWindowFocusChanged
I/ActivityEventDispatcher: onTrimMemory
I/ActivityEventDispatcher: onRestart
I/ActivityEventDispatcher: onStart
I/ActivityEventDispatcher: onResume
I/ActivityEventDispatcher: onWindowFocusChanged
```

## Events

Included are `GameObjectListener`, `GameObjectLifecycleListener`, `GameObjectAllListener` classes
which wraps around the `UnityPlayer.UnitySendMessage(String gameObjectName, String methodName, String msg)` method.

You can use `GameObjectLifecycleListener` or `GameObjectAllListener` by
adding one of these snippets to your `AndroidManifest.xml` file.

```xml
<!-- Add GameObjectLifecycleListener instance. -->
<meta-data
    android:name="com.unity3d.listener.lifecycle"
    android:value="com.unity3d.xplayer.GameObjectLifecycleListener" />
```

```xml
<!-- Add GameObjectAllListener instance. -->
<meta-data
    android:name="com.unity3d.listener.all"
    android:value="com.unity3d.xplayer.GameObjectAllListener" />
```

it should look like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="${applicationId}">
    <application
        android:label="@string/app_name"
        android:icon="@mipmap/app_icon">
        <activity>...</activity>

        <!-- Add GameObjectAllListener instance. -->
        <meta-data
            android:name="com.unity3d.listener.all"
            android:value="com.unity3d.xplayer.GameObjectAllListener" />

    </application>
</manifest>
```

You can receive these events in Unity by creating a GameObject with a name matching
the one specified by the listener class and has a Monobehaviour component that
contains the specified method.

For `GameObjectLifecycleListener`
- `android.app.Activity Lifecycle Listener`
- `public void OnMessage(string msg)`

For `GameObjectAllListener`
- `android.app.Activity Listener`
- `public void OnMessage(string msg)`

Events emitted by the `GameObjectAllListener` class received by a GameObject in Unity.

```json
{"event":"setIntent"}
{"event":"setContext"}
{"event":"onCreate"}
{"event":"onStart"}
{"event":"onResume"}
{"event":"onWindowFocusChanged","data":{"hasFocus":true}}
{"event":"onUserInteraction"}
{"event":"onUserInteraction"}
{"event":"onUserInteraction"}
{"event":"onPause"}
{"event":"onWindowFocusChanged","data":{"hasFocus":false}}
{"event":"onTrimMemory","data":{"level":20}}
{"event":"onStop"}
{"event":"onSaveInstanceState"}
{"event":"onRestart"}
{"event":"onStart"}
{"event":"onResume"}
{"event":"onWindowFocusChanged","data":{"hasFocus":true}}
{"event":"onUserInteraction"}
{"event":"onWindowFocusChanged","data":{"hasFocus":false}}
{"event":"onWindowFocusChanged","data":{"hasFocus":true}}
{"event":"onPause"}
{"event":"onStop"}
{"event":"onSaveInstanceState"}
{"event":"onWindowFocusChanged","data":{"hasFocus":false}}
{"event":"onTrimMemory","data":{"level":20}}
{"event":"onRestart"}
{"event":"onStart"}
{"event":"onResume"}
{"event":"onWindowFocusChanged","data":{"hasFocus":true}}
```

You can create your own `GameObjectListener`

```java
// Create a GameObject listener named
// 'com.unity3d.xample.XampleActivity Listener' that will
// receive messages through its 'OnMessage(string s)' method.
GameObjectListener listener = new GameObjectListener(
        "com.unity3d.xample.XampleActivity Listener",
        "OnMessage");
```

and send messages to it.

```java
// Sends a message to the listener.
listener.sendMessage("Hello from XampleActivity!");
```

You receive messages in Unity by creating a C# class that inherits
from `MonoBehaviour` and has a `public void` method with a single `string` parameter.
The name of the class and the parameter name is not important,
only the name of the GameObject it is attached to and the method name.

```csharp
using UnityEngine;

public class Listener : MonoBehaviour
{
    public void OnMessage(string message)
    {
       // Do something.
    }
}
```

## Creating you own Listener or Modifying an existing Activity.

Included is an Android Studio project `deeplinking` that you can use as a reference.

This project contains the class `DeepLinkingActivity` which forwards deep links
to Unity through a GameObject named `com.unity3d.deeplinking.DeepLinkingActivity Listener`
with a `public void OnMessage(string msg)` method.

Logs by the `DeepLinkingActivity` class.

```
I/DeepLinkingActivity: onDeepLink: example://deeplink/home?id=0
```

Events emitted by the `DeepLinkingActivity` class received by a GameObject in Unity.

```json
{"event":"onDeepLink","data":{"deepLink":"example:\/\/deeplink\/home?id=0"}}
```

### Steps

1. Create an Android Library Module.

2. Add the snippet below in the `dependencies` section in the `build.gradle` file included in your module.

    ```gradle
    compileOnly files('../relative_path_to/com.unity3d.xplayer.aar')
    ```

    it should look like this:

    ```gradle
    dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar'])

        implementation 'androidx.appcompat:appcompat:1.0.2'
        testImplementation 'junit:junit:4.12'
        androidTestImplementation 'androidx.test.ext:junit:1.1.0'
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'

        compileOnly files('../../../refs/xplayer/com.unity3d.xplayer.aar')
    }
    ```

3. Create a class that extends from `com.unity3d.xplayer.ActivityEventListener`.

    ```java
    package com.companyname.mypackage;

    import com.unity3d.xplayer.ActivityEventListener;

    public final class MyActivity extends ActivityEventListener {

        public MyActivity() {
            // Make sure you have an empty constructor.
        }

        @Override
        public void onUserInteraction() {
            // Override methods as needed.
        }

    }
    ```

4. Add the snippet below in the `AndroidManifest.xml` file included in your module.

    ```xml
    <meta-data
        android:name="com.unity3d.listener.your_module_name"
        android:value="full_class_name" />
    ```

    it should look like this:

    ```xml
    <manifest
        xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.companyname.mypackage">
        <application>

            <meta-data
                android:name="com.unity3d.listener.mypackage"
                android:value="com.companyname.mypackage.MyActivity" />

        </application>
    </manifest>
    ```

5. Build and add the `.aar` file into you `Assets/Plugins/Android` folder.

### How It Works

```java
package com.unity3d.xplayer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

...

// Return PackageManager instance to find global package information.
PackageManager packageManager = context.getPackageManager();

// Retrieve all of the information we know about a particular package/application.
ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
        context.getPackageName(),
        PackageManager.GET_META_DATA);

// Get the additional meta-data associated with this component.
Bundle bundle = applicationInfo.metaData;

// Returns a Set containing the Strings used as keys in this Bundle.
for (String key : bundle.keySet()) {

    //  <meta-data
    //      android:name="com.unity3d.listener.xample"
    //      android:value="com.unity3d.xample.XampleActivity" />

    //  <meta-data
    //      android:name="com.unity3d.listener.mypackage"
    //      android:value="com.companyname.mypackage.MyActivity" />

    // Filter out meta-data(s) by its key (name) and whose value is not a String.
    if (!isListener(key)        // checks if android:name starts with "com.unity3d.listener."
     && !isString(value)) {     // checks if android:value is a String.
        continue;
    }

    // Returns the Class object associated with the class with the given name.
    Class cls = Class.forName((String) value);

    // Filter out classes that don't extend from ActivityEventListener.
    if (!isListenerClass(cls)) {
        continue;
    }

    // Create instance through reflection.
    ActivityEventListener listener = (ActivityEventListener) cls.newInstance();

    // Save instance for later.
    doSomething(listener);
}

...

@Override
protected final void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Forward event to listeners.
    for (ActivityEventListener l : listeners) {
        l.onCreate(savedInstanceState);
    }
}

...
```

### Limitations

* An activity can not override a method completely.

    ```java
    @Override
    public void onBackPressed() {
        // Not calling super.onBackPressed() does not
        // ignore the back button event.
        // super.onBackPressed();
    }
    ```

## Contribution

Suggestions and contributions are always welcome.
Make sure to read the [Contribution Guidelines](${contributing_file}) file for more information before submitting a `${merge_term}`.

## License

`${project_name}` is released under the ${license_type} License. See the [LICENSE](${license_file}) file for details.
