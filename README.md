# Running Instructions
* To run this, you need python, which can be downloaded [here](https://www.python.org/).
  * After downloading python, download the python file in this repository and put it in a folder located somewhere easy to access on your computer.
  * When you are ready to run this tool, open a command line by typing "cmd" into the windows search bar and click on the "Command Prompt" application.
  * Into the command prompt type in the following:
    `cd "C:\<full_path_to_where_you_put>\libby_calculator.py"`
    then
    `python libby_calculator.py`
# Button Behaviors
* All buttons except "Clear" can toggle on and off.
* The item buttons are additive, toggling them will either add or subtract from the subtotal.
* You can add more items to the <self.item_values> array on line 18 of the python code by following that bracket format.
  * CSV support coming soon for use with Excel.
* The "Add tax" button adds and subtracts the tax from the current subtotal by a percent amount specified via the <self.tax_rate> variable on line 38 of the python code.
  * This is a literal percent value, ie. "10" and not "0.1" for 10%
  * In the current version, if you toggle any of the item buttons while the tax is toggled on, the tax will toggle itself off before a new subtotal is calculated.
    * Thus you must re-press "Add tax" after any updates to the subtotal.
    * Support for dynamically updating the taxed total is coming soon.
* "Clear" resets the total to 0.00 and toggles all buttons to off position.
* Please report any bugs you discover, I will keep a running list of fixes and improvements.
  * If I sent this to you then you know how to contact me.
  * If you stumbled upon this as a stranger from the internet, thanks for stopping by! Feel free to leave a comment somewhere in my code, I'm sure there are many ways I can make this better.
