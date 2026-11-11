# QuickChat Application - Part 3 (PROG5121)

**Student Name:** Amanda Mshweshwe  
**Student Number:** ST10277492  
**Module:** Programming 1A (PROG5121)  
**Assessment:** Final PoE

## Project Description

QuickChat is a console-based Java application built over three parts of the Programming 1A PoE. It started with a secure Registration and Login system, then a Messaging feature that lets users send, store or disregard messages, and finally in Part 3 it grew a full Stored Messages manager. Users can now view a list of who sent what to whom, find the longest stored message, search by message ID, search by recipient, delete a stored message using its hash, and print a full report of everything saved.

Stored messages are kept in a `messages.json` file. Sent and disregarded messages are kept in memory arrays while the app runs. No GUI is used. Everything runs in the console with `Scanner` and `System.out.println`.

## Features

### Part 1: Registration and Login

- Validates usernames. Must contain an underscore and be no more than 5 characters long.
- Validates password complexity. Must be at least 8 characters with 1 capital letter, 1 number and 1 special character.
- Validates South African cell phone numbers. Must start with `+27` followed by 9 digits.
- Secure login system. The user must type the same username and password they registered with.
- Friendly welcome message shows the user's first and last name after a successful login.

### Part 2: Messaging

- **Welcome Menu:** Shows the QuickChat menu and loops until the user picks Quit.
- **Send Messages:** The user chooses how many messages to enter and the app runs a for loop for exactly that number.
- **Message Validation:** The recipient must be in international format and the message must be 250 characters or fewer.
- **Message Number:** Comes from the loop counter so each message gets its own number automatically.
- **Message ID:** A 10 digit number generated per message. The first two digits come from the message number so the hash stays predictable for tests. The rest are random.
- **Message Hash:** Built from the first two digits of the ID, a colon, the message number, a colon, then the first and last word of the message in uppercase. Example: `00:0:HITONIGHT`.
- **Actions:** The user can Send, Store or Disregard each message.
- **JSON Storage:** Stored messages are written to `messages.json` using Gson. Old messages are kept and new ones are added.

### Part 3: Stored Messages Manager

Part 3 adds the fourth main menu option "Stored Messages" plus five arrays the rubric asks for.

- **Sent Messages array:** Holds every message the user marked as Sent.
- **Disregarded Messages array:** Holds every message the user marked as Disregard.
- **Stored Messages array:** Built by reading `messages.json` into a list at runtime. No hard coding.
- **Message Hash array:** Built from all messages in the three lists.
- **Message ID array:** Built from all messages in the three lists.

The Stored Messages sub menu gives the user these options:

1. **Display sender and recipient** of every stored message.
2. **Display the longest stored message** by comparing message lengths in a loop.
3. **Search by message ID** and show the matching recipient and message.
4. **Search by recipient** and show every message stored or sent to that number.
5. **Delete a message by its hash** and remove it from the JSON file.
6. **Display a full report** with the hash, recipient and message for every stored message.
7. **Back to main menu.**

## Input Loops

Every input in the console loops until the user types something valid. This stops crashes and keeps the app running.

- First name and last name loop until not empty.
- Username loops until it has an underscore and is 5 characters or shorter.
- Password loops until it passes every complexity rule.
- Cell phone number loops until it matches `+27` plus 9 digits.
- Main menu choice loops until it is a number from 1 to 4.
- Number of messages loops until it is a positive whole number.
- Recipient cell number loops until it matches the international format.
- Message text loops until it is not empty and is 250 characters or fewer.
- Send, Store or Disregard choice loops until it is 1, 2 or 3.
- Stored Messages sub menu choice loops until it is a number from 1 to 7.

## Project Structure

```
QuickChat/
├── src/
│   └── com/mycompany/quickchat/part1/
│       ├── Login.java
│       ├── Message.java
│       ├── MessageStorage.java
│       └── Main.java
├── test/
│   └── com/mycompany/quickchat/part1/
│       ├── LoginTest.java
│       └── MessageTest.java
├── messages.json
├── test-messages.json
├── build.xml
└── README.md
```

`messages.json` is created the first time a message is stored. `test-messages.json` is only used by the unit tests so the real messages file is not touched during testing.

## Class Overview

