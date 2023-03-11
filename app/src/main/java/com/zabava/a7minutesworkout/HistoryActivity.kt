package com.zabava.a7minutesworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zabava.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDAO: HistoryDAO) {
        lifecycleScope.launch {
            historyDAO.fetchAllDates().collect { allCompletedDatesList ->
                if (allCompletedDatesList.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList) {
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter


                } else {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}