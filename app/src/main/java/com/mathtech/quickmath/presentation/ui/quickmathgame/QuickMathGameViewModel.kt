package com.mathtech.quickmath.presentation.ui.quickmathgame

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathtech.quickmath.data.datastore.HighScoreState
import com.mathtech.quickmath.data.datastore.HighScoreWithKey
import com.mathtech.quickmath.domain.interactors.GetMathScoreStateUseCase
import com.mathtech.quickmath.domain.interactors.SaveMathScoreWithKeyUseCase
import com.mathtech.quickmath.utils.Constants
import com.mathtech.quickmath.utils.TAG
import com.mathtech.quickmath.utils.TypeMath
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class QuickMathGameViewModel(
    savedStateHandle: SavedStateHandle,
    val getMathScoreStateUseCase: GetMathScoreStateUseCase,
    val saveMathScoreWithKeyUseCase: SaveMathScoreWithKeyUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<QuickMathUiState> =
        MutableStateFlow(QuickMathUiState())

    val stateFlow: StateFlow<QuickMathUiState> = _stateFlow.asStateFlow()

    init {
        _stateFlow.update {
            it.copy(
                typeMathGame = try {
                    checkNotNull(savedStateHandle["type"])
                } catch (e: NullPointerException) {
                    ""
                }
            )
        }
        getHighScoreFromDS()
        viewModelScope.launch {
            startGame()
            val timeDelay = 10L
            while (isActive) {
                delay(timeDelay)
                val timeLeft = stateFlow.value.timeLeft - timeDelay
                if (stateFlow.value.isPlayingGame)
                    if (timeLeft <= 0) {
                        _stateFlow.update {
                            it.copy(
                                isDefeat = true,
                                isPlayingGame = false,
                            )
                        }
                    } else {
                        _stateFlow.update {
                            it.copy(
                                timeLeft = timeLeft
                            )
                        }
                    }
            }
        }
    }

    fun startGame() {
        _stateFlow.update {
            it.copy(
                levelGame = 1,
                levelTypeGame = 1,
            )
        }
        generateNumber()
    }

    private fun getHighScoreFromDS() {
        viewModelScope.launch {
            getMathScoreStateUseCase(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _stateFlow.update {
                        it.copy(
                            highScoreState = value
                        )
                    }
                }
            }
        }
    }

    private fun generateAnswerNumberCorrect(listNumberQuestion: ArrayList<Int>): Double {
        val result = when (stateFlow.value.typeMathGame) {
            TypeMath.ADD.name -> {
                listNumberQuestion.sum().toDouble()
            }

            TypeMath.SUB.name -> {
                var result = listNumberQuestion[0]
                for (i in 1 until listNumberQuestion.size) {
                    result -= listNumberQuestion[i]
                }
                result.toDouble()
            }

            TypeMath.MUL.name -> {
                listNumberQuestion.reduce { acc, i ->
                    acc * i
                }.toDouble()
            }

            TypeMath.DIV.name -> {
                var result = listNumberQuestion[0].toDouble()
                for (i in 1 until listNumberQuestion.size) {
                    result /= listNumberQuestion[i]
                }
                result
            }

            else -> {
                0.0
            }
        }
        return result
    }

    private fun generateNumber() {
        val listNumber = arrayListOf<Int>()
        do {
            listNumber.clear()
            val typeListNumber = levelTypeGame()
            repeat(typeListNumber.numberDigit) {
                if (!((stateFlow.value.typeMathGame == TypeMath.DIV.name || stateFlow.value.typeMathGame == TypeMath.MUL.name) && it == 2)) {
                    listNumber.add(
                        when (typeListNumber.numberLetter) {
                            1 -> {
                                (0..9).random()
                            }

                            2 -> {
                                (10..99).random()
                            }

                            3 -> {
                                when (stateFlow.value.typeMathGame) {
                                    TypeMath.ADD.name -> {
                                        (100..999).random()
                                    }

                                    TypeMath.SUB.name -> {
                                        (100..999).random()
                                    }

                                    TypeMath.MUL.name -> {
                                        (10..99).random()
                                    }

                                    TypeMath.DIV.name -> {
                                        (10..99).random()
                                    }

                                    else -> {
                                        (10..99).random()
                                    }
                                }
                            }

                            else -> {
                                (0..9).random()
                            }
                        }
                    )
                }

            }
            Log.d(TAG, "generateNumber: ${generateAnswerNumberCorrect(listNumber)}")
        } while (generateAnswerNumberCorrect(listNumber).toInt() < 0 || !Constants.isNaturalNumber(
                generateAnswerNumberCorrect(listNumber)
            )
        )

        val result = generateAnswerNumberCorrect(listNumber)
        val distractRange = 20

        val variationPercentage = (30..40).random()
        // Generate list answer```
        val distract1 = result * (1.0 + (50 / 100.0))
        val distract2 = result * (1.0 + (variationPercentage.toDouble() / 100.0))
        val distract3 = result * (1.0 - (variationPercentage.toDouble() / 100.0))
        val listAnswer = arrayListOf(
            result.toInt(),
            distract1.roundToInt(),
            distract2.roundToInt(),
            distract3.toInt()
        ).shuffled()


        _stateFlow.update {
            it.copy(
                timeLeft = 5000L,
                isDefeat = false,
                isPlayingGame = true,
                listNumberQuestion = listNumber,
                listAnswerCorrectNumber = listAnswer,
                correctAnswerNumber = result.toInt(),
            )
        }
    }

    private fun levelTypeGame(): TypeListNumber {
        val leveTypeGame = stateFlow.value.levelTypeGame
        return when (leveTypeGame) {
            1 -> {
                TypeListNumber(
                    numberLetter = 1,
                    numberDigit = 2
                )
            }

            2 -> {
                TypeListNumber(
                    numberLetter = 1,
                    numberDigit = 3
                )
            }

            3 -> {
                TypeListNumber(
                    numberLetter = 2,
                    numberDigit = 2
                )
            }

            4 -> {
                TypeListNumber(
                    numberLetter = 2,
                    numberDigit = 3
                )
            }

            5 -> {
                TypeListNumber(
                    numberLetter = 3,
                    numberDigit = 2
                )
            }

            6 -> {
                TypeListNumber(
                    numberLetter = 3,
                    numberDigit = 3
                )
            }

            else -> {
                TypeListNumber(
                    numberLetter = 1,
                    numberDigit = 2
                )
            }
        }
    }

    fun chooseAnswer(number: Int) {
        Log.d(TAG, "chooseAnswer: $number")
        if (stateFlow.value.correctAnswerNumber == number) {
            nextLevel()
        } else {
            _stateFlow.update {
                it.copy(
                    isDefeat = true,
                    isPlayingGame = false,
                )
            }
            saveHighScore()
        }
    }

    private fun nextLevel() {
        val levelTypeGame = (stateFlow.value.levelGame / 3 + 1).coerceAtMost(6)
        _stateFlow.update {
            it.copy(
                levelGame = it.levelGame + 1,
                levelTypeGame = levelTypeGame,
            )
        }
        generateNumber()
    }

    private fun saveHighScore() {
        val score = stateFlow.value.levelGame - 1
        viewModelScope.launch {
            saveMathScoreWithKeyUseCase(
                HighScoreWithKey(
                    highScore = score,
                    key = when (stateFlow.value.typeMathGame) {
                        TypeMath.ADD.name -> {
                            Constants.HIGH_SCORE_PLUS
                        }

                        TypeMath.SUB.name -> {
                            Constants.HIGH_SCORE_MINUS
                        }

                        TypeMath.MUL.name -> {
                            Constants.HIGH_SCORE_MULTIPLY
                        }

                        TypeMath.DIV.name -> {
                            Constants.HIGH_SCORE_DIVIDE
                        }

                        else -> {
                            Constants.HIGH_SCORE_PLUS
                        }
                    }
                )
            ).onSuccess {
                getHighScoreFromDS()
            }
        }
    }
}

data class QuickMathUiState(
    val typeMathGame: String = TypeMath.ADD.name,
    val highScoreState: HighScoreState? = null,
    val listNumberQuestion: ArrayList<Int> = arrayListOf(),
    val listAnswerCorrectNumber: List<Int> = arrayListOf(),
    val levelGame: Int = 1,
    val levelTypeGame: Int = 1,
    val timeLeft: Long = 5000L,
    val timeAlive: Long = 5000L,
    val isDefeat: Boolean = false,
    val isPlayingGame: Boolean = false,
    val correctAnswerNumber: Int = 0,
)

data class TypeListNumber(
    val numberLetter: Int = 1,
    val numberDigit: Int = 1,
)