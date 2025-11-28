package com.ojasx.eduplay.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _firstname = MutableLiveData("Ojas")
    val firstname : LiveData<String> = _firstname

    private val _lastname = MutableLiveData("Choudhary")
    val lastname : LiveData<String> = _lastname

    private val _email = MutableLiveData("ojas@email.com")
    val email: LiveData<String> = _email

    fun updateProfile(first: String, last: String, mail: String) {
        _firstname.value = first
        _lastname.value = last
        _email.value = mail
    }

}