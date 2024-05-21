package com.mathtech.quickmath.domain.interactors

import com.mathtech.quickmath.data.datastore.HighScoreState
import com.mathtech.quickmath.data.repository.IRepository
import com.mathtech.quickmath.domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetHighScoreFromDSUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, HighScoreState>(dispatcher) {
    override suspend fun build(param: Unit): Flow<HighScoreState> = repository.getHighScoreState()

}