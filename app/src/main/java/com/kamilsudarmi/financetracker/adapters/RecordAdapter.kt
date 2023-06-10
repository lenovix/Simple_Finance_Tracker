package com.kamilsudarmi.financetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kamilsudarmi.financetracker.R
import com.kamilsudarmi.financetracker.models.Record

class RecordAdapter(private var recordList: List<Record>) :
    RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val currentRecord = recordList[position]
        holder.bind(currentRecord)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    fun setData(records: List<Record>) {
        recordList = records
        notifyDataSetChanged()
    }

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView: TextView = itemView.findViewById(R.id.txtAmount)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tvDescription)
        private val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        private val typeTextView: TextView = itemView.findViewById(R.id.tvType)

        fun bind(record: Record) {
            amountTextView.text = record.amount.toString()
            descriptionTextView.text = record.description
            dateTextView.text = record.date
            typeTextView.text = record.type
        }
    }

}