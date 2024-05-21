package com.mathtech.quickmath.data.repository

import androidx.datastore.preferences.core.Preferences
import com.mathtech.quickmath.data.datastore.HighScoreState
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getHighScoreState(): Flow<HighScoreState>
    suspend fun saveHighScoreDS(score: Int, key: Preferences.Key<Int>)
}