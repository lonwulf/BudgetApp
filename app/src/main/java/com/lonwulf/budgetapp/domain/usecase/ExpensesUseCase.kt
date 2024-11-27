package com.lonwulf.budgetapp.domain.usecase

import com.lonwulf.budgetapp.domain.model.Expenses
import com.lonwulf.budgetapp.domain.model.Transactions
import com.lonwulf.budgetapp.domain.repository.Repository
import com.lonwulf.budgetapp.presentation.R
import com.lonwulf.budgetapp.ui.theme.aquaBlue
import com.lonwulf.budgetapp.ui.theme.darkBlue
import com.lonwulf.budgetapp.ui.theme.lightGreen
import com.lonwulf.budgetapp.ui.theme.orange
import com.lonwulf.budgetapp.util.GenericUseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExpensesUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<GenericUseCaseResult<List<Expenses>>> =
        flow {
            val list = repository.fetchAllExpensesRecords()
            emit(GenericUseCaseResult(result = list, isSuccessful = true))
        }

    fun insertAllItems(): Flow<GenericUseCaseResult<Boolean>> = flow {
        try {
            val list = Expenses(
                expense = 58670.40,
                date = "June",
                spendingCategory = listOf(
                    Expenses.ExpenseCategory(
                        categoryName = "Food & Beverages",
                        amount = 28005.00,
                        color = darkBlue,
                        progressFraction = 28005.00f / 58670.40f
                    ),
                    Expenses.ExpenseCategory(
                        categoryName = "Transport & Fuel",
                        amount = 14000.00,
                        color = lightGreen,
                        progressFraction = 14000.00f / 58670.40f
                    ),
                    Expenses.ExpenseCategory(
                        categoryName = "Clothing",
                        amount = 19000.00,
                        color = aquaBlue,
                        progressFraction = 19000.00f / 58670.40f
                    ),
                    Expenses.ExpenseCategory(
                        categoryName = "Transaction Fees",
                        amount = 265.40,
                        color = orange,
                        progressFraction = 265.40f / 58670.40f
                    )
                ),
                recentTransactions = listOf(
                    Transactions(
                        name = "KFC Westlands",
                        amount = 780.00,
                        date = "June 10, 2024",
                        time = "02:35 PM",
                        category = "Eating-out",
                        imageRes = R.drawable.kfc_logo
                    ),
                    Transactions(
                        name = "Naivas Supermarket",
                        amount = 1200.00,
                        date = "June 09, 2024",
                        time = "06:03 PM",
                        category = "Shopping",
                        imageRes = R.drawable.naivas_logo
                    ),
                    Transactions(
                        name = "Total Energies",
                        amount = 1200.00,
                        date = "June 08, 2024",
                        time = "03:20 PM",
                        category = "Fuel",
                        imageRes = R.drawable.total_logo
                    )
                )
            )
            repository.insert(list)
            emit(GenericUseCaseResult(result = true, isSuccessful = true))
        } catch (ex: Exception) {
            emit(GenericUseCaseResult(result = false, isSuccessful = false))
        }
    }

    suspend fun deleteCache() = repository.deleteAllExpenses()

    fun fetchExpenseByMonthDate(date: String): Flow<GenericUseCaseResult<Expenses?>> =
        flow {
            val list = repository.fetchExpensesRecordsByMonthDate(date)
            emit(GenericUseCaseResult(result = list, isSuccessful = true))
        }
}