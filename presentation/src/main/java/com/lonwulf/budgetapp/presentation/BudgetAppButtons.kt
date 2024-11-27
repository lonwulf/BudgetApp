package com.lonwulf.budgetapp.presentation

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ButtonFilled(onclick: () -> Unit = {}, colors: ButtonColors, modifier: Modifier, text: String) {
    OutlinedButton(modifier = modifier, onClick = { onclick() }) {

    }
}

@Composable
fun ButtonWithStroke(onclick: () -> Unit = {}, modifier: Modifier, text: String) {
    OutlinedButton(modifier = modifier, onClick = { onclick() }) {
        Text(modifier = modifier, style = MaterialTheme.typography.bodyMedium, text = text)
    }

}