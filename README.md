# OOP Project

 # Blockchain Payment App
A Java-based console application that simulates a blockchain-style payment system using OOP concepts like Inheritance, Polymorphism, Interfaces, Encapsulation, Method Overriding, Exception Handling and GUI components.
<br>

## How the Code Works
The Blockchain-Based Family Payment & Vault System is designed using modular Object-Oriented Programming (OOP) principles in Java. The code is organized into specific modules to handle core logic, data models, and user interfaces.

➤ **Core Architecture and Modules** <br>
The application is divided into several key modules that manage different aspects of the system: <br>
  - **Core Module:** This includes BlockchainService.java, which uses the Singleton pattern to ensure only one instance of the service manages all users, transactions, and vaults. It also contains Start.java for launching the GUI and BlockchainPaymentApp.java for a console-based interface.

  - **Models Module:** Contains data representations such as User.java (base class), BasicUser.java, and Guardian.java. It also includes classes for Transaction.java, FamilyVault.java, and SavingsPlan.java.

  - **Services Module:** Provides specialized functionality like ExchangeRateService.java for currency lookups and EmailNotifier.java for transaction notifications.

  - **UI Module:** Consists of BlockchainPaymentGUI.java, a Swing-based graphical interface with tabs for various features, and ConsoleUI.java for terminal interaction. 


➤ **Core Logic and Transaction Flow** <br>
The main logic for sending money is handled by the sendMoney method in the BlockchainService class: <br>
  - **Validation:** The system first checks if the recipient's wallet address is valid (at least 10 characters) and ensures the transfer amount does not exceed the sender's specific transaction limit.

  - **Fee Calculation:** Using polymorphism, the system calculates a transaction fee based on the sender's user type (e.g., Basic or Premium).

  - **Balance Check:** It verifies if the sender has enough funds to cover both the transaction amount and the calculated fee.

  - **Execution:** If all checks pass, the system deducts the total cost from the sender and adds the base amount to the recipient's balance.

  - **Notification & History:** The transaction is marked as "SUCCESS," recorded in the transactionHistory, and all registered observers (like an email notifier) are alerted.
    

➤ **Implementation of OOP Concepts** <br>

The code serves as a practical application of major OOP principles: <br>

  - **Inheritance:** The User class serves as a parent, providing common fields like name, wallet, and balance, which are inherited by BasicUser and Guardian.

  - **Encapsulation:** Sensitive data like the balance is kept private and only accessible through public getters and setters with built-in validation.

  - **Polymorphism & Method Overriding:** Subclasses override methods like calculateFee() to apply different rules at runtime without complex if-else logic.

  - **Observer Pattern:** The system uses a TransactionObserver interface to allow external services, such as an EmailNotifier, to react to blockchain events without modifying the core service code.
    
  - **Exception Handling:** Custom classes such as InsufficientBalanceException and InvalidAddressException are used to manage errors gracefully, allowing the GUI to show user-friendly messages instead of crashing 

---

## Features
- Create user accounts and manage balances.
- Make payments between users with basic validation.
- Log transactions and view transaction history.
- Demonstrates OOP concepts: classes, interfaces, exceptions, packages.

---

## Project Structure
- core – main business logic and payment processing.
- models – data classes such as User, Transaction, Account.
- services – service classes that coordinate operations.
- exceptions – custom exception classes.
- ui – entry point and user interaction (console menus).
- logs – log or output files.
- utils – helper/utility **classes.**


## Output of code

**Initial User Tab**
 <img width="1057" height="614" alt="image" src="https://github.com/user-attachments/assets/6fc176bf-9a42-49e4-ab1d-14fed8ca1032" />
<br><br> <!-- This statement is used for giving space -->



**Registering a User in Register Tab**
<img width="1049" height="610" alt="image" src="https://github.com/user-attachments/assets/2577b7c1-56da-4a5f-bf0c-d166b273f43c" />
<br><br> <!-- This statement is used for giving space -->



**Updated user list in Users Tab**
<img width="1049" height="611" alt="image" src="https://github.com/user-attachments/assets/e0480c3f-817a-4eeb-8815-24d3902669dc" />
<br><br> <!-- This statement is used for giving space -->



**Sending money feature in Send Money Tab**
<img width="1035" height="603" alt="image" src="https://github.com/user-attachments/assets/5507b061-3537-4596-9850-2a028a79add6" />
<br><br> <!-- This statement is used for giving space -->



**History Tab to see the transactions that has taken place**
<img width="1028" height="602" alt="image" src="https://github.com/user-attachments/assets/6d760f3d-09e2-4994-ada8-2b21638b41fa" />
<br><br> <!-- This statement is used for giving space -->



**Analysis of the transactions in Analytics Tab**
<img width="1014" height="596" alt="image" src="https://github.com/user-attachments/assets/cf5d012a-f40c-49f2-a753-00a04a6fb670" />
<br><br> <!-- This statement is used for giving space -->



**Real time conversion rates in Rates Tab**
<img width="1007" height="591" alt="image" src="https://github.com/user-attachments/assets/bee093b7-c1f1-4133-8122-69d31e8a0bc5" />
<br><br> <!-- This statement is used for giving space -->


**Vault feature where the money can be withdrawn only when all the account members give their permission in Vault Tab**
<img width="1008" height="583" alt="image" src="https://github.com/user-attachments/assets/8c82cee6-99ae-4d38-82bc-3960b178cf82" />
<br><br> <!-- This statement is used for giving space -->
