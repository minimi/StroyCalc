package com.github.minimi.stroycalc.feature.laminateCalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.minimi.stroycalc.models.Calculator
import kotlin.math.ceil

class LaminateCalcViewModel : ViewModel(), Calculator<InputParameters, CalculationState> {

    override var result by mutableStateOf<CalculationState>(CalculationState.Empty)
        private set

    override fun calculate(params: InputParameters) {
        val (width, length, layoutType, squareInPack) = params
        val fraction = if (layoutType == LayoutType.DIRECT) 0.05 else 0.15
        val roomArea = width * length
        val requiredMaterialArea = roomArea + (roomArea * fraction)
        result = CalculationState.Success(ceil(requiredMaterialArea / squareInPack).toInt())
    }

    fun calculate(width: String, length: String, layoutType: LayoutType, squareInPack: String) {
        val widthAsFloat = try {
            width.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWidth
            return
        }

        val lengthAsFloat = try {
            length.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongLength
            return
        }

        val squareInPackAsFloat = try {
            squareInPack.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongPackSquare
            return
        }

        val params = InputParameters(
            width = widthAsFloat,
            length = lengthAsFloat,
            layoutType = layoutType,
            squareInPack = squareInPackAsFloat
        )

        calculate(params)
    }

    fun clear() {
        result = CalculationState.Empty
    }
}

enum class LayoutType {
    DIRECT,
    DIAGONAL
}

data class InputParameters(
    val width: Float,
    val length: Float,
    val layoutType: LayoutType,
    val squareInPack: Float
)

interface CalculationState {
    @JvmInline
    value class Success(val packsQuantity: Int) : CalculationState

    object WrongWidth : CalculationState

    object WrongLength : CalculationState

    object WrongPackSquare : CalculationState

    object Empty : CalculationState
}