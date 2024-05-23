package com.mathtech.quickmath.presentation.ui.quickmathgame

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mathtech.quickmath.R
import com.mathtech.quickmath.presentation.ui.component.CustomDialog
import com.mathtech.quickmath.presentation.ui.quickmathgame.components.QuickMathAnswerButton
import com.mathtech.quickmath.presentation.ui.quickmathgame.components.QuickMathDialogGameOver
import com.mathtech.quickmath.presentation.ui.theme.QuickMathTheme
import com.mathtech.quickmath.utils.TAG
import com.mathtech.quickmath.utils.TypeMath
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuickMathGameScreen(navController: NavHostController) {
    val quickMathGameViewModel: QuickMathGameViewModel = koinViewModel()
    val uiState by quickMathGameViewModel.stateFlow.collectAsState()

    QuickMathGameScreen(
        modifier = Modifier
            .fillMaxSize(),
        onBack = { navController.popBackStack() },
        processFloat = uiState.timeLeft.toFloat() / uiState.timeAlive,
        levelGame = uiState.levelGame,
        listAnswerNumber = uiState.listAnswerCorrectNumber,
        onClickAnswerButton = quickMathGameViewModel::chooseAnswer,
        onNewGame = quickMathGameViewModel::startGame,
        listNumberQuestion = uiState.listNumberQuestion,
        typeMathGame = uiState.typeMathGame,
        isDefeat = uiState.isDefeat,
        onExitGame = { navController.popBackStack() }
    )
}

@Composable
fun QuickMathGameScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    levelGame: Int = 1,
    processFloat: Float = 0.5f,
    listAnswerNumber: List<Int>,
    listNumberQuestion: List<Int>,
    onClickAnswerButton: (Int) -> Unit,
    onNewGame: () -> Unit,
    onExitGame: () -> Unit,
    typeMathGame: String = TypeMath.ADD.name,
    isDefeat: Boolean = false,
) {
    Image(
        painter = painterResource(id = R.drawable.img_background_math),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = modifier
            .background(Color.Gray.copy(alpha = 0.5f))
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 5.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 5.dp
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null,
                    tint = Color.Black,
                )
            }
            Text(
                text = stringResource(id = R.string.title_level_regex, levelGame),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info, contentDescription = null,
                    tint = Color.Black,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { processFloat },
                color = Color.White,
                trackColor = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(15.dp),
                strokeCap = StrokeCap.Round
            )
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "${
                            listNumberQuestion.joinToString(
                                when (typeMathGame) {
                                    TypeMath.ADD.name -> " + "
                                    TypeMath.SUB.name -> " - "
                                    TypeMath.MUL.name -> " * "
                                    TypeMath.DIV.name -> " : "
                                    else -> " + "
                                }
                            )
                        } = ...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 10.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Log.d(TAG, "QuickMathGameScreen: $listAnswerNumber")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ) {
            items(4) {
                Log.d(TAG, "QuickMathGameScreen: ${listAnswerNumber[it]} - $it")
                QuickMathAnswerButton(
                    modifier = Modifier.heightIn(max = 300.dp),
                    onClick = onClickAnswerButton,
                    answerNumber = listAnswerNumber[it]
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    QuickMathDialogGameOver(
        showDialog = isDefeat,
        onDismissRequest = { /*TODO*/ },
        onReload = onNewGame,
        onExit = onExitGame,
        containerColor = Color(0xFF5F7CFF),
        textScore = levelGame - 1
    )
}

@Composable
@Preview(name = "QuickMathGameScreen", showSystemUi = true)
private fun QuickMathGameScreenPreview() {
    QuickMathTheme {
        QuickMathGameScreen(
            modifier = Modifier
                .fillMaxSize(),
            onBack = {/* no-op */ },
            listAnswerNumber = arrayListOf(2, 55, 666, 7),
            onClickAnswerButton = {/* no-op */ },
            onNewGame = {/* no-op */ },
            typeMathGame = TypeMath.ADD.name,
            listNumberQuestion = arrayListOf(343,355),
            onExitGame = {/* no-op */}
        )
    }
}

