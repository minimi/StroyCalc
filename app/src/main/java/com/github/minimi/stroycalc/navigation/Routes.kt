package com.github.minimi.stroycalc.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.minimi.stroycalc.R
import com.github.minimi.stroycalc.feature.calculatorsList.navigation.calculatorsListNavigationRoute
import com.github.minimi.stroycalc.feature.concreteCalc.navigation.concreteCalcNavigationRoute
import com.github.minimi.stroycalc.feature.foamBlockCalc.navigation.foamBlockCalcNavigationRoute
import com.github.minimi.stroycalc.feature.laminateCalc.navigation.laminateCalcNavigationRoute

sealed interface BottomNavItem {
    val icon: ImageVector
}

sealed class Route(val path: String, @StringRes val title: Int) {

    object Calculators : Route(calculatorsListNavigationRoute, R.string.title_calculators), BottomNavItem {
        override val icon: ImageVector get() = Icons.Filled.List
    }

    object About : Route("about", R.string.title_calculators), BottomNavItem {
        override val icon: ImageVector get() = Icons.Filled.Info
    }

    object LaminateCalc : Route(laminateCalcNavigationRoute, R.string.title_laminateCalc)
    object ConcreteCalc : Route(concreteCalcNavigationRoute, R.string.title_concreteCalc)
    object FoamBlockCalc : Route(foamBlockCalcNavigationRoute, R.string.title_foamBlockCalc)
}


val topLevelRoutes = listOf<Route>(
    Route.Calculators,
    Route.About
)