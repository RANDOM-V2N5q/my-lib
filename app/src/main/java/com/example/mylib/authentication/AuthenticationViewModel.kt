package com.example.mylib.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel() {

    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

}