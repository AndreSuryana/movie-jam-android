package com.example.moviejam.data.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context?): AppDatabase? {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE
    }

    private fun buildRoomDB(context: Context?) =
        context?.applicationContext?.let {
            Room.databaseBuilder(
                it,
                AppDatabase::class.java,
                "favorite_database"
            ).build()
        }
}