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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lonwulf.budgetapp.R
import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.domain.model.Transactions
import com.lonwulf.budgetapp.navigation.Destinations
import com.lonwulf.budgetapp.navigation.NavComposable
import com.lonwulf.budgetapp.navigation.TopLevelDestinations
import com.lonwulf.budgetapp.ui.theme.darkGreen
import com.lonwulf.budgetapp.ui.theme.lightGreen
import com.lonwulf.budgetapp.ui.theme.textBlack
import com.lonwulf.budgetapp.ui.theme.veryLightGray
import com.lonwulf.budgetapp.ui.theme.whiteBg
import com.lonwulf.budgetapp.ui.viewmodel.SharedViewModel
import com.lonwulf.budgetapp.util.GenericResultState
import com.lonwulf.budgetapp.util.getFormattedDate
import com.lonwulf.budgetapp.util.getLocalTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale

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
    val parentEntry =
        remember { navHostController.getBackStackEntry(TopLevelDestinations.HomepageScreen.route) }
    val vm = hiltViewModel<SharedViewModel>(parentEntry)
    val resultState by vm.insertExpensesStateFlow.collectAsState()

    LaunchedEffect(key1 = resultState) {
        when (resultState) {
            is GenericResultState.Loading -> {}
            is GenericResultState.Empty -> {}
            is GenericResultState.Error -> {}
            is GenericResultState.Success -> {
                navHostController.navigate(Destinations.SuccessScreen.route)
            }
        }
    }
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(whiteBg)
    ) {
        val (title, categoryName, categoryField, allocationName, allocationfield, createBtn) = createRefs()
        val guideline = createGuidelineFromBottom(0.4f)
        var itemName by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }

        Text(
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            color = textBlack,
            modifier = modifier.constrainAs(title) {
                top.linkTo(parent.top, 30.dp)
                start.linkTo(parent.start, 15.dp)
            },
            text = stringResource(R.string.add_new)
        )

        Text(
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
            color = textBlack, modifier = modifier.constrainAs(categoryName) {
                top.linkTo(title.bottom, 20.dp)
                start.linkTo(title.start)
            }, text = stringResource(R.string.item_name)
        )

        OutlinedTextField(
            shape = RoundedCornerShape(8.dp),
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text(stringResource(R.string.item_name)) },
            placeholder = { Text(stringResource(R.string.placeholder_txt)) },
            colors = getTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = modifier.constrainAs(categoryField) {
                top.linkTo(categoryName.bottom, 5.dp)
                start.linkTo(title.start)
                end.linkTo(parent.end, 15.dp)
                width = Dimension.fillToConstraints
            },
            maxLines = 1
        )
        Text(
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
            color = textBlack,
            modifier = modifier.constrainAs(allocationName) {
                top.linkTo(categoryField.bottom, 20.dp)
                start.linkTo(title.start)
            },
            text = stringResource(R.string.allocation)
        )

        AllocationTextField(modifier.constrainAs(allocationfield) {
            top.linkTo(allocationName.bottom, 5.dp)
            start.linkTo(categoryName.start)
            end.linkTo(parent.end, 15.dp)
        }, amount = { amount = it })

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
                val expenseModel =
                    Expenses(
                        expense = amount.toDouble(),
                        spendingCategory = listOf(
                            Expenses.ExpenseCategory(
                                categoryName = itemName,
                                amount = amount.toDouble()
                            )
                        ),
                        recentTransactions = listOf(
                            Transactions(
                                name = itemName,
                                amount = amount.toDouble(),
                                date = getFormattedDate(),
                                time = getLocalTime()
                            )
                        )
                    )
                vm.insertExpense(expenseModel)
                vm.cacheExpenseModel(expenseModel)
            }) {
            Text(
                text = stringResource(R.string.create_new_budget_item),
                color = textBlack
            )
        }
    }
}

@Composable
fun AllocationTextField(modifier: Modifier, amount: (String) -> Unit = {}) {
    var text by remember { mutableStateOf("") }
    val currencies = listOf("Ksh.", "UG.", "TZSh.")
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    Text(
                        text = selectedCurrency,
                        modifier = modifier
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
                    modifier = modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(veryLightGray)
                )

                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        amount(text)
                    },
                    placeholder = { Text(stringResource(R.string.enter_allocated_amount)) },
                    colors = getTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = modifier
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
    unfocusedTextColor = lightGreen,
    focusedBorderColor = darkGreen,
    focusedLabelColor = darkGreen,
    unfocusedLabelColor = veryLightGray,
    unfocusedBorderColor = veryLightGray
)