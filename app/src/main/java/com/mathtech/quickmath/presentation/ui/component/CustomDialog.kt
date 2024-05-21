package com.mathtech.quickmath.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mathtech.quickmath.R

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            )
        ) {
            Box(
                Modifier
                    .pointerInput(Unit) { detectTapGestures { } }
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCustomDialog() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            CustomDialog(showDialog = true, onDismissRequest = { /*TODO*/ }) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.img_defeat),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Card(
                        modifier = Modifier.padding(bottom = 80.dp),
                        onClick = { /* no-op */ },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF122131),
                            contentColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 30.dp
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                            text = stringResource(id = R.string.new_game),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}