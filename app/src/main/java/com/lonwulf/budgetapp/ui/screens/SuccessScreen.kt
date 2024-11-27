package com.lonwulf.budgetapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.lonwulf.budgetapp.navigation.NavComposable
import com.lonwulf.budgetapp.presentation.R
import com.lonwulf.budgetapp.ui.theme.darkGreen
import com.lonwulf.budgetapp.ui.theme.lightGreen
import com.lonwulf.budgetapp.ui.theme.textBlack
import com.lonwulf.budgetapp.ui.theme.textGray
import com.lonwulf.budgetapp.ui.theme.whiteBg
import kotlinx.coroutines.launch

class SuccessScreenComposable : NavComposable {
    @Composable
    override fun Composable(
        navHostController: NavHostController,
        snackbarHostState: SnackbarHostState
    ) {
        SuccessScreen(navHostController = navHostController, snackbarHostState = snackbarHostState)
    }

}

@Composable
fun SuccessScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (box, headline, descriptionTxt, divider, budgetTitle, category, amountTxt, amount, dateText, dateValue, homeBtn, experienceBtn) = createRefs()

        val guideline = createGuidelineFromBottom(0.35f)
        Box(modifier = modifier.constrainAs(box) {
            top.linkTo(parent.top, margin = 30.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Image(
                modifier = modifier.size(80.dp),
                painter = painterResource(R.drawable.confetti_img),
                contentDescription = "confetti img"
            )
            Image(
                modifier = modifier.size(40.dp),
                painter = painterResource(R.drawable.check),
                contentDescription = "success img"
            )
        }

        Text(
            color = darkGreen,
            modifier = modifier.constrainAs(headline) {
                top.linkTo(box.bottom, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = stringResource(com.lonwulf.budgetapp.R.string.budget_item_added)
        )
        Text(
            color = textGray,
            modifier = modifier.constrainAs(descriptionTxt) {
                top.linkTo(headline.bottom, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = stringResource(com.lonwulf.budgetapp.R.string.budget_item_desc)
        )
        HorizontalDivider(modifier = modifier.constrainAs(divider) {
            top.linkTo(descriptionTxt.bottom, 30.dp)
            start.linkTo(parent.start, 15.dp)
            end.linkTo(parent.end, 15.dp)
        })

        Text(
            color = textGray,
            modifier = modifier.constrainAs(budgetTitle) {
                top.linkTo(divider.bottom, 20.dp)
                start.linkTo(parent.start, 30.dp)
            },
            text = stringResource(com.lonwulf.budgetapp.R.string.category_name)
        )

        Text(
            color = textBlack,
            modifier = modifier.constrainAs(category) {
                top.linkTo(descriptionTxt.top)
                end.linkTo(parent.end, 30.dp)
            },
            text = ""
        )
        Text(
            color = textBlack,
            modifier = modifier.constrainAs(amountTxt) {
                top.linkTo(budgetTitle.bottom, 10.dp)
                start.linkTo(budgetTitle.start)
            },
            text = stringResource(com.lonwulf.budgetapp.R.string.amount)
        )

        Text(
            color = lightGreen,
            modifier = modifier.constrainAs(amount) {
                top.linkTo(amountTxt.top)
                end.linkTo(category.end)
            },
            text = ""
        )
        Text(
            color = textGray,
            modifier = modifier.constrainAs(dateText) {
                top.linkTo(amountTxt.bottom, 10.dp)
                start.linkTo(budgetTitle.start)
            },
            text = stringResource(com.lonwulf.budgetapp.R.string.date)
        )

        Text(
            color = textBlack,
            modifier = modifier.constrainAs(dateValue) {
                top.linkTo(amountTxt.top)
                end.linkTo(category.end)
            },
            text = ""
        )

        ElevatedButton(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier.constrainAs(homeBtn) {
                top.linkTo(guideline)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            onClick = {

            }) {
            Text(
                text = stringResource(com.lonwulf.budgetapp.R.string.back_home),
                color = whiteBg
            )
        }
        ElevatedButton(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                1.dp,
                darkGreen
            ),
            modifier = modifier.constrainAs(experienceBtn) {
                top.linkTo(homeBtn.bottom, 10.dp)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            },
            colors = ButtonDefaults.buttonColors(containerColor = whiteBg),
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "feature not yet available",
                        duration = SnackbarDuration.Long
                    )
                }
            }) {
            Text(
                text = stringResource(com.lonwulf.budgetapp.R.string.rate_experience),
                color = darkGreen
            )
        }

    }

}