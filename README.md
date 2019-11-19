# SimpleBiometricHandler
A simple library to handle biometric authentication in Android.

[![](https://jitpack.io/v/juhchamp/SimpleBiometricHandler.svg)](https://jitpack.io/#juhchamp/SimpleBiometricHandler)

## Download
Download the latest version via Gradle:

**Step 1.** Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the SimpleBiometricHandler dependency

```
dependencies {
  implementation 'com.github.juhchamp:SimpleBiometricHandler:1.0.0'
}
```

## Usage

**Step 1.** Add permission to your manifest file

```
 <uses-permission android:name="android.permission.USE_BIOMETRIC"
        android:requiredFeature="false"
        tools:targetApi="o" />
```
        
**Step 2.** Set up a BiometricCallback to handle callbacks

```
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

**Step 3.** Call the BiometricHandler instance passing activity, callback object and set the other needed options

```
BiometricHandler(this, biometricCallback)
  .setTitle("Confirm Payment")
  .setSubtitle("corextechnologies@gmail.com")
  .setDescription("Paying for Kamba Gas Service")
  .setNegativeButtonText("Cancel")
  .show()
```
