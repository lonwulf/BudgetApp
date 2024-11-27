package com.lonwulf.budgetapp.domain.model

import androidx.compose.ui.graphics.Color

data class Expenses(
    var expense: Double = 0.0,
    var spendingCategory: List<ExpenseCategory> = emptyList(),
    var recentTransactions: List<Transactions>? = emptyList(),
    var date: String = String()
) {
    data class ExpenseCategory(
        val categoryName: String,
        val amount: Double,
        val color: Color,
        val progressFraction: Float
    )
}


