package com.lonwulf.budgetapp.domain.model

import androidx.compose.ui.graphics.Color
import java.util.Date

data class Transactions(
    var name: String = "",
    var amount: Double = 0.0,
    var category: String = "",
    var progress: Float = 0f,
    var color: Color? = null,
    var imageRes: Int = 0,
    var date: Date? = null
)
