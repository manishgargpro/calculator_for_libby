package com.example.libby_calculator

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libby_calculator.ui.theme.Libby_calculatorTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
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
    MAIN, EXTRA, DISCOUNT, BLE, FOO, MER, GIFT
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = remember { db.giftCardDao() }
    val scope = rememberCoroutineScope()

    var currentMenu by remember { mutableStateOf(MenuState.MAIN) }
    var showModifierDialog by remember { mutableStateOf(false) }
    var showMiscModal by remember { mutableStateOf(false) }

    var selectedItemName by remember { mutableStateOf("") }
    var selectedItemPrice by remember { mutableDoubleStateOf(0.0) }
    var currentModifierType by remember { mutableStateOf(ModifierType.NONE) }

    var total by remember { mutableDoubleStateOf(0.0) }
    val addedItems = remember { mutableStateListOf<MenuItem>() }

    // Gift Card Navigation State
    var selectedCustomerId by remember { mutableStateOf<Int?>(null) }
    val selectedCustomerWithHistory by remember(selectedCustomerId) {
        if (selectedCustomerId != null) {
            dao.getCustomerById(selectedCustomerId!!)
        } else {
            flowOf(null)
        }
    }.collectAsState(initial = null)

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
            Column(modifier = Modifier.weight(2f)) {
                when (currentMenu) {
                    MenuState.MAIN -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
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
                    }
                    MenuState.BLE -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                            BackButton { currentMenu = MenuState.MAIN }
                            MenuRow(MenuData.bleRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                            MenuRow(MenuData.bleRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                        }
                    }
                    MenuState.FOO -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                            Row {
                                BackButton { currentMenu = MenuState.MAIN }
                                MenuRow(MenuData.fooRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                            }
                            MenuRow(MenuData.fooRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                            MenuRow(MenuData.fooRow3, buttonWidth, buttonTextFontSize, ::onActionClick)
                            MenuRow(MenuData.fooRow4, buttonWidth, buttonTextFontSize, ::onActionClick)
                        }
                    }
                    MenuState.MER -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                            Row {
                                BackButton { currentMenu = MenuState.MAIN }
                                MenuRow(MenuData.merRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                            }
                            MenuRow(MenuData.merRow2, buttonWidth, buttonTextFontSize, ::onActionClick)
                            MenuRow(MenuData.merRow3, buttonWidth, buttonTextFontSize, ::onActionClick)
                        }
                    }
                    MenuState.EXTRA -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                            BackButton { currentMenu = MenuState.MAIN }
                            MenuRow(MenuData.extraRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                        }
                    }
                    MenuState.DISCOUNT -> {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                            BackButton { currentMenu = MenuState.MAIN }
                            MenuRow(MenuData.discountRow1, buttonWidth, buttonTextFontSize, ::onActionClick)
                        }
                    }
                    MenuState.GIFT -> {
                        if (selectedCustomerId == null) {
                            GiftCardListPage(
                                dao = dao,
                                onBack = { currentMenu = MenuState.MAIN },
                                onSelectCustomer = { selectedCustomerId = it }
                            )
                        } else {
                            TransactionHistoryPage(
                                customerWithHistory = selectedCustomerWithHistory,
                                dao = dao,
                                onBack = { selectedCustomerId = null }
                            )
                        }
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
                onConfirm = { amount, note ->
                    total += amount
                    addedItems.add(MenuItem(note.ifBlank { "Misc" }, amount))
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
            Row(modifier = Modifier.weight(2f).horizontalScroll(rememberScrollState())) {
                val isChargeEnabled = if (currentMenu == MenuState.GIFT) {
                    selectedCustomerId != null && total > 0 && 
                    (selectedCustomerWithHistory?.let { total <= it.remainingAmount } ?: false)
                } else {
                    total > 0
                }
                
                Column(modifier = Modifier.padding(1.dp)) {
                    Button(
                        onClick = {
                            if (currentMenu == MenuState.GIFT && selectedCustomerId != null) {
                                scope.launch {
                                    val transactionNote = if (addedItems.size == 1 && addedItems[0].name != "Misc") addedItems[0].name else null
                                    dao.insertTransaction(GiftTransaction(customerId = selectedCustomerId!!, amount = total, note = transactionNote))
                                    total = 0.0
                                    addedItems.clear()
                                }
                            } else {
                                total = 0.0
                                addedItems.clear()
                            }
                        },
                        modifier = Modifier.width(buttonWidth).aspectRatio(3f),
                        shape = RectangleShape,
                        enabled = isChargeEnabled
                    ) {
                        Text("CHARGE", fontSize = 13.sp)
                    }
                }

                MenuButton("DISCOUNTS", modifier = Modifier.width(buttonWidth), isFooter = true, isUnderlined = true) {
                    currentMenu = MenuState.DISCOUNT
                }
                MenuButton("CUSTOM", modifier = Modifier.width(buttonWidth), isFooter = true) {
                    showMiscModal = true
                }
                
                Column(modifier = Modifier.padding(1.dp)) {
                    Button(
                        onClick = { currentMenu = MenuState.GIFT },
                        modifier = Modifier.width(buttonWidth).aspectRatio(3f),
                        shape = RectangleShape
                    ) {
                        Text("GIFT CARD", fontSize = 13.sp)
                    }
                }
            }
            VerticalDivider()
            Text("$${String.format(Locale.US, "%.2f", total)}", modifier = Modifier.padding(10.dp).weight(1f), fontSize = 50.sp)
        }
    }
}

