package com.mathtech.quickmath.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathtech.quickmath.data.datastore.HighScoreState
import com.mathtech.quickmath.domain.interactors.GetHighScoreFromDSUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val getHighScoreFromDSUseCase: GetHighScoreFromDSUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getHighScoreFromDSUseCase(Unit).collectLatest {
                it.onSuccess {highScore ->
                    println("HomeViewModel: $highScore")
                    _uiState.update { state ->
                        state.copy(
                            highScoreState = highScore
                        )
                    }
                }
            }
        }
    }
}

data class HomeUiState(
    val highScoreState: HighScoreState? = null
)