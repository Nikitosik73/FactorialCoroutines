package ru.paramonov.factorialcoroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

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
            val result = factorial(number)
            _viewState.value = State.Result(factorial = result)
        }
    }

    // функция для расчёта факториала
//    private suspend fun factorial(number: Long): String = withContext(Dispatchers.IO) {
//        var result = BigInteger.ONE
//        for (i in 1..number) {
//            result = result.multiply(BigInteger.valueOf(i))
//        }
//        result.toString()
//    }

    // через callback
    private suspend fun factorial(number: Long): String = suspendCoroutine {
        thread {
            var result = BigInteger.ONE
            for (i in 1..number) {
                result = result.multiply(BigInteger.valueOf(i))
            }
            it.resumeWith(Result.success(result.toString()))
        }
    }
}