@Composable
fun GiftCardListPage(
    dao: GiftCardDao,
    onBack: () -> Unit,
    onSelectCustomer: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val customers by dao.searchCustomers(searchQuery).collectAsState(initial = emptyList())
    val allCustomers by dao.getAllCustomers().collectAsState(initial = emptyList())
    val showNewCustomerModal = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onBack, shape = RectangleShape) {
                Text("<Back")
            }
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search Name/Email/Phone") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )
            Button(onClick = { showNewCustomerModal.value = true }, shape = RectangleShape) {
                Text("Add New")
            }
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    val dateFmt = SimpleDateFormat("MMM d, yyyy", Locale.US)
                    val sb = StringBuilder()
                    sb.appendLine("Gift Card Export - ${SimpleDateFormat("MMM d, yyyy HH:mm", Locale.US).format(Date())}")
                    sb.appendLine("=".repeat(50))
                    allCustomers.forEach { item ->
                        sb.appendLine("Name: ${item.customer.name}")
                        sb.appendLine("Phone: ${item.customer.phone ?: "N/A"}")
                        sb.appendLine("Email: ${item.customer.email ?: "N/A"}")
                        sb.appendLine("Started: $${String.format(Locale.US, "%.2f", item.customer.startingAmount)} on ${dateFmt.format(Date(item.customer.createdAt))}")
                        sb.appendLine("Remaining: $${String.format(Locale.US, "%.2f", item.remainingAmount)}")
                        sb.appendLine("-".repeat(30))
                    }
                    val fileName = "gift_cards_export.txt"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val collection = MediaStore.Files.getContentUri("external")
                        val existing = context.contentResolver.query(
                            collection,
                            arrayOf(MediaStore.MediaColumns._ID),
                            "${MediaStore.MediaColumns.DISPLAY_NAME} = ? AND ${MediaStore.MediaColumns.RELATIVE_PATH} = ?",
                            arrayOf(fileName, Environment.DIRECTORY_DOCUMENTS + "/"),
                            null
                        )
                        existing?.use { cursor ->
                            while (cursor.moveToNext()) {
                                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                                context.contentResolver.delete(android.content.ContentUris.withAppendedId(collection, id), null, null)
                            }
                        }
                        val contentValues = ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                        }
                        val uri = context.contentResolver.insert(collection, contentValues)
                        uri?.let { context.contentResolver.openOutputStream(it)?.use { stream -> stream.write(sb.toString().toByteArray()) } }
                    } else {
                        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        dir.mkdirs()
                        File(dir, fileName).writeText(sb.toString())
                    }
                }
            }, shape = RectangleShape) {
                Text("Export")
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(customers) { item ->
                val contactInfo = if (!item.customer.phone.isNullOrBlank()) item.customer.phone else item.customer.email ?: ""
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectCustomer(item.customer.id) }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${item.customer.id}: ${item.customer.name}, $contactInfo")
                    Text("$${String.format(Locale.US, "%.2f", item.remainingAmount)}", fontWeight = FontWeight.Bold)
                }
                HorizontalDivider()
            }
        }
    }

    if (showNewCustomerModal.value) {
        NewCustomerModal(
            onDismiss = { showNewCustomerModal.value = false },
            onConfirm = { name, phone, email, startAmount ->
                scope.launch {
                    val newId = dao.insertCustomer(Customer(name = name, phone = phone, email = email, startingAmount = startAmount))
                    onSelectCustomer(newId.toInt())
                    showNewCustomerModal.value = false
                }
            }
        )
    }
}

