package com.github.minimi.stroycalc.feature.calculatorsList.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.minimi.stroycalc.feature.calculatorsList.CalculatorsListRoute


const val calculatorsListNavigationRoute = "calculatorsList"

fun NavController.navigateToCalculatorsList(navOptions: NavOptions? = null) {
    this.navigate(calculatorsListNavigationRoute, navOptions)
}

fun NavGraphBuilder.calculatorsListScreen(onNavigateTo: (route: String) -> Unit) {
    composable(route = calculatorsListNavigationRoute) {
        CalculatorsListRoute(onNavigateTo = onNavigateTo)
    }
}
