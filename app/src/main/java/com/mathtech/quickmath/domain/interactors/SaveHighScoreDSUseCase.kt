package com.mathtech.quickmath.domain.interactors

import com.mathtech.quickmath.data.datastore.HighScoreWithKey
import com.mathtech.quickmath.data.repository.IRepository
import com.mathtech.quickmath.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SaveHighScoreDSUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<HighScoreWithKey, Unit>(dispatcher) {
    override suspend fun block(param: HighScoreWithKey): Unit = repository.saveHighScoreDS(score = param.highScore, key = param.key)
}