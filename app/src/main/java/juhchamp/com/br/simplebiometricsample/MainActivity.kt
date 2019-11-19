package juhchamp.com.br.simplebiometricsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import juhchamp.com.br.simplebiometric.BiometricHandler
import juhchamp.com.br.simplebiometric.BiometricCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authenticateButton.setOnClickListener {
            BiometricHandler(this, biometricCallback)
                .setTitle("Test title")
                .setSubtitle("Test subtitle")
                .setDescription("Test description")
                .setNegativeButtonText("Cancel")
                .show()
        }
    }
}
