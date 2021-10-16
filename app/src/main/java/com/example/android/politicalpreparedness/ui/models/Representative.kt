package com.example.android.politicalpreparedness.ui.models

import com.example.android.politicalpreparedness.data.models.Office
import com.example.android.politicalpreparedness.data.models.Official

//TODO: This should be different than domain entity. Parcelable should be placed here.
//https://budioktaviyans.medium.com/the-android-clean-architecture-pov-d9d5ec888534
data class Representative(
    val official: Official,
    val office: Office
)