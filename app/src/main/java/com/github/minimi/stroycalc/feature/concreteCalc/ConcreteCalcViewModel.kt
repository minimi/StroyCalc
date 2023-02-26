package com.github.minimi.stroycalc.feature.concreteCalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.minimi.stroycalc.models.Calculator

class ConcreteCalcViewModel : ViewModel(), Calculator<InputParameters, CalculationState> {

    override var result by mutableStateOf<CalculationState>(CalculationState.Empty)
        private set

    override fun calculate(params: InputParameters) {
        val totalCement = params.volume * params.type.cement
        val totalSandInKilos = params.volume * params.type.sandInKilos
        val totalSandInCubicMeters = params.volume * params.type.sandInCubicMeters
        val totalBreakstoneInKilos = params.volume * params.type.breakstoneInKilos
        val totalBreakstoneInCubicMeters = params.volume * params.type.breakstoneInQubicMeters
        val totalWater = params.volume * params.type.waterInLiters

        result = CalculationState.Success(
            cementInKilos = totalCement,
            sandInKilos = totalSandInKilos,
            sandInCubicMeters = totalSandInCubicMeters,
            breakstoneInKilos = totalBreakstoneInKilos,
            breakstoneInQubicMeters = totalBreakstoneInCubicMeters,
            waterInLiters = totalWater
        )
    }

    fun calculate(volume: String, concreteType: ConcreteType) {
        val volumeAsDouble = try {
            volume.toDouble()
        } catch (ex: Throwable) {
            result = CalculationState.WrongVolume
            return
        }
        calculate(InputParameters(volumeAsDouble, concreteType))
    }

    fun clear() {
        result = CalculationState.Empty
    }

}

enum class ConcreteType(
    val cement: Int,
    val sandInKilos: Int,
    val sandInCubicMeters: Double,
    val breakstoneInKilos: Int,
    val breakstoneInQubicMeters: Double,
    val waterInLiters: Int
) {
    M100(220, 780, 0.6, 1250, 0.8, 180),
    M200(280, 740, 0.55, 1250, 0.8, 180),
    M300(320, 700, 0.5, 1250, 0.8, 180),
    M400(380, 630, 0.45, 1250, 0.8, 180),
}

data class InputParameters(
    val volume: Double,
    val type: ConcreteType
)

interface CalculationState {

    data class Success(
        val cementInKilos: Double,
        val sandInKilos: Double,
        val sandInCubicMeters: Double,
        val breakstoneInKilos: Double,
        val breakstoneInQubicMeters: Double,
        val waterInLiters: Double,
    ) : CalculationState

    object WrongVolume : CalculationState

    object Empty : CalculationState
}