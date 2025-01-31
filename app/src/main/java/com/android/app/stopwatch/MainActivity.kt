package com.android.app.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.app.stopwatch.ui.theme.Cyan
import com.android.app.stopwatch.ui.theme.DarkBlue
import com.android.app.stopwatch.ui.theme.LightBlue
import com.android.app.stopwatch.ui.theme.StopWatchTheme
import com.android.app.stopwatch.ui.theme.Teal
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            StopWatchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val viewModel : StopWatchViewModel = viewModel()
                    val time = viewModel.formattedTime.value
                    StopWatch(
                        stopWatchViewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
private fun StopWatch(
    stopWatchViewModel: StopWatchViewModel
) {
    var isRunning by rememberSaveable{
        mutableStateOf(false)
    }

    var startTime by rememberSaveable { mutableLongStateOf(0L) }
    var elapsedTime by rememberSaveable { mutableLongStateOf(0L) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            while (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                delay(10L) // Update every 10 milliseconds for smoother millisecond display
            }
        }
    }

    val formattedTime = stopWatchViewModel.formatTime(elapsedTime)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkBlue,
                        Color.Black,
                    ),
                    startY = 1000f
                )
            )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val sp = 40.sp
        val timerColor = LightBlue
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                Text(
                    formattedTime,
                    fontSize = sp,
                    fontWeight = FontWeight.Bold,
                    color = timerColor
                )
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        isRunning = !isRunning
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Cyan,
                        contentColor = Color.White
                    )
                ) {
                    Text(if (isRunning) "Pause" else "Start")
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        isRunning = false
                        elapsedTime = 0L
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Teal,
                        contentColor = Color.White
                    )
                ) {
                    Text("Reset")
                }
            }
        }

    }


}
