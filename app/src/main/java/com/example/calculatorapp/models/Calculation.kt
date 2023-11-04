package com.example.calculatorapp.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.util.Log
import com.example.calculatorapp.constants.ListOfFeatures
import java.math.RoundingMode
import java.text.DecimalFormat

class Calculation : ViewModel() {

    var result = mutableStateOf("0.0");
    var parsedNumber = mutableStateOf("");
    var showResult = mutableStateOf(false);
    var listOfNumbers : MutableList<String> = mutableListOf();
    val isAddedWithMathFunctionality = mutableStateOf(false);
    val displayNumber = mutableStateOf("0.0");
    val isMultiplyOne = mutableStateOf(false);
    val isDivideBy100 = mutableStateOf(false);

    //Insert each single number into List for later calculation
    fun insertNumber (singleNumber : Any) {


        if (isMultiplyOne.value) {
            if (parsedNumber.value == "" && displayNumber.value.toDouble() == 0.0) {
                parsedNumber.value = "";
                displayNumber.value = parsedNumber.value;
            } else if  (parsedNumber.value == "-0.0") {
                parsedNumber.value = "-";
                displayNumber.value = parsedNumber.value;
            } else if (parsedNumber.value != "" && parsedNumber.value.contains(".") && parsedNumber.value.split(".").get(1).toDouble() == 0.0) {
                parsedNumber.value = if (parsedNumber.value.split(".").get(0).toDouble() > 0.0) parsedNumber.value.split(".").get(0) else "";
                displayNumber.value = parsedNumber.value;
            }
            isMultiplyOne.value = false;
        }

        if (isDivideBy100.value) {
            if (((parsedNumber.value == "" || parsedNumber.value.toDouble() == 0.0) && displayNumber.value.toDouble() == 0.0) || (displayNumber.value.toDouble() > 0.0 && !isAddedWithMathFunctionality.value)) {
                parsedNumber.value = "";
                displayNumber.value = parsedNumber.value;
                isDivideBy100.value = false;
            }
        }

        if (!checkContainsSymbol(singleNumber) && !showResult.value) {
            if (singleNumber.toString() == "," && !parsedNumber.toString().contains(".") && parsedNumber.value.length > 0) {
                parsedNumber.value += ".";
                displayNumber.value = parsedNumber.value;
                return;
            } else if (singleNumber.toString() == "," && parsedNumber.toString().contains(".")) {
                return;
            }
            parsedNumber.value += singleNumber.toString();

        } else if (checkContainsSymbol(singleNumber) && listOfNumbers.size == 0) {
            Log.i("Cannot insert symbol", "Cannot insert number from Keyboard");
        } else if (showResult.value && listOfNumbers.size > 0 && !checkContainsSymbol(singleNumber)) {
            listOfNumbers.clear();
            parsedNumber.value += singleNumber.toString();
            showResult.value = false;
        }

        isAddedWithMathFunctionality.value = false;
        displayNumber.value = parsedNumber.value;

        Log.i("Insert number: ", "Insert a single number");
    }

    //Show a number to UI

    fun displayNumber () : String {

        if (showResult.value) {
            return if (result.value != "Error" && result.value != "Infinity") result.value else if (result.value == "-0.0") "0.0" else "Error";
        }

        return "${displayNumber.value}";

        Log.i("Display number: ", "Show a number to a result!");

    }

    //Show result after calculation

    fun showResult () {
        implementCalculation();
        showResult.value = true;
        displayNumber.value = if (result.value != "Error" && result.value != "Infinity") result.value else if (result.value == "-0.0") "0.0" else "Error";
        isAddedWithMathFunctionality.value = false;
        parsedNumber.value = "";
    }

    //Add math functionality into the calculation

    fun addMathFunctionality (symbolInitialize : String) {
        showResult.value = false;
        Log.i("Check math condition: ", isAddedWithMathFunctionality.value.toString());
        if (symbolInitialize != "=" && !isAddedWithMathFunctionality.value) {
            Log.i("Check math: ", isAddedWithMathFunctionality.value.toString());
            if (parsedNumber.value.length > 0) {
                listOfNumbers.add(parsedNumber.value);
            } else if (checkZeroDivision()) {
                result.value = "Error";
                showResult.value = true;
                return;
            }
            listOfNumbers.add(symbolInitialize);
        } else if (listOfNumbers.size == 0 && checkContainsSymbol(symbolInitialize as Any) && !isAddedWithMathFunctionality.value) {
            Log.e("Cannot insert symbol!", "Cannot insert symbol!");
        }
        else if (symbolInitialize == "=") {
            if (parsedNumber.value.length > 0) {
                listOfNumbers.add(parsedNumber.value);
            }
            showResult();
            return;
        }
        Log.i("Show all: ", listOfNumbers.toString());
        if (checkZeroDivision()) {
            result.value = "Error";
            showResult.value = true;
            parsedNumber.value = "";
            return;
        }
        isAddedWithMathFunctionality.value = true;
        parsedNumber.value = "";

        Log.i("Add math functionality", "Add math functionality ${symbolInitialize}");
    }

