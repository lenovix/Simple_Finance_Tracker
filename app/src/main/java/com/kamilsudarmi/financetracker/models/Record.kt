package com.kamilsudarmi.financetracker.models

// Record.kt
data class Record(
    val amount: Double = 0.0,
    val description: String = "",
    val date: String = "",
    val type: String = "" // Menambahkan properti type
)
