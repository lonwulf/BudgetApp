package com.lonwulf.budgetapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.lonwulf.budgetapp.R
import com.lonwulf.budgetapp.navigation.NavComposable
import com.lonwulf.budgetapp.ui.theme.darkGreen
import com.lonwulf.budgetapp.ui.theme.lightGreen
import com.lonwulf.budgetapp.ui.theme.textBlack
import com.lonwulf.budgetapp.ui.theme.veryLightGray
import com.lonwulf.budgetapp.ui.theme.whiteBg

class BudgetScreenComposable : NavComposable {
    @Composable
    override fun Composable(
        navHostController: NavHostController,
        snackbarHostState: SnackbarHostState
    ) {
        BudgetScreen(navHostController)
    }

}

@Composable
fun BudgetScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (title, categoryName, categoryField, allocationName, allocationfield, createBtn) = createRefs()
        val guideline = createGuidelineFromBottom(0.4f)
        var itemName by remember { mutableStateOf("") }

        Text(color = textBlack, modifier = modifier.constrainAs(title) {
            top.linkTo(parent.top, 30.dp)
            start.linkTo(parent.start, 15.dp)
        }, text = stringResource(R.string.add_new))

        Text(color = textBlack, modifier = modifier.constrainAs(categoryName) {
            top.linkTo(title.bottom, 20.dp)
            start.linkTo(title.start)
        }, text = stringResource(R.string.item_name))

        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text(stringResource(R.string.item_name)) },
            placeholder = { Text(stringResource(R.string.placeholder_txt)) },
            colors = getTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = modifier.constrainAs(categoryField) {
                top.linkTo(categoryName.bottom, 10.dp)
                start.linkTo(categoryName.start)
                end.linkTo(parent.end, 15.dp)
            },
            maxLines = 1
        )
        Text(color = textBlack, modifier = modifier.constrainAs(allocationName) {
            top.linkTo(categoryField.bottom, 20.dp)
            start.linkTo(title.start)
        }, text = stringResource(R.string.allocation))

        AllocationTextField(modifier.constrainAs(allocationfield) {
            top.linkTo(allocationName.bottom, 10.dp)
            start.linkTo(categoryName.start)
            end.linkTo(parent.end, 15.dp)
        })

        ElevatedButton(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier.constrainAs(createBtn) {
                top.linkTo(guideline)
                start.linkTo(categoryName.start)
                end.linkTo(parent.end, 15.dp)
                width = Dimension.fillToConstraints
            },
            colors = ButtonDefaults.buttonColors(containerColor = lightGreen),
            onClick = {

            }) {
            Text(
                text = stringResource(R.string.create_new_budget_item),
                color = textBlack
            )
        }
    }

}

@Composable
fun AllocationTextField(modifier: Modifier) {
    var text by remember { mutableStateOf("") }
    val currencies = listOf("Ksh.", "UG.", "TZSh.")
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Allocation",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    Text(
                        text = selectedCurrency,
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .padding(8.dp),
                        color = Color.Black
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        currencies.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(text = currency) },
                                onClick = {
                                    selectedCurrency = currency
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(veryLightGray)
                )

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text(stringResource(R.string.enter_allocated_amount)) },
                    colors = getTextFieldColors(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun getTextFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = lightGreen,
    unfocusedTextColor = whiteBg,
    focusedBorderColor = darkGreen,
    focusedLabelColor = darkGreen,
    unfocusedLabelColor = veryLightGray,
    unfocusedBorderColor = veryLightGray
)