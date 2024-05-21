package com.mathtech.quickmath.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.mathtech.quickmath.utils.Constants
import com.mathtech.quickmath.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quick_math")

class DataStoreManager(private val context: Context) {
    init {
        context
    }

    suspend fun storeHighScoreWithKey(score: Int, keyString: Preferences.Key<Int>) {
        Log.d(TAG, "storeHighScore: $score")
        context.dataStore.edit {
            it[keyString] = score
        }
    }

    val highScoreState: Flow<HighScoreState> = context.dataStore.data.map {
        HighScoreState(
            highScorePlus = it[Constants.HIGH_SCORE_PLUS] ?: 0,
            highScoreSubtract = it[Constants.HIGH_SCORE_MINUS] ?: 0,
            highScoreMultiply = it[Constants.HIGH_SCORE_MULTIPLY] ?: 0,
            highScoreDivide = it[Constants.HIGH_SCORE_DIVIDE] ?: 0
        )
    }
}

data class HighScoreState(
    val highScorePlus: Int,
    val highScoreSubtract: Int,
    val highScoreMultiply: Int,
    val highScoreDivide: Int
)

data class HighScoreWithKey(
    val highScore: Int,
    val key: Preferences.Key<Int>
)