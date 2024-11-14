package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var timerService : TimerService.TimerBinder
    var isConnected = false

    val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            timerService = service as TimerService.TimerBinder
            isConnected = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isConnected = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        findViewById<Button>(R.id.startButton).setOnClickListener {
            if (isConnected) {
                if (timerService.isRunning == true) {
                    timerService.pause()
                    findViewById<Button>(R.id.startButton).text = "Start"

                } else {
                    timerService.start(100)
                    findViewById<Button>(R.id.startButton).text = "Pause"
                }
            }

        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {

        }
    }
}