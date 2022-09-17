package com.udacity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class MainViewModel() : ViewModel() {

    var checkedRadioId = MutableLiveData<Int>()

}