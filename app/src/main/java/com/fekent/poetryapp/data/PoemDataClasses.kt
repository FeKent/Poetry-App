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
    Authored(1, "Fire and Ice", "Some say the world will end in fire" +
            "Some say in ice," +
            "From what I;ve tasted of desire" +
            "I hold with those who favor fire." +
            "But if I had to perish twice," +
            "I think I know enough of hate" +
            "To say that for destruction ice" +
            "Is also great" +
            "And would suffice.")
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