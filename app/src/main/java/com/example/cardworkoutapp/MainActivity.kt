package com.example.cardworkoutapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardworkoutapp.databinding.ActivityMainBinding
import com.example.cardworkoutapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Random
import java.util.Timer
import java.util.TimerTask


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startWorkout()
        openDialog()
        setStopButtonClickListener()

    }

    var cardList = listOf(
        R.drawable.img_ac,
        R.drawable.img_ad,
        R.drawable.img_ah,
        R.drawable.img_as,
        R.drawable.img_jc,
        R.drawable.img_jh,
        R.drawable.img_jd,
        R.drawable.img_js,
        R.drawable.img_kc,
        R.drawable.img_kd,
        R.drawable.img_kh,
        R.drawable.img_ks,
        R.drawable.img_qc,
        R.drawable.img_qd,
        R.drawable.img_qh,
        R.drawable.img_qs
    )

    private fun startWorkout() {
        binding.startButton.setOnClickListener {
            binding.startButton.isEnabled = false
            if (timer == null) {
                timer = Timer()
            }
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        Log.i("timer task", "card changed")
                        val randomCard = cardList[Random().nextInt(cardList.size)]
                        binding.cardImageView.setImageResource(randomCard)
                    }
                }
            }, 0, 1000)
        }
    }

    private fun openDialog() {

        binding.rulesButton.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetBinding = BottomSheetBinding.inflate(layoutInflater)
            bottomSheetDialog.setContentView(bottomSheetBinding.root)


            bottomSheetBinding.buttonOk.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetBinding.buttonX.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()


        }
    }


    private fun setStopButtonClickListener() {
        binding.stopButton.setOnClickListener {
            timer?.cancel()
            timer = null
            binding.startButton.isEnabled = true

        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
        timer = null
        binding.startButton.isEnabled = true
    }
}