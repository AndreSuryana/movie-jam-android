package com.example.moviejam.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(

    val name: String,

    val picture: Int? = 0,

    val email: String
) : Parcelable
