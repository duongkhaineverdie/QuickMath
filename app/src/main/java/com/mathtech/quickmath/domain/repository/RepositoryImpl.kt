package com.mathtech.quickmath.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.mathtech.quickmath.data.datastore.DataStoreManager
import com.mathtech.quickmath.data.datastore.HighScoreState
import com.mathtech.quickmath.data.repository.IRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val dataStoreManager: DataStoreManager,
): IRepository {
    override fun getMathScoreState(): Flow<HighScoreState> = dataStoreManager.highScoreState

    override suspend fun saveMathScoreWithKey(score: Int, key: Preferences.Key<Int>) = dataStoreManager.storeHighScoreWithKey(score, key)
}