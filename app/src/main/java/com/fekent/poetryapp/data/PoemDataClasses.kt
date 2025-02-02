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
    Authored(1, "Fire and Ice", "Some say the world will end in fire,\n" +
            "Some say in ice.\n" +
            "From what I’ve tasted of desire\n" +
            "I hold with those who favor fire.\n" +
            "But if it had to perish twice,\n" +
            "I think I know enough of hate\n" +
            "To say that for destruction ice\n" +
            "Is also great\n" +
            "And would suffice.")
)

@Entity
data class Grouped(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String,
    val poems: List<Authored>
)

val groupedExamples = listOf(
    Grouped(0,"Test List", authoredExample)
)

@Entity
data class Saved(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String?,
    val poem: String,
    val author: String,
    val translator: String?
)

val savedExamples = listOf(
    Saved(0, "Duckie", "Rollies? Dice Rollies?", "Ciprian Hirlea", "Snippy Rex"),
    Saved(0, "Fire and Ice", "Some say the world will end in fire,\n" +
            "Some say in ice.\n" +
            "From what I’ve tasted of desire\n" +
            "I hold with those who favor fire.\n" +
            "But if it had to perish twice,\n" +
            "I think I know enough of hate\n" +
            "To say that for destruction ice\n" +
            "Is also great\n" +
            "And would suffice.", "Robert Frost", null)
)