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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libby_calculator.ui.theme.Libby_calculatorTheme

data class MenuItem(val name: String, val price: Double, val options: List<String> = emptyList())
data class ActionButton(val text: String, val action: () -> Unit, val color: Color = Color.Unspecified)

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
    var showDiscountMenu by remember { mutableStateOf(false) }
    var showBleMenu by remember { mutableStateOf(false) }
    var showFooMenu by remember { mutableStateOf(false) }
    var showMerMenu by remember { mutableStateOf(false) }
    var showPh1Modal by remember { mutableStateOf(false) }
    var itemTotal by remember { mutableDoubleStateOf(0.00) }
    var total by remember { mutableDoubleStateOf(0.00) }
    val addedItems = remember { mutableStateListOf<MenuItem>() }
    var itemName by remember { mutableStateOf("") }

    var buttonWidth by remember { mutableStateOf(130.dp) }
    var buttonTextFontSize by remember { mutableStateOf(25.sp) }

    val itemButtonAction = { name: String, price: Double ->
        itemTotal = price
        itemName = name
        showPh1Modal = true
    }

    val itemButtonAction2 = { name: String, price: Double ->
        val item = MenuItem(name, price)
        total += price
        addedItems.add(item)
    }

    // Main menu buttons
    val buttonsRow1 = listOf(
        ActionButton("COFFE", { itemButtonAction("COFFEE", 10.00) }, Color(0xFF0F5FB7)),
        ActionButton("OLE", { itemButtonAction("OLE", 5.00) }, Color(0xFF0F5FB7)),
        ActionButton("CB", { itemButtonAction("CB", 20.00) }, Color(0xFF0F5FB7))
    )
    val buttonsRow2 = listOf(
        ActionButton("L CAP", { itemButtonAction("L CAP", 10.00) }, Color(0xFFBB5918)),
        ActionButton("A", { itemButtonAction("A", 5.00) }, Color(0xFFBB5918)),
        ActionButton("FL", { itemButtonAction("FL", 20.00) }, Color(0xFFBB5918)),
        ActionButton("CRL", { itemButtonAction("CRL", 20.00) }, Color(0xFFBB5918))
    )
    val buttonsRow3 = listOf(
        ActionButton("M WM", { itemButtonAction("M WM", 10.00) }, Color(0xFF6F1C0D)),
        ActionButton("FW", { itemButtonAction("FW", 5.00) }, Color(0xFF6F1C0D)),
        ActionButton("KW", { itemButtonAction("KW", 20.00) }, Color(0xFF6F1C0D)),
        ActionButton("WELL", { itemButtonAction("WELL", 20.00) }, Color(0xFF126583))
    )
    val buttonsRow4 = listOf(
        ActionButton("ESM", { itemButtonAction("ESM", 10.00) }, Color(0xFFF5A724)),
        ActionButton("ESP", { itemButtonAction("ESP", 5.00) }, Color(0xFFF5A724)),
        ActionButton("HC", { itemButtonAction("KC", 20.00) }, Color(0xFFF5A724)),
        ActionButton("COLD", { itemButtonAction("COLD", 20.00) }, Color(0xFF6ABCB8))
    )
    val buttonsRow5 = listOf(
        ActionButton("TEA", { itemButtonAction("TEA", 10.00) }, Color(0xFF7DD675)),
        ActionButton("TEA L", { itemButtonAction("TEA L", 5.00) }, Color(0xFF7DD675)),
        ActionButton("CHAI", { itemButtonAction("CHAI", 20.00) }, Color(0xFF7DD675)),
        ActionButton("GTL", { itemButtonAction("GTL", 20.00) }, Color(0xFF7DD675))
    )
    // End main menu buttons

    // BLE menu buttons
    val bleButtonsRow1 = listOf(
        ActionButton("ESPB", { itemButtonAction("ESPB", 2.50) }, Color(0xFFB9710B)),
        ActionButton("FLB", { itemButtonAction("FLB", 3.95) }, Color(0xFFB9710B)),
        ActionButton("CRB", { itemButtonAction("CRB", 8.00) }, Color(0xFFB9710B)),
        ActionButton("MB", { itemButtonAction("MB", 8.00) }, Color(0xFFB9710B))
    )
    val bleButtonsRow2 = listOf(
        ActionButton("TEA B", { itemButtonAction("TEA B", 2.50) }, Color(0xFF7DD675)),
        ActionButton("CHAIB", { itemButtonAction("CHAIB", 3.95) }, Color(0xFF7DD675)),
        ActionButton("SM", { itemButtonAction("SM", 8.00) }, Color(0xFF6ABCB8)),
        ActionButton("CRBL", { itemButtonAction("CRBL", 8.00) }, Color(0xFF48638A))
    )
    // End BLE menu buttons

    // FOO menu buttons
    val fooButtonsRow1 = listOf(
        ActionButton("MUFF", { itemButtonAction2("MUFF", 7.65) }, Color(0xFFF00B6A)),
        ActionButton("FCRO", { itemButtonAction2("FCRO", 3.23) }, Color(0xFFF00B6A)),
        ActionButton("PLAIN", { itemButtonAction2("PLAIN", 4.80) }, Color(0xFFF00B6A))
    )
    val fooButtonsRow2 = listOf(
        ActionButton("BARS", { itemButtonAction2("BARS", 7.65) }, Color(0xFFD62027)),
        ActionButton("SCONE", { itemButtonAction2("SCONE", 3.23) }, Color(0xFFD62027)),
        ActionButton("POP", { itemButtonAction2("POP", 4.80) }, Color(0xFFD62027)),
        ActionButton("COOKI", { itemButtonAction2("COOKI", 4.80) }, Color(0xFFD62027))
    )
    val fooButtonsRow3 = listOf(
        ActionButton("LOAF", { itemButtonAction2("LOAF", 7.65) }, Color(0xFF91132F)),
        ActionButton("PAR", { itemButtonAction2("PAR", 3.23) }, Color(0xFFF5A724)),
        ActionButton("G&G", { itemButtonAction2("G&G", 4.80) }, Color(0xFFFB8125)),
        ActionButton("BALLZ", { itemButtonAction2("BALLZ", 4.80) }, Color(0xFFBC1441))
    )
    val fooButtonsRow4 = listOf(
        ActionButton("SAMMI", { itemButtonAction2("SAMMI", 7.65) }, Color(0xFF0F3D7F)),
        ActionButton("BB", { itemButtonAction2("BB", 3.23) }, Color(0xFF24157A)),
        ActionButton("EMPAN", { itemButtonAction2("EMPAN", 4.80) }, Color(0xFF2BBDAB)),
        ActionButton("OAT", { itemButtonAction2("OAT", 4.80) }, Color(0xFF2BBDAB))
    )
    // End FOO menu buttons

    // MER menu buttons
    val merButtonsRow1 = listOf(
        ActionButton("T SHI", { itemButtonAction2("T SHI", 12.00) }, Color(0xFF0E5672)),
        ActionButton("MUG", { itemButtonAction2("MUG", 15.30) }, Color(0xFF48638A))
    )
    val merButtonsRow2 = listOf(
        ActionButton("BULK", { itemButtonAction2("BULK", 20.45) }, Color(0xFF6F1C0D)),
        ActionButton("BLKT", { itemButtonAction2("BLKT", 103.67) }, Color(0xFFA70B10)),
        ActionButton("HRBT", { itemButtonAction2("HRBT", 20.45) }, Color(0xFF91132F)),
        ActionButton("HONEY", { itemButtonAction2("HONEY", 103.67) }, Color(0xFFF5A724))
    )
    val merButtonsRow3 = listOf(
        ActionButton("SPTEA", { itemButtonAction2("SPTEA", 20.45) }, Color(0xFFBC1441)),
        ActionButton("GRT", { itemButtonAction2("GRT", 103.67) }, Color(0xFF2AAD65))
    )
    // End MER menu buttons

    // Extra menu buttons
    val extraButtonsRow1 = listOf(
        ActionButton("$1.00", { itemButtonAction2("$1.00", 1.00) }, Color(0xFF6ABCB8)),
        ActionButton("1P", { itemButtonAction2("1P", 0.25) }, Color(0xFF6ABCB8)),
    )
    // End extra menu buttons

    // Discount menu buttons
    val discountButtonsRow1 = listOf(
        ActionButton("-$0.50", { itemButtonAction2("-$0.50", -0.50) }),
        ActionButton("-$5.00", { itemButtonAction2("-$5.00", -5.00) })
    )
    // End Discount menu buttons

    // Modifier options
    val checkboxOptions = remember { listOf(
        "Trout +1",
        "Whaley +2",
        "Almond +1",
        "Macadamia +1",
        "Soy +1",
        "Oat +1",
        "Breve +1",
        "Heavy Cream +2",
        "Extra Milk +1",
        "Espresso Shot +1",
        "Syrup +1",
        "Honey +1"
    ) }
    val checkedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false, false, false, false, false) }
    // End modifier options

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(5f)
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
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
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
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6ABCB8))
                            ) {
                                Text("EXTRA", fontSize = buttonTextFontSize, textDecoration = TextDecoration.Underline)
                            }
                        }
                    }
                    Row {
                        buttonsRow2.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        buttonsRow3.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        buttonsRow4.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        buttonsRow5.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showBleMenu = !showBleMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BBDAB))
                            ) {
                                Text("BLE", fontSize = buttonTextFontSize, textDecoration = TextDecoration.Underline)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showFooMenu = !showFooMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF00B6A))
                            ) {
                                Text("FOO", fontSize = buttonTextFontSize, textDecoration = TextDecoration.Underline)
                            }
                        }
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMainMenu = false
                                    showMerMenu = !showMerMenu
                                },
                                modifier = Modifier
                                    .width(buttonWidth)
                                    .aspectRatio(1.5f),
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24157A))
                            ) {
                                Text("MER", fontSize = buttonTextFontSize, textDecoration = TextDecoration.Underline)
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
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF419EBF))
                            ) {
                                Text("ITAL", fontSize = buttonTextFontSize)
                            }
                        }
                    }
                }
            }
            else if (showBleMenu) {
                Column (
                    modifier = Modifier
                        .weight(2f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showBleMenu = !showBleMenu
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
                    }
                    Row {
                        bleButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        bleButtonsRow2.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                }
            }
            else if (showFooMenu) {
                Column (
                    modifier = Modifier
                        .weight(2f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showFooMenu = !showFooMenu
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
                        fooButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        fooButtonsRow2.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        fooButtonsRow3.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        fooButtonsRow4.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                }
            }
            else if (showMerMenu) {
                Column (
                    modifier = Modifier
                        .weight(2f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showMerMenu = !showMerMenu
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
                        merButtonsRow1.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        merButtonsRow2.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                    Row {
                        merButtonsRow3.forEach { button ->
                            Column(modifier = Modifier.padding(1.dp)) {
                                Button(
                                    onClick = button.action,
                                    modifier = Modifier
                                        .width(buttonWidth)
                                        .aspectRatio(1.5f),
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = button.color)
                                ) {
                                    Text(button.text, fontSize = buttonTextFontSize)
                                }
                            }
                        }
                    }
                }
            }
            else if (showExtraMenu) {
                Column (
                    modifier = Modifier
                        .weight(2f)
                        .verticalScroll(rememberScrollState())
                ) {
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
            else if (showDiscountMenu) {
                Column (
                    modifier = Modifier
                        .weight(2f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        Column(modifier = Modifier.padding(1.dp)) {
                            Button(
                                onClick = {
                                    showDiscountMenu = !showDiscountMenu
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
                        discountButtonsRow1.forEach { button ->
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
                Text("Added Items:", fontSize = buttonTextFontSize)
                LazyColumn {
                    itemsIndexed(addedItems) { index, item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${item.name} - $${String.format("%.2f", item.price)}",
                                    fontSize = 20.sp
                                )
                                item.options.forEach { option ->
                                    Text(
                                        text = "  - $option",
                                        fontSize = 15.sp // Smaller font
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
                    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                        if (selectedOptions.contains("Trout +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Whaley +2")) {
                            ph1Price += 2.00
                        }
                        if (selectedOptions.contains("Almond +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Macadamia +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Soy +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Oat +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Breve +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Heavy Cream +2")) {
                            ph1Price += 2.00
                        }
                        if (selectedOptions.contains("Extra Milk +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Espresso Shot +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Syrup +1")) {
                            ph1Price += 1.00
                        }
                        if (selectedOptions.contains("Honey +1")) {
                            ph1Price += 1.00
                        }
                        val ph1Item = MenuItem(name = itemName, price = ph1Price, options = selectedOptions)
                        addedItems.add(ph1Item)
                        total += ph1Item.price
                        itemTotal = 0.00
                        itemName = ""
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

        Row (
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Row {
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = { total = 0.00; addedItems.clear() },
                            modifier = Modifier
                                .padding(10.dp)
                                .width(buttonWidth)
                                .aspectRatio(3f),
                            shape = RectangleShape
                        ) {
                            Text("Reset", fontSize = buttonTextFontSize)
                        }
                    }
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = {
                                showMainMenu = false
                                showDiscountMenu = !showDiscountMenu
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .width(buttonWidth)
                                .aspectRatio(3f),
                            shape = RectangleShape
                        ) {
                            Text(
                                "DISCOUNTS",
                                fontSize = 15.sp,
                                textDecoration = TextDecoration.Underline,
                                softWrap = false
                            )
                        }
                    }
                }
            }
            VerticalDivider()
            Column (modifier = Modifier.weight(1f)) {
                Text(
                    "$${String.format("%.2f", total)}",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 50.sp
                )
            }
        }
    }
}

//@Composable
//fun GreetingPreview() {
//    Libby_calculatorTheme {
//        Greeting()
//    }
//}
