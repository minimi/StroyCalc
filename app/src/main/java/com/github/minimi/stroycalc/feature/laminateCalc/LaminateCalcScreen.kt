package com.github.minimi.stroycalc.feature.laminateCalc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.minimi.stroycalc.R
import com.github.minimi.stroycalc.designsystem.theme.StroyCalcTheme


data class FloorCoveringPack(
    val name: String,
    val square: Float,
)

val samples = listOf(
    FloorCoveringPack("Ламинат Quick Step - 1,5524 кв.м/уп", 1.5524f),
    FloorCoveringPack("Ламинат Kronoflooring - 2,22 кв.м/уп", 2.22f),
    FloorCoveringPack("Ламинат Tarkett - 2,005 кв.м/уп", 2.005f),
    FloorCoveringPack("Ламинат Balterio - 2,3832 кв.м/уп", 2.3832f),
    FloorCoveringPack("Ламинат Kronospan - 2,208 кв.м/уп", 2.208f),
    FloorCoveringPack("Ламинат Kronostar -  2,131 кв.м/уп", 2.131f),
    FloorCoveringPack("Ламинат Ecoflooring - 2,46 кв.м/уп", 2.46f),
    FloorCoveringPack("Ламинат Egger - 1,385 кв.м/уп", 1.385f),
    FloorCoveringPack("Ламинат Kronotex - 2,131 кв.м/уп", 2.131f),
    FloorCoveringPack("Ламинат Floorwood - 2,8919 кв.м/уп", 2.8919f),
    FloorCoveringPack("Ламинат Classen  -  1,996 кв.м/уп", 1.996f),
    FloorCoveringPack("Ламинат Kaindl - 2,397 кв.м/уп", 2.397f),
    FloorCoveringPack("Ламинат Kaindl - 2,397 кв.м/уп", 2.397f),
    FloorCoveringPack("Паркетная доска Quick Step - 2,508 кв.м/уп", 2.508f),
    FloorCoveringPack("Паркетная доска Tarkett - 1,29 кв.м/уп", 1.29f),
    FloorCoveringPack("Паркетная доска Barlinek - 3,184 кв.м/уп", 3.184f),
    FloorCoveringPack("Влагостойкий ламинат Dumafloor - 1,62 кв.м/уп", 1.62f),
)

@Composable
fun LaminateCalcRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: LaminateCalcViewModel = viewModel()
) {

    LaminateCalcScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        calculationResult = viewModel.result,
        calculate = viewModel::calculate,
        clear = viewModel::clear
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaminateCalcScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    calculationResult: CalculationState,
    calculate: (width: String, length: String, layoutType: LayoutType, squareInPack: String) -> Unit,
    clear: () -> Unit,
) {
    var width by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var squareInPack by remember { mutableStateOf("") }
    var layoutType by remember { mutableStateOf(LayoutType.DIRECT) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = width,
            onValueChange = {
                clear()
                width = it
            },
            isError = calculationResult is CalculationState.WrongWidth,
            label = { Text("Ширина помещения") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = length,
            onValueChange = {
                clear()
                length = it
            },
            isError = calculationResult is CalculationState.WrongLength,
            label = { Text("Длинна помещения") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        LayoutSelector(layoutType = layoutType, onSelect = {
            layoutType = it
        })

        Spacer(modifier = Modifier.height(16.dp))


        BoxPackSquareInputSelector(
            modifier = Modifier.fillMaxWidth(),
            predefinedOptions = samples,
            isError = calculationResult is CalculationState.WrongPackSquare,
            value = squareInPack,
            onValueChange = {
                clear()
                squareInPack = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { calculate(width, length, layoutType, squareInPack) }) {
            Text(text = "Рассчитать")
        }

        LaminateCalculationResult(calculationState = calculationResult)

    }
}

@Composable
fun LaminateCalculationResult(modifier: Modifier = Modifier, calculationState: CalculationState) {
    OutlinedCard(modifier = Modifier.padding(24.dp)) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            when (calculationState) {
                is CalculationState.Success -> {
                    Text(text = "Количество упаковок ламината или паркетной доски")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.packsQuantity.toString() + " шт.",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                is CalculationState.WrongWidth -> {
                    Text(
                        text = "Проверте значения ширины",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongLength -> {
                    Text(
                        text = "Проверте значения длинны",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongPackSquare -> {
                    Text(
                        text = "Проверте значения площади в упаковке",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LayoutSelector(
    modifier: Modifier = Modifier,
    layoutType: LayoutType,
    onSelect: (LayoutType) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.direct_layout),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            ElevatedFilterChip(
                selected = layoutType == LayoutType.DIRECT,
                onClick = { onSelect(LayoutType.DIRECT) },
                label = { Text(text = "Прямая укладка") }
            )

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.diagonal_layout),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            ElevatedFilterChip(
                selected = layoutType == LayoutType.DIAGONAL,
                onClick = { onSelect(LayoutType.DIAGONAL) },
                label = { Text(text = "Диагональная укладка") }
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun LayoutSelectorPreview() {
    StroyCalcTheme {
        LayoutSelector(layoutType = LayoutType.DIAGONAL, onSelect = {})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxPackSquareInputSelector(
    modifier: Modifier = Modifier,
    predefinedOptions: List<FloorCoveringPack>,
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = value,
            onValueChange = onValueChange,
            label = { Text("Площадь в упаковке") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            isError = isError,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Default
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            predefinedOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.name) },
                    onClick = {
                        expanded = false
                        onValueChange(selectionOption.square.toString())
                    }
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LaminateCalcScreenPreview() {
    StroyCalcTheme {
        Surface {
            LaminateCalcScreen(modifier = Modifier.fillMaxSize(),
                onBackClick = {},
                calculationResult = CalculationState.Success(10),
                calculate = { _, _, _, _ -> },
                clear = {}
            )
        }
    }
}

@Preview(name = "Success Calc Result", group = "Calculation Result")
@Composable
fun LaminateCalculationResultPreview() {
    LaminateCalculationResult(calculationState = CalculationState.Success(17))
}


@Preview(name = "Error Calc Result - Width", group = "Calculation Result")
@Composable
fun LaminateCalculationResultWrongWidthPreview() {
    LaminateCalculationResult(calculationState = CalculationState.WrongWidth)
}


@Preview(name = "Error Calc Result - Length", group = "Calculation Result")
@Composable
fun LaminateCalculationResultWrongLengthPreview() {
    LaminateCalculationResult(calculationState = CalculationState.WrongLength)
}


@Preview(name = "Error Calc Result - Pack Square", group = "Calculation Result")
@Composable
fun LaminateCalculationResultWrongPackSquarePreview() {
    LaminateCalculationResult(calculationState = CalculationState.WrongPackSquare)
}

@Preview(name = "Empty Calc Result", group = "Calculation Result")
@Composable
fun LaminateCalculationResultEmptyPreview() {
    LaminateCalculationResult(calculationState = CalculationState.Empty)
}