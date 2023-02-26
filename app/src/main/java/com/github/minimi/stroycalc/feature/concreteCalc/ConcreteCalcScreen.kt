package com.github.minimi.stroycalc.feature.concreteCalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun ConcreteCalcRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ConcreteCalcViewModel = viewModel()
) {

    ConcreteCalcScreen(
        onBackClick = onBackClick,
        calculationResult = viewModel.result,
        calculate = viewModel::calculate,
        clear = viewModel::clear
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcreteCalcScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    calculationResult: CalculationState,
    calculate: (volume: String, concreteType: ConcreteType) -> Unit,
    clear: () -> Unit,
) {

    var volume by rememberSaveable { mutableStateOf("") }
    var concreteType by rememberSaveable { mutableStateOf(ConcreteType.M100) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = volume,
            onValueChange = {
                clear()
                volume = it
            },
            isError = calculationResult is CalculationState.WrongVolume,
            label = { Text("Объем бетона") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        ConcreteTypeSelector(
            value = concreteType.name,
            onValueChange = {
                concreteType = it
            },
            isError = calculationResult is CalculationState.WrongVolume
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { calculate(volume, concreteType) }
        ) {
            Text(text = "Рассчитать")
        }

        ConcreteCalculationResult(calculationState = calculationResult)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcreteTypeSelector(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    onValueChange: (ConcreteType) -> Unit
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
            ConcreteType.values().forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.name) },
                    onClick = {
                        expanded = false
                        onValueChange(selectionOption)
                    }
                )
            }
        }
    }
}



@Composable
fun ConcreteCalculationResult(modifier: Modifier = Modifier, calculationState: CalculationState) {
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
                        text = "Цемент"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.cementInKilos.toString() + " кг",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Песок"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = calculationState.sandInKilos.toString() + " кг",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = df.format(calculationState.sandInCubicMeters) + " м3",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Щебень"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = calculationState.breakstoneInKilos.toString() + " кг",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = df.format(calculationState.breakstoneInQubicMeters) + " м3",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Вода"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = calculationState.waterInLiters.toString() + " л",
                        style = MaterialTheme.typography.headlineMedium
                    )

                }

                is CalculationState.WrongVolume -> {
                    Text(
                        text = "Проверте значения объема",
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



@Preview
@Composable
fun ConcreteCalculationResultrPreview() {
    StroyCalcTheme {
        Surface {
            ConcreteCalculationResult(calculationState = CalculationState.Success(
                cementInKilos = 2200.0,
                sandInKilos = 7800.0,
                sandInCubicMeters = 6.0,
                breakstoneInKilos = 12500.0,
                breakstoneInQubicMeters = 8.0,
                waterInLiters = 1800.0
            ))
        }
    }
}


@Preview
@Composable
fun ConcreteTypeSelectorPreview() {
    StroyCalcTheme {
        Surface {
            ConcreteTypeSelector(value = "M100", onValueChange = {})
        }
    }
}

@Preview
@Composable
fun ConcreteTypeSelectorErrorPreview() {
    StroyCalcTheme {
        Surface {
            ConcreteTypeSelector(value = "M100", isError = true, onValueChange = {})
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ConcreteCalcScreenPreview() {
    StroyCalcTheme {
        Surface {
            ConcreteCalcScreen(modifier = Modifier.fillMaxSize(),
                onBackClick = {},
                calculationResult = CalculationState.Success(1234.0, 56.0, 7.0, 8910.0, 11.0, 345.0),
                calculate = { _, _ -> },
                clear = {}
            )
        }
    }
}
