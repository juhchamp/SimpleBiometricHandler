package juhchamp.com.br.simplebiometric

/**
 * Callback structure provided to [BiometricHandler].
 * Users of BiometricHandler must provide an implementation of this for listening to biometric events
 */
interface BiometricCallback {
    fun onNoBiometricHardware()
    fun onBiometricNotEnrolled()
    fun onBiometricUnavailable()
    fun onAuthenticationError(errCode: Int, message: CharSequence)
    fun onAuthenticationFail()
    fun onAuthenticationSuccess()
}
