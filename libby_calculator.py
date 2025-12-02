import tkinter as tk
from tkinter import ttk

class Calculator:
    def __init__(self, master):
        self.master = master
        master.title("Toggle Calculator")

        self.total = tk.StringVar()
        self.total.set("0.00")

        self.label = tk.Label(master, textvariable=self.total, font=("Arial", 24))
        self.label.grid(row=0, column=0, columnspan=4, padx=10, pady=10)

        # Editable Values (Top of the Code)
        self.item_values = [
            {
                "item_name": "Bagel",
                "item_price": 1,
                "check_var": tk.BooleanVar()
            },
            {
                "item_name": "Coffee",
                "item_price": 2,
                "check_var": tk.BooleanVar()
            },
            {
                "item_name": "Sandwich",
                "item_price": 3,
                "check_var": tk.BooleanVar()
            },
            {
                "item_name": "Juice",
                "item_price": 4,
                "check_var": tk.BooleanVar()
            }
        ]

        # Checkbuttons
        for i, item in enumerate(self.item_values):
            self.check_button = ttk.Checkbutton(master, style="Toolbutton", text=item["item_name"], variable=item["check_var"], command=lambda item=item: self.toggle_item(item))
            self.check_button.grid(row=1, column=i, padx=5, pady=5)

        # Add Tax Button
        self.tax_button_bool = tk.BooleanVar()
        self.add_tax_button = ttk.Checkbutton(master, style="Toolbutton", text="Add Tax (10%)", variable=self.tax_button_bool, command=lambda: self.add_tax(0.10))
        self.add_tax_button.grid(row=2, column=0, columnspan=4, padx=10, pady=10)

        # Add Clear Button
        self.add_clear_button = ttk.Button(master, style="Toolbutton", text="Clear", command=lambda: self.total.set("0.00"))
        self.add_clear_button.grid(row=2, column=1, columnspan=4, padx=10, pady=10)

    def toggle_item(self, item):
        try:
            print(item["item_name"], item["item_price"], item["check_var"].get())
            if self.total.get() != "0":
                if item["check_var"].get():
                    new_total = float(self.total.get()) + item["item_price"]
                else:
                    new_total = float(self.total.get()) - item["item_price"]
                self.total.set(f'{new_total:.2f}')
            else:
                self.total.set(f'{item["item_price"]:.2f}')
        except ValueError:
            print("ValueError")
            self.total.set("0.00")

    def add_tax(self, tax_rate):
        try:
            print(self.tax_button_bool.get())
            amount = float(self.total.get())
            tax_amount = amount * tax_rate
            if self.tax_button_bool.get():
                print(amount, tax_amount)
                self.total.set(f'{amount + tax_amount:.2f}')
            else:
                print(amount, tax_amount)
                self.total.set(f'{amount - tax_amount:.2f}')
        except ValueError:
            print("ValueError")
            self.total.set("0.00")

root = tk.Tk()
calculator = Calculator(root)
root.mainloop()