@Composable
fun TransactionHistoryPage(
    customerWithHistory: CustomerWithTransactions?,
    dao: GiftCardDao,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val transactionToDelete = remember { mutableStateOf<GiftTransaction?>(null) }
    val customerToDelete = remember { mutableStateOf<Customer?>(null) }
    val customerToEdit = remember { mutableStateOf<Customer?>(null) }
    val transactionToEdit = remember { mutableStateOf<GiftTransaction?>(null) }

    if (customerWithHistory == null) return

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack, shape = RectangleShape) {
                Text("<Back")
            }
            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(
                    "${customerWithHistory.customer.id}: ${customerWithHistory.customer.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                val createdDate = SimpleDateFormat("MMM d, yyyy", Locale.US).format(Date(customerWithHistory.customer.createdAt))
                val startAmt = "$" + String.format(Locale.US, "%.2f", customerWithHistory.customer.startingAmount)
                val remaining = "$" + String.format(Locale.US, "%.2f", customerWithHistory.remainingAmount)
                val underline = SpanStyle(textDecoration = TextDecoration.Underline)
                Text(
                    buildAnnotatedString {
                        append("Started with ")
                        withStyle(underline) { append(startAmt) }
                        append(" on $createdDate")
                    },
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Text(
                    buildAnnotatedString {
                        append("Now Has ")
                        withStyle(underline) { append(remaining) }
                    },
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
            Row {
                IconButton(onClick = { customerToEdit.value = customerWithHistory.customer }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Customer")
                }
                IconButton(onClick = { customerToDelete.value = customerWithHistory.customer }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Customer", tint = Color.Red)
                }
            }
        }
        HorizontalDivider()

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(customerWithHistory.transactions.sortedByDescending { it.timestamp }) { transaction ->
                val dateStr = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US).format(Date(transaction.timestamp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val noteStr = if (!transaction.note.isNullOrBlank()) " (${transaction.note})" else ""
                    Text("$dateStr: $${String.format(Locale.US, "%.2f", transaction.amount)}$noteStr")
                    Row {
                        IconButton(onClick = { transactionToEdit.value = transaction }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Transaction")
                        }
                        IconButton(onClick = { transactionToDelete.value = transaction }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Transaction")
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }

    if (customerToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { customerToDelete.value = null },
            title = { Text("Are you sure?") },
            text = { Text("This will delete everything about ${customerToDelete.value?.name}, including their ID, name, phone/email, and entire transaction history.") },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        dao.deleteCustomer(customerToDelete.value!!)
                        customerToDelete.value = null
                        onBack()
                    }
                }) { Text("Yes") }
            },
            dismissButton = {
                Button(onClick = { customerToDelete.value = null }) { Text("No") }
            }
        )
    }

    if (transactionToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { transactionToDelete.value = null },
            title = { Text("Are you sure?") },
            text = { Text("This will delete this transaction entry.") },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        dao.deleteTransaction(transactionToDelete.value!!)
                        transactionToDelete.value = null
                    }
                }) { Text("Yes") }
            },
            dismissButton = {
                Button(onClick = { transactionToDelete.value = null }) { Text("No") }
            }
        )
    }

    if (customerToEdit.value != null) {
        EditCustomerModal(
            customer = customerToEdit.value!!,
            onDismiss = { customerToEdit.value = null },
            onConfirm = { name, phone, email, startAmount ->
                scope.launch {
                    dao.updateCustomer(customerToEdit.value!!.copy(name = name, phone = phone, email = email, startingAmount = startAmount))
                    customerToEdit.value = null
                }
            }
        )
    }

    if (transactionToEdit.value != null) {
        EditTransactionModal(
            transaction = transactionToEdit.value!!,
            onDismiss = { transactionToEdit.value = null },
            onConfirm = { newAmount, newNote ->
                scope.launch {
                    dao.updateTransaction(transactionToEdit.value!!.copy(amount = newAmount, note = newNote))
                    transactionToEdit.value = null
                }
            }
        )
    }
}

