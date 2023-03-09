package com.zabava.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.zabava.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null

    private var currentExercisePosition: Int? = null

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        currentExercisePosition = intent.getIntExtra("currentExercisePosition", 0)

        tts = TextToSpeech(this@ExerciseActivity, this@ExerciseActivity,"")

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        speakOut(exerciseList!![currentExercisePosition!!].getName())

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

        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        binding = null
    }

    private fun startExercise() {
        restProgress = 0
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(30000, 1000) {
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
                    val i = Intent(this@ExerciseActivity,MainActivity::class.java)
                    startActivity(i)
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language is not supported or missing!")
                Toast.makeText(this, "Language is not supported or missing!", Toast.LENGTH_SHORT).show()
            }else{
                speakOut(exerciseList!![currentExercisePosition!!].getName())
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
            Toast.makeText(this, "Text To Speech initialization failed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }
}