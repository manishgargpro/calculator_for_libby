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
data class ActionButton(val text: String, val action: () -> Unit)

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
    var showExtraMenu by remember { mutableStateOf(false) }
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

    val itemButtonAction = { name: String, price: Double ->
        itemTotal = price
        itemName = name
        showPh1Modal = true
    }

    // Main menu buttons
    val buttonsRow1 = listOf(
        ActionButton("PH1") { itemButtonAction("PH1", 10.00) },
        ActionButton("PH2") { itemButtonAction("PH2", 5.00) },
        ActionButton("PH3") { itemButtonAction("PH3", 20.00) }
    )
    // End main menu buttons

    // Coffee menu buttons
    val coffeeButtonsRow1 = listOf(
        ActionButton("coffee large") { itemButtonAction("coffee large", 2.50) },
        ActionButton("coffee medium") { itemButtonAction("coffee medium", 3.95) },
        ActionButton("coffee small") { itemButtonAction("coffee small", 8.00) },
    )
    // End coffee menu buttons

    // Bagel menu buttons
    val bagelButtonsRow1 = listOf(
        ActionButton("poppy seed") { itemButtonAction("poppy seed", 7.65) },
        ActionButton("plain bagel") { itemButtonAction("plain bagel", 3.23) },
        ActionButton("everything bagel") { itemButtonAction("everything bagel", 4.80) },
    )
    // End bagel menu buttons

    // Pastry menu buttons
    val pastryButtonsRow1 = listOf(
        ActionButton("croissant") { itemButtonAction("croissant", 12.00) },
        ActionButton("muffin") { itemButtonAction("muffin", 15.30) },
        ActionButton("banana bread") { itemButtonAction("banana bread", 20.45) },
    )
    val pastryButtonsRow2 = listOf(
        ActionButton("cake") { itemButtonAction("cake", 103.67) }
    )
    // End pastry menu buttons

    // Extra menu buttons
    val extraButtonsRow1 = listOf(
        ActionButton("dash of syrup") {
            val item = MenuItem("dash of syrup", 0.65)
            total += item.price; addedItems.add(item)
        },
        ActionButton("hot water") {
            val item = MenuItem("hot water", 0.50)
            total += item.price; addedItems.add(item)
        },
        ActionButton("something else") {
            val item = MenuItem("something else", 1.00)
            total += item.price; addedItems.add(item)
        },
    )
    // End extra menu buttons

    // Modifier options
    val checkboxOptions = remember { listOf(
        "Option 1",
        "Option 2",
        "Option 3"
    ) }
    val checkedStates = remember { mutableStateListOf(false, false, false) }
    // End modifier options

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
                        buttonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showExtraMenu = !showExtraMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape
                            ) {
                                Text("extras", fontSize = buttonTextFontSize)
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
                        coffeeButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
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
                        bagelButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
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
                        pastryButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        pastryButtonsRow2.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                }
            }
            else if (showExtraMenu) {
                Column {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showExtraMenu = !showExtraMenu
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
                        extraButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
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
                        Button(onClick = {
                            showPh1Modal = false
                            for (i in checkedStates.indices) {
                                checkedStates[i] = false
                            }
                        }) {
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
