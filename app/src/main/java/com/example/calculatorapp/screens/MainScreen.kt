package com.example.calculatorapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorapp.constants.ListOfFeatures
import com.example.calculatorapp.models.Calculation
import com.example.calculatorapp.models.DarkMode

@Composable
fun MainScreen () {

    val calculation : Calculation = Calculation();
    val darkMode : DarkMode = DarkMode();

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = if (darkMode.isDarkMode.value) Color.Black else MaterialTheme.colors.surface)
           ) {
            Column(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
            , horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f)
                   , horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { darkMode.setDarkMode() }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (darkMode.isDarkMode.value) Color.LightGray else (Color.Black),
                        contentColor = if (darkMode.isDarkMode.value) Color.Cyan else (Color.White)
                    )) {
                        Text(text = "Dark mode")
                    }
                    
                    Button(onClick = { darkMode.setLightMode() }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (darkMode.isDarkMode.value) Color.White else Color.LightGray,
                        contentColor = if (darkMode.isDarkMode.value) Color.Black else Color.Cyan
                    )) {
                        Text(text = "Light Mode")
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {
                Box(modifier = Modifier.padding(10.dp)) {
                    Text(text = "${calculation.displayNumber()}", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp));
                }
            }
            Column(modifier = Modifier
                .border(2.dp, Color.Black, shape = RoundedCornerShape(5.dp))
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = if (darkMode.isDarkMode.value) Color.Black else MaterialTheme.colors.background)
                ) {
                Row(modifier = Modifier.fillMaxSize()) {

                    Column(modifier = Modifier.fillMaxWidth(0.75f)) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            val firstRowOfFunctions = ListOfFeatures.listOfFirstLineFunctionsOfCalculator;
                            
                            firstRowOfFunctions.forEach { it ->
                                Button(
                                    onClick = { calculation.functionToClearOrMultiplyByMinusOneOrDivideBy100(it) },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Gray,
                                        contentColor = Color.Black
                                    ),
                                    shape = CircleShape,
                                    border = BorderStroke(2.dp, Color.LightGray),
                                    modifier = Modifier.size(80.dp)
                                ) {
                                    Text(text = "$it", style = TextStyle(color = Color.Black,fontWeight = FontWeight.Bold, fontSize = 20.sp))
                                }
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxHeight(0.75f)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            val numberOfColumns = ListOfFeatures.listOfSecondLineFunctionsOfCalculator.size;
                            
                            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                                for (i in 1..numberOfColumns) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                                        val singleRowInTheList = ListOfFeatures.listOfSecondLineFunctionsOfCalculator.get(i-1);
                                        
                                        singleRowInTheList.forEach { it ->
                                            Button(onClick = { calculation.insertNumber(it) }, colors = ButtonDefaults.buttonColors(
                                                backgroundColor = Color.Cyan,
                                                contentColor = Color.Black

                                            ),
                                                border = BorderStroke(1.dp, Color.LightGray),
                                                shape = CircleShape,
                                                modifier= Modifier.size(80.dp)
                                            ) {
                                                Text(text = "$it", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(3.dp)
                            , horizontalArrangement = Arrangement.SpaceAround,verticalAlignment = Alignment.CenterVertically) {
                            val thirdRowsOfFunctionInCalculator = ListOfFeatures.listOfThirdLineFunctionsOfCalculator;

                            thirdRowsOfFunctionInCalculator.forEachIndexed { index, any ->
                                val weightOfButton = if (index == 0) 0.66f else 0.80f;

                                Button(onClick = { calculation.insertNumber(any) }, colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.onSecondary,
                                    contentColor = Color.White
                                ),
                                    border = BorderStroke(1.dp, Color.LightGray),
                                    shape = if (index == 0) RoundedCornerShape(5.dp) else CircleShape,
                                    modifier= Modifier
                                        .fillMaxWidth(weightOfButton)
                                        .size(80.dp)) {
                                    Text(text = "$any", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxSize()
                        , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
                        val signs: List<String> = ListOfFeatures.listOfSymbols;

                        signs.forEach { it ->
                            Button(onClick = { calculation.addMathFunctionality(it) }, colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.onSecondary,
                                contentColor = Color.White
                            ),
                                border = BorderStroke(1.dp, Color.LightGray),
                                shape = CircleShape,
                                modifier= Modifier.size(80.dp)
                            ) {
                                Text(text = "$it", style = TextStyle(color = Color.White,fontWeight = FontWeight.Bold, fontSize = 20.sp))
                            }
                        }
                        }
                    }
                }
            }
        }
    }