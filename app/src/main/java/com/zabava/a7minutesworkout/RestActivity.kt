package com.zabava.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.zabava.a7minutesworkout.databinding.ActivityRestBinding

private var binding: ActivityRestBinding? = null

private var restTimer: CountDownTimer? = null
private var restProgress = 0

private var exerciseList: ArrayList<ExerciseModel>? = null
private var currentExercisePosition = -1


class RestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        exerciseList = Constants.defaultExerciseList()

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.max = 10
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                val intent = Intent(this@RestActivity, ExerciseActivity::class.java)
                intent.putExtra("currentExercisePosition", currentExercisePosition)
                startActivity(intent)
            }
        }.start()
    }

    private fun setupRestView() {
        currentExercisePosition++
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }

}