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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libby_calculator.ui.theme.Libby_calculatorTheme
import kotlinx.coroutines.delay
import java.util.Locale

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
        CalculatorScreen(modifier = Modifier.padding(innerPadding))
    }
}

enum class MenuState {
    MAIN, EXTRA, DISCOUNT, BLE, FOO, MER
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var currentMenu by remember { mutableStateOf(MenuState.MAIN) }
    var showModifierDialog by remember { mutableStateOf(false) }
    var showMiscModal by remember { mutableStateOf(false) }

    var selectedItemName by remember { mutableStateOf("") }
    var selectedItemPrice by remember { mutableDoubleStateOf(0.0) }
    var currentModifierType by remember { mutableStateOf(ModifierType.NONE) }

    var total by remember { mutableDoubleStateOf(0.0) }
    val addedItems = remember { mutableStateListOf<MenuItem>() }

    val buttonWidth = 130.dp
    val buttonTextFontSize = 25.sp

    fun onActionClick(button: ActionButton) {
        if (button.isDirectAdd) {
            val item = MenuItem(button.text, button.price)
            addedItems.add(item)
            total += item.price
        } else {
            selectedItemName = button.text
            selectedItemPrice = button.price
            currentModifierType = button.modifierType
            showModifierDialog = true
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(5f)) {
            // Menu Section
            Column(modifier = Modifier.weight(2f).verticalScroll(rememberScrollState())) {
                when (currentMenu) {
                    MenuState.MAIN -> {
                        MenuRow(MenuData.mainRow1, buttonWidth, buttonTextFontSize, ::onActionClick) {
                            MenuButton("EXTRA", modifier = Modifier, color = Color(0xFF6ABCB8), isUnderlined = true) { currentMenu = MenuState.EXTRA }
                        }
                        MenuRow(MenuData.mainRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.mainRow3, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.mainRow4, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.mainRow5, buttonWidth, buttonTextFontSize, ::onActionClick)
                        Row {
                            MenuButton("BLE", modifier = Modifier, color = Color(0xFF2BBDAB), isUnderlined = true) { currentMenu = MenuState.BLE }
                            MenuButton("FOO", modifier = Modifier, color = Color(0xFFF00B6A), isUnderlined = true) { currentMenu = MenuState.FOO }
                            MenuButton("MER", modifier = Modifier, color = Color(0xFF24157A), isUnderlined = true) { currentMenu = MenuState.MER }
                            MenuButton("ITAL", modifier = Modifier, color = Color(0xFF419EBF)) {
                                onActionClick(ActionButton("ITAL", 12.50, ModifierType.BASE))
                            }
                        }
                    }
                    MenuState.BLE -> {
                        BackButton { currentMenu = MenuState.MAIN }
                        MenuRow(MenuData.bleRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.bleRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                    }
                    MenuState.FOO -> {
                        BackButton { currentMenu = MenuState.MAIN }
                        MenuRow(MenuData.fooRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.fooRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.fooRow3, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.fooRow4, buttonWidth, buttonTextFontSize, ::onActionClick)
                    }
                    MenuState.MER -> {
                        BackButton { currentMenu = MenuState.MAIN }
                        MenuRow(MenuData.merRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.merRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                        MenuRow(MenuData.merRow3, buttonWidth, buttonTextFontSize, ::onActionClick)
                    }
                    MenuState.EXTRA -> {
                        BackButton { currentMenu = MenuState.MAIN }
                        MenuRow(MenuData.extraRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                    }
                    MenuState.DISCOUNT -> {
                        BackButton { currentMenu = MenuState.MAIN }
                        MenuRow(MenuData.discountRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                    }
                }
            }

            VerticalDivider()

            // Added Items Section
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text("Added Items:", fontSize = buttonTextFontSize)
                LazyColumn {
                    itemsIndexed(addedItems) { index, item ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("${item.name} - $${String.format(Locale.US, "%.2f", item.price)}", fontSize = 20.sp)
                                item.options.forEach { option ->
                                    Text("  - $option", fontSize = 15.sp)
                                }
                            }
                            Button(onClick = {
                                total -= item.price
                                addedItems.removeAt(index)
                            }) { Text("X") }
                        }
                    }
                }
            }
        }

        if (showModifierDialog) {
            ModifierDialog(
                itemName = selectedItemName,
                itemPrice = selectedItemPrice,
                modifierType = currentModifierType,
                onDismiss = { showModifierDialog = false },
                onConfirm = { name, price, options ->
                    addedItems.add(MenuItem(name, price, options))
                    total += price
                    showModifierDialog = false
                }
            )
        }

        if (showMiscModal) {
            MiscDialog(
                onDismiss = { showMiscModal = false },
                onConfirm = { amount ->
                    total += amount
                    addedItems.add(MenuItem("Misc", amount))
                    showMiscModal = false
                }
            )
        }

        HorizontalDivider()

        // Footer Section
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(2f)) {
                MenuButton("RESET", modifier = Modifier.padding(10.dp).width(buttonWidth), isFooter = true) {
                    total = 0.0; addedItems.clear()
                }
                MenuButton("DISCOUNTS", modifier = Modifier.padding(10.dp).width(buttonWidth), isFooter = true, isUnderlined = true) {
                    currentMenu = MenuState.DISCOUNT
                }
                MenuButton("MISC", modifier = Modifier.padding(10.dp).width(buttonWidth), isFooter = true) {
                    showMiscModal = true
                }
            }
            VerticalDivider()
            Text("$${String.format(Locale.US, "%.2f", total)}", modifier = Modifier.padding(10.dp).weight(1f), fontSize = 50.sp)
        }
    }
}

