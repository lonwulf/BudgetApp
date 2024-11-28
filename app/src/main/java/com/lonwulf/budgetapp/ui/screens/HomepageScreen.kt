package com.lonwulf.budgetapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lonwulf.budgetapp.R
import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.navigation.NavComposable
import com.lonwulf.budgetapp.presentation.CircularProgressBar
import com.lonwulf.budgetapp.ui.theme.darkGreen
import com.lonwulf.budgetapp.ui.theme.gray
import com.lonwulf.budgetapp.ui.theme.lightGreen
import com.lonwulf.budgetapp.ui.theme.red
import com.lonwulf.budgetapp.ui.theme.textBlack
import com.lonwulf.budgetapp.ui.theme.textGray
import com.lonwulf.budgetapp.ui.theme.veryLightGray
import com.lonwulf.budgetapp.ui.theme.whiteBg
import com.lonwulf.budgetapp.ui.viewmodel.SharedViewModel
import com.lonwulf.budgetapp.util.GenericResultState
import com.lonwulf.budgetapp.util.thousandFormatter
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
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    vm: SharedViewModel = hiltViewModel()
) {
    val resultState by vm.monthlyExpensesReport.collectAsState(initial = GenericResultState.Empty)
    var expenses by remember {
        mutableStateOf(Expenses())
    }
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
//        vm.fetchMonthlyExpenses(getCurrentMonth().toString())
        vm.fetchAllExpenses()
    }
    LaunchedEffect(key1 = resultState) {
        when (resultState) {
            is GenericResultState.Loading -> isLoading = true
            is GenericResultState.Empty -> isLoading = false
            is GenericResultState.Error -> isLoading = false
            is GenericResultState.Success -> {
                isLoading = false
                val successState = resultState as? GenericResultState.Success<Expenses>
                successState?.result?.let {
                    expenses = it
                }
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(color = whiteBg)
    ) {
        val (profileImg, welcomeTxt, notificationIcn, boxBg, loader, summary, recentTransactionsTitle, listview) = createRefs()

        CircularProgressBar(isDisplayed = isLoading, modifier = modifier.constrainAs(loader) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        })

        Image(
            painter = painterResource(com.lonwulf.budgetapp.presentation.R.drawable.profile_img),
            contentDescription = "profile_img",
            modifier
                .size(25.dp)
                .constrainAs(profileImg) {
                    top.linkTo(parent.top, 15.dp)
                    start.linkTo(parent.start, 15.dp)
                }
        )

        Text(
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            text = stringResource(R.string.welcome_txt),
            style = MaterialTheme.typography.labelMedium,
            color = textBlack,
            modifier = modifier.constrainAs(welcomeTxt) {
                top.linkTo(profileImg.top)
                start.linkTo(profileImg.end, 8.dp)
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
                    shape = RoundedCornerShape(8.dp),
                    brush = Brush.linearGradient(
                        colors = listOf(
                            lightGreen, darkGreen
                        )
                    )
                )
                .constrainAs(boxBg) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(profileImg.bottom, 32.dp)
                    width = Dimension.fillToConstraints
                }) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(top = 20.dp)
                ) {
                    Text(
                        text = expenses.expense.toString(),
                        fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                        color = whiteBg
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    Text(
                        text = "Ksh.",
                        fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                        color = whiteBg
                    )
                }
                Text(
                    text = stringResource(R.string.remaining_txt),
                    fontStyle = MaterialTheme.typography.headlineSmall.fontStyle,
                    color = whiteBg,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
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
            text = stringResource(R.string.recent_transactions),
            fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
            color = textBlack,
            modifier = modifier.constrainAs(recentTransactionsTitle) {
                top.linkTo(summary.bottom, 20.dp)
                start.linkTo(parent.start, 15.dp)
            }
        )
        expenses.recentTransactions?.forEach { transaction ->
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .constrainAs(listview) {
                        top.linkTo(recentTransactionsTitle.bottom, 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
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
                            .background(color = veryLightGray)
                            .padding(10.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        val (img, expenseName, date, amount, category) = createRefs()
                        Image(
                            painter = painterResource(transaction.imageRes),
                            contentDescription = "null",
                            modifier = modifier
                                .size(35.dp)
                                .constrainAs(img) {
                                    start.linkTo(parent.start, 15.dp)
                                    top.linkTo(parent.top, 15.dp)
                                })
                        Text(
                            text = transaction.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = textBlack,
                            modifier = modifier.constrainAs(expenseName) {
                                top.linkTo(img.top)
                                start.linkTo(img.end, 10.dp)
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
                            text = transaction.category,
                            style = MaterialTheme.typography.bodySmall,
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
                    text = "Total Spend this Month",
                    style = MaterialTheme.typography.labelMedium,
                    color = textBlack
                )
                Spacer(modifier = modifier.padding(vertical = 5.dp))
                Text(
                    text = "Ksh. ${thousandFormatter(totalSpend.toDouble())}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = textBlack
                )
            }
            CustomDropdownMenu(
                months = listOf("June", "July", "August"),
                selectedMonth = spendMonth
            )
        }

        categories.forEach { category ->
            CategorySpendItem(category)
        }
    }
}

@Composable
fun CustomDropdownMenu(
    months: List<String>,
    selectedMonth: String
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(BorderStroke(1.dp, color = lightGreen))

        ) {
            Text(selectedMonth, style = MaterialTheme.typography.bodyMedium, color = textBlack)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                tint = textBlack,
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select Month",
                modifier = Modifier.size(24.dp)
            )
        }
        DropdownMenu(
            shape = RoundedCornerShape(8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentWidth()
                .padding(20.dp)
        ) {
            months.forEach { month ->
                DropdownMenuItem(
                    colors = MenuItemColors(
                        textColor = textBlack,
                        leadingIconColor = lightGreen,
                        trailingIconColor = lightGreen,
                        disabledTextColor = gray,
                        disabledLeadingIconColor = gray,
                        disabledTrailingIconColor = gray
                    ),
                    text = { Text(month, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CategorySpendItem(category: Expenses.ExpenseCategory) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
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
            color = textBlack
        )
        Text(
            text = "Ksh. ${thousandFormatter(category.amount.toDouble())}",
            style = MaterialTheme.typography.bodyMedium,
            color = textBlack
        )
    }
}
