package com.ojasx.eduplay.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    private val auth : FirebaseAuth= FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private fun isPasswordUserAndUnverified(): Boolean {
        val user = auth.currentUser ?: return false
        val hasPasswordProvider = user.providerData.any { it.providerId == "password" }
        return hasPasswordProvider && !user.isEmailVerified
    }

    private val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser
        _authState.value = when {
            user == null -> AuthState.Unauthenticated
            isPasswordUserAndUnverified() -> AuthState.EmailNotVerified("Please verify your email to continue.")
            else -> AuthState.Authenticated
        }
    }

    init{
        _authState.value = when {
            auth.currentUser == null -> AuthState.Unauthenticated
            isPasswordUserAndUnverified() -> AuthState.EmailNotVerified("Please verify your email to continue.")
            else -> AuthState.Authenticated
        }
        auth.addAuthStateListener(authListener)
    }

    fun login(email:String, password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return

        }

        _authState.value  = AuthState.Loading

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    if (isPasswordUserAndUnverified()) {
                        _authState.value = AuthState.EmailNotVerified("Please verify your email to continue.")
                    } else {
                        _authState.value = AuthState.Authenticated
                    }

                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun SignUp(name: String, email: String, password: String, confirmPassword: String) {

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || confirmPassword.isEmpty()) {
            _authState.value = AuthState.Error("All fields are required")
            return
        }

        if (password != confirmPassword) {
            _authState.value = AuthState.Error("Password do not match")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser

                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    // 1) Try to update display name (non-blocking for verification email)
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (!profileTask.isSuccessful) {
                                // Don't block verification email; just surface a softer warning.
                                _authState.value = AuthState.Error(
                                    profileTask.exception?.message ?: "Failed to set username"
                                )
                            }
                        }

                    // 2) Send verification email (this is what the user needs)
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verifyTask ->
                            if (verifyTask.isSuccessful) {
                                _authState.value = AuthState.EmailVerificationSent(
                                    "Verification email sent. Please verify your email to continue."
                                )
                            } else {
                                _authState.value = AuthState.Error(
                                    verifyTask.exception?.message
                                        ?: "Failed to send verification email"
                                )
                            }
                        } ?: run {
                        _authState.value = AuthState.Error("Signup succeeded, but user session not found.")
                    }

                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun resendEmailVerification() {
        val user = auth.currentUser
        if (user == null) {
            _authState.value = AuthState.Error("No user session found. Please login again.")
            return
        }
        user.sendEmailVerification().addOnCompleteListener { task ->
            _authState.value = if (task.isSuccessful) {
                AuthState.EmailVerificationSent("Verification email re-sent. Please check your inbox.")
            } else {
                AuthState.Error(task.exception?.message ?: "Failed to resend verification email")
            }
        }
    }

    fun checkEmailVerification() {
        val user = auth.currentUser
        if (user == null) {
            _authState.value = AuthState.Unauthenticated
            return
        }
        user.reload().addOnCompleteListener { reloadTask ->
            if (!reloadTask.isSuccessful) {
                _authState.value = AuthState.Error("Couldn't refresh user. Please try again.")
                return@addOnCompleteListener
            }
            _authState.value = when {
                isPasswordUserAndUnverified() -> AuthState.EmailNotVerified("Email still not verified.")
                else -> AuthState.Authenticated
            }
        }
    }

    fun sendPasswordReset(email: String) {
        if (email.isBlank()) {
            _authState.value = AuthState.Error("Please enter your email first.")
            return
        }
        auth.sendPasswordResetEmail(email.trim()).addOnCompleteListener { task ->
            _authState.value = if (task.isSuccessful) {
                AuthState.PasswordResetEmailSent(
                    "Password reset email sent. Check your inbox. It might be in your spam folder too, so please check there as well."
                )
            } else {
                AuthState.Error(task.exception?.message ?: "Failed to send password reset email")
            }
        }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    override fun onCleared() {
        auth.removeAuthStateListener(authListener)
        super.onCleared()
    }



}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()

    data class EmailVerificationSent(val message: String) : AuthState()
    data class EmailNotVerified(val message: String) : AuthState()
    data class PasswordResetEmailSent(val message: String) : AuthState()


}