package com.lonwulf.budgetapp.domain.model

data class Transactions(
    var name: String = "",
    var amount: Double = 0.0,
    var category: String = "",
    var imageRes: Int = 0,
    var date: String? = null,
    var time: String = ""
)
