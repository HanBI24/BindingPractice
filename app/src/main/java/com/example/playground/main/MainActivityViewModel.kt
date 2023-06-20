package com.example.playground.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playground.main.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    private lateinit var job: Job

    private val _tvCount = MutableLiveData("10")
    val tvCount: LiveData<String>
        get() = _tvCount

    private val _btnText = MutableLiveData("Start")
    val btnText: LiveData<String>
        get() = _btnText

    private val _isReset = MutableLiveData<Boolean>()
    val isReset: LiveData<Boolean>
        get() = _isReset

    private val _isStop = MutableLiveData("Press Start Button")
    val isStop: LiveData<String>
        get() = _isStop

    val etAnyText = MutableLiveData("EditText Result Here!")

    fun btnOnClick() {
        when (_btnText.value) {
            "Start" -> {
                job = Job()
                _isReset.value = false
                _btnText.value = "Stop"
                val tvCountFlow = changeTime()
                viewModelScope.launch(job) {
                    tvCountFlow.collect {
                        _tvCount.postValue(it.toString())
                    }
                }
            }
            "Stop" -> {
                _btnText.value = "Start"
                _isStop.value = "Stop!"
                job.cancel()
            }
        }
    }

    // 순차 비동기 처리를 위해 Flow 사용
    private fun changeTime() = flow {
        for (i in _tvCount.value?.toInt()!! - 1 downTo 0) {
            delay(1000L)
            emit(i)
        }
    }

    fun resetTime() {
        job.cancel()
        _isReset.value = true
        _btnText.value = "Start"
        _tvCount.value = "10"
    }

    fun getData() {
//        repository 와 통신하는 함수
//        addDisposable()
    }
}
