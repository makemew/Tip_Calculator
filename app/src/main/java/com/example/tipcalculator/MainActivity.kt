package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme{
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TipCalculatorApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorApp() {
    TipCalculator()
}

@Composable
fun TipCalculator() {
    var amountInput by remember { mutableStateOf("") }
    var tip by remember { mutableFloatStateOf(0f) }
    var tipPercentage by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
    tip = calculateTip(amountInput.toIntOrNull()?:0, tipPercentage.toIntOrNull()?:0, roundUp)

    Column(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculate tip",
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )
        EditTextField(
            text = amountInput,
            onValueChange = { amountInput = it },
            label = "Bill Amount"
        )
        EditTextField(
            text = tipPercentage,
            onValueChange = {tipPercentage = it},
            label = "Tip Percentage"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text("Round up tip?")
            Switch(
                checked = roundUp,
                onCheckedChange = {
                    roundUp = !roundUp
                }
            )
        }
        Text(
            "Tip Amount: \$${"%.2f".format(tip)}",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

fun calculateTip(billAmount:Int = 0, tipPercentage: Int = 15, roundUp: Boolean) = if (roundUp) Math.ceil(billAmount * (tipPercentage/100f).toDouble()).toFloat() else billAmount * (tipPercentage/100f)

@Composable
fun EditTextField(text: String, onValueChange: (String)->Unit, label: String) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        value = text,
        label = {Text(label)},
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        singleLine = true
    )
}