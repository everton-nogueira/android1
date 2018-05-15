package br.com.iesb.radar

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.PI

class MainActivity : AppCompatActivity() {
    private lateinit var scannerHandler: Handler
    private lateinit var scannerRunnable: Runnable

    private var currentAngle = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scannerHandler = Handler()
    }

    override fun onResume() {
        super.onResume()

        scannerRunnable = Runnable {
            scan()
            scannerHandler.postDelayed(scannerRunnable, 0)
        }
        scannerHandler.postDelayed(scannerRunnable, 0)
    }

    override fun onPause() {
        super.onPause()
        scannerHandler.removeCallbacks(scannerRunnable)
    }


    private fun scan() {
        currentAngle = currentAngle + (PI / 120).toFloat()
        scanner.angulo = currentAngle
    }
}
