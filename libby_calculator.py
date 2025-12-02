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

        self.vars_holder = []

        # Editable Values (Top of the Code)
        self.item_values = [
            {
                "item_name": "Bagel",
                "item_price": 1
            },
            {
                "item_name": "Coffee",
                "item_price": 2
            },
            {
                "item_name": "Sandwich",
                "item_price": 3
            },
            {
                "item_name": "Juice",
                "item_price": 4
            }
        ]

        # Going tax rate
        self.tax_rate = 10

        # Checkbuttons
        for i, item in enumerate(self.item_values):
            check_var = tk.BooleanVar()
            self.vars_holder.append(check_var)
            self.check_button = ttk.Checkbutton(master, style="Toolbutton", text=item["item_name"], variable=self.vars_holder[i], command=lambda item=item, i=i: self.toggle_item(item, i))
            self.check_button.grid(row=1, column=i, padx=5, pady=5)

        # Add Tax Button
        self.tax_button_bool = tk.BooleanVar()
        self.add_tax_button = ttk.Checkbutton(master, style="Toolbutton", text="Add Tax (10%)", variable=self.tax_button_bool, command=lambda: self.add_tax(self.tax_rate))
        self.add_tax_button.grid(row=2, column=0, columnspan=4, padx=10, pady=10)

        # Add Clear Button
        self.add_clear_button = ttk.Button(master, style="Toolbutton", text="Clear", command=lambda: self.clear_all())
        self.add_clear_button.grid(row=2, column=1, columnspan=4, padx=10, pady=10)

    def toggle_item(self, item, i):
        try:
            print(item["item_name"], item["item_price"])
            if self.total.get() != "0":
                if self.vars_holder[i].get():
                    new_total = float(self.total.get()) + item["item_price"]
                else:
                    new_total = float(self.total.get()) - item["item_price"]
                self.total.set(f'{new_total:.2f}')
            else:
                self.total.set(f'{item["item_price"]:.2f}')
        except ValueError:
            print("ValueError: ", ValueError)
            self.total.set("0.00")

    def add_tax(self, tr):
        try:
            print(self.tax_button_bool.get())
            amount = float(self.total.get())
            new_tax_amount = round(amount * ((100+tr)/100), 2)
            old_tax_amount = round(amount * (100/(100+tr)), 2)
            if self.tax_button_bool.get():
                print(amount, (100+tr)/100, new_tax_amount)
                self.total.set(f'{new_tax_amount:.2f}')
            else:
                print(amount, (100-tr)/100, old_tax_amount)
                self.total.set(f'{old_tax_amount:.2f}')
        except ValueError:
            print("ValueError: ", ValueError)
            self.total.set("0.00")
    
    def clear_all(self):
        try:
            self.total.set("0.00")
            self.tax_button_bool.set(False)
            for vars in self.vars_holder:
                vars.set(False)
        except ValueError:
            print("ValueError: ", ValueError)


root = tk.Tk()
calculator = Calculator(root)
root.mainloop()
