package com.lonwulf.budgetapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lonwulf.budgetapp.R
import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.navigation.NavComposable
import com.lonwulf.budgetapp.ui.theme.BudgetAppTheme
import com.lonwulf.budgetapp.ui.theme.darkGreen
import com.lonwulf.budgetapp.ui.theme.lightGreen
import com.lonwulf.budgetapp.ui.theme.red
import com.lonwulf.budgetapp.ui.theme.textBlack
import com.lonwulf.budgetapp.ui.theme.textGray
import com.lonwulf.budgetapp.ui.theme.whiteBg
import com.lonwulf.budgetapp.ui.viewmodel.SharedViewModel
import com.lonwulf.budgetapp.util.GenericResultState
import java.time.LocalDate
import java.time.Month


class HomepageScreenComposable : NavComposable {
    @Composable
    override fun Composable(
        navHostController: NavHostController,
        snackbarHostState: SnackbarHostState
    ) {
        HomepageScreen(navHostController = navHostController)
    }

}

fun getCurrentMonth(): Month {
    val currentDate = LocalDate.now()
    return currentDate.month
}

@Composable
fun HomepageScreen(
    navHostController: NavHostController?,
    modifier: Modifier = Modifier,
    vm: SharedViewModel = hiltViewModel()
) {
    val resultState by vm.monthlyExpensesReport.collectAsState(initial = GenericResultState.Empty)
    var expenses by remember {
        mutableStateOf(Expenses())
    }
    LaunchedEffect(Unit) {
        vm.fetchMonthlyExpenses(getCurrentMonth().toString())
    }
    LaunchedEffect(key1 = resultState) {
        when (resultState) {
            is GenericResultState.Loading -> {}
            is GenericResultState.Empty -> {}
            is GenericResultState.Success -> {
                val successState = resultState as? GenericResultState.Success<Expenses>
                successState?.result?.let {
                    expenses = it
                }
            }
        }
    }

    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .background(color = whiteBg)) {
        val (profileImg, welcomeTxt, notificationIcn, boxBg, amount, currency, amountTxt, summary, recentTransactionsTitle, listview) = createRefs()
        Icon(
            painter = painterResource(com.lonwulf.budgetapp.presentation.R.drawable.profile_img),
            contentDescription = "profile_img",
            modifier.constrainAs(profileImg) {
                top.linkTo(parent.top, 15.dp)
                start.linkTo(parent.start, 15.dp)
            }
        )

        Text(
            text = stringResource(R.string.welcome_txt),
            style = MaterialTheme.typography.labelMedium,
            color = textBlack,
            modifier = modifier.constrainAs(welcomeTxt) {
                top.linkTo(profileImg.top)
                start.linkTo(profileImg.end, 5.dp)
            }
        )

        Image(
            painter = painterResource(com.lonwulf.budgetapp.presentation.R.drawable.notification_bell),
            contentDescription = "notification bell",
            modifier = modifier
                .size(30.dp)
                .constrainAs(notificationIcn) {
                    end.linkTo(parent.end, 15.dp)
                    top.linkTo(profileImg.top)
                }
        )
        Box(
            modifier = modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            lightGreen, darkGreen
                        )
                    )
                )
                .constrainAs(boxBg) {
                    start.linkTo(profileImg.start)
                    end.linkTo(parent.end, 15.dp)
                    top.linkTo(amount.top, 20.dp)
                    bottom.linkTo(amountTxt.bottom, 20.dp)
                    width = Dimension.fillToConstraints
                })
        Text(
            text = expenses.expense.toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = whiteBg,
            modifier = modifier.constrainAs(amount) {
                top.linkTo(profileImg.bottom, 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = "currency here",
            style = MaterialTheme.typography.labelSmall,
            color = whiteBg,
            modifier = modifier.constrainAs(currency) {
                bottom.linkTo(amount.bottom)
                start.linkTo(amount.end, 5.dp)
            }
        )
        Text(
            text = stringResource(R.string.remaining_txt),
            style = MaterialTheme.typography.labelSmall,
            color = whiteBg,
            modifier = modifier.constrainAs(currency) {
                top.linkTo(amount.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        SpendingSummary(
            totalSpend = expenses.expense.toString(),
            spendMonth = expenses.date,
            categories = expenses.spendingCategory,
            modifier = modifier.constrainAs(summary) {
                top.linkTo(boxBg.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(
            text = stringResource(R.string.remaining_txt),
            style = MaterialTheme.typography.headlineLarge,
            color = textBlack,
            modifier = modifier.constrainAs(amount) {
                top.linkTo(summary.bottom, 20.dp)
                start.linkTo(parent.start, 15.dp)
            }
        )
        expenses.recentTransactions?.forEach { transaction ->
            ElevatedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
                    .clickable {
                    },
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                ConstraintLayout(
                    modifier = modifier
                        .background(color = MaterialTheme.colorScheme.secondary)
                        .padding(10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    val (img, expenseName, date, amount, category) = createRefs()
                    Image(
                        painter = painterResource(transaction.imageRes),
                        contentDescription = "null",
                        modifier = modifier
                            .size(30.dp)
                            .constrainAs(img) {
                                start.linkTo(parent.start, 15.dp)
                                top.linkTo(parent.top, 15.dp)
                            })
                    Text(
                        text = transaction.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textBlack,
                        modifier = modifier.constrainAs(expenseName) {
                            top.linkTo(img.top)
                            start.linkTo(img.start, 10.dp)
                        }
                    )
                    Text(
                        text = transaction.date.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = textGray,
                        modifier = modifier.constrainAs(date) {
                            top.linkTo(expenseName.bottom, 10.dp)
                            start.linkTo(expenseName.start)
                        }
                    )
                    Text(
                        text = transaction.amount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = red,
                        modifier = modifier.constrainAs(amount) {
                            top.linkTo(img.top)
                            end.linkTo(parent.end, 15.dp)
                        }
                    )
                    Text(
                        text = stringResource(R.string.remaining_txt),
                        style = MaterialTheme.typography.headlineLarge,
                        color = textBlack,
                        modifier = modifier.constrainAs(category) {
                            top.linkTo(date.top)
                            end.linkTo(parent.end, 15.dp)
                        }
                    )
                }
            }
        }


    }
}

@Composable
fun SpendingSummary(
    modifier: Modifier,
    totalSpend: String,
    spendMonth: String,
    categories: List<Expenses.ExpenseCategory>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.total_spent_txt),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = totalSpend,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            CustomDropdownMenu(
                months = listOf("June", "July", "August"),
                selectedMonth = spendMonth
            )
        }

        Column {
            categories.forEach { category ->
                CategorySpendItem(category)
            }
        }
    }
}

@Composable
fun CustomDropdownMenu(months: List<String>, selectedMonth: String) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(
            onClick = { expanded = true }
        ) {
            Text(text = selectedMonth)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Select Month")
        }
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            months.forEach { month ->
                DropdownMenuItem(
                    text = { Text(month) },
                    onClick = { expanded = false }
                )
            }
        }
    }
}

@Composable
fun CategorySpendItem(category: Expenses.ExpenseCategory) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Category Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(category.color, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category.categoryName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = category.amount.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        LinearProgressIndicator(
            progress = category.progressFraction,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = category.color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BudgetAppTheme {
        HomepageScreen(null)
    }
}
