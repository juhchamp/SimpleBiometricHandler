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
class BiometricHandler(
     private val activity: FragmentActivity,
     private val biometricCallback: BiometricCallback
) {

     private var title: String? = null
     private var subtitle: String? = null
     private var description: String? = null
     private var negativeButtonText: String? = null

     private val executor: Executor = Executors.newSingleThreadExecutor()

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
      * Show the authentication dialog for the user.
      */
     fun show() {
          val biometricManager = BiometricManager.from(activity)
          when (biometricManager.canAuthenticate()) {
               BiometricManager.BIOMETRIC_SUCCESS -> showBiometricPrompt()
               BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> biometricCallback.onNoBiometricHardware()
               BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> biometricCallback.onBiometricUnavailable()
               BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> biometricCallback.onBiometricNotEnrolled()
          }
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
                              biometricCallback.onAuthenticationError(errorCode, errString)
                         }
                    }

                    override fun onAuthenticationSucceeded(
                         result: BiometricPrompt.AuthenticationResult) {
                         super.onAuthenticationSucceeded(result)
                         activity.runOnUiThread {
                              biometricCallback.onAuthenticationSuccess()
                         }
                    }

                    override fun onAuthenticationFailed() {
                         super.onAuthenticationFailed()
                         activity.runOnUiThread {
                              biometricCallback.onAuthenticationFail()
                         }
                    }
               })
          biometricPrompt.authenticate(promptInfo)
     }
}
