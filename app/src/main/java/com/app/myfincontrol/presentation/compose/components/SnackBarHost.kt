package com.app.myfincontrol.presentation.compose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.compose.FinControlTheme
import kotlinx.coroutines.launch

/**
 * SnackBarHost component to display snackbar
 *
 * @param snackBarHostState : [SnackbarHostState] to display snackbar
 *
 * @return Snackbar displayed
 *
 */
@Composable
fun SnackBarHost(
    snackBarHostState : SnackbarHostState,
){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {

        val(snackBarHostRef) = createRefs()

        SnackbarHost(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    top = 26.dp
                )
                .clip(RoundedCornerShape(12.dp))
                .constrainAs(snackBarHostRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            hostState = snackBarHostState,
            snackbar = {
                Snackbar (
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    action = {
                        TextButton(onClick = {
                            snackBarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Text(
                                text = snackBarHostState.currentSnackbarData?.visuals?.actionLabel?:"",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                ){
                    Text(
                        text = snackBarHostState.currentSnackbarData?.visuals?.message?:"",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SnackBarHostPreview() {
    val test = SnackbarHostState()
    val scope = rememberCoroutineScope()

    FinControlTheme {
        Column (
            modifier = Modifier
                .size(400.dp)
        ) {
            Row {
                TextButton(onClick = {
                    scope.launch {
                        test.showSnackbar("test", actionLabel = "pizdec")
                    }
                }) {
                    Text("click")
                }
            }
            Row {
                SnackBarHost(test)
            }
        }
    }
}