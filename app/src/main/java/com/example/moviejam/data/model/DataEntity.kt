package com.example.moviejam.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class DataEntity(

    val id: Int = 0,

    @PrimaryKey
    val title: String,

    val poster: String,

    val rating: String,

    val date: String,

    val genre: String,

    val duration: String,

    val overview: String,

    val status: String,

    val language: String,

    val creator: String

) : Parcelable
