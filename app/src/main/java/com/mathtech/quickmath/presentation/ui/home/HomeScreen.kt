package com.mathtech.quickmath.presentation.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import com.mathtech.quickmath.R
import com.mathtech.quickmath.data.datastore.DataStoreManager
import com.mathtech.quickmath.domain.navigation.Destination
import com.mathtech.quickmath.presentation.ui.theme.QuickMathTheme
import com.mathtech.quickmath.utils.TypeMath
import org.koin.androidx.compose.koinViewModel
import kotlin.math.floor

@Composable
fun HomeScreen(navController: NavHostController) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        highScorePlus = uiState.highScoreState?.highScorePlus ?: 0,
        highScoreMinus = uiState.highScoreState?.highScoreSubtract ?: 0,
        highScoreMulti = uiState.highScoreState?.highScoreMultiply ?: 0,
        highScoreDivide = uiState.highScoreState?.highScoreDivide ?: 0,
        onClickPlus = { navController.navigate(Destination.QuickMathScreen(TypeMath.ADD.toString())) },
        onClickMinus = { navController.navigate(Destination.QuickMathScreen(TypeMath.SUB.toString())) },
        onClickMulti = { navController.navigate(Destination.QuickMathScreen(TypeMath.MUL.toString())) },
        onClickDivide = { navController.navigate(Destination.QuickMathScreen(TypeMath.DIV.toString())) },
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    highScorePlus: Int = 0,
    highScoreMinus: Int = 0,
    highScoreMulti: Int = 0,
    highScoreDivide: Int = 0,
    onClickPlus: () -> Unit,
    onClickMinus: () -> Unit,
    onClickMulti: () -> Unit,
    onClickDivide: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF63AFED),
                        Color(0xFFCBE1DC),
                    )
                )
            )
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 10.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.title_home),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                modifier = Modifier,
                letterSpacing = 2.sp
            )
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
        ) {
            items(4) {
                val idImage = when (it) {
                    0 -> R.drawable.img_plus
                    1 -> R.drawable.img_minus
                    2 -> R.drawable.img_multi
                    3 -> R.drawable.img_divide
                    else -> R.drawable.img_plus
                }

                val titleMath = when (it) {
                    0 -> R.string.addition
                    1 -> R.string.subtraction
                    2 -> R.string.multiplication
                    3 -> R.string.division
                    else -> R.string.addition
                }

                val onClickAction = when (it) {
                    0 -> onClickPlus
                    1 -> onClickMinus
                    2 -> onClickMulti
                    3 -> onClickDivide
                    else -> onClickPlus
                }

                val highScore = when (it) {
                    0 -> highScorePlus
                    1 -> highScoreMinus
                    2 -> highScoreMulti
                    3 -> highScoreDivide
                    else -> 0
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF6F6F6)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    onClick = onClickAction,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .heightIn(min = 200.dp)
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = stringResource(id = titleMath),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Image(
                            painter = painterResource(id = idImage),
                            contentDescription = null,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.high_score, highScore),
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
    }


}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    QuickMathTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            onClickPlus = {/* no-op */ },
            onClickMinus = {/* no-op */ },
            onClickMulti = {/* no-op */ },
            onClickDivide = {/* no-op */ },
        )
    }
}