package com.example.electricityprice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.electricityprice.ui.theme.ElectricityPriceTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElectricityPriceTheme(dynamicColor = false) {
                Scaffold { innerPadding ->
                    ElectricityPrice(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ElectricityPrice(modifier: Modifier = Modifier){
    var electricityConsumption by remember {mutableStateOf(1000)}
    var electricityPrice by remember {mutableStateOf(0.25f)}
    var includeVat by remember {mutableStateOf(false)}
    val electricityCost = if(includeVat){
        electricityConsumption * electricityPrice * 1.24f} else{
        electricityConsumption * electricityPrice}


    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text(
            text = "Cost of Electricity",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)

        )
        OutlinedTextField(
            value = electricityConsumption.toString(),
            onValueChange = {electricityConsumption = it.toIntOrNull() ?: 0},
            label = {Text(text = "Consumption in kWh")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth())
        Text(
            text = String.format("Price/kWh: %.2f", electricityPrice )
        )
        Slider(
            value = electricityPrice,
            onValueChange = {electricityPrice = it},
            onValueChangeFinished = {
                // round to nearest 0.01
                electricityPrice = (electricityPrice * 100).roundToInt() / 100f
            },
            valueRange = 0f..1f,

            )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ){
            Checkbox(
                includeVat,
                onCheckedChange = {includeVat = it},
                modifier = Modifier.padding(start = 0.dp)
            )
            Text(text = "VAT 24%")
        }

        Surface(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary,

            ) {
            Text(
                text = String.format("%d €", electricityCost.roundToInt()),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ElectricityPricePreview() {
    ElectricityPriceTheme(dynamicColor = false) {
        ElectricityPrice()
    }
}
