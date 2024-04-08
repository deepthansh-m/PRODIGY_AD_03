package com.example.stopwatchapp

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStartPause: ImageView
    private lateinit var btnReset: ImageView

    private var seconds = 0
    private var isRunning = false

    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        btnStartPause = findViewById(R.id.btnStartPause)
        btnReset = findViewById(R.id.btnReset)

        btnStartPause.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        isRunning = true
        btnStartPause.setImageResource(R.drawable.ic_baseline_pause_24)
        runnable = object : Runnable {
            override fun run() {
                seconds++
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                tvTimer.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun pauseTimer() {
        isRunning = false
        btnStartPause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        handler.removeCallbacks(runnable)
    }

    private fun resetTimer() {
        isRunning = false
        seconds = 0
        tvTimer.text = "00:00:00"
        btnStartPause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        if (isRunning) {
            pauseTimer()
        }
    }
}
