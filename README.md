# 🏦 TermiCash - A Terminal-Based Banking Application

## What is this project about?
This entire project reflects the interactions people can have in a bank through the terminal. Here, you can either log in as an admin or a customer or create a new account with TermiCash.

I used this application to understand how to work with ORMs and how we can interact with databases. While this was a simple project, it helped me understand what skills are required for a backend engineer.

There are 2 accounts pre-defined in the system:
1. **Customer:** Username -> Guest, Password -> Guest, ID -> 0
2. **Admin:** Username -> Admin, Password -> Admin, ID -> 1

This system stores all account details in a local database, so the accounts can be accessed anytime once created. The passwords are also stored using MD5 encryption.

## What did I learn from this project?
1. A deeper understanding of databases and how a user can interact with them.
2. Ensuring users can only perform actions intended for them without breaking the system.

## How to Run the Application
### Prerequisites:
- Ensure you have **Java 17+** installed.
- Install **Gradle** (if not included in your Java setup).

### Steps to Run:
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/TermiCash.git
   cd TermiCash
   ```
2. Build the project using Gradle:
   ```sh
   ./gradlew build  # (or gradlew.bat build on Windows)
   ```
3. Run the application:
   ```sh
   java -jar build/libs/TermiCash.jar
   ```
4. Follow the on-screen instructions to log in or create an account.

## Future Plans for this Project
1. Implement JUnit5 tests and integrate build automation tools (e.g., Jenkins).
2. Complete code documentation.
3. Convert the application to a GUI-based system.

Thanks for stopping by! 😊 Happy coding! 🚀

