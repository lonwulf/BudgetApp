package com.lonwulf.budgetapp.domain.model

import androidx.compose.ui.graphics.Color

data class Expenses(
    var expense: Double = 0.0,
    var spendingCategory: List<ExpenseCategory> = emptyList(),
    var recentTransactions: List<Transactions>? = emptyList(),
    var date: String = String()
) {
    data class ExpenseCategory(
        var categoryName: String,
        var amount: Double = 0.0,
        var color: Color = Color.Unspecified,
        var progressFraction: Float = 0f
    )
}


