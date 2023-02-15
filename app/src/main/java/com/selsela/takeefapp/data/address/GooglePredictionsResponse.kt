package com.selsela.takeefapp.data.address

import androidx.annotation.Keep

@Keep
data class GooglePredictionsResponse(
    val predictions: ArrayList<GooglePrediction>
)

@Keep
data class GooglePredictionTerm(
    val offset: Int,
    val value: String
)

@Keep
data class GooglePrediction(
    val description: String,
    val terms: List<GooglePredictionTerm>
)