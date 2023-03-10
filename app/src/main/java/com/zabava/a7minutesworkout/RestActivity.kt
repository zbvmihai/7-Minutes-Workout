package com.zabava.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.zabava.a7minutesworkout.databinding.ActivityRestBinding


private var restTimer: CountDownTimer? = null
private var restProgress = 0

private var exerciseList: ArrayList<ExerciseModel>? = null
private var currentExercisePosition = -1

@Suppress("DEPRECATION")
class RestActivity : AppCompatActivity() {
    private var binding: ActivityRestBinding? = null
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
            restTimer?.cancel()
            val i = Intent(this@RestActivity, MainActivity::class.java)
            startActivity(i)
        }

        setupRestView()

    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
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