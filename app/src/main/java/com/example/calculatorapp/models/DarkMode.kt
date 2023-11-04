package com.example.calculatorapp.models

import androidx.compose.runtime.mutableStateOf

class DarkMode {

    val isDarkMode = mutableStateOf(false);

    fun setDarkMode () {
        isDarkMode.value = true;
    }

    fun setLightMode () {
        isDarkMode.value = false;
    }
}