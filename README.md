# QuickChat Application - Part 2 (PROG5121)

**Student Name:**Amanda Mshweshwe  
**Student Number:** ST10277492
**Module:** Programming 1A (PROG5121)

## Project Description
QuickChat is a console-based Java application. It includes a secure Registration and Login system (Part 1), followed by a Messaging feature (Part 2) where users can send, store, or disregard messages. Messages are saved permanently using a JSON file.

## Features
### Part 1: Registration & Login
*   Validates usernames (must contain an underscore and be ≤ 5 characters).
*   Validates password complexity (≥ 8 characters, 1 capital letter, 1 number, 1 special character).
*   Validates South African cell phone numbers (must start with +27).
*   Secure login system requiring the correct credentials.

### Part 2: Messaging
*   **Main Menu:** Loops until the user chooses to quit.
*   **Send Messages:** Users can specify how many messages they want to send.
*   **Message Validation:** Validates recipient cell numbers and ensures messages do not exceed 250 characters.
*   **Message Hash:** Automatically generates a unique hash for each message (e.g., `00:0:HITONIGHT`).
*   **Actions:** Users can choose to Send, Store (saves to JSON), or Disregard a message.
*   **JSON Storage:** Automatically saves "Stored" messages to a `messages.json` file without overwriting previous messages.

## Technologies Used
*   **Java 17**
*   **Ant** (Build Tool via Apache NetBeans)
*   **JUnit 4** (Unit Testing)
*   **Gson 2.10.1** (JSON Serialization)


## JSON Storage Information
When a user chooses **Option 2 (Store Message)** in the menu, the `MessageStorage` class uses the Gson library to save the message. 
*   The file `messages.json` is generated automatically in the root directory of the project.
*   It appends new messages to the array without deleting the old ones.

## Attribution
*   **JSON Handling:** Google, 2024. *Gson User Guide*. Available at: https://github.com/google/gson/blob/main/UserGuide.md (Accessed: 2026).
*   **Regex Patterns:** Adapted from the Oracle Java 17 Pattern documentation.
