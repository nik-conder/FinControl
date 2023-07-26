package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SnackBarHost(
    snackbarHostState : SnackbarHostState,
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
                .constrainAs(snackBarHostRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar (
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    action = {
                        Text(
                            text = snackbarHostState.currentSnackbarData?.visuals?.actionLabel?:"",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                ){
                    Text(text = snackbarHostState.currentSnackbarData?.visuals?.message?:"")
                }
            }
        )
    }
}