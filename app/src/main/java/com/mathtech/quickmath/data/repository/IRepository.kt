package com.mathtech.quickmath.data.repository

import androidx.datastore.preferences.core.Preferences
import com.mathtech.quickmath.data.datastore.HighScoreState
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getMathScoreState(): Flow<HighScoreState>
    suspend fun saveMathScoreWithKey(score: Int, key: Preferences.Key<Int>)
}