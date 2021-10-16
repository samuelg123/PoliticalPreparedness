package com.example.android.politicalpreparedness.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Division(
        val id: String,
        val country: String,
        val state: String
) : Parcelable