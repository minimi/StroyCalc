package com.github.minimi.stroycalc.feature.calculatorsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.minimi.stroycalc.designsystem.theme.StroyCalcTheme
import com.github.minimi.stroycalc.feature.foamBlockCalc.navigation.foamBlockCalcNavigationRoute
import com.github.minimi.stroycalc.feature.laminateCalc.navigation.laminateCalcNavigationRoute

@Composable
fun CalculatorsListRoute(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit
) {
    CalculatorsListScreen(modifier, onNavigateTo)
}


@Composable
fun CalculatorsListScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit
) {
    Column(modifier = modifier) {

        CalculatorItem(
            title = "Калькулятор покрытия пола",
            description = "Рассчет ламината и паркета",
            onClick = { onNavigateTo(laminateCalcNavigationRoute) }
        )

        Divider()
//
//        CalculatorItem(
//            title = "Калькулятор бетона",
//            description = "Рассчет смеси для бетона",
//            onClick = { onNavigateTo("") }
//        )
//        Divider()

        CalculatorItem(
            title = "Калькулятор пеноблоков",
            description = "Рассчет количества пеноблоков",
            onClick = { onNavigateTo(foamBlockCalcNavigationRoute) }
        )
        Divider()

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorItem(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    onClick: () -> Unit
) {
    ListItem(
        headlineText = { Text(text = title) },
        modifier = modifier.clickable { onClick() },
        supportingText = { Text(text = description) }
    )
}

@Preview
@Composable
fun CalculatorItemPreview() {
    StroyCalcTheme {
        CalculatorItem(title = "Заголовок", description = "Некоторое описание", onClick = {})
    }
}


@Preview(device = "id:pixel_4", showSystemUi = true, showBackground = true)
@Composable
fun CalculatorListScreenPreview() {
    StroyCalcTheme {
        CalculatorsListScreen(onNavigateTo = {})
    }
}