package ru.paramonov.factorialcoroutines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {

    private val _viewState = MutableLiveData<State>()
    val viewState: LiveData<State> = _viewState

    private val coroutineScope = CoroutineScope(Dispatchers.Default + CoroutineName("My coroutine scope"))

    fun calculate(value: String?) {

        _viewState.value = State.Progress
        if(value.isNullOrBlank()) {
            _viewState.value = State.Error
            return
        }

        coroutineScope.launch(Dispatchers.Main) {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _viewState.value = State.Result(factorial = result)
            Log.d("MainViewModel", coroutineScope.toString())
        }
    }

    // функция для расчёта факториала
    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
