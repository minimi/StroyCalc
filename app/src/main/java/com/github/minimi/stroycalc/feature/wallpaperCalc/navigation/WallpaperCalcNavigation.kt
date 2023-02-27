package com.github.minimi.stroycalc.feature.wallpaperCalc.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.minimi.stroycalc.feature.wallpaperCalc.WallpaperCalcRoute


const val wallpaperCalcNavigationRoute = "wallpaperCalc"

fun NavController.navigateToWallpaperCalc(navOptions: NavOptions? = null) {
    this.navigate(wallpaperCalcNavigationRoute, navOptions)
}

fun NavGraphBuilder.wallpaperCalcScreen(onBackClick: () -> Unit) {
    composable(route = wallpaperCalcNavigationRoute) {
        WallpaperCalcRoute(onBackClick = onBackClick)
    }
}
