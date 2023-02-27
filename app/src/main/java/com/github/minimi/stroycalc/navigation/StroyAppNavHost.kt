package com.github.minimi.stroycalc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.github.minimi.stroycalc.feature.calculatorsList.navigation.calculatorsListNavigationRoute
import com.github.minimi.stroycalc.feature.calculatorsList.navigation.calculatorsListScreen
import com.github.minimi.stroycalc.feature.concreteCalc.navigation.concreteCalcNavigationRoute
import com.github.minimi.stroycalc.feature.concreteCalc.navigation.concreteCalcScreen
import com.github.minimi.stroycalc.feature.concreteCalc.navigation.navigateToConcreteCalc
import com.github.minimi.stroycalc.feature.foamBlockCalc.navigation.foamBlockCalcNavigationRoute
import com.github.minimi.stroycalc.feature.foamBlockCalc.navigation.foamBlockCalcScreen
import com.github.minimi.stroycalc.feature.foamBlockCalc.navigation.navigateToFoamBlockCalc
import com.github.minimi.stroycalc.feature.laminateCalc.navigation.laminateCalcNavigationRoute
import com.github.minimi.stroycalc.feature.laminateCalc.navigation.laminateCalcScreen
import com.github.minimi.stroycalc.feature.laminateCalc.navigation.navigateToLaminateCalc
import com.github.minimi.stroycalc.feature.wallpaperCalc.navigation.navigateToWallpaperCalc
import com.github.minimi.stroycalc.feature.wallpaperCalc.navigation.wallpaperCalcNavigationRoute
import com.github.minimi.stroycalc.feature.wallpaperCalc.navigation.wallpaperCalcScreen

@Composable
fun StroyAppNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = calculatorsListNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        calculatorsListScreen(
            onNavigateTo = { route ->
                when (route) {
                    laminateCalcNavigationRoute -> navController.navigateToLaminateCalc()
                    foamBlockCalcNavigationRoute -> navController.navigateToFoamBlockCalc()
                    concreteCalcNavigationRoute -> navController.navigateToConcreteCalc()
                    wallpaperCalcNavigationRoute -> navController.navigateToWallpaperCalc()
                    else -> {}
                }
            }
        )

        laminateCalcScreen(onBackClick)

        foamBlockCalcScreen(onBackClick)

        concreteCalcScreen(onBackClick)

        wallpaperCalcScreen(onBackClick)
    }
}
