package com.sergio.guiat.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DEFAULT_DELAY = 2000L

class SplashViewModel : ViewModel(){

    private val timer = MutableLiveData<Boolean>()
    val onTimer : LiveData<Boolean> = timer

    init {
        viewModelScope.launch(Dispatchers.IO){
            delay(DEFAULT_DELAY)
            timer.postValue(true)
        }
    }

}