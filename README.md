# expenses-and-currency
Simple Java console-app, could be used to track expenses using currency rates converter API.

You'll need MySQL Server running and one table - "balance" with correponding columns (date, currency, amount, product).
Also you can use IDE like Itellij IDEA and few Maven dependencies to run project.

LEGEND:
Date - date when you've made your expense in yyyy-mm-dd format,
Amount - your's expense in decimal, CUR - Currency (USD, EUR, PLN etc.), 
Product - name of product you've made expenses for.

You can use following COMMANDS to work with App:
 	exit - stops the app; 
 	list - shows all expences; 
 	add - adds new expense (should be written like: **add yyyy-mm-dd Amount CUR Product** sample); 
 	clear - deletes expense by date (should be written like: **clear yyyy-mm-dd** sample); 
 	total CUR - shows all expences in selected CUR (getting rates from fixer.io API, where CUR is currency you select).
 
