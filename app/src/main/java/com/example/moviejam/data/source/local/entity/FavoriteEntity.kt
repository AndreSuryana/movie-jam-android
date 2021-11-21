package com.example.moviejam.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.*
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteentities")
data class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id", typeAffinity = INTEGER)
    val id: Int,

    @ColumnInfo(name = "posterPath", typeAffinity = TEXT)
    val posterPath: String,

    @ColumnInfo(name = "title", typeAffinity = TEXT)
    val title: String,

    @ColumnInfo(name = "voteAverage", typeAffinity = REAL)
    val voteAverage: Double,

    @ColumnInfo(name = "releaseDate", typeAffinity = TEXT)
    val releaseDate: String,

    @ColumnInfo(name = "isMovie", typeAffinity = INTEGER)
    val isMovie: Boolean
)
