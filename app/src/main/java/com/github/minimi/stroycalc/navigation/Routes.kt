package com.github.minimi.stroycalc.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.minimi.stroycalc.R

sealed interface BottomNavItem {
    val icon: ImageVector
}

sealed class Route(val path: String, @StringRes val title: Int) {

    object Calculators : Route("calculators", R.string.title_calculators), BottomNavItem {
        override val icon: ImageVector get() = Icons.Filled.List
    }

    object About : Route("about", R.string.title_calculators), BottomNavItem {
        override val icon: ImageVector get() = Icons.Filled.Info
    }

    object LaminateCalc : Route("laminateCalc", R.string.title_laminateCalc)

    object ConcreteCalc : Route("laminateCalc", R.string.title_concreteCalc)
    object FoamBlockCalc : Route("foamBlockCalc", R.string.title_foamBlockCalc)
}


val topLevelRoutes = listOf<Route>(
    Route.Calculators,
    Route.About
)