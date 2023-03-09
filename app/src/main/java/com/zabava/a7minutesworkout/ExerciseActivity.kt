package com.zabava.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.zabava.a7minutesworkout.databinding.ActivityExerciseBinding


class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null

    private var currentExercisePosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        currentExercisePosition = intent.getIntExtra("currentExercisePosition", 0)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.ivExerciseImage?.setImageResource(exerciseList!![currentExercisePosition!!.toInt()].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition!!].getName()

        startExercise()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }

    private fun startExercise() {
        restProgress = 0
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.max = 30
                binding?.progressBar?.progress = 30 - restProgress
                binding?.tvTimer?.text = (30 - restProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition!! < exerciseList?.size!! - 1) {
                    val intent = Intent(this@ExerciseActivity, RestActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ExerciseActivity, "Exercise Finished", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.start()
    }
}