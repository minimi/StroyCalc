package com.github.minimi.stroycalc.feature.foamBlockCalc.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.minimi.stroycalc.feature.foamBlockCalc.FoamBlockCalcRoute
import com.github.minimi.stroycalc.feature.laminateCalc.LaminateCalcRoute


const val foamBlockCalcNavigationRoute = "foamBlockCalc"

fun NavController.navigateToFoamBlockCalc(navOptions: NavOptions? = null) {
    this.navigate(foamBlockCalcNavigationRoute, navOptions)
}

fun NavGraphBuilder.foamBlockCalcScreen(onBackClick: () -> Unit) {
    composable(route = foamBlockCalcNavigationRoute) {
        FoamBlockCalcRoute(onBackClick = onBackClick)
    }
}
