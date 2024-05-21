package com.mathtech.quickmath.utils

import androidx.datastore.preferences.core.intPreferencesKey

object Constants {
    val HIGH_SCORE_PLUS = intPreferencesKey("HIGH_SCORE_PLUS")
    val HIGH_SCORE_MINUS = intPreferencesKey("HIGH_SCORE_MINUS")
    val HIGH_SCORE_MULTIPLY = intPreferencesKey("HIGH_SCORE_MULTIPLY")
    val HIGH_SCORE_DIVIDE = intPreferencesKey("HIGH_SCORE_DIVIDE")

    fun isNaturalNumber(number: Double): Boolean {
        return number > 0 && number.toInt().toDouble() == number
    }
}

