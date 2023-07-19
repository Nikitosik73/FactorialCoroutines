package ru.paramonov.factorialcoroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _viewState = MutableLiveData<State>()
    val viewState: LiveData<State> = _viewState

    fun calculate(value: String?) {

        _viewState.value = State.Progress
        if(value.isNullOrBlank()) {
            _viewState.value = State.Error
            return
        }

        viewModelScope.launch {
            val number = value.toLong()
            // calculate
            delay(1000)
            _viewState.value = State.Result(factorial = number.toString())
        }
    }
}
