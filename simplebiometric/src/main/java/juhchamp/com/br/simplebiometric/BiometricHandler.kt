package juhchamp.com.br.simplebiometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * This class handle the biometric authentication easy.
 * @author José Jailton da Silva Júnior ( JuhChamp )
 */
class BiometricHandler(private var activity: FragmentActivity) {

    private var title: String? = null
    private var subtitle: String? = null
    private var description: String? = null
    private var negativeButtonText: String? = null

    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var biometricManager: BiometricManager = BiometricManager.from(activity)
    private var biometricHandlerCallback: BiometricHandlerCallback? = null

    /**
    * Set title for authentication dialog.
    * @param title [String] representing the title
    * @return [BiometricHandler] handler for next call
    */
    fun setTitle(title: String): BiometricHandler {
        this.title = title
        return this
    }

    /**
    * Set subtitle for authentication dialog.
    * @param subtitle [String] representing the subtitle
    * @return [BiometricHandler] handler for next call
    */
    fun setSubtitle(subtitle: String): BiometricHandler {
        this.subtitle = subtitle
        return this
    }

    /**
    * Set description for authentication dialog.
    * @param description [String] representing the description
    * @return [BiometricHandler] handler for next call
    */
    fun setDescription(description: String): BiometricHandler {
        this.description = description
        return this
    }

    /**
    * Set negative button text for authentication dialog.
    * @param text [String] representing the text for negative button
    * @return [BiometricHandler] handler for next call
    */
    fun setNegativeButtonText(text: String): BiometricHandler {
        this.negativeButtonText = text
        return this
    }

    /**
     * Set the callback for handler.
     * @param biometricHandlerCallback [BiometricHandlerCallback] for the handler
     */
    fun setHandlerCallback(biometricHandlerCallback: BiometricHandlerCallback): BiometricHandler {
        this.biometricHandlerCallback = biometricHandlerCallback
        return this
    }

    /**
    * Show the authentication dialog for the user.
    */
    fun show() {
        if(biometricHandlerCallback == null) {
            throw NullPointerException("BiometricCallback can be not a null! " +
                    "Set the callback for the handler like : biometricHandler.setHandlerCallback(object)")
        }
        when (this.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> showBiometricPrompt()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> biometricHandlerCallback?.onNoBiometricHardware()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> biometricHandlerCallback?.onBiometricUnavailable()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> biometricHandlerCallback?.onBiometricNotEnrolled()
        }
    }

    /**
     * Check if the device can use biometric sensor to authenticate.
     * @return [Int] representing the error based on [BiometricManager] error constants
     * @see BiometricManager.canAuthenticate
     */
    fun canAuthenticate() : Int {
        return biometricManager.canAuthenticate()
    }

    private fun showBiometricPrompt() {
        val promptInfo = PromptInfo.Builder()
            .setTitle(title?:"")
            .setSubtitle(subtitle?:"")
            .setDescription(description?:"")
            .setNegativeButtonText(negativeButtonText?:activity.getString(R.string.cancel_label))
            .build()

        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                 errorCode: Int,
                 errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    activity.runOnUiThread {
                        biometricHandlerCallback?.onAuthenticationError(errorCode, errString)
                    }
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    activity.runOnUiThread {
                        biometricHandlerCallback?.onAuthenticationSuccess()
                     }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    activity.runOnUiThread {
                        biometricHandlerCallback?.onAuthenticationFail()
                    }
                }
            })
        biometricPrompt.authenticate(promptInfo)
    }
}
