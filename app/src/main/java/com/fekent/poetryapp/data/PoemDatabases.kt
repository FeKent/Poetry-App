package com.fekent.poetryapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Authored::class], version = 1, exportSchema = false)
abstract class AuthoredDatabase : RoomDatabase() {
    abstract fun authoredDao(): AuthoredDao
}

@Database(entities = [Saved::class], version = 1, exportSchema = false)
abstract class SavedDatabase : RoomDatabase(){
    abstract fun savedDao() : SavedDao
}