package com.example.android.politicalpreparedness.domain.entity

import com.example.android.politicalpreparedness.data.models.Office
import com.example.android.politicalpreparedness.data.models.Official

data class Representative(
    val official: Official,
    val office: Office
)