package com.github.minimi.stroycalc.feature.concreteCalc.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.minimi.stroycalc.feature.concreteCalc.ConcreteCalcRoute


const val concreteCalcNavigationRoute = "concreteCalc"

fun NavController.navigateToConcreteCalc(navOptions: NavOptions? = null) {
    this.navigate(concreteCalcNavigationRoute, navOptions)
}

fun NavGraphBuilder.concreteCalcScreen(onBackClick: () -> Unit) {
    composable(route = concreteCalcNavigationRoute) {
        ConcreteCalcRoute(onBackClick = onBackClick)
    }
}
