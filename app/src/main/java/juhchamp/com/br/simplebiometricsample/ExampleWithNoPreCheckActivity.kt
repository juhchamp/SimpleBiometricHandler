package juhchamp.com.br.simplebiometricsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import juhchamp.com.br.simplebiometric.BiometricHandler
import juhchamp.com.br.simplebiometric.BiometricHandlerCallback
import kotlinx.android.synthetic.main.layout_info_action.*

class ExampleWithNoPreCheckActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_example_with_no_pre_check)

        infoLabelTv.visibility = View.GONE

        authenticateButton.setOnClickListener {
            BiometricHandler(this)
                .setTitle("Payment action")
                .setSubtitle("youremail@test.com")
                .setDescription("Hello there, this is a test description for this test payment action.")
                .setNegativeButtonText("Cancel")
                .setHandlerCallback(biometricHandlerCallback)
                .show()
        }
    }

    private fun applyErrorMessageToUI(message: String, activeButton: Boolean) {
        infoLabelTv.text = message
        authenticateButton.isEnabled = activeButton
        infoLabelTv.visibility = View.VISIBLE
    }
}
