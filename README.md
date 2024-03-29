## A simple application for managing patients in a medical clinic

### Features:
Backend:
- create patient
- update patient
- delete patient
- list patients
- pagination
- data validation
- exception handling

Frontend - same features as backend, but lacks (due to lack of time):
- data validation
- error handling
- visual aspects
and minor bugs are possible

### How to run:

Requirements:
- Java 17
- Gradle
- Node.js
- NPM

Backend:
1. Setup a PostgreSQL database.
2. Input proper data such as username, password and database URL in the source code (backend/../application.properties)
3. Run the backend application with `./gradlew bootRun` or Run the application use Intellij IDE

Frontend:
1. Install node packages with npm install
2. Run dev server with `npm runDev`

Usage:  
Go to `localhost:5173`
