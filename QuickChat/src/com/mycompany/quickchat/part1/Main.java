package com.mycompany.quickchat.part1;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final MessageStorage storage = new MessageStorage();
    private static int totalMessages = 0;
    private static String currentUser = "";

    public static void main(String[] args) {
        String fn = readString("Enter First Name: ");
        String ln = readString("Enter Last Name: ");

        Login login = new Login(fn, ln);
        String username, password, cell;

        // loop till username is valid
        while (true) {
            System.out.print("Enter Username (must contain _ and <=5 chars): ");
            username = sc.nextLine();
            if (login.checkUserName(username)) { System.out.println("Username successfully captured."); break; }
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        // loop till password passes all rules
        while (true) {
            System.out.print("Enter Password: ");
            password = sc.nextLine();
            if (login.checkPasswordComplexity(password)) { System.out.println("Password successfully captured."); break; }
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        // loop till cell is valid
        while (true) {
            System.out.print("Cell (+27...): ");
            cell = sc.nextLine();
            if (login.checkCellPhoneNumber(cell)) { System.out.println("Cell number successfully captured."); break; }
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }

        login.registerUser(username, password, cell);
        System.out.println("\nUser registered successfully.\n-----------------------------------");

        System.out.print("Enter Username to LOGIN: ");
        String uLogin = sc.nextLine();
        System.out.print("Enter Password to LOGIN: ");
        String pLogin = sc.nextLine();

        boolean success = login.loginUser(uLogin, pLogin);
        System.out.println(login.returnLoginStatus(uLogin, pLogin));

        currentUser = fn + " " + ln;

        if (success) runMenu();
    }

    // main menu, now 4 options
    private static void runMenu() {
        while (true) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");

            int choice = readInt("Choose an option: ", 1, 4);
            switch (choice) {
                case 1: sendMessages(); break;
                case 2: System.out.println("Coming Soon."); break;
                case 3: storedMessagesMenu(); break;
                case 4: System.out.println("Goodbye!"); return;
            }
        }
    }

    // send messages, same as before but every input loops
    private static void sendMessages() {
        int count = readPositiveInt("How many messages do you wish to enter? ");

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + count + " ---");

            // loop till recipient matches +countrycode pattern
            String recipient;
            while (true) {
                System.out.print("Recipient cell number (e.g. +27718693002): ");
                recipient = sc.nextLine().trim();
                Message temp = new Message(i, recipient, "test");
                if (temp.checkRecipientCell()) break;
                System.out.println("Cell phone number incorrectly formatted or does not contain an international code.");
            }

            // loop till message fits and isn't empty
            String text;
            while (true) {
                System.out.print("Message text: ");
                text = sc.nextLine();
                Message temp = new Message(i, recipient, text);
                if (temp.checkMessageLength()) break;
                if (text.trim().isEmpty()) System.out.println("Message cannot be empty.");
                else System.out.println("Message exceeds 250 characters by " + (text.length() - 250) + "; please reduce the size.");
            }

            Message msg = new Message(i, recipient, text);
            msg.setSender(currentUser);
            System.out.println("\n" + msg.getDetails());

            // loop till user picks 1, 2 or 3
            System.out.println("\n1) Send Message");
            System.out.println("2) Store Message");
            System.out.println("3) Disregard Message");
            int action = readInt("Choose: ", 1, 3);

            if (action == 1) {
                msg.setFlag("Sent"); storage.addMessage(msg); totalMessages++;
                System.out.println("Message sent.");
            } else if (action == 2) {
                msg.setFlag("Stored"); storage.addMessage(msg); totalMessages++;
                System.out.println("Message stored.");
            } else {
                msg.setFlag("Disregard"); storage.addMessage(msg);
                System.out.println("Message disregarded.");
            }
        }
        System.out.println("\nTotal messages processed: " + totalMessages);
    }

    // sub-menu for stored messages, loops till user picks back
    private static void storedMessagesMenu() {
        while (true) {
            System.out.println("\n--- Stored Messages ---");
            System.out.println("1) Display sender & recipient");
            System.out.println("2) Display longest message");
            System.out.println("3) Search by message ID");
            System.out.println("4) Search by recipient");
            System.out.println("5) Delete by message hash");
            System.out.println("6) Display full report");
            System.out.println("7) Back to main menu");

            int choice = readInt("Choose: ", 1, 7);
            switch (choice) {
                case 1:
                    System.out.println(storage.generateSenderRecipientList());
                    break;
                case 2:
                    Message longest = storage.findLongestMessage();
                    if (longest == null) System.out.println("No stored messages.");
                    else System.out.println("Longest message: " + longest.getText());
                    break;
                case 3:
                    System.out.print("Enter message ID: ");
                    String id = sc.nextLine().trim();
                    Message found = storage.findByMessageId(id);
                    if (found == null) System.out.println("Message not found.");
                    else System.out.println(found.getDetails());
                    break;
                case 4:
                    System.out.print("Enter recipient: ");
                    String recip = sc.nextLine().trim();
                    List<Message> list = storage.findByRecipient(recip);
                    if (list.isEmpty()) System.out.println("No messages for that recipient.");
                    else for (Message m : list) System.out.println("- " + m.getText());
                    break;
                case 5:
                    System.out.print("Enter hash to delete: ");
                    String hash = sc.nextLine().trim();
                    System.out.println(storage.deleteByHash(hash));
                    break;
                case 6:
                    System.out.println(storage.generateReport());
                    break;
                case 7:
                    return;
            }
        }
    }

    // keeps asking till they type something not empty
    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Cannot be empty. Try again.");
        }
    }

    // keeps asking till they type a number in range
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) return value;
                System.out.println("Enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("That's not a number. Try again.");
            }
        }
    }

    // keeps asking till they type a positive number
    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value > 0) return value;
                System.out.println("Must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("That's not a number. Try again.");
            }
        }
    }
}
