package com.example.libby_calculator

import androidx.compose.ui.graphics.Color

data class MenuItem(val name: String, val price: Double, val options: List<String> = emptyList())

data class ActionButton(
    val text: String,
    val price: Double = 0.0,
    val modifierType: ModifierType = ModifierType.NONE,
    val color: Color = Color.Unspecified,
    val isUnderlined: Boolean = false,
    val isDirectAdd: Boolean = false
)

enum class ModifierType {
    NONE, BASE, EXTRA, NITRO, SHORT, SHRIMP, GRAB_N_GO, GRAB_N_GO_FOOD,
    FANCY_CROISSANT, SANDWICH, OATMEAL, HOUSE_TEA, BLACK_TEA, HERBAL_TEA,
    GREEN_TEA, SPEC_TEA, BAGEL, BULK_COFFEE, HONEY
}

object MenuData {
    val mainRow1 = listOf(
        ActionButton("COFFE", 0.0, ModifierType.EXTRA, Color(0xFF0F5FB7)),
        ActionButton("OLE", 4.0, ModifierType.BASE, Color(0xFF0F5FB7)),
        ActionButton("CB", 5.0, ModifierType.NITRO, Color(0xFF0F5FB7))
    )
    val mainRow2 = listOf(
        ActionButton("L CAP", 5.0, ModifierType.BASE, Color(0xFFBB5918)),
        ActionButton("A", 4.0, ModifierType.BASE, Color(0xFFBB5918)),
        ActionButton("FL", 5.50, ModifierType.BASE, Color(0xFFBB5918)),
        ActionButton("CRL", 5.75, ModifierType.BASE, Color(0xFFBB5918))
    )
    val mainRow3 = listOf(
        ActionButton("M WM", 5.75, ModifierType.BASE, Color(0xFF6F1C0D)),
        ActionButton("FW", 5.25, ModifierType.SHORT, Color(0xFF6F1C0D)),
        ActionButton("KW", 5.50, ModifierType.BASE, Color(0xFF6F1C0D)),
        ActionButton("WELL", 4.50, ModifierType.BASE, Color(0xFF126583))
    )
    val mainRow4 = listOf(
        ActionButton("ESM", 3.75, ModifierType.SHORT, Color(0xFFF5A724)),
        ActionButton("ESP", 3.25, ModifierType.SHORT, Color(0xFFF5A724)),
        ActionButton("HC", 5.0, ModifierType.SHRIMP, Color(0xFFF5A724)),
        ActionButton("COLD", 0.0, ModifierType.GRAB_N_GO, Color(0xFF6ABCB8))
    )
    val mainRow5 = listOf(
        ActionButton("TEA", 3.50, ModifierType.BASE, Color(0xFF7DD675)),
        ActionButton("TEA L", 5.00, ModifierType.BASE, Color(0xFF7DD675)),
        ActionButton("CHAI", 5.25, ModifierType.BASE, Color(0xFF7DD675)),
        ActionButton("GTL", 5.75, ModifierType.BASE, Color(0xFF7DD675))
    )

    val bleRow1 = listOf(
        ActionButton("ESPB", 6.00, ModifierType.BASE, Color(0xFFB9710B)),
        ActionButton("FLB", 6.25, ModifierType.BASE, Color(0xFFB9710B)),
        ActionButton("CRB", 6.25, ModifierType.BASE, Color(0xFFB9710B)),
        ActionButton("MB", 6.25, ModifierType.BASE, Color(0xFFB9710B))
    )
    val bleRow2 = listOf(
        ActionButton("TEA B", 6.25, ModifierType.BASE, Color(0xFF7DD675)),
        ActionButton("CHAIB", 6.25, ModifierType.BASE, Color(0xFF7DD675)),
        ActionButton("SM", 7.50, ModifierType.BASE, Color(0xFF6ABCB8), isDirectAdd = true),
        ActionButton("CRBL", 5.75, ModifierType.BASE, Color(0xFF48638A))
    )

