package com.github.minimi.stroycalc.feature.laminateCalc.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.minimi.stroycalc.feature.laminateCalc.LaminateCalcRoute


const val laminateCalcNavigationRoute = "laminateCalc"

fun NavController.navigateToLaminateCalc(navOptions: NavOptions? = null) {
    this.navigate(laminateCalcNavigationRoute, navOptions)
}

fun NavGraphBuilder.laminateCalcScreen(onBackClick: () -> Unit) {
    composable(route = laminateCalcNavigationRoute) {
        LaminateCalcRoute(onBackClick = onBackClick)
    }
}
