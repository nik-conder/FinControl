package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.InfoPageType
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.example.compose.FinControlTheme
import kotlinx.coroutines.delay

@Composable
fun InfoPageComponent(
    type: InfoPageType,
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            when (type) {
                InfoPageType.NOT_PROFILE -> Text(
                    stringResource(id = R.string.no_profiles),
                    style = MaterialTheme.typography.headlineMedium
                )

                InfoPageType.LOADING -> Text(
                    stringResource(id = R.string.loading),
                    style = MaterialTheme.typography.headlineMedium
                )
                InfoPageType.NO_DATA -> {

                }
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp)
        ) {
            Text(
                text = when (type) {
                    InfoPageType.NOT_PROFILE -> stringResource(id = R.string.create_new_profile)
                    InfoPageType.LOADING -> stringResource(id = R.string.wait)
                    InfoPageType.NO_DATA -> stringResource(id = R.string.no_data)
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 24.dp)
        ) {
            when (type) {
                InfoPageType.NOT_PROFILE -> {
                    Text(
                        text = "\uD83E\uDD14",
                        fontSize = 48.sp
                    )
                }

                InfoPageType.LOADING -> {
                    ClockEmojiAnimation()
                }

                InfoPageType.NO_DATA -> {
                    Text(
                        text = "\uD83E\uDEE5",
                        fontSize = 48.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 24.dp)
        ) {
            when (type) {
                InfoPageType.NOT_PROFILE -> {
                    Button(onClick = {
                        navController!!.navigate(Screen.CreateProfile.route)
                    }) {
                        Text(text = stringResource(id = R.string.create_profile))
                    }
                }

                InfoPageType.LOADING -> {}
                InfoPageType.NO_DATA -> {}
            }
        }
    }
}

@Composable
fun ClockEmojiAnimation() {
    val clockEmojis = listOf(
        "\uD83D\uDD5B", "\uD83D\uDD50", "\uD83D\uDD51", "\uD83D\uDD52",
        "\uD83D\uDD53", "\uD83D\uDD54", "\uD83D\uDD55", "\uD83D\uDD56",
        "\uD83D\uDD57", "\uD83D\uDD58", "\uD83D\uDD59", "\uD83D\uDD5A"
    )

    var currentEmojiIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            currentEmojiIndex = (currentEmojiIndex + 1) % clockEmojis.size
        }
    }

    Row(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text(
            text = clockEmojis[currentEmojiIndex],
            fontSize = 48.sp
        )
    }
}

@PreviewLightDark
@Composable
fun InfoPageComponentPreview() {
    FinControlTheme {
        InfoPageComponent(type = InfoPageType.NO_DATA)
    }
}