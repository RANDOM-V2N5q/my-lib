package com.example.mylib

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel: ViewModel() {

    val searchPrase = MutableLiveData<String>("")

}