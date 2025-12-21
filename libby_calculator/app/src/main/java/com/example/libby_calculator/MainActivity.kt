package com.example.libby_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libby_calculator.ui.theme.Libby_calculatorTheme

data class MenuItem(val name: String, val price: Double)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Libby_calculatorTheme {
                Libby_calculatorApp()
            }
        }
    }
}

@Composable
fun Libby_calculatorApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Greeting(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var showCoffeeMenu by remember { mutableStateOf(false) }
    var showBagelMenu by remember { mutableStateOf(false) }
    var showPastryMenu by remember { mutableStateOf(false) }
    var total by remember { mutableDoubleStateOf(0.00) }
    val addedItems = remember { mutableStateListOf<MenuItem>() }

    var buttonWidth by remember { mutableStateOf(130.dp) }
    var showPh1Modal by remember { mutableStateOf(false) }
    val checkboxOptions = remember { listOf("Option 1", "Option 2", "Option 3") }
    val checkedStates = remember { mutableStateListOf(false, false, false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = { showCoffeeMenu = !showCoffeeMenu },
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape
                        ) {
                            Text("coffee", fontSize = 30.sp)
                        }

                        if (showCoffeeMenu) {
                            Column {
                                Button(onClick = { val item = MenuItem("coffee_large", 2.50); total += item.price; addedItems.add(item) }) {
                                    Text("large")
                                }
                                Button(onClick = { val item = MenuItem("coffee_medium", 3.95); total += item.price; addedItems.add(item) }) {
                                    Text("medium")
                                }
                                Button(onClick = { val item = MenuItem("coffee_small", 8.0); total += item.price; addedItems.add(item) }) {
                                    Text("small")
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = { showBagelMenu = !showBagelMenu },
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape) {
                            Text("bagel", fontSize = 30.sp)
                        }

                        if (showBagelMenu) {
                            Column {
                                Button(onClick = { val item = MenuItem("poppyseed", 7.65); total += item.price; addedItems.add(item) }) {
                                    Text("poppyseed")
                                }
                                Button(onClick = { val item = MenuItem("plain", 3.23); total += item.price; addedItems.add(item) }) {
                                    Text("plain")
                                }
                                Button(onClick = { val item = MenuItem("everything", 4.8); total += item.price; addedItems.add(item) }) {
                                    Text("everything")
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = { showPastryMenu = !showPastryMenu },
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape) {
                            Text("pastry", fontSize = 30.sp)
                        }

                        if (showPastryMenu) {
                            Column {
                                Button(onClick = { val item = MenuItem("croissant", 12.0); total += item.price; addedItems.add(item) }) {
                                    Text("croissant")
                                }
                                Button(onClick = { val item = MenuItem("muffin", 15.3); total += item.price; addedItems.add(item) }) {
                                    Text("muffin")
                                }
                                Button(onClick = { val item = MenuItem("banana bread", 20.45); total += item.price; addedItems.add(item) }) {
                                    Text("banana bread")
                                }
                                Button(onClick = { val item = MenuItem("cake", 103.67); total += item.price; addedItems.add(item) }) {
                                    Text("cake")
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape) {
                            Text("something", fontSize = 30.sp)
                        }
                    }
                }
                Row {
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = { showPh1Modal = true },
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape
                        ) {
                            Text("PH1", fontSize = 30.sp)
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape) {
                            Text("PH2", fontSize = 30.sp)
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape) {
                            Text("PH3", fontSize = 30.sp)
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .width(buttonWidth)
                                .aspectRatio(1.5f),
                            shape = RectangleShape) {
                            Text("PH4", fontSize = 30.sp)
                        }
                    }
                }
            }
            VerticalDivider()
            // Added items list
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text("Added Items:", fontSize = 20.sp)
                LazyColumn {
                    itemsIndexed(addedItems) { index, item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${item.name} - $${String.format("%.2f", item.price)}", modifier = Modifier.weight(1f), fontSize = 16.sp)
                            Button(onClick = {
                                total -= item.price
                                addedItems.removeAt(index)
                            }) {
                                Text("X", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }

        if (showPh1Modal) {
            AlertDialog(
                onDismissRequest = { showPh1Modal = false },
                title = { Text("PH1 Options") },
                text = {
                    Column {
                        checkboxOptions.forEachIndexed { index, text ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = checkedStates[index],
                                    onCheckedChange = { checkedStates[index] = it }
                                )
                                Text(text)
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showPh1Modal = false }) {
                        Text("OK")
                    }
                }
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { total = 0.00; addedItems.clear() },
                modifier = Modifier
                    .padding(20.dp)
                    .width(buttonWidth)
                    .aspectRatio(1.5f),
                shape = RectangleShape) {
                Text("Reset", fontSize = 30.sp)
            }

            Text("Total: $${String.format("%.2f", total)}", modifier = Modifier.padding(20.dp), fontSize = 50.sp)
        }
    }
}

@Preview(showBackground = true, name = "Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun GreetingPreview() {
    Libby_calculatorTheme {
        Greeting()
    }
}
