package com.github.minimi.stroycalc.models

interface Calculator<Input, Output> {
    val result: Output
    fun calculate(params: Input)
}