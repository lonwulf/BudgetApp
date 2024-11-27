package com.lonwulf.budgetapp.domain.model

data class Expenses(
    var expense: Double = 0.0,
    var spendingCategory: String = String(),
    var recentTransactions: List<Transactions>? = emptyList(),
    var date: String = String()
)