    val fooRow1 = listOf(
        ActionButton("MUFF", 5.25, isDirectAdd = true, color = Color(0xFFF00B6A)),
        ActionButton("FCRO", 0.0, ModifierType.FANCY_CROISSANT, Color(0xFFF00B6A)),
        ActionButton("PLAIN", 5.0, isDirectAdd = true, color = Color(0xFFF00B6A))
    )
    val fooRow2 = listOf(
        ActionButton("BARS", 7.65, isDirectAdd = true, color = Color(0xFFD62027)),
        ActionButton("SCONE", 5.25, isDirectAdd = true, color = Color(0xFFD62027)),
        ActionButton("POP", 5.75, isDirectAdd = true, color = Color(0xFFD62027)),
        ActionButton("COOKI", 4.80, isDirectAdd = true, color = Color(0xFFD62027))
    )
    val fooRow3 = listOf(
        ActionButton("LOAF", 5.0, isDirectAdd = true, color = Color(0xFF91132F)),
        ActionButton("PAR", 8.25, isDirectAdd = true, color = Color(0xFFF5A724)),
        ActionButton("G&G", 0.0, ModifierType.GRAB_N_GO_FOOD, Color(0xFFFB8125)),
        ActionButton("BALLZ", 6.00, isDirectAdd = true, color = Color(0xFFBC1441))
    )
    val fooRow4 = listOf(
        ActionButton("SAMMI", 0.0, ModifierType.SANDWICH, Color(0xFF0F3D7F)),
        ActionButton("BB", 0.0, ModifierType.BAGEL, Color(0xFF24157A)),
        ActionButton("EMPAN", 8.0, isDirectAdd = true, color = Color(0xFF2BBDAB)),
        ActionButton("OAT", 0.0, ModifierType.OATMEAL, Color(0xFF2BBDAB))
    )

    val merRow1 = listOf(
        ActionButton("TSHIS", 30.0, isDirectAdd = true, color = Color(0xFF0E5672)),
        ActionButton("TSHIL", 32.0, isDirectAdd = true, color = Color(0xFF0E5672)),
        ActionButton("MUG", 19.75, isDirectAdd = true, color = Color(0xFF48638A))
    )
    val merRow2 = listOf(
        ActionButton("BULK", 0.0, ModifierType.BULK_COFFEE, color = Color(0xFF6F1C0D)),
        ActionButton("BLKT", 0.0, ModifierType.BLACK_TEA, Color(0xFFA70B10)),
        ActionButton("HRBT", 0.0, ModifierType.HERBAL_TEA, Color(0xFF91132F)),
        ActionButton("HONEY", 0.0, ModifierType.HONEY, color = Color(0xFFF5A724))
    )
    val merRow3 = listOf(
        ActionButton("SPTEA", 0.0, ModifierType.SPEC_TEA, Color(0xFFBC1441)),
        ActionButton("GRT", 0.0, ModifierType.GREEN_TEA, Color(0xFF2AAD65))
    )

    val extraRow1 = listOf(
        ActionButton("$1.00", 1.0, isDirectAdd = true, color = Color(0xFF6ABCB8)),
        ActionButton("1P", 0.50, isDirectAdd = true, color = Color(0xFF6ABCB8)),
    )

    val discountRow1 = listOf(
        ActionButton("-$0.50", -0.5, isDirectAdd = true),
        ActionButton("-$5.00", -5.0, isDirectAdd = true)
    )

