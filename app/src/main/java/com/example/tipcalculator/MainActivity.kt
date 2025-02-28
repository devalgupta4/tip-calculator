package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import kotlin.math.ceil // Import for rounding up

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorApp()
        }
    }
}

@Composable
fun TipCalculatorApp() {
    TipCalculator()
}

@Composable
fun TipCalculator() {
    var billAmount by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf("") }
    var tipAmount by remember { mutableStateOf(0f) }
    var totalAmount by remember { mutableStateOf(0f) }
    var roundUp by remember { mutableStateOf(false) }


    LaunchedEffect(billAmount, tipPercentage, roundUp) {
        val bill = billAmount.toFloatOrNull() ?: 0f
        val percentage = tipPercentage.toFloatOrNull() ?: 0f
        tipAmount = bill * percentage / 100
        totalAmount = bill + tipAmount


        if (roundUp) {
            totalAmount = ceil(totalAmount)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Tip Calculator", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = tipPercentage,
            onValueChange = { tipPercentage = it },
            label = { Text("Tip Percentage") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Round Up Tip")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Tip: $${"%.2f".format(tipAmount)}")
        Text(text = "Total: $${"%.2f".format(totalAmount)}")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTipCalculator() {
    TipCalculator()
}