    //Function to implement calculation
    fun implementCalculation () {
        var convertToList = listOfNumbers.toList();

        if (listOfNumbers.size == 1 && !listOfNumbers.any { it.toDoubleOrNull() != null }) {
            listOfNumbers.clear();
            addCombination(listOfNumbers);
            return;
        }

        var indexOne = 0;
        var indexTwo = 0;
        for ((index, element) in convertToList.withIndex()) {
            if (element.toDoubleOrNull() != null) {
                indexOne = index;
                break;
            }
        }

        for (i in convertToList.size - 1 downTo(0)) {
            if (convertToList.get(i).toDoubleOrNull() != null) {
                indexTwo = i;
                break;
            }
        }
        var newList = mutableListOf<String>();

        for (i in indexOne..indexTwo) {
            newList.add(convertToList.get(i));
        }

        addCombination(newList);

        Log.i("Implement Calculation: ", "Implement a sequence of calculation in a List!");
    }

    //Combine result from the List to the result
    fun addCombination (list: MutableList<String>) {

        if (list.size == 0) {
            result.value = "0.0";
            showResult.value = true;
            return;
        }

        if (list.size == 1) {
            result.value = list.get(0).toString();
            showResult.value = true;
            return;
        }

        var parsedResult = list.get(0);

        for (i in 1 until list.size - 1) {
            if (i % 2 != 0) {
                Log.i("Result 1", list.get(i));
                Log.i("Result 2", list.get(i+1));
                parsedResult = addCalculation(parsedResult, list.get(i), list.get(i+1));
            }
        }

        result.value = parsedResult;
        showResult.value = true;

    }

    // Add calculation to each number
    fun addCalculation (result : String, symbol : String, number : String) : String {

        when (symbol) {
            "+" -> return (result.toDouble() + number.toDouble()).toString();
            "-" -> return (result.toDouble() - number.toDouble()).toString();
            "*" -> return (result.toDouble() * number.toDouble()).toString();
            "/" -> return (result.toDouble() / number.toDouble()).toString();
         }

        return "";

    }

    //Function to check whether math functionality symbols existed in the List or not
    fun checkContainsSymbol (singleNumber: Any) : Boolean {
        if (ListOfFeatures.listOfSymbolsWithoutEquation.any { it == singleNumber.toString() }) {
            return true;
        }

        return false;
    }

    //Function to check zero division exception
    fun checkZeroDivision () : Boolean {
        for (i in 1 until listOfNumbers.size) {
            if (listOfNumbers.get(i).toDoubleOrNull() == 0.0 && listOfNumbers.get(i-1) == "/") {
                return true;
            }
        }
        return false;
    }

    //Function to clear, multiply by -1, and divide by 100
    fun functionToClearOrMultiplyByMinusOneOrDivideBy100 (value : String) {
        when (value) {
            "C" -> clearFunction();
            "+/-" -> multiplyByMinusOne();
            "%" -> divideBy100();
        }

        Log.i("Special calculation: ", "Clear, multiply by -1, and divide by 100");
    }

    //Clear all states
    fun clearFunction () {
        result.value = "0.0";
        parsedNumber.value = "";
        showResult.value = false;
        listOfNumbers.clear();
        isAddedWithMathFunctionality.value = false;
        displayNumber.value = "0.0";
    }

    //Multiply by minus 1
    fun multiplyByMinusOne () {
        isMultiplyOne.value = true;

        if (showResult.value) {
            result.value = if (result.value.toDouble() == 0.0) (0.0 * -1.0).toString() else (result.value.toDouble() * -1.0).toString();
            if (!isAddedWithMathFunctionality.value) {
                listOfNumbers.add("*");
                listOfNumbers.add("-1");
            }
            displayNumber.value = result.value;
            return;
        }
        parsedNumber.value = if (parsedNumber.value == "") (0.0 * -1.0).toString() else (parsedNumber.value.toDouble() * -1.0).toString();
        displayNumber.value = parsedNumber.value;
        
    }

    // Divide by 100
    fun divideBy100 () {
        isDivideBy100.value = true;

        if (showResult.value) {
            result.value = if (result.value.toDouble() == 0.0) (0.0 / 100.0).toString() else (result.value.toDouble() / 100.0).toString();
            if (!isAddedWithMathFunctionality.value) {
                listOfNumbers.add("/");
                listOfNumbers.add("100");
            }
            displayNumber.value = result.value;
            return;
        }
        parsedNumber.value = if (parsedNumber.value == "") (0.0 / 100.0).toString() else (parsedNumber.value.toDouble() / 100.0).toString();
        displayNumber.value = parsedNumber.value;
    }

}