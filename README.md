# Moatable Redistributor

A simple Java application that redistributes money between accounts to reach a desired threshold. Accounts with a surplus transfer funds to those with a deficit, minimizing the number of transfers required.

## Features

- Reads account data and threshold from a file
- Calculates and returns a list of money transfers to balance accounts
- Validates input data for correctness
- Console output of all required transfers

---

## 📁 File Format

Input is read from a file named `data.txt`, located in the project root. The expected format is:

```angular2html
threshold: 100
Alice: 120
Bob: 80
Charlie: 60
David: 140
```


The first line contains the threshold. Each subsequent line represents an account and its balance.

---

## 🚀 Running the Project

Make sure you have **Java 21** and **Maven** installed.

1. Build the project:
   ```bash
   mvn clean install
   ```

2. Run .jar file:
   ```bash
   java -jar .\target\app.jar
   ```

## Running tests

   ```bash
   mvn test