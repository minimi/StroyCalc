package com.github.minimi.stroycalc.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.github.minimi.stroycalc.navigation.BottomNavItem
import com.github.minimi.stroycalc.navigation.Route
import com.github.minimi.stroycalc.navigation.StroyAppNavHost

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun StroyApp(
    windowSizeClass: WindowSizeClass,
    appState: StroyAppState = rememberStroyAppState(
        windowSizeClass = windowSizeClass
    ),
) {
    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
    ) { paddings ->
        StroyAppNavHost(
            modifier = Modifier
                .padding(paddings)
                .consumedWindowInsets(paddings),
            navController = appState.navController,
            onBackClick = appState::onBackClick
        )
    }

    if (appState.shouldShowSettingsDialog) {
       AlertDialog(
           onDismissRequest = { appState.setShowSettingsDialog(false) },
           confirmButton = { appState.setShowSettingsDialog(false) },
           title = {
               Text(text = "О приложении")
           },
           text = {
               Text(text = "Сторительный калькулятор")
           }
       )
    }
}

@Composable
fun StroyBottomBar(
    destinations: List<Route>,
    onNavigateToDestination: (Route) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = { Icon((destination as BottomNavItem).icon, contentDescription = null) },
                label = { Text(stringResource(destination.title)) }
            )
        }
    }
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: Route) =
    this?.hierarchy?.any {
        it.route?.contains(destination.path, true) ?: false
    } ?: false
