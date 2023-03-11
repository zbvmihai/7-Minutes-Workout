package com.zabava.a7minutesworkout

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zabava.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(binding: ItemHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val llHistoryItemMain = binding.llHistoryItemMain
        val tvPosition = binding.tvPosition
        val tvDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date: String = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvDate.text = date

        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#a2a2a3"))
        } else {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}