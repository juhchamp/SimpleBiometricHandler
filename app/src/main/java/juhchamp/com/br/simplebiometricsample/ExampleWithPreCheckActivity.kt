package juhchamp.com.br.simplebiometricsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.biometric.BiometricManager.*
import juhchamp.com.br.simplebiometric.BiometricHandler
import juhchamp.com.br.simplebiometric.BiometricHandlerCallback
import kotlinx.android.synthetic.main.layout_info_action.*

class ExampleWithPreCheckActivity : AppCompatActivity() {

    // Create var for the handler calling the instance
    private var biometricHandler: BiometricHandler = BiometricHandler(this)
    // Create var for callback
    private val biometricHandlerCallback = object : BiometricHandlerCallback {

        override fun onBiometricNotEnrolled() {
            applyErrorMessageToUI(getString(R.string.biometry_not_enrolled), false)
        }

        override fun onBiometricUnavailable() {
            applyErrorMessageToUI(getString(R.string.biometry_unavailable), false)
        }

        override fun onNoBiometricHardware() {
            applyErrorMessageToUI(getString(R.string.biometry_not_supported), false)
        }

        override fun onAuthenticationError(errCode: Int, message: CharSequence) {
            applyErrorMessageToUI(getString(R.string.biometry_auth_error, message), true)
        }

        override fun onAuthenticationFail() {
            applyErrorMessageToUI(getString(R.string.biometry_auth_failed), true)
        }

        override fun onAuthenticationSuccess() {
            applyErrorMessageToUI(getString(R.string.biometry_auth_successed), true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_with_pre_check)

        infoLabelTv.visibility = GONE

        // Check if can authenticate first
        // If code is BIOMETRIC_SUCCESS setup the prompt to show when user tap the button
        // Otherwise, apply error message based on the code
        when(biometricHandler.canAuthenticate()) {
            BIOMETRIC_SUCCESS -> setupBiometricPrompt()
            BIOMETRIC_ERROR_NO_HARDWARE -> applyErrorMessageToUI(getString(R.string.biometry_not_supported), false)
            BIOMETRIC_ERROR_HW_UNAVAILABLE -> applyErrorMessageToUI(getString(R.string.biometry_unavailable), false)
            BIOMETRIC_ERROR_NONE_ENROLLED -> applyErrorMessageToUI(getString(R.string.biometry_not_enrolled), false)
        }
    }

    private fun applyErrorMessageToUI(message: String, activeButton: Boolean) {
        infoLabelTv.text = message
        authenticateButton.isEnabled = activeButton
        infoLabelTv.visibility = VISIBLE
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
}
