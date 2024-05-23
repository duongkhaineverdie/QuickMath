package com.mathtech.quickmath.presentation.ui.quickmathgame.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathtech.quickmath.R
import com.mathtech.quickmath.presentation.ui.component.CustomDialog
import com.mathtech.quickmath.presentation.ui.theme.QuickMathTheme

@Composable
fun QuickMathDialogGameOver(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onReload: () -> Unit,
    onExit: () -> Unit,
    containerColor: Color = Color.White.copy(alpha = 0.5f),
    textScore: Int = 100,
) {
    CustomDialog(showDialog = showDialog, onDismissRequest = onDismissRequest) {
        var pressedReload by remember {
            mutableStateOf(false)
        }
        val colorFilterReload = when (pressedReload) {
            true -> ColorFilter.tint(Color(0xFFF9A8D4))
            false -> null
        }
        val textColorReload = when (pressedReload) {
            true -> Color.White
            false -> Color(0xFFF9A8D4)
        }
        var pressExit by remember {
            mutableStateOf(false)
        }
        val colorFilterExit = when (pressExit) {
            true -> ColorFilter.tint(Color(0xFF93C5FD))
            false -> null
        }
        val textColorExit = when (pressExit) {
            true -> Color.White
            false -> Color(0xFF93C5FD)
        }
        Column(
            modifier = Modifier.background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp))
                    .background(containerColor)
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    text = "Game Over",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 40.sp,
                    color = Color.White
                )
            }
            Card(
                modifier = Modifier.padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = containerColor,
                    contentColor = Color.White
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.your_score).uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 50.sp
                    )
                    Text(
                        text = textScore.toString().uppercase(),
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 100.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            pressedReload = true
                                            awaitRelease()
                                            pressedReload = false
                                            onReload()
                                        },
                                    )
                                }
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            val painter =
                                painterResource(id = R.drawable.img_background_button_reload)
                            val imageRatio =
                                painter.intrinsicSize.width / painter.intrinsicSize.height
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(imageRatio),
                                colorFilter = colorFilterReload,
                            )
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 10.dp
                                ),
                                text = stringResource(id = R.string.try_again),
                                style = MaterialTheme.typography.titleLarge,
                                color = textColorReload,
                                fontSize = 20.sp
                            )

                        }

                        Box(
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            pressExit = true
                                            awaitRelease()
                                            pressExit = false
                                            onExit()
                                        },
                                    )
                                }
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            val painter =
                                painterResource(id = R.drawable.img_background_button_exit)
                            val imageRatio =
                                painter.intrinsicSize.width / painter.intrinsicSize.height
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(imageRatio),
                                colorFilter = colorFilterExit,
                            )
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 10.dp
                                ),
                                text = stringResource(id = R.string.exit),
                                style = MaterialTheme.typography.titleLarge,
                                color = textColorExit,
                                fontSize = 20.sp
                            )

                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "QuickMathDialogGameOver")
@Composable
private fun PreviewQuickMathDialogGameOver() {
    QuickMathTheme {
        QuickMathDialogGameOver(
            showDialog = true,
            onDismissRequest = {/* no-op */},
            onReload = {/* no-op */},
            onExit = {/* no-op */}
        )
    }
}