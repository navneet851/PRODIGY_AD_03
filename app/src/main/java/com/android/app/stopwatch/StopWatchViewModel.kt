package com.android.app.stopwatch

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.Locale
import kotlin.text.format

class StopWatchViewModel : ViewModel() {
    private var time = 0L
    private var isRunning = false

    private val _formattedTime = mutableStateOf("00:00:00")
    val formattedTime: State<String> = _formattedTime

    init {
        _formattedTime.value = formatTime(time)
    }

    fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / (1000 * 60)) % 60
        val hours = (milliseconds / (1000 * 60 * 60)) % 24
        val millis = (milliseconds % 1000) / 10 // Change here

        val formatter = DecimalFormat("00")
        val milliFormatter = DecimalFormat("00") // Change here
        return "${formatter.format(hours)}:${formatter.format(minutes)}:${formatter.format(seconds)}:${milliFormatter.format(millis)}"
    }

}