    val modifiers = mapOf(
        ModifierType.BASE to listOf(
            "Almond +1.00", "Macadamia +1.00", "Soy +1.00", "Oat +1.00", "Breve +1.00",
            "Heavy Cream +2.00", "Extra Milk +1.00", "Espresso Shot +1.00", "Double Espresso +2.00", "Syrup +1.00", "Honey +1.00"
        ),
        ModifierType.EXTRA to listOf(
            "Guppy +3.50", "Trout +4.50", "Whaley +5.50", "Guppy Refill +1.75",
            "Trout Refill +2.25", "Whaley Refill +2.75", "Slow Pour +4.50"
        ),
        ModifierType.NITRO to listOf("Guppy +0.00", "Trout +1.25", "Whaley +2.50"),
        ModifierType.SHORT to listOf("Guppy +0.00", "Trout +1.00"),
        ModifierType.SHRIMP to listOf("Shrimp -1.00", "Guppy +0.00", "Trout +1.00", "Whaley +2.00"),
        ModifierType.GRAB_N_GO to listOf(
            "PH Water +3.25", "Smart Water +5.00", "Perrier +3.25", "Topo-Chico +3.75",
            "CNWater +3.75", "OJ +3.50", "Mama Chia +4.00", "Better Booch +4.25",
            "Synergy Booch +5.25", "CBD +8.25", "Yerba Mate +4.25", "Cup H20 +1.00"
        ),
        ModifierType.GRAB_N_GO_FOOD to listOf(
            "Fruit Jerky +3.50", "HB Eggs +2.50", "Biscotti +1.50", "Banana +1.50",
            "Chips +2.00", "Popcorn +2.00", "Candy +2.50", "YoPup +4.50"
        ),
        ModifierType.FANCY_CROISSANT to listOf(
            "Chorizo +5.75", "Jalapeno +5.75", "Chocolate +5.75", "Cheddar +5.75",
            "Almond +5.75", "Turkey +6.25", "Ham & Cheese +6.25"
        ),
        ModifierType.SANDWICH to listOf(
            "Ham Sammie +13.25", "Turk Sammie +13.25", "Classico +14.25",
            "Guac This Way +14.25", "Millennial +14.25", "Caff Caprese +14.25",
            "Add Cheese Bagel +1.50", "Add Fancy Croiss +2.75"
        ),
        ModifierType.OATMEAL to listOf(
            "Classic +6.50", "Sassy Fruit +9.25", "Jen +8.00", "Sweet Start +8.75", "Tiramisu +9.00", "Goldenmilk +9.00",
            "Dried Cranberries +0.75", "Pecans +0.75", "Butter +1.00", "Walnuts +0.75", "Fresh Berries +2.75",
            "Almonds +0.75", "Golden Milk +1.00", "Chocolate Chips +0.75", "Cocoa Powder +0.75", "Half Banana +0.75",
            "Esp Shot +1.00", "Peanut Butter +0.75", "Whipped Cream +0.75", "Steamed Milk +0.75", "Extra +0.75",
        ),
        ModifierType.HOUSE_TEA to listOf(
            "Kermint +4.25", "Good Karma +5.50", "Tea Rex +5.50", "Frankie Goes to Hollywood +6.00",
            "Virgin Chai +5.25", "Aphroditea +5.25", "Auntea Flow +5.25", "To Breathe or Not to Breathe +5.75",
            "Good Vibrations +5.75", "Good Night Moon +6.00", "Pug in a Rug +5.25", "Custom Blend +6.00", "Lady Grey +5.50"
        ),
        ModifierType.BLACK_TEA to listOf(
            "Masala Chai +5.50", "Earl Grey +5.00", "English Breakfast +5.00", "India Black +5.00",
            "Irish Breakfast +5.00", "Assam +5.00", "Darjeeling +5.50", "Lapang Souchong +5.00"
        ),
        ModifierType.HERBAL_TEA to listOf(
            "Berry Patch +4.50", "Chamomile +4.00", "Lavender +5.50", "Lemon Balm +3.50",
            "Mango Flip +4.50", "Rooibos +4.00", "Stevia +4.00"
        ),
        ModifierType.GREEN_TEA to listOf(
            "Gen Mai Cha +5.00", "Gunpowder +5.00", "Jasmine +5.25", "Moroccan Mint +4.75",
            "Sencha +5.00", "Vietnamese +5.25", "Matcha +9.00"
        ),
        ModifierType.SPEC_TEA to listOf("Yerba Matte +4.25", "Kukicha Twig +4.00", "Peach White +4.75"),
        ModifierType.BAGEL to listOf(
            "Classic Bagel +4.00", "Cheesy Bagel +5.50", "GF Bagel +5.50", "CC +1.75",
            "Peanut Butter +1.75", "Banana +1.75", "Butter +1.00", "Avocado +2.50",
            "Avo x2 +5.00", "Slice Cheese +1.25", "Salami +2.50", "Mozzarella +2.50",
            "1 Egg +3.00", "2 Egg +5.50", "Tomato +2.25", "Pepper +2.25", "Turkey +2.50", "Everything Seasoning +0.75"
        ),
        ModifierType.BULK_COFFEE to listOf(
            "House/Espresso +19.00", "Black Gold/Vulcan +20.00", "Columbia/Bitches +21.25",
            "1/2lb Any +10.00", "Iced Coffee Growler +25.00", "Growler Refill +21.00",
            "Growler Flavor (20p) +4.50", "Nitro Growler +31.00", "Nitro Growler Refill +26.00"
        ),
        ModifierType.HONEY to listOf(
            "Honey Stick +1.00", "Honey Comb +4.50", "Honey Bear (12oz) +18.00",
            "2 bear bonus (12oz) +28.00", "BB Honey Bear +6.00", "3 BB Bear Bonus +15.00"
        )
    )
}
