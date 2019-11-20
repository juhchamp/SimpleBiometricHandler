# SimpleBiometricHandler
A simple library to handle biometric authentication in Android.

[![](https://jitpack.io/v/juhchamp/SimpleBiometricHandler.svg)](https://jitpack.io/#juhchamp/SimpleBiometricHandler)

## Download
Download the latest version via Gradle:

**Step 1.**
Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.**
Add the SimpleBiometricHandler dependency

```
dependencies {
  implementation 'com.github.juhchamp:SimpleBiometricHandler:1.0.0'
}
```

## Usage

**Step 1.**
Add permission to your manifest file

```java
 <uses-permission android:name="android.permission.USE_BIOMETRIC"
        android:requiredFeature="false"
        tools:targetApi="o" />
```

**Step 2.**
Set up a BiometricCallback to handle callbacks

```kotlin
  private val biometricCallback = object : BiometricCallback {

        override fun onBiometricNotEnrolled() {
            Toast.makeText(this@MainActivity, "onNoBiometricEnrolled",
                Toast.LENGTH_SHORT)
                .show()
        }

        override fun onBiometricUnavailable() {
            Toast.makeText(this@MainActivity, "onBiometricUnavailable",
                Toast.LENGTH_SHORT)
                .show()
        }

        override fun onNoBiometricHardware() {
            Toast.makeText(this@MainActivity, "onNoBiometricHardware",
                Toast.LENGTH_SHORT)
                .show()
        }

        override fun onAuthenticationError(errCode: Int, message: CharSequence) {
            Toast.makeText(this@MainActivity,
                "Authentication error: $message", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onAuthenticationFail() {
            Toast.makeText(this@MainActivity, "Authentication failed",
                Toast.LENGTH_SHORT)
                .show()
        }

        override fun onAuthenticationSuccess() {
            Toast.makeText(this@MainActivity, "Authentication success",
                Toast.LENGTH_SHORT)
                .show()
        }
    }
```

**Step 3.**
Call the BiometricHandler instance passing activity and set the other needed options or see ***Step 4*** to another way to use the Handler.
> You can see this in action on ***ExampleWithNoPreCheckActivity*** in the app project.

```kotlin
BiometricHandler(this)
    .setTitle("Payment action")
    .setSubtitle("youremail@test.com")
    .setDescription("Hello there, this is a test description for this test payment action.")
    .setNegativeButtonText("Cancel")
    .setHandlerCallback(biometricHandlerCallback)
    .show()
```

**Step 4.** ***( another way to use )***
Instantiating the handler and checking if can authenticate first.
> You can see this in action on ***ExampleWithPreCheckActivity*** in the app project.

```kotlin
// Create var for the handler calling the instance
private var biometricHandler: BiometricHandler = BiometricHandler(this)
// Create var for callback
private val biometricHandlerCallback { ... }

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_example_with_pre_check)

    // Check if can authenticate first
    // If code is BIOMETRIC_SUCCESS setup the prompt to show when user tap the button
    // Otherwise, apply error message based on the code
    when(biometricHandler.canAuthenticate()) {
        BIOMETRIC_SUCCESS -> setupBiometricPrompt()
        BIOMETRIC_ERROR_NO_HARDWARE -> {
          // do anything when no hardware
        }
        BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
          // do anything when biometry unavailable
        }
        BIOMETRIC_ERROR_NONE_ENROLLED -> {
          // do anything when biometry not enrolled
        }
    }
}

private fun setupBiometricPrompt() {
    authenticateButton.setOnClickListener {
        biometricHandler
            .setTitle("Payment action")
            .setSubtitle("youremail@test.com")
            .setDescription("Hello there, this is a test description for this test payment action.")
            .setNegativeButtonText("Cancel")
            .setHandlerCallback(biometricHandlerCallback)
            .show()
    }
}
```
