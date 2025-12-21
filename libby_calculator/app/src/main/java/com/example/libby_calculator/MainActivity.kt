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

data class MenuItem(val name: String, val price: Double, val options: List<String> = emptyList())

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

@Preview(showBackground = true, name = "Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun Libby_calculatorApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Greeting(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var showMainMenu by remember { mutableStateOf(true) }
    var showCoffeeMenu by remember { mutableStateOf(false) }
    var showBagelMenu by remember { mutableStateOf(false) }
    var showPastryMenu by remember { mutableStateOf(false) }
    var showPh1Modal by remember { mutableStateOf(false) }
    var itemTotal by remember { mutableDoubleStateOf(0.00) }
    var total by remember { mutableDoubleStateOf(0.00) }
    val addedItems = remember { mutableStateListOf<MenuItem>() }
    var itemName by remember { mutableStateOf("") }

    var buttonWidth by remember { mutableStateOf(130.dp) }
    var buttonTextFontSize by remember { mutableStateOf(30.sp) }

    val checkboxOptions = remember { listOf(
        "Option 1",
        "Option 2",
        "Option 3"
    ) }
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
            if (showMainMenu) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "PH1",
                                        10.00
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("PH1", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "PH2",
                                        5.00
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("PH2", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "PH3",
                                        20.00
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("PH3", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "something",
                                        15.00
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("something", fontSize = buttonTextFontSize)
                            }
                        }
                    }
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showCoffeeMenu = !showCoffeeMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("coffee", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showBagelMenu = !showBagelMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("bagel", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showPastryMenu = !showPastryMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("pastry", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "PH4",
                                        12.50
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("PH4", fontSize = buttonTextFontSize)
                            }
                        }
                    }
                }
            }
            else if (showCoffeeMenu) {
                Column {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showCoffeeMenu = !showCoffeeMenu
                                    showMainMenu = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("<Back", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "coffee_large",
                                        2.50
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("large", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "coffee_medium",
                                        3.95
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("medium", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "coffee_small",
                                        8.0
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("small", fontSize = buttonTextFontSize)
                            }
                        }
                    }
                }
            }
            else if (showBagelMenu) {
                Column {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showBagelMenu = !showBagelMenu
                                    showMainMenu = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("<Back", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "poppyseed",
                                        7.65
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("poppyseed", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "plain",
                                        3.23
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1f),
                                shape = RectangleShape
                            ) {
                                Text("plain", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "everything",
                                        4.8
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("everything", fontSize = buttonTextFontSize)
                            }
                        }
                    }
                }
            }
            else if (showPastryMenu) {
                Column {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showPastryMenu = !showPastryMenu
                                    showMainMenu = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("<Back", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "croissant",
                                        12.0
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("croissant", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "muffin",
                                        15.3
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("muffin", fontSize = buttonTextFontSize)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "banana bread",
                                        20.45
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("banana bread", fontSize = buttonTextFontSize)
                            }
                        }
                    }
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    val item = MenuItem(
                                        "cake",
                                        103.67
                                    )
                                    itemTotal = item.price
                                    itemName = item.name
                                    showPh1Modal = true
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("cake", fontSize = buttonTextFontSize)
                            }
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${item.name} - $${String.format("%.2f", item.price)}",
                                    fontSize = 16.sp
                                )
                                item.options.forEach { option ->
                                    Text(
                                        text = "  - $option",
                                        fontSize = 12.sp // Smaller font
                                    )
                                }
                            }
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
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$itemName Modifiers")
                        Button(onClick = { showPh1Modal = false }) {
                            Text("X")
                        }
                    }
                },
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
                    Button(onClick = {
                        val selectedOptions = checkboxOptions.filterIndexed { index, _ -> checkedStates[index] }
                        var ph1Price = itemTotal
                        if (selectedOptions.contains("Option 1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Option 2")) {
                            ph1Price += 2.50
                        }
                        if (selectedOptions.contains("Option 3")) {
                            ph1Price += 3.98
                        }
                        val ph1Item = MenuItem(name = "PH1", price = ph1Price, options = selectedOptions)
                        addedItems.add(ph1Item)
                        total += ph1Item.price
                        itemTotal = 0.00
                        showPh1Modal = false
                        for (i in checkedStates.indices) {
                            checkedStates[i] = false
                        }
                    }) {
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
                    .padding(10.dp)
                    .width(buttonWidth)
                    .aspectRatio(1.5f),
                shape = RectangleShape) {
                Text("Reset", fontSize = buttonTextFontSize)
            }

            Text("Total: $${String.format("%.2f", total)}", modifier = Modifier.padding(10.dp), fontSize = 50.sp)
        }
    }
}

//@Composable
//fun GreetingPreview() {
//    Libby_calculatorTheme {
//        Greeting()
//    }
//}
