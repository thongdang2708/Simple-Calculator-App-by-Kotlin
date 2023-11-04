package com.example.calculatorapp.constants

class ListOfFeatures {
    companion object {
        val listOfSymbols : List<String> = listOf("/", "*", "-", "+", "=");
        val listOfSymbolsWithoutEquation : List<String> = listOf("/", "*", "-", "+");
        val listOfFirstLineFunctionsOfCalculator : List<String> = listOf("C", "+/-", "%");
        val listOfSecondLineFunctionsOfCalculator : List<List<Int>> = listOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9));
        val listOfThirdLineFunctionsOfCalculator = listOf(0, ",");
    }
}