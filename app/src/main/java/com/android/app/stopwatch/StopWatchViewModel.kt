package com.android.app.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class StopWatchViewModel : ViewModel() {
    private var time = 0L
    private var isRunning = false

    val formattedTime: String
        get() = String.format(Locale.getDefault(), "%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60)

    fun start() {
        if (isRunning) return
        isRunning = true
        viewModelScope.launch {
            while (isRunning) {
                delay(1000L)
                time++
            }
        }
    }

    fun stop() {
        isRunning = false
    }

    fun reset() {
        isRunning = false
        time = 0L
    }
}