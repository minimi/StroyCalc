package com.github.minimi.stroycalc.feature.wallpaperCalc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.minimi.stroycalc.R
import com.github.minimi.stroycalc.designsystem.theme.StroyCalcTheme

@Composable
fun WallpaperCalcRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: WallpaperCalcViewModel = viewModel()
) {

    WallpaperCalcScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        calculationResult = viewModel.result,
        calculate = viewModel::calculate,
        clear = viewModel::clear
    )
}

@Composable
fun WallpaperCalcScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    calculationResult: CalculationState,
    calculate: (
        width: String,
        length: String,
        height: String,
        doorsQuantity: String,
        doorHeight: String,
        doorWidth: String,
        windowsQuantity: String,
        windowHeight: String,
        windowWidth: String,
        wallpaperRollLength: String,
        wallpaperWidth: String,
        wallpaperRapport: String,
        shiftedPattern: Boolean,
        levelingMargin: String,
    ) -> Unit,
    clear: () -> Unit,
) {

    var roomWidth by rememberSaveable { mutableStateOf("") }
    var roomLength by rememberSaveable { mutableStateOf("") }
    var roomHeight by rememberSaveable { mutableStateOf("") }

    var doorsQuantity by rememberSaveable { mutableStateOf("") }
    var doorHeight by rememberSaveable { mutableStateOf("") }
    var doorWidth by rememberSaveable { mutableStateOf("") }

    var windowsQuantity by rememberSaveable { mutableStateOf("") }
    var windowHeight by rememberSaveable { mutableStateOf("") }
    var windowWidth by rememberSaveable { mutableStateOf("") }

    var wallpaperRollLength by rememberSaveable { mutableStateOf("") }
    var wallpaperWidth by rememberSaveable { mutableStateOf("") }
    var wallpaperRapport by rememberSaveable { mutableStateOf("1") }
    var shiftedPattern by rememberSaveable { mutableStateOf(false) }

    var levelingMargin by rememberSaveable { mutableStateOf("0") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        //region Room parameters

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.label_room_parameters),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = roomWidth,
            onValueChange = {
                clear()
                roomWidth = it
            },
            isError = calculationResult is CalculationState.WrongRoomWidth,
            label = { Text(stringResource(id = R.string.label_room_width)) },
            suffix = { Text(stringResource(id = R.string.suffix_meters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = roomLength,
            onValueChange = {
                clear()
                roomLength = it
            },
            isError = calculationResult is CalculationState.WrongRoomLength,
            label = { Text(stringResource(id = R.string.label_room_length)) },
            suffix = { Text(stringResource(id = R.string.suffix_meters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = roomHeight,
            onValueChange = {
                clear()
                roomHeight = it
            },
            isError = calculationResult is CalculationState.WrongRoomHeight,
            label = { Text(stringResource(id = R.string.label_room_height)) },
            suffix = { Text(stringResource(id = R.string.suffix_meters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //endregion

        //region Doors parameters

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.label_door_parameters),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = doorsQuantity,
            onValueChange = {
                clear()
                doorsQuantity = it
            },
            isError = calculationResult is CalculationState.WrongDoorsQty,
            label = { Text("Количество дверей") },
            suffix = { Text(stringResource(id = R.string.suffix_item)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = doorHeight,
            onValueChange = {
                clear()
                doorHeight = it
            },
            isError = calculationResult is CalculationState.WrongDoorHeight,
            label = { Text("Высота двери") },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = doorWidth,
            onValueChange = {
                clear()
                doorWidth = it
            },
            isError = calculationResult is CalculationState.WrongDoorWidth,
            label = { Text("Ширина двери") },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //endregion
        
        //region Window parameters

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.label_window_parameters),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = windowsQuantity,
            onValueChange = {
                clear()
                windowsQuantity = it
            },
            isError = calculationResult is CalculationState.WrongWindowsQty,
            label = { Text(stringResource(id = R.string.label_windows_quantity)) },
            suffix = { Text(stringResource(id = R.string.suffix_item)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = windowHeight,
            onValueChange = {
                clear()
                windowHeight = it
            },
            isError = calculationResult is CalculationState.WrongWindowHeight,
            label = { Text(stringResource(id = R.string.label_window_height)) },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = windowWidth,
            onValueChange = {
                clear()
                windowWidth = it
            },
            isError = calculationResult is CalculationState.WrongWindowWidth,
            label = { Text(stringResource(id = R.string.label_window_width)) },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //endregion

        //region Wallpaper parameters

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.label_wallpaper_parameters),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = wallpaperRollLength,
            onValueChange = {
                clear()
                wallpaperRollLength = it
            },
            isError = calculationResult is CalculationState.WrongWallpaperRollLength,
            label = { Text(stringResource(id = R.string.label_wallpaper_roll_length)) },
            suffix = { Text(stringResource(id = R.string.suffix_meters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = wallpaperWidth,
            onValueChange = {
                clear()
                wallpaperWidth = it
            },
            isError = calculationResult is CalculationState.WrongWallpaperWidth,
            label = { Text(stringResource(id = R.string.label_wallpaper_width)) },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = wallpaperRapport,
            onValueChange = {
                clear()
                wallpaperRapport = it
            },
            isError = calculationResult is CalculationState.WrongWallpaperRapport,
            label = { Text(stringResource(id = R.string.label_wallpaper_rapport)) },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = shiftedPattern,
                onCheckedChange = { shiftedPattern = !shiftedPattern })
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(id = R.string.label_wallpaper_shifted_pattern))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = levelingMargin,
            onValueChange = {
                clear()
                levelingMargin = it
            },
            isError = calculationResult is CalculationState.WrongLevelingMargin,
            label = { Text(stringResource(id = R.string.label_leveling_margin)) },
            suffix = { Text(stringResource(id = R.string.suffix_centimeters)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //endregion

        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                calculate(
                    roomWidth,
                    roomLength,
                    roomHeight,
                    doorsQuantity,
                    doorHeight,
                    doorWidth,
                    windowsQuantity,
                    windowHeight,
                    windowWidth,
                    wallpaperRollLength,
                    wallpaperWidth,
                    wallpaperRapport,
                    shiftedPattern,
                    levelingMargin
                )
            })
        {
            Text(text = stringResource(id = R.string.label_calculate))
        }

        WallpaperCalculationResult(calculationState = calculationResult)

    }
}

@Composable
fun WallpaperCalculationResult(modifier: Modifier = Modifier, calculationState: CalculationState) {
    OutlinedCard(modifier = modifier.padding(24.dp)) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            when (calculationState) {
                is CalculationState.Success -> {
                    Text(text = stringResource(id = R.string.label_totalWallpapersLength))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.wallpaperQty.toString() + " " + stringResource(id = R.string.suffix_running_meter),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = stringResource(id = R.string.label_totalWallpaperRolls))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.rollsQty.toString() + " " + stringResource(id = R.string.suffix_item),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                is CalculationState.WrongRoomHeight,
                is CalculationState.WrongWindowHeight,
                is CalculationState.WrongDoorHeight -> {
                    Text(
                        text = "Проверте значения высоты",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongRoomLength,
                is CalculationState.WrongWallpaperRollLength -> {
                    Text(
                        text = "Проверте значения длинны",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongWallpaperWidth,
                is CalculationState.WrongDoorWidth,
                is CalculationState.WrongWindowWidth,
                is CalculationState.WrongRoomWidth -> {
                    Text(
                        text = "Проверте значения ширины",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongLevelingMargin -> {
                    Text(
                        text = "Проверте запаса на выравнивание",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongWallpaperRapport -> {
                    Text(
                        text = "Проверте раппорта рисунка",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongWindowsQty,
                is CalculationState.WrongDoorsQty -> {
                    Text(
                        text = "Проверте значения количества",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    Text(text = "Введите значения в поля выше для расчета")
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WallpaperCalcScreenPreview() {
    StroyCalcTheme {
        Surface {
            WallpaperCalcScreen(modifier = Modifier.fillMaxSize(),
                onBackClick = {},
                calculationResult = CalculationState.Success(10.0, 11),
                calculate = { _, _, _, _, _, _, _, _, _, _, _, _, _, _ -> },
                clear = {}
            )
        }
    }
}

@Preview(name = "Success Calc Result", group = "Calculation Result")
@Composable
fun WallpaperCalculationResultPreview() {
    WallpaperCalculationResult(calculationState = CalculationState.Success(17.0, 5))
}


@Preview(name = "Error Calc Result - WrongWallpaperWidth", group = "Calculation Result")
@Composable
fun WallpaperCalculationResultWrongWidthPreview() {
    WallpaperCalculationResult(calculationState = CalculationState.WrongWallpaperWidth)
}

@Preview(name = "Error Calc Result - WrongWallpaperWidth", group = "Calculation Result")
@Composable
fun WallpaperCalculationResultWrongWallpaperWidtPreview() {
    WallpaperCalculationResult(calculationState = CalculationState.WrongWallpaperWidth)
}
