import tkinter as tk
from tkinter import ttk

class Calculator:
    def __init__(self, master):
        self.master = master
        master.title("Toggle Calculator")

        self.total = tk.StringVar()
        self.total.set(str(0))

        self.label = tk.Label(master, textvariable=self.total, font=("Arial", 24))
        self.label.grid(row=0, column=0, columnspan=4, padx=10, pady=10)

        # Editable Values (Top of the Code)
        self.item_values = {
            "Bagel": tk.StringVar(value="1.0"),
            "Coffee": tk.StringVar(value="1.0"),
            "Sandwich": tk.StringVar(value="2.0"),
            "Juice": tk.StringVar(value="3.0")
        }

        # Checkbuttons
        for i in self.item_values:
            counter += 1
            self.button_check = ttk.Checkbutton(master, text=i, variable=self.item_values["Bagel"], command=lambda: self.toggle_item(self.item_values["Bagel"].get()))
        self.bagel_check.grid(row=1, column=counter-1, padx=5, pady=5)
        # self.bagel_check = ttk.Checkbutton(master, text="Bagel", variable=self.item_values["Bagel"], command=lambda: self.toggle_item(self.item_values["Bagel"].get()))
        # self.bagel_check.grid(row=1, column=0, padx=5, pady=5)

        # self.coffee_check = ttk.Checkbutton(master, text="Coffee", variable=self.item_values["Coffee"], command=lambda: self.toggle_item(self.item_values["Coffee"].get()))
        # self.coffee_check.grid(row=1, column=1, padx=5, pady=5)

        # self.sandwich_check = ttk.Checkbutton(master, text="Sandwich", variable=self.item_values["Sandwich"], command=lambda: self.toggle_item(self.item_values["Sandwich"].get()))
        # self.sandwich_check.grid(row=1, column=2, padx=5, pady=5)

        # self.juice_check = ttk.Checkbutton(master, text="Juice", variable=self.item_values["Juice"], command=lambda: self.toggle_item(self.item_values["Juice"].get()))
        # self.juice_check.grid(row=1, column=3, padx=5, pady=5)

        # Add Tax Button
        self.add_tax_button = ttk.Button(master, text="Add Tax (10%)", command=lambda: self.add_tax(0.10))
        self.add_tax_button.grid(row=2, column=0, columnspan=4, padx=10, pady=10)

    def toggle_item(self, amount):
        try:
            if self.total.get() != "0":
                new_total = float(self.total.get()) + float(amount)
                self.total.set(str(new_total))
            else:
                self.total.set(str(amount))
        except ValueError:
            self.total.set("0")

    def add_tax(self, tax_rate):
        try:
            amount = float(self.total.get())
            tax_amount = amount * tax_rate
            self.total.set(str(amount + tax_amount))
        except ValueError:
            self.total.set("0")

root = tk.Tk()
calculator = Calculator(root)
root.mainloop()
