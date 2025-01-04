package com.fekent.poetryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Authored(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String?,
    val poem: String
)

@Entity
data class Saved(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String?,
    val poem: String,
    val author: String
)