@Composable
fun MenuRow(
    buttons: List<ActionButton>,
    width: androidx.compose.ui.unit.Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    onClick: (ActionButton) -> Unit,
    extraContent: @Composable () -> Unit = {}
) {
    Row {
        buttons.forEach { button ->
            MenuButton(button.text, modifier = Modifier, color = button.color, isUnderlined = button.isUnderlined, width = width, aspectRatio = 1.5f, fontSize = fontSize) {
                onClick(button)
            }
        }
        extraContent()
    }
}

@Composable
fun MenuButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    isUnderlined: Boolean = false,
    width: androidx.compose.ui.unit.Dp = 130.dp,
    aspectRatio: Float = 1.5f,
    fontSize: androidx.compose.ui.unit.TextUnit = 25.sp,
    isFooter: Boolean = false,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.padding(1.dp)) {
        Button(
            onClick = onClick,
            modifier = modifier.width(width).aspectRatio(if (isFooter) aspectRatio * 2 else aspectRatio),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
                softWrap = !isUnderlined
            )
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Row {
        MenuButton("<Back", onClick = onClick)
    }
}

@Composable
fun ModifierDialog(
    itemName: String,
    itemPrice: Double,
    modifierType: ModifierType,
    onDismiss: () -> Unit,
    onConfirm: (String, Double, List<String>) -> Unit
) {
    val options = remember(modifierType) {
        val list = mutableListOf<String>()
        MenuData.modifiers[modifierType]?.let { list.addAll(it) }
        if (modifierType in listOf(ModifierType.BASE, ModifierType.EXTRA, ModifierType.NITRO, ModifierType.SHORT, ModifierType.SHRIMP)) {
            MenuData.modifiers[ModifierType.BASE]?.let { if (modifierType != ModifierType.BASE) list.addAll(it) }
        }
        list.distinct()
    }
    val checkedStates = remember(options) { mutableStateListOf<Boolean>().apply { repeat(options.size) { add(false) } } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("$itemName ${if (itemPrice != 0.0) "($${String.format(Locale.US, "%.2f", itemPrice)})" else ""} Modifiers")
                Button(onClick = onDismiss) { Text("X") }
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                val anyRefill = options.indices.any { options[it].contains("Refill*") && checkedStates[it] }
                options.forEachIndexed { index, text ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val isEnabled = !anyRefill || text.contains("Refill*")
                        Checkbox(
                            checked = checkedStates[index],
                            onCheckedChange = { isChecked ->
                                checkedStates[index] = isChecked
                                if (isChecked && text.contains("Refill*")) {
                                    options.indices.forEach { if (!options[it].contains("Refill*")) checkedStates[it] = false }
                                }
                            },
                            enabled = isEnabled
                        )
                        Text(text, color = if (text.contains("Refill*")) Color.Blue else if (isEnabled) Color.Unspecified else Color.Gray)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val selected = options.filterIndexed { index, _ -> checkedStates[index] }
                var finalPrice = itemPrice
                selected.forEach { opt ->
                    val opIdx = opt.lastIndexOfAny(charArrayOf('+', '-'))
                    if (opIdx != -1) {
                        val amountStr = opt.substring(opIdx).trim()
                        finalPrice += amountStr.toDoubleOrNull() ?: 0.0
                    }
                }
                onConfirm(itemName, finalPrice, selected)
            }) { Text("OK") }
        }
    )
}

@Composable
fun MiscDialog(onDismiss: () -> Unit, onConfirm: (Double) -> Unit) {
    val vState = remember { mutableStateOf("") }
    val eState = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Misc") },
        text = {
            OutlinedTextField(
                value = vState.value,
                onValueChange = { newValue -> 
                    if (newValue.matches(Regex("^\\d*\\.?\\d*$"))) { 
                        vState.value = newValue 
                        eState.value = false 
                    } 
                },
                label = { Text("Custom dollar amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = eState.value,
                supportingText = { if (eState.value) Text("Invalid input") },
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        confirmButton = {
            Button(onClick = {
                val amount = vState.value.toDoubleOrNull()
                if (amount != null && amount > 0) {
                    onConfirm(amount)
                } else {
                    eState.value = true
                }
            }) { Text("OK") }
        },
        dismissButton = { Button(onClick = onDismiss) { Text("Cancel") } }
    )
    LaunchedEffect(Unit) { delay(100); focusRequester.requestFocus() }
}
