# QuickChat - Part 1

A Java console application for user registration and login with validation.

## Features

- Register user with username, password, and cell number.
- Validate username: contains underscore, max 5 characters.
- Validate password: at least 8 characters, one capital letter, one number, one special character.
- Validate cell: +27 followed by 9 digits.
- Login with registered credentials.
- Welcome message on successful login.

## Project Files

- `Main.java` - entry point, handles user input.
- `Login.java` - validation and login logic.
- `LoginTest.java` - JUnit 5 tests.

## How to Run

1. Open project in NetBeans or any Java IDE.
2. Run `Main.java`.
3. Follow prompts to register and login.

## Validation Rules

| Field    | Rule                                                         |
|----------|--------------------------------------------------------------|
| Username | Contains `_` and length <= 5                                 |
| Password | Length >= 8, has uppercase, digit, special character         |
| Cell     | Matches `+27` followed by 9 digits                           |

## Testing

Run `LoginTest.java` with JUnit 5.

## Author

Kyle Smith

# Changelog

All notable changes to QuickChat will be documented in this file.

## [1.0.0] - 2026-09-17

### Added
- Main.java console entry point for user registration and login.
- Login.java with username, password, and cell phone number validation.
- Username validation: must contain `_` and be no more than 5 characters.
- Password validation: at least 8 characters, one capital letter, one number, and one special character.
- Cell validation: must match `+27` followed by 9 digits.
- `registerUser` methods for registering user details.
- `loginUser` and `returnLoginStatus` methods for login and welcome messages.
- LoginTest.java JUnit 5 tests for validation, registration messages, and login.
- Default test credentials: `kyl_1` / `Ch&&sec@ke99!`.

### Notes
- Part 1 of the QuickChat project.
- Single-user registration only; no database or file storage.