### Login.java

Handles registration and login. Part 1 work, unchanged in Part 3.

- `checkUserName(String u)` checks the underscore and the 5 character limit.
- `checkPasswordComplexity(String p)` checks length, capital letter, number and special character.
- `checkCellPhoneNumber(String c)` checks the `+27` format.
- `registerUser(...)` saves the details and returns the correct message.
- `loginUser(...)` returns true if the details match.
- `returnLoginStatus(...)` returns the welcome or error message.

### Message.java

Represents one message. Holds the ID, the message number, the sender, the recipient, the text, the hash and the flag.

- `buildHash()` builds the hash in the format `prefix:num:FIRSTLAST`.
- `checkMessageId()` checks the ID is 10 characters.
- `checkRecipientCell()` checks the international format.
- `checkMessageLength()` checks the 250 character limit and that it is not empty.
- `setFlag(...)` sets "Sent", "Stored" or "Disregard".
- `setSender(...)` records who sent the message so reports can show it.
- `getDetails()` returns the details in the required order: Message ID, Message Hash, Recipient, Message.

### MessageStorage.java

Handles the arrays and the JSON file.

- `addMessage(...)` sends the message to the right array based on its flag.
- `saveMessage(...)` appends a message to `messages.json`.
- `loadMessages()` reads the JSON file back into a list.
- `getMessageHashes()` and `getMessageIds()` build the hash and ID arrays.
- `generateSenderRecipientList()` prints sender and recipient for every stored message.
- `findLongestMessage()` loops through and returns the message with the longest text.
- `findByMessageId(...)` searches the stored, sent and disregarded lists.
- `findByRecipient(...)` returns every message for one cell number.
- `deleteByHash(...)` removes a stored message and rewrites the JSON file.
- `generateReport()` returns a formatted report of all stored messages.

### Main.java

Runs the console app.

- Registers and logs in the user.
- Shows the welcome menu with four options.
- Runs the send messages loop.
- Opens the Stored Messages sub menu.

## Technologies Used

- **Java 17**
- **Ant** (Build tool used by Apache NetBeans)
- **JUnit 4** (Unit testing)
- **Gson 2.10.1** (JSON serialization)

## JSON Storage

When a user marks a message as Stored, `MessageStorage.saveMessage(...)` is called. It loads the current `messages.json` file into a list, adds the new message, and writes the whole list back with Gson. The file uses pretty printing so it is easy to read.

If the file does not exist yet, `loadMessages()` returns an empty list and the new message becomes the first entry. No data is lost when the app closes and reopens because the JSON file stays on disk.

If a user deletes a message by hash, `deleteByHash(...)` finds the matching message, removes it from the list and rewrites the JSON file without it. The user gets a confirmation message with the deleted message text.

## Test Data Used

The unit tests use the exact test data from the brief.

| Message | Recipient | Text | Flag |
|---|---|---|---|
| 1 | +27834557896 | Did you get the cake? | Sent |
| 2 | +27838884567 | Where are you? You are late! I have asked you to be on time. | Stored |
| 3 | +27834484567 | Yohoooo, I am at your gate. | Disregard |
| 4 | Developer | It is dinner time ! | Sent |
| 5 | +27838884567 | Ok, I am leaving without you. | Stored |

## Unit Tests

`MessageTest.java` covers Part 2 and Part 3 behaviour.

Part 2 tests:

- Valid message length.
- Message too long.
- Valid recipient.
- Invalid recipient.
- Official hash for message 1, checking it returns `00:0:HITONIGHT`.
- Loop through three hashes and check each one matches.

Part 3 tests:

- Sent messages array is filled with the two Sent messages from the test data.
- Longest stored message is message 2.
- Search by message ID finds message 4 using `0838884567`.
- Search by recipient `+27838884567` returns messages 2 and 5.
- Delete by hash removes message 2 and the confirmation message contains "successfully deleted".
- Report contains the hash, recipient and message text.
- Hash and ID arrays are built with the right count.

`LoginTest.java` covers Part 1 checks. It stays exactly as it was.

All tests can be run from NetBeans by right clicking the project and choosing Test. They can also be run from GitHub Actions using the `TestJava.yml` workflow.

## How To Run

### In NetBeans

