package com.github.minimi.stroycalc.feature.wallpaperCalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.minimi.stroycalc.models.Calculator
import kotlin.math.ceil
import kotlin.math.roundToInt

class WallpaperCalcViewModel : ViewModel(), Calculator<InputParameters, CalculationState> {

    override var result by mutableStateOf<CalculationState>(CalculationState.Empty)
        private set

    override fun calculate(params: InputParameters) {

        val perimeter = (params.roomWidth + params.roomLength) * 2

        val perimInCm = perimeter * 100

        val cp = if (params.wallpaperWidth > 0)
            ceil(perimInCm / params.wallpaperWidth).toDouble()
        else
            0.0

        val shift = if (params.shiftedPattern) params.wallpaperRapport / 2 else 0

        val roomHeightCm = params.roomHeight * 100

        val hp = roomHeightCm + params.levelingMargin

        val hpReal = if (params.wallpaperRapport > 0) {
            val povtor = ceil(hp / params.wallpaperRapport)
            povtor * params.wallpaperRapport + shift
        } else hp

        val proem =
            (params.doorsQuantity * params.doorHeightCm * params.doorWidthCm / params.wallpaperWidth + params.windowsQuantity * params.windowHeightCm * params.windowWidth / params.wallpaperWidth) / 100

        val totalWallpaperLength = ceil((hpReal * cp / 100) - proem)
        val totalWallpaperRolls = ceil(totalWallpaperLength / params.wallpaperRollLength)

        result = CalculationState.Success(
            wallpaperQty = totalWallpaperLength,
            rollsQty = totalWallpaperRolls.roundToInt()
        )
    }

    fun calculate(
        roomWidth: String,
        roomLength: String,
        roomHeight: String,

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
    ) {
        //region Room measures conversion

        val roomWidthAsFloat = try {
            roomWidth.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongRoomWidth
            return
        }

        val roomLengthAsFloat = try {
            roomLength.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongRoomLength
            return
        }

        val roomHeightAsFloat = try {
            roomHeight.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongRoomHeight
            return
        }

        //endregion

        //region Door measures conversion

        val doorsQuantityAsInt = try {
            doorsQuantity.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongDoorsQty
            return
        }

        val doorHeightAsInt = try {
            doorHeight.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongDoorHeight
            return
        }

        val doorWidthAsInt = try {
            doorWidth.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongDoorWidth
            return
        }

        //endregion

        //region Window measures conversion

        val windowsQuantityAsInt = try {
            windowsQuantity.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWindowsQty
            return
        }

        val windowHeightAsInt = try {
            windowHeight.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWindowHeight
            return
        }

        val windowWidthAsInt = try {
            windowWidth.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWindowWidth
            return
        }

        //endregion

        //region Wallpaper measures conversion

        val wallpaperRollLengthAsFloat = try {
            wallpaperRollLength.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWallpaperRollLength
            return
        }

        val wallpaperWidthAsInt = try {
            wallpaperWidth.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWallpaperWidth
            return
        }

        val wallpaperRapportAsInt = try {
            wallpaperRapport.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongWallpaperRapport
            return
        }

        val levelingMarginAsInt = try {
            levelingMargin.toInt()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongLevelingMargin
            return
        }

        //endregion

        val params = InputParameters(
            roomWidth = roomWidthAsFloat,
            roomLength = roomLengthAsFloat,
            roomHeight = roomHeightAsFloat,
            doorsQuantity = doorsQuantityAsInt,
            doorHeightCm = doorHeightAsInt,
            doorWidthCm = doorWidthAsInt,
            windowsQuantity = windowsQuantityAsInt,
            windowHeightCm = windowHeightAsInt,
            windowWidth = windowWidthAsInt,
            wallpaperRollLength = wallpaperRollLengthAsFloat,
            wallpaperWidth = wallpaperWidthAsInt,
            wallpaperRapport = wallpaperRapportAsInt,
            shiftedPattern = shiftedPattern,
            levelingMargin = levelingMarginAsInt
        )

        calculate(params)
    }

    fun clear() {
        result = CalculationState.Empty
    }
}

data class InputParameters(
    val roomWidth: Float,
    val roomLength: Float,
    val roomHeight: Float,

    val doorsQuantity: Int,
    val doorHeightCm: Int,
    val doorWidthCm: Int,

    val windowsQuantity: Int,
    val windowHeightCm: Int,
    val windowWidth: Int,

    val wallpaperRollLength: Float,
    val wallpaperWidth: Int,
    val wallpaperRapport: Int,
    val shiftedPattern: Boolean,
    val levelingMargin: Int,
)

interface CalculationState {

    data class Success(
        val wallpaperQty: Double,
        val rollsQty: Int
    ) : CalculationState

    object WrongRoomWidth : CalculationState
    object WrongRoomLength : CalculationState
    object WrongRoomHeight : CalculationState

    object WrongDoorsQty : CalculationState
    object WrongDoorHeight : CalculationState
    object WrongDoorWidth : CalculationState

    object WrongWindowsQty : CalculationState
    object WrongWindowHeight : CalculationState
    object WrongWindowWidth : CalculationState

    object WrongWallpaperRollLength : CalculationState
    object WrongWallpaperWidth : CalculationState
    object WrongWallpaperRapport : CalculationState

    object WrongLevelingMargin : CalculationState

    object Empty : CalculationState
}