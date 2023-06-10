package com.kamilsudarmi.financetracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kamilsudarmi.financetracker.R
import com.kamilsudarmi.financetracker.adapters.RecordAdapter
import com.kamilsudarmi.financetracker.databinding.ActivityMainBinding
import com.kamilsudarmi.financetracker.models.Record
import com.kamilsudarmi.financetracker.utils.FirebaseUtils

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recordAdapter: RecordAdapter
    private lateinit var database: DatabaseReference

    private var saldo: Double = 0.0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = FirebaseUtils.getDatabase().getReference("records")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recordAdapter = RecordAdapter(emptyList())
        recyclerView.adapter = recordAdapter

        fetchRecords()

        button()
    }

    private fun button() {
        binding.btnAddRecord.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchRecords() {
        val recordsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val records = mutableListOf<Record>()
                for (snapshot in dataSnapshot.children) {
                    val record = snapshot.getValue(Record::class.java)
                    record?.let {
                        records.add(it)
                    }
                }
                recordAdapter.setData(records)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        }

        database.addValueEventListener(recordsListener)
    }
}