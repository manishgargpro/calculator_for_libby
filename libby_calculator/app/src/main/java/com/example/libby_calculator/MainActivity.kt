package com.example.libby_calculator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import kotlinx.coroutines.delay
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
import androidx.compose.runtime.LaunchedEffect

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // Menu booleans
    var showMainMenu by remember { mutableStateOf(true) }
    var showExtraMenu by remember { mutableStateOf(false) }
    var showDiscountMenu by remember { mutableStateOf(false) }
    var showBleMenu by remember { mutableStateOf(false) }
    var showFooMenu by remember { mutableStateOf(false) }
    var showMerMenu by remember { mutableStateOf(false) }
    // End menu booleans

    // Modal booleans
    var showPh1Modal by remember { mutableStateOf(false) }
    var showMiscModal by remember { mutableStateOf(false) }
    // End modal booleans

    // Modifier booleans
    var baseModifier by remember { mutableStateOf(false) }
    var extraModifier by remember { mutableStateOf(false) }
    var shortModifier by remember { mutableStateOf(false) }
    var nitroModifier by remember { mutableStateOf(false) }
    var shrimpModifier by remember { mutableStateOf(false) }
    var grabNGoModifier by remember { mutableStateOf(false) }
    var fancyCroissantModifier by remember { mutableStateOf(false) }
    var sandwichModifier by remember { mutableStateOf(false) }
    var oatmealModifier by remember { mutableStateOf(false) }
    var houseTeaModifier by remember { mutableStateOf(false) }
    var blackTeaModifier by remember { mutableStateOf(false) }
    var herbalTeaModifier by remember { mutableStateOf(false) }
    var greenTeaModifier by remember { mutableStateOf(false) }
    var specTeaModifier by remember { mutableStateOf(false) }
    var bagelModifier by remember { mutableStateOf(false) }
    // End modifier booleans

    var itemTotal by remember { mutableDoubleStateOf(0.00) }
    var total by remember { mutableDoubleStateOf(0.00) }
    val addedItems = remember { mutableStateListOf<MenuItem>() }
    var itemName by remember { mutableStateOf("") }
    var miscTotal by remember { mutableStateOf("") }
    var miscError by remember { mutableStateOf(false) }

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
        ActionButton("COFFE", { itemButtonAction("COFFEE", 0.00); extraModifier = true }, Color(0xFF0F5FB7)),
        ActionButton("OLE", { itemButtonAction("OLE", 4.00); baseModifier = true }, Color(0xFF0F5FB7)),
        ActionButton("CB", { itemButtonAction("CB", 5.00); nitroModifier = true }, Color(0xFF0F5FB7))
    )
    val buttonsRow2 = listOf(
        ActionButton("L CAP", { itemButtonAction("L CAP", 5.00); baseModifier = true }, Color(0xFFBB5918)),
        ActionButton("A", { itemButtonAction("A", 4.00); baseModifier = true }, Color(0xFFBB5918)),
        ActionButton("FL", { itemButtonAction("FL", 5.50); baseModifier = true }, Color(0xFFBB5918)),
        ActionButton("CRL", { itemButtonAction("CRL", 5.75); baseModifier = true }, Color(0xFFBB5918))
    )
    val buttonsRow3 = listOf(
        ActionButton("M WM", { itemButtonAction("M WM", 5.75); baseModifier = true }, Color(0xFF6F1C0D)),
        ActionButton("FW", { itemButtonAction("FW", 5.25); shortModifier = true }, Color(0xFF6F1C0D)),
        ActionButton("KW", { itemButtonAction("KW", 5.50); baseModifier = true }, Color(0xFF6F1C0D)),
        ActionButton("WELL", { itemButtonAction("WELL", 20.00); baseModifier = true }, Color(0xFF126583)) // NEED PRICE
    )
    val buttonsRow4 = listOf(
        ActionButton("ESM", { itemButtonAction("ESM", 3.75); shortModifier = true }, Color(0xFFF5A724)),
        ActionButton("ESP", { itemButtonAction("ESP", 3.25); shortModifier = true }, Color(0xFFF5A724)),
        ActionButton("HC", { itemButtonAction("HC", 5.00); shrimpModifier = true }, Color(0xFFF5A724)),
        ActionButton("COLD", { itemButtonAction("COLD", 20.00); baseModifier = true }, Color(0xFF6ABCB8)) // NEED PRICE
    )
    val buttonsRow5 = listOf(
        ActionButton("TEA", { itemButtonAction("TEA", 3.50); baseModifier = true }, Color(0xFF7DD675)),
        ActionButton("TEA L", { itemButtonAction("TEA L", 5.00); baseModifier = true }, Color(0xFF7DD675)),
        ActionButton("CHAI", { itemButtonAction("CHAI", 5.25); baseModifier = true }, Color(0xFF7DD675)),
        ActionButton("GTL", { itemButtonAction("GTL", 5.75); baseModifier = true }, Color(0xFF7DD675))
    )
    // End main menu buttons

    // BLE menu buttons
    val bleButtonsRow1 = listOf(
        ActionButton("ESPB", { itemButtonAction("ESPB", 6.00); baseModifier = true }, Color(0xFFB9710B)),
        ActionButton("FLB", { itemButtonAction("FLB", 6.25); baseModifier = true }, Color(0xFFB9710B)),
        ActionButton("CRB", { itemButtonAction("CRB", 6.25); baseModifier = true }, Color(0xFFB9710B)),
        ActionButton("MB", { itemButtonAction("MB", 6.25); baseModifier = true }, Color(0xFFB9710B))
    )
    val bleButtonsRow2 = listOf(
        ActionButton("TEA B", { itemButtonAction("TEA B", 6.25); baseModifier = true }, Color(0xFF7DD675)),
        ActionButton("CHAIB", { itemButtonAction("CHAIB", 6.25); baseModifier = true }, Color(0xFF7DD675)),
        ActionButton("SM", { itemButtonAction2("SM", 7.50); baseModifier = true }, Color(0xFF6ABCB8)),
        ActionButton("CRBL", { itemButtonAction("CRBL", 5.75); baseModifier = true }, Color(0xFF48638A))
    )
    // End BLE menu buttons

    // FOO menu buttons
    val fooButtonsRow1 = listOf(
        ActionButton("MUFF", { itemButtonAction2("MUFF", 5.25) }, Color(0xFFF00B6A)),
        ActionButton("FCRO", { itemButtonAction("FCRO", 0.00); fancyCroissantModifier = true }, Color(0xFFF00B6A)),
        ActionButton("PLAIN", { itemButtonAction2("PLAIN", 5.00) }, Color(0xFFF00B6A))
    )
    val fooButtonsRow2 = listOf(
        ActionButton("BARS", { itemButtonAction2("BARS", 7.65) }, Color(0xFFD62027)),
        ActionButton("SCONE", { itemButtonAction2("SCONE", 5.25) }, Color(0xFFD62027)),
        ActionButton("POP", { itemButtonAction2("POP", 5.75) }, Color(0xFFD62027)),
        ActionButton("COOKI", { itemButtonAction2("COOKI", 4.80) }, Color(0xFFD62027))
    )
    val fooButtonsRow3 = listOf(
        ActionButton("LOAF", { itemButtonAction2("LOAF", 5.00) }, Color(0xFF91132F)),
        ActionButton("PAR", { itemButtonAction2("PAR", 8.25) }, Color(0xFFF5A724)),
        ActionButton("G&G", { itemButtonAction("G&G", 0.00); grabNGoModifier = true }, Color(0xFFFB8125)),
        ActionButton("BALLZ", { itemButtonAction2("BALLZ", 4.80) }, Color(0xFFBC1441)) // NEED PRICE
    )
    val fooButtonsRow4 = listOf(
        ActionButton("SAMMI", { itemButtonAction("SAMMI", 0.00); sandwichModifier = true }, Color(0xFF0F3D7F)),
        ActionButton("BB", { itemButtonAction("BB", 0.00); bagelModifier = true }, Color(0xFF24157A)),
        ActionButton("EMPAN", { itemButtonAction2("EMPAN", 4.80) }, Color(0xFF2BBDAB)), // NEED PRICE
        ActionButton("OAT", { itemButtonAction("OAT", 6.50); oatmealModifier = true }, Color(0xFF2BBDAB))
    )
    // End FOO menu buttons

    // MER menu buttons
    val merButtonsRow1 = listOf(
        ActionButton("T SHI", { itemButtonAction2("T SHI", 12.00) }, Color(0xFF0E5672)), // NEED PRICE
        ActionButton("MUG", { itemButtonAction2("MUG", 15.30) }, Color(0xFF48638A)) // NEED PRICE
    )
    val merButtonsRow2 = listOf(
        ActionButton("BULK", { itemButtonAction2("BULK", 20.45) }, Color(0xFF6F1C0D)), // NEED PRICE
        ActionButton("BLKT", { itemButtonAction("BLKT", 0.00); blackTeaModifier = true }, Color(0xFFA70B10)),
        ActionButton("HRBT", { itemButtonAction("HRBT", 0.00); herbalTeaModifier = true }, Color(0xFF91132F)),
        ActionButton("HONEY", { itemButtonAction2("HONEY", 103.67) }, Color(0xFFF5A724)) // NEED PRICE
    )
    val merButtonsRow3 = listOf(
        ActionButton("SPTEA", { itemButtonAction("SPTEA", 0.00); specTeaModifier = true }, Color(0xFFBC1441)),
        ActionButton("GRT", { itemButtonAction("GRT", 0.00); greenTeaModifier = true }, Color(0xFF2AAD65))
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

    // Base modifier options
    val checkboxOptions = remember { listOf(
        "Almond +1.00",
        "Macadamia +1.00",
        "Soy +1.00",
        "Oat +1.00",
        "Breve +1.00",
        "Heavy Cream +2.00",
        "Extra Milk +1.00",
        "Espresso Shot +1.00",
        "Syrup +1.00",
        "Honey +1.00"
    ) }
    val checkedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false, false, false) }
    // End base modifier options

    // Coffee base modifier options
    val coffeeBaseCheckboxOptions = remember { listOf(
        "Guppy +0.00",
        "Trout +1.00",
        "Whaley +2.00"
    ) }
    val coffeeBaseCheckedStates = remember { mutableStateListOf(false, false, false) }
    // End coffee base modifier options

    // Coffee short modifier options
    val coffeeShortCheckboxOptions = remember { listOf(
        "Guppy +0.00",
        "Trout +1.00"
    ) }
    val coffeeShortCheckedStates = remember { mutableStateListOf(false, false) }
    // End coffee short modifier options

    // Coffee extra modifier options
    val coffeeExtraCheckboxOptions = remember { listOf(
        "Guppy +3.50",
        "Trout +4.50",
        "Whaley +5.50",
        "Guppy Refill +1.75",
        "Trout Refill +2.25",
        "Whaley Refill +2.75",
        "Slow Pour +4.50"
    ) }
    val coffeeExtraCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    // End coffee extra modifier options

    // Coffee nitro modifier options
    val coffeeNitroCheckboxOptions = remember { listOf(
        "Guppy +0.00",
        "Trout +1.25",
        "Whaley +2.50"
    ) }
    val coffeeNitroCheckedStates = remember { mutableStateListOf(false, false, false) }
    // End coffee nitro modifier options

    // Hot chocolate shrimp modifier options
    val hotChocolateShrimpCheckboxOptions = remember { listOf(
        "Shrimp -1.00",
        "Guppy +0.00",
        "Trout +1.00",
        "Whaley +2.00"
    ) }
    val hotChocolateShrimpCheckedStates = remember { mutableStateListOf(false, false, false, false) }
    // End hot chocolate shrimp modifier options

    // Grab N Go modifier options
    val grabNGoCheckboxOptions = remember { listOf(
        "PH Water +3.25",
        "Smart Water +5.00",
        "Perrier +3.25",
        "Topo-Chico +3.75",
        "CNWater +3.75",
        "OJ +3.50",
        "Mama Chia +4.00",
        "Better Booch +4.25",
        "Synergy Booch +5.25",
        "CBD +8.25",
        "Yerba Mate +4.25",
        "Cup H20 +1.00"
    ) }
    val grabNGoCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false, false, false, false, false) }
    // End Grab N Go modifier options

    // Fancy croissant modifier options
    val fancyCroissantCheckboxOptions = remember { listOf(
        "Chorizo +5.75",
        "Jalapeno +5.75",
        "Chocolate +5.75",
        "Cheddar +5.75",
        "Almond +5.75",
        "Turkey +6.25",
        "Ham & Cheese +6.25"
        ) }
    val fancyCroissantCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    // End fancy croissant modifier options

    // Sandwich modifier options
    val sandwichCheckboxOptions = remember { listOf(
        "Ham Sammie +13.25",
        "Turk Sammie +13.25",
        "Classico +14.25",
        "Guac This Way +14.25",
        "Millennial +14.25",
        "Caff Caprese +14.25",
        "Add Cheese Bagel +1.50",
        "Add Fancy Croiss +2.75",
    ) }
    val sandwichCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }
    // End sandwich modifier options

    // Oatmeal modifier options
    val oatmealCheckboxOptions = remember { listOf(
        "Add ons +0.75",
        "Fresh Berries +2.75"
    ) }
    val oatmealCheckedStates = remember { mutableStateListOf(false, false) }
    // End oatmeal modifier options

    // House tea modifier options
    val houseTeaCheckboxOptions = remember { listOf(
        "Kermint +4.25",
        "Good Karma +5.50",
        "Tea Rex +5.50",
        "Frankie Goes to Hollywood +6.00",
        "Virgin Chai +5.25",
        "Aphroditea +5.25",
        "Auntea Flow +5.25",
        "To Breathe or Not to Breathe +5.75",
        "Good Vibrations +5.75",
        "Good Night Moon +6.00",
        "Pug in a Rug +5.25",
        "Custom Blend +6.00",
        "Lady Grey +5.50"
    ) }
    val houseTeaCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false, false, false, false, false, false) }
    // End house tea modifier options

    // Black tea modifier options
    val blackTeaCheckboxOptions = remember { listOf(
        "Masala Chai +5.50",
        "Earl Grey +5.00",
        "English Breakfast +5.00",
        "India Black +5.00",
        "Irish Breakfast +5.00",
        "Assam +5.00",
        "Darjeeling +5.50",
        "Lapang Souchong +5.00"
    ) }
    val blackTeaCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }
    // End black tea modifier options

    // Herbal tea modifier options
    val herbalTeaCheckboxOptions = remember { listOf(
        "Berry Patch +4.50",
        "Chamomile +4.00",
        "Lavender +5.50",
        "Lemon Balm +3.50",
        "Mango Flip +4.50",
        "Rooibos +4.00",
        "Stevia +4.00"
    ) }
    val herbalTeaCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    // End herbal tea modifier options

    // Green tea modifier options
    val greenTeaCheckboxOptions = remember { listOf(
        "Gen Mai Cha +5.00",
        "Gunpowder +5.00",
        "Jasmine +5.25",
        "Moroccan Mint +4.75",
        "Sencha +5.00",
        "Vietnamese +5.25",
        "Matcha +9.00",
        ) }
    val greenTeaCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    // End green tea modifier options

    // Special tea modifier options
    val specTeaCheckboxOptions = remember { listOf(
        "Yerba Matte +4.25",
        "Kukicha Twig +4.00",
        "Peach White +4.75"
    ) }
    val specTeaCheckedStates = remember { mutableStateListOf(false, false, false) }
    // End special tea modifier options

    // Bagel modifier options
    val bagelCheckboxOptions = remember { listOf(
        "Classic Bagel +4.00",
        "Cheesy Bagel +5.50",
        "GF Bagel +5.50",
        "CC +1.75",
        "Peanut Butter +1.75",
        "Banana +1.75",
        "Butter +1.00",
        "Avocado +2.50",
        "Avo x2 +5.00",
        "Slice Cheese +1.25",
        "Salami +2.50",
        "Mozzarella +2.50",
        "1 Egg +3.00",
        "2 Egg +5.50",
        "Tomato +2.25",
        "Pepper +2.25",
        "Turkey +2.50",
        "Everything Seasoning +0.75"
    ) }
    val bagelCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false) }
    // End bagel modifier options

    val turnOffAllCheckboxes = {
        showPh1Modal = false
        baseModifier = false
        extraModifier = false
        shortModifier = false
        nitroModifier = false
        shrimpModifier = false
        grabNGoModifier = false
        fancyCroissantModifier = false
        sandwichModifier = false
        oatmealModifier = false
        houseTeaModifier = false
        blackTeaModifier = false
        herbalTeaModifier = false
        greenTeaModifier = false
        specTeaModifier = false
        bagelModifier = false
    }

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
            val finalCheckboxList = remember { mutableStateListOf<String>()}
            val finalCheckedStates = remember { mutableStateListOf<Boolean>()}
            LaunchedEffect(
                baseModifier,
                extraModifier,
                nitroModifier,
                shortModifier,
                shrimpModifier,
                grabNGoModifier,
                fancyCroissantModifier,
                sandwichModifier,
                oatmealModifier,
                houseTeaModifier,
                blackTeaModifier,
                herbalTeaModifier,
                greenTeaModifier,
                specTeaModifier,
                bagelModifier
            ) {
                finalCheckboxList.clear()
                finalCheckedStates.clear()
                when {
                    baseModifier -> {
                        finalCheckboxList.addAll(coffeeBaseCheckboxOptions + checkboxOptions)
                        finalCheckedStates.addAll(coffeeBaseCheckedStates + checkedStates)
                    }
                    extraModifier -> {
                        finalCheckboxList.addAll(coffeeExtraCheckboxOptions + checkboxOptions)
                        finalCheckedStates.addAll(coffeeExtraCheckedStates + checkedStates)
                    }
                    nitroModifier -> {
                        finalCheckboxList.addAll(coffeeNitroCheckboxOptions + checkboxOptions)
                        finalCheckedStates.addAll(coffeeNitroCheckedStates + checkedStates)
                    }
                    shortModifier -> {
                        finalCheckboxList.addAll(coffeeShortCheckboxOptions + checkboxOptions)
                        finalCheckedStates.addAll(coffeeShortCheckedStates + checkedStates)
                    }
                    shrimpModifier -> {
                        finalCheckboxList.addAll(hotChocolateShrimpCheckboxOptions + checkboxOptions)
                        finalCheckedStates.addAll(hotChocolateShrimpCheckedStates + checkedStates)
                    }
                    grabNGoModifier -> {
                        finalCheckboxList.addAll(grabNGoCheckboxOptions)
                        finalCheckedStates.addAll(grabNGoCheckedStates)
                    }
                    fancyCroissantModifier -> {
                        finalCheckboxList.addAll(fancyCroissantCheckboxOptions)
                        finalCheckedStates.addAll(fancyCroissantCheckedStates)
                    }
                    sandwichModifier -> {
                        finalCheckboxList.addAll(sandwichCheckboxOptions)
                        finalCheckedStates.addAll(sandwichCheckedStates)
                    }
                    oatmealModifier -> {
                        finalCheckboxList.addAll(oatmealCheckboxOptions)
                        finalCheckedStates.addAll(oatmealCheckedStates)
                    }
                    houseTeaModifier -> {
                        finalCheckboxList.addAll(houseTeaCheckboxOptions)
                        finalCheckedStates.addAll(houseTeaCheckedStates)
                    }
                    blackTeaModifier -> {
                        finalCheckboxList.addAll(blackTeaCheckboxOptions)
                        finalCheckedStates.addAll(blackTeaCheckedStates)
                    }
                    herbalTeaModifier -> {
                        finalCheckboxList.addAll(herbalTeaCheckboxOptions)
                        finalCheckedStates.addAll(herbalTeaCheckedStates)
                    }
                    greenTeaModifier -> {
                        finalCheckboxList.addAll(greenTeaCheckboxOptions)
                        finalCheckedStates.addAll(greenTeaCheckedStates)
                    }
                    specTeaModifier -> {
                        finalCheckboxList.addAll(specTeaCheckboxOptions)
                        finalCheckedStates.addAll(specTeaCheckedStates)
                    }
                    bagelModifier -> {
                        finalCheckboxList.addAll(bagelCheckboxOptions)
                        finalCheckedStates.addAll(bagelCheckedStates)
                    }
                }
            }
            AlertDialog(
                onDismissRequest = turnOffAllCheckboxes,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$itemName ${ if (itemTotal != 0.00) "($${String.format("%.2f", itemTotal)})" else "" } Modifiers")
                        Button(onClick = turnOffAllCheckboxes) {
                            Text("X")
                        }
                    }
                },
                text = {
                    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                        if (finalCheckboxList.isNotEmpty() && finalCheckboxList.size == finalCheckedStates.size) {
                            val anyRefillOptionSelected = finalCheckboxList.indices.any { index ->
                                finalCheckboxList[index].contains("Refill") && finalCheckedStates[index]
                            }
                            finalCheckboxList.forEachIndexed { index, text ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val isEnabled = if (anyRefillOptionSelected) {
                                        text.contains("Refill")
                                    } else {
                                        true
                                    }
                                    Checkbox(
                                        checked = finalCheckedStates[index],
                                        onCheckedChange = { isChecked ->
                                            finalCheckedStates[index] = isChecked
                                            if (isChecked && text.contains("Refill")) {
                                                finalCheckedStates.indices.forEach { otherIndex ->
                                                    if (!finalCheckboxList[otherIndex].contains("Refill")) {
                                                        finalCheckedStates[otherIndex] = false
                                                    }
                                                }
                                            }
                                        },
                                        enabled = isEnabled
                                    )
                                    Text(
                                        text = text,
                                        color = when {
                                            text.contains("Refill") -> Color.Blue
                                            isEnabled -> Color.Unspecified
                                            else -> Color.Gray
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val selectedOptions = finalCheckboxList.filterIndexed { index, _ -> finalCheckedStates[index] }
                        var ph1Price = itemTotal
//                        val refillOption = selectedOptions.firstOrNull { it.contains("Refill") }
//                        if (refillOption != null) {
//                            val numberString = refillOption.substringAfterLast(" ")
//                            ph1Price = numberString.toDoubleOrNull() ?: itemTotal // Set price from refill, or default
//                        } else {
                        selectedOptions.forEach { option ->
                            val operatorIndex = option.lastIndexOfAny(charArrayOf('+', '-'))
                            if (operatorIndex != -1) {
                                val numberString = option.substring(operatorIndex).trim()
                                ph1Price += numberString.toDoubleOrNull() ?: 0.0
                            }
                        }
//                        }
                        val ph1Item = MenuItem(name = itemName, price = ph1Price, options = selectedOptions)
                        addedItems.add(ph1Item)
                        total += ph1Item.price
                        itemTotal = 0.00
                        itemName = ""
                        showPh1Modal = false
                        turnOffAllCheckboxes()
                    }) {
                        Text("OK")
                    }
                }
            )
        }
        if (showMiscModal) {
            val focusRequester = remember { FocusRequester() }
            AlertDialog(
                onDismissRequest = {
                    showMiscModal = false
                    miscTotal = ""
                    miscError = false
                },
                title = { Text("Misc") },
                text = {
                    OutlinedTextField(
                        value = miscTotal,
                        onValueChange = { newValue ->
                            if (newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                                miscTotal = newValue
                            }
                            if (miscError) miscError = false
                        },
                        label = { Text("Custom dollar amount") },
//                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = miscError,
                        supportingText = {
                            if (miscError) {
                                Text("Invalid input")
                            }
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        val amountToAdd = miscTotal.toDoubleOrNull()
                        if (amountToAdd != null && amountToAdd > 0) {
                            total += amountToAdd
                            val customItem = MenuItem(name = "Misc", price = amountToAdd)
                            addedItems.add(customItem)
                            showMiscModal = false
                            miscTotal = ""
                            miscError = false
                        } else {
                            miscError = true
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showMiscModal = false
                        miscTotal = ""
                        miscError = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
            LaunchedEffect(Unit) {
                delay(100)
                focusRequester.requestFocus()
            }
        }

        HorizontalDivider()

        Row (
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Row {
                    // RESET BUTTON
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
                    // DISCOUNTS BUTTON
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = {
                                if (showMainMenu) { showMainMenu = false }
                                if (showExtraMenu) { showExtraMenu = false }
                                if (showBleMenu) { showBleMenu = false }
                                if (showFooMenu) { showFooMenu = false }
                                if (showMerMenu) { showMerMenu = false }
                                if (showPh1Modal) { showPh1Modal = false }
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
                    // MISC BUTTON
                    Column(modifier = Modifier.padding(1.dp)) {
                        Button(
                            onClick = { showMiscModal = true },
                            modifier = Modifier
                                .padding(10.dp)
                                .width(buttonWidth)
                                .aspectRatio(3f),
                            shape = RectangleShape
                        ) {
                            Text("MISC", fontSize = buttonTextFontSize)
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