@Composable
fun NewCustomerModal(
    onDismiss: () -> Unit,
    onConfirm: (String, String?, String?, Double) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val startAmount = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth(0.9f),
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("New Customer")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        name.value = ""
                        phone.value = ""
                        email.value = ""
                        onDismiss()
                    }) { Text("Cancel") }
                    val isEnabled = name.value.isNotBlank() && (phone.value.isNotBlank() || email.value.isNotBlank())
                    Button(
                        onClick = { onConfirm(name.value, phone.value.ifBlank { null }, email.value.ifBlank { null }, startAmount.value.toDoubleOrNull() ?: 0.0) },
                        enabled = isEnabled
                    ) { Text("Add") }
                }
            }
        },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name (Required)") }, keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words), modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone.value, onValueChange = { phone.value = it }, label = { Text("Phone Number") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = startAmount.value,
                        onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*$"))) startAmount.value = it },
                        label = { Text("Starting Balance") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun EditCustomerModal(
    customer: Customer,
    onDismiss: () -> Unit,
    onConfirm: (String, String?, String?, Double) -> Unit
) {
    val name = remember { mutableStateOf(customer.name) }
    val phone = remember { mutableStateOf(customer.phone ?: "") }
    val email = remember { mutableStateOf(customer.email ?: "") }
    val startAmount = remember { mutableStateOf(customer.startingAmount.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth(0.9f),
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Edit Customer")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onDismiss) { Text("Cancel") }
                    val isEnabled = name.value.isNotBlank() && (phone.value.isNotBlank() || email.value.isNotBlank())
                    Button(
                        onClick = { onConfirm(name.value, phone.value.ifBlank { null }, email.value.ifBlank { null }, startAmount.value.toDoubleOrNull() ?: 0.0) },
                        enabled = isEnabled
                    ) { Text("Save") }
                }
            }
        },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name (Required)") }, keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words), modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone.value, onValueChange = { phone.value = it }, label = { Text("Phone Number") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = startAmount.value,
                        onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*$"))) startAmount.value = it },
                        label = { Text("Starting Balance") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun EditTransactionModal(
    transaction: GiftTransaction,
    onDismiss: () -> Unit,
    onConfirm: (Double, String?) -> Unit
) {
    val amount = remember { mutableStateOf(transaction.amount.toString()) }
    val note = remember { mutableStateOf(transaction.note ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth(0.9f),
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Edit Transaction")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onDismiss) { Text("Cancel") }
                    Button(onClick = { onConfirm(amount.value.toDoubleOrNull() ?: 0.0, note.value.ifBlank { null }) }) { Text("Save") }
                }
            }
        },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = amount.value,
                    onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*$"))) amount.value = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = note.value,
                    onValueChange = { note.value = it },
                    label = { Text("Note (optional)") },
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
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
            MenuButton(button.text, color = button.color, isUnderlined = button.isUnderlined, width = width, aspectRatio = 1.5f, fontSize = fontSize) {
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
            modifier = modifier.width(width).aspectRatio(if (isFooter) 3f else aspectRatio),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text(
                text = text,
                fontSize = if (isFooter) 13.sp else fontSize,
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
        if (modifierType in setOf(ModifierType.BASE, ModifierType.EXTRA, ModifierType.NITRO, ModifierType.SHORT, ModifierType.SHRIMP, ModifierType.BULK_COFFEE, ModifierType.HONEY)) {
            MenuData.modifiers[ModifierType.BASE]?.let { if (modifierType != ModifierType.BASE) list.addAll(it) }
        }
        list.distinct()
    }
    val checkedStates = remember(options) { mutableStateListOf<Boolean>().apply { repeat(options.size) { add(false) } } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                val priceLabel = if (itemPrice != 0.0) "($" + String.format(Locale.US, "%.2f", itemPrice) + ")" else ""
                Text("$itemName $priceLabel Modifiers")
                Button(onClick = onDismiss) { Text("X") }
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                options.forEachIndexed { index, label ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checkedStates[index],
                            onCheckedChange = { isChecked ->
                                checkedStates[index] = isChecked
                            },
                        )
                        Text(label)
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
fun MiscDialog(onDismiss: () -> Unit, onConfirm: (Double, String) -> Unit) {
    val vState = remember { mutableStateOf("") }
    val noteState = remember { mutableStateOf("") }
    val eState = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth(0.9f),
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Misc")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onDismiss) { Text("Cancel") }
                    Button(onClick = {
                        val amount = vState.value.toDoubleOrNull()
                        if (amount != null && amount > 0) {
                            onConfirm(amount, noteState.value)
                        } else {
                            eState.value = true
                        }
                    }) { Text("OK") }
                }
            }
        },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                    modifier = Modifier.focusRequester(focusRequester).weight(1f)
                )
                OutlinedTextField(
                    value = noteState.value,
                    onValueChange = { noteState.value = it },
                    label = { Text("Note (optional)") },
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
    LaunchedEffect(Unit) { delay(100); focusRequester.requestFocus() }
}
