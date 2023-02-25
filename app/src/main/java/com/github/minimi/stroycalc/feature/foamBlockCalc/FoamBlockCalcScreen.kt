package com.github.minimi.stroycalc.feature.foamBlockCalc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.minimi.stroycalc.designsystem.theme.StroyCalcTheme
import java.text.DecimalFormat


@Composable
fun FoamBlockCalcRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: FoamBlockViewModel = viewModel()
) {

    FoamBlockCalcScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        predefinedOptions = viewModel.predefinedBlockSizes,
        calculationResult = viewModel.result,
        calculate = viewModel::calculate,
        clear = viewModel::clear
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoamBlockCalcScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    predefinedOptions: List<BlockSize>,
    calculationResult: CalculationState,
    calculate: (length: String, height: String, doorsAndWindowsSquare: String, blockSize: BlockSize) -> Unit,
    clear: () -> Unit,
) {
    var length by remember { mutableStateOf("") }
    var heigth by remember { mutableStateOf("") }
    var blockSize by remember { mutableStateOf(predefinedOptions[0]) }
    var doorsAndWindowsSquare by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = length,
            onValueChange = {
                clear()
                length = it
            },
            isError = calculationResult is CalculationState.WrongLength,
            label = { Text("Общая длинна стен") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = heigth,
            onValueChange = {
                clear()
                heigth = it
            },
            isError = calculationResult is CalculationState.WrongHeight,
            label = { Text("Средняя высота стен") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = doorsAndWindowsSquare,
            onValueChange = {
                clear()
                doorsAndWindowsSquare = it
            },
            isError = calculationResult is CalculationState.WrongDoorsAndWindowsSquare,
            label = { Text("Общая площадь окон и дверей") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        BoxPackSquareInputSelector(
            modifier = Modifier.fillMaxWidth(),
            predefinedOptions = predefinedOptions,
            value = blockSize.toString(),
            onValueChange = {
                clear()
                blockSize = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { calculate(length, heigth, doorsAndWindowsSquare, blockSize) }
        ) {
            Text(text = "Рассчитать")
        }

        FoamBlockCalculationResult(calculationState = calculationResult)

    }
}

@Composable
fun FoamBlockCalculationResult(modifier: Modifier = Modifier, calculationState: CalculationState) {
    val df = remember { DecimalFormat("#.##") }

    OutlinedCard(modifier = Modifier.padding(24.dp)) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            when (calculationState) {
                is CalculationState.Success -> {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Количество блоков"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = calculationState.blocksQuantity.toString() + " шт",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = df.format(calculationState.blocksQuantityInQubicMeters) + " м3",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Количество блоков кратное паллете"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = calculationState.blocksQuantityMultipleOfAPallet.toString() + " шт",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = df.format(calculationState.blocksQuantityMultipleOfAPalletInQubicMeters) + " м3",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Количество блоков на паллете"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.blocksQuantityPerPallet.toString() + " шт",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Количество паллет"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.palletsQuantity.toString() + " шт",
                        style = MaterialTheme.typography.headlineMedium
                    )

                }

                is CalculationState.WrongHeight -> {
                    Text(
                        text = "Проверте значения высоты стен",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongLength -> {
                    Text(
                        text = "Проверте значения длинны стен",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CalculationState.WrongDoorsAndWindowsSquare -> {
                    Text(
                        text = "Проверте значения площади окон и дверей",
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
fun BoxPackSquareInputSelector(
    modifier: Modifier = Modifier,
    predefinedOptions: List<BlockSize>,
    value: String,
    isError: Boolean = false,
    onValueChange: (BlockSize) -> Unit
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
            onValueChange = {},
            label = { Text("Размер блока, ДxВxГ") },
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
                    text = { Text(selectionOption.toString()) },
                    onClick = {
                        expanded = false
                        onValueChange(selectionOption)
                    }
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FoamBlockCalcScreenPreview() {
    StroyCalcTheme {
        Surface {
            FoamBlockCalcScreen(modifier = Modifier.fillMaxSize(),
                onBackClick = {},
                predefinedOptions = listOf(BlockSize("600x250x75")),
                calculationResult = CalculationState.Success(1, 1.0, 1, 1.0, 1, 1),
                calculate = { _, _, _, _ -> },
                clear = {}
            )
        }
    }
}

@Preview(name = "Success Calc Result", group = "Calculation Result")
@Composable
fun FoamBlockCalculationResultPreview() {
    StroyCalcTheme {
        FoamBlockCalculationResult(
            calculationState = CalculationState.Success(
                17,
                11.1,
                5,
                6.4,
                123,
                5
            )
        )
    }
}


@Preview(name = "Error Calc Result - Height", group = "Calculation Result")
@Composable
fun FoamBlockCalculationResultWrongWidthPreview() {
    FoamBlockCalculationResult(calculationState = CalculationState.WrongHeight)
}


@Preview(name = "Error Calc Result - Length", group = "Calculation Result")
@Composable
fun FoamBlockCalculationResultWrongLengthPreview() {
    FoamBlockCalculationResult(calculationState = CalculationState.WrongLength)
}


@Preview(name = "Error Calc Result - Square", group = "Calculation Result")
@Composable
fun FoamBlockCalculationResultWrongPackSquarePreview() {
    FoamBlockCalculationResult(calculationState = CalculationState.WrongDoorsAndWindowsSquare)
}

@Preview(name = "Empty Calc Result", group = "Calculation Result")
@Composable
fun FoamBlockCalculationResultEmptyPreview() {
    FoamBlockCalculationResult(calculationState = CalculationState.Empty)
}