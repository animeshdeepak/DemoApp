package com.example.demoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val pan = MutableLiveData<String>()
    val year = MutableLiveData<String>()
    val dob = MutableLiveData<String>()
    val buttonState = MutableLiveData<Boolean>()

    fun validateData() {
        when {
            pan.value?.validatePanCard() == false ->
                buttonState.value = false
            dob.value?.validateDoB() == false ->
                buttonState.value = false
            pan.value?.validatePanCard() == true && dob.value?.validateDoB() == true ->
                buttonState.value = true
        }
    }
}