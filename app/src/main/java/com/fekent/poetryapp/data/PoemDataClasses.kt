package com.fekent.poetryapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

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
data class Groups(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String,
)

val groupedExamples = listOf(
    Groups(0,"Test List")
)

@Entity(primaryKeys = ["groupId", "authoredId"],
    foreignKeys = [
        ForeignKey(entity = Groups::class, parentColumns = ["id"], childColumns = ["groupId"], onDelete = CASCADE),
        ForeignKey(entity = Authored::class, parentColumns = ["id"], childColumns = ["authoredId"], onDelete = CASCADE)
    ]
)
data class GroupedAuthorCrossRef(
    val groupId: Int,
    val authoredId: Int
)

data class GroupedPoems(
    @Embedded val group: Groups,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(GroupedAuthorCrossRef::class)
    )
    val poems: List<Authored>
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