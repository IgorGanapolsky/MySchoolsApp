package com.cnh.samples.apps.schools.compose.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Simple API to display a Snackbar with text on the screen
 */
@Composable
fun TextSnackbarContainer(
    snackbarText: String,
    showSnackbar: Boolean,
    onDismissSnackbar: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable () -> Unit,
) {
    Box(modifier) {
        content()

        val onDismissState by rememberUpdatedState(onDismissSnackbar)
        LaunchedEffect(showSnackbar, snackbarText) {
            if (showSnackbar) {
                try {
                    snackbarHostState.showSnackbar(
                        message = snackbarText,
                        duration = SnackbarDuration.Short,
                    )
                } finally {
                    onDismissState()
                }
            }
        }

        // Override shapes to not use the ones coming from the MdcTheme
        MaterialTheme(shapes = Shapes()) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier =
                    modifier
                        .align(Alignment.BottomCenter)
                        .systemBarsPadding()
                        .padding(all = 8.dp),
            ) {
                Snackbar(it)
            }
        }
    }
}
