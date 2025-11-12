package com.ojasx.eduplay.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class AuthViewModel : ViewModel() {

    private val auth : FirebaseAuth= FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    init{
        // checkAuthStatus()
        _authState.value = AuthState.Unauthenticated
    }

    // This code is to automatically move to home page if user is authenticaed

//    fun checkAuthStatus(){
//        if(auth.currentUser == null){
//            _authState.value = AuthState.Unauthenticated
//        }else{
//            _authState.value = AuthState.Authenticated
//        }
//    }

    fun login(email:String, password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return

        }

        _authState.value  = AuthState.loading

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }

            }
    }

    fun SignUp(name: String,email:String, password:String,confirmPassword:String){
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || confirmPassword.isEmpty()) {
            _authState.value = AuthState.Error("All fields are required")
            return
        }
        if(password != confirmPassword){
            _authState.value = AuthState.Error("Password do not match")
            return
        }

        _authState.value  = AuthState.loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        _authState.value = AuthState.Authenticated
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }


    }

    fun SighOut(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }




}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object loading : AuthState()
    data class Error(val message : String) : AuthState()

}