1. Open the `QuickChat` project in NetBeans.
2. Right click the project and choose Run.
3. Follow the console prompts to register, log in and use the menus.
4. To run the tests, right click the project and choose Test.

### From the command line with Ant

```
ant clean
ant run
ant test
```

### GitHub Actions

The workflow file `.github/workflows/TestJava.yml` is set up to run `ant test` every time code is pushed to the main branch. If any test fails, the workflow fails. If every test passes, the workflow shows a green tick.

## Expected Console Output (Short Sample)

This is a short sample of how a Part 3 run looks when the user searches by recipient and then deletes a message by hash.

```
Welcome to QuickChat.
1) Send Messages
2) Show recently sent messages
3) Stored Messages
4) Quit
Choose an option: 3

--- Stored Messages ---
1) Display sender & recipient
2) Display longest message
3) Search by message ID
4) Search by recipient
5) Delete by message hash
6) Display full report
7) Back to main menu
Choose: 4
Enter recipient: +27838884567
- Where are you? You are late! I have asked you to be on time.
- Ok, I am leaving without you.

--- Stored Messages ---
1) Display sender & recipient
2) Display longest message
3) Search by message ID
4) Search by recipient
5) Delete by message hash
6) Display full report
7) Back to main menu
Choose: 5
Enter hash to delete: 01:1:WHERETIME
Message: "Where are you? You are late! I have asked you to be on time." successfully deleted.
```

## Evidence Checklist

This is the evidence that needs to be captured after running the app. It is not faked.

1. Console showing registration and login with wrong inputs, then correct ones.
2. Console showing the main menu with four options.
3. Console showing a wrong menu choice, then a correct one.
4. Console showing the send messages loop and message details.
5. Console showing one Send, one Store and one Disregard action.
6. Console showing the Stored Messages sub menu with all seven options.
7. Console showing option 1, sender and recipient list.
8. Console showing option 2, longest message.
9. Console showing option 3, search by message ID.
10. Console showing option 4, search by recipient with more than one result.
11. Console showing option 5, delete by hash with confirmation.
12. Console showing option 5 again with a hash that does not exist.
13. Console showing option 6, full report.
14. `messages.json` opened in Notepad before and after the delete so the change is visible.
15. JUnit test results in NetBeans, all green.
16. GitHub Actions run, all green.
17. At least six commits for Part 3 in the commit history.
18. Unlisted YouTube video showing the app running with my own voice explaining the stored messages logic.

## GitHub Submission Checklist

- Repository name: QuickChat.
- Main branch holds the working code.
- Commits for Part 3 are clear and meaningful.
- `Login.java`, `Message.java`, `MessageStorage.java` and `Main.java` are in `src`.
- `LoginTest.java` and `MessageTest.java` are in `test`.
- `messages.json` is either committed or ignored, but the app creates it on first store.
- `test-messages.json` should not be committed. It is only for tests.
- `.github/workflows/TestJava.yml` is committed.
- The README is updated to Part 3.
- The unlisted YouTube link and the GitHub link are submitted on Arc.

## Attribution

- **JSON handling:** Google, 2024. *Gson User Guide*. Available at: https://github.com/google/gson/blob/main/UserGuide.md (Accessed: 3 October 2026).
- **Regex patterns:** Oracle, 2024. *Java SE 17 Pattern documentation*. Available at: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html (Accessed: 3 October 2026).
- **JUnit tests:** JUnit Team, 2024. *JUnit 4 documentation*. Available at: https://junit.org/junit4/ (Accessed: 3 October 2026).
- **Build and workflow:** GitHub, 2024. *Building and testing Java with Ant*. Available at: https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-ant (Accessed: 3 October 2026).

## Notes To Self

- Login and its test did not change in Part 3.
- Every input loops until the user types something valid.
- No hard coded IDs or hashes in the app. IDs are generated, hashes are built.
- No GUI. Console only.
- Comments in the code are short and plain.
- No stream API used. Simple loops everywhere to keep it first year level.
- Do not commit `test-messages.json`.
- Before final push, delete `messages.json` if I do not want my test messages in the repo. The app will recreate it on the next store.
- Run `ant clean` before taking screenshots so the build is fresh.
- Re-record the video if any of the stored messages features break after a change.
