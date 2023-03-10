package com.zabava.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zabava.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

@Suppress("DEPRECATION")
class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMetricUnits()) {
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val heightValue: Float =
                    binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)

            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun displayBMIResult(bmi: Float){

        val bmiLabel : String
        val bmiDescription : String

        if (bmi.compareTo(15)<= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Ops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <=  0) {
            bmiLabel = "Underweight"
            bmiDescription = "Ops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <=  0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <=  0) {
            bmiLabel = "Overweight"
            bmiDescription = "Ops! You really need to take better care of yourself! Workout more!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <=  0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Ops! You really need to take better care of yourself! Workout more!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <=  0) {
            bmiLabel = "Obese Class | (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }else  {
            bmiLabel = "Obese Class | (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).
        setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
}