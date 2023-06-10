package com.kamilsudarmi.financetracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.google.firebase.database.DatabaseReference
import com.kamilsudarmi.financetracker.R
import com.kamilsudarmi.financetracker.databinding.ActivityAddRecordBinding
import com.kamilsudarmi.financetracker.databinding.ActivityMainBinding
import com.kamilsudarmi.financetracker.models.Record
import com.kamilsudarmi.financetracker.utils.FirebaseUtils
import java.text.SimpleDateFormat
import java.util.*

class AddRecordActivity : AppCompatActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var typeRadioGroup: RadioGroup

    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivityAddRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = FirebaseUtils.getDatabase().getReference("records")

        amountEditText = findViewById(R.id.editAmount)
        descriptionEditText = findViewById(R.id.editDescription)
        saveButton = findViewById(R.id.btnSave)

        typeRadioGroup = findViewById(R.id.radioGroupType)
        saveButton.setOnClickListener {
            saveRecord()
        }
    }

    private fun saveRecord() {
        val amount = amountEditText.text.toString().toDoubleOrNull()
        val description = descriptionEditText.text.toString()
        val selectedType = when (typeRadioGroup.checkedRadioButtonId) {
            R.id.radioExpense -> "Expense"
            R.id.radioIncome -> "Income"
            else -> "" // Jika tidak ada yang dipilih
        }
        if (amount != null && description.isNotEmpty() && selectedType.isNotEmpty()) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val record = Record(amount, description, date, selectedType)

            val recordId = database.push().key
            if (recordId != null) {
                database.child(recordId).setValue(record)
                    .addOnSuccessListener {
                        // Record saved successfully
                        finish()
                    }
                    .addOnFailureListener {
                        // Failed to save record
                    }
            }
        }
    }
}