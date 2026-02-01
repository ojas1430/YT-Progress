package com.ojasx.eduplay.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _firstname = MutableLiveData("Ojas")
    val firstname : LiveData<String> = _firstname

    private val _lastname = MutableLiveData("Choudhary")
    val lastname : LiveData<String> = _lastname

    private val _email = MutableLiveData("ojas@email.com")
    val email: LiveData<String> = _email

    private val _ProfileImage = MutableLiveData<String?>(null)
    val profileImage : LiveData<String?> = _ProfileImage

    fun updateProfile(first: String, last: String, mail: String) {
        _firstname.value = first
        _lastname.value = last
        _email.value = mail
    }
    fun updateProfileImage(uri : String?){
        _ProfileImage.value = uri
    }

}