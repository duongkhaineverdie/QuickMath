package com.mathtech.quickmath.presentation.ui.quickmathgame.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathtech.quickmath.R
import com.mathtech.quickmath.utils.TAG
import kotlinx.coroutines.flow.collectLatest

@Composable
fun QuickMathAnswerButton(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    answerNumber: Int = 0
) {
    var pressed by remember {
        mutableStateOf(false)
    }
    val imageSource = when (pressed) {
        true -> R.drawable.background_button_clicked
        false -> R.drawable.background_button
    }
    val painter = painterResource(id = imageSource)
    val imageRatio = painter.intrinsicSize.width / painter.intrinsicSize.height

    Box(
        modifier = modifier.pointerInput(answerNumber) {
            detectTapGestures(
                onPress = {
                    pressed = true
                    awaitRelease()
                    pressed = false
                    onClick(answerNumber)
                },
            )
        },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageRatio)
        )
        Text(
            text = answerNumber.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 70.sp,
            color = Color.Black,
            fontWeight = FontWeight(900)
        )
    }
}

@Preview(name = "QuickMathAnswerButton", showSystemUi = true)
@Composable
private fun PreviewQuickMathAnswerButton() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        items(4) {
            QuickMathAnswerButton(
                modifier = Modifier.heightIn(max = 300.dp),
                onClick = {/* no-op */ }
            )
        }
    }
}