package com.zabava.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zabava.a7minutesworkout.databinding.ActivityBmiBinding

@Suppress("DEPRECATION")
class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbBMI)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.tbBMI?.setNavigationOnClickListener {
            onBackPressed()

        }
    }
}