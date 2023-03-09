package com.zabava.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.zabava.a7minutesworkout.databinding.ActivityExerciseBinding
import com.zabava.a7minutesworkout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object: CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.max = 10
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                startExercise()
                Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setupRestView(){
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }

    private fun startExercise(){
        restProgress = 0
        binding?.tvTitle?.text = "Exercise Name"
        binding?.progressBar?.progress = restProgress
        restTimer = object: CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.max = 30
                binding?.progressBar?.progress = 30 - restProgress
                binding?.tvTimer?.text = (30 - restProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise Finished", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ExerciseActivity, MainActivity::class.java)
                startActivity(intent)

            }
        }.start()
    }
}