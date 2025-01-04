package com.fekent.poetryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Authored(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String?,
    val poem: String
)

val authoredExample = listOf(
    Authored(0, "Disturbia", "Late September in the city"),
    Authored(1, "Messy", "Cause I'm too messy, then I'm too fucking clean")
)



@Entity
data class Saved(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String?,
    val poem: String,
    val author: String
)

val savedExamples = listOf(
    Saved(0, "Duckie", "Rollies? Dice Rollies?", "Ciprian"),
    Saved(0, "Nobody", "Nobody, Nobody, No body", "Mitski")
)