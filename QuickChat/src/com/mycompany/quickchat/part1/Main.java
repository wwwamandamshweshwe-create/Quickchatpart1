package com.mycompany.quickchat.part1;

import java.util.Scanner;

public class Main {
    
    private static final Scanner sc = new Scanner(System.in);
    private static final MessageStorage storage = new MessageStorage();
    private static int totalMessages = 0;

    public static void main(String[] args) {
        // --- PART 1: REGISTRATION ---
        System.out.print("Enter First Name: ");
        String fn = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String ln = sc.nextLine();
        
        Login login = new Login(fn, ln);
        
        String username, password, cell;
     
        // Loop until a valid username is entered
        while(true){
            System.out.print("Enter Username (must contain _ and <=5 chars): ");
            username = sc.nextLine();
            if(login.checkUserName(username)){
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }
        
        // Loop until a valid password is entered
        while(true){
            System.out.print("Enter Password: ");
            password = sc.nextLine();
            if(login.checkPasswordComplexity(password)){
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }
       
        // Loop until a valid cellphone number is entered
        while(true){
            System.out.print("Cell (+27...): ");
            cell = sc.nextLine();
            if(login.checkCellPhoneNumber(cell)){
                System.out.println("Cell number successfully captured.");
                break;
            } else {
                System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            }
        }
        
        login.registerUser(username, password, cell);
        System.out.println("\nUser registered successfully.");
        System.out.println("-----------------------------------");
        
        // --- PART 1: LOGIN ---
        System.out.print("Enter Username to LOGIN: ");
        String uLogin = sc.nextLine();
        System.out.print("Enter Password to LOGIN: ");
        String pLogin = sc.nextLine();
        
        boolean success = login.loginUser(uLogin, pLogin);
        System.out.println(login.returnLoginStatus(uLogin, pLogin));

        // --- PART 2: START MENU (Only if login was successful) ---
        if (success) {
            runMenu();
        }
    }

    // Displays the main menu in a while loop until the user quits
    private static void runMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    sendMessages();
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    // Handles the message entry process using a for loop
    private static void sendMessages() {
        int count = 0;
        while (true) {
            System.out.print("How many messages do you wish to enter? ");
            try {
                count = Integer.parseInt(sc.nextLine().trim());
                if (count > 0) break;
                System.out.println("Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }

        // For loop runs exactly the number of times the user requested
        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + count + " ---");
            
            // Loop until a valid recipient is entered
            String recipient;
            while (true) {
                System.out.print("Recipient cell number (e.g. +27718693002): ");
                recipient = sc.nextLine().trim();
                Message temp = new Message(i, recipient, "test");
                if (temp.checkRecipientCell()) break;
                System.out.println("Cell phone number incorrectly formatted or does not contain an international code.");
            }

            // Loop until a valid message length is entered
            String text;
            while (true) {
                System.out.print("Message text: ");
                text = sc.nextLine();
                Message temp = new Message(i, recipient, text);
                if (temp.checkMessageLength()) break;
                System.out.println("Message exceeds 250 characters or is empty.");
            }

            // Create the final message object
            Message msg = new Message(i, recipient, text);
            
            // Display details in the required order
            System.out.println("\n" + msg.getDetails());

            System.out.println("\n1) Send Message\n2) Store Message\n3) Disregard Message");
            System.out.print("Choose: ");
            String action = sc.nextLine().trim();

            // Action logic
            if (action.equals("1")) {
                msg.setFlag("Sent");
                totalMessages++;
                System.out.println("Message sent.");
            } else if (action.equals("2")) {
                msg.setFlag("Stored");
                storage.saveMessage(msg); // Save to JSON
                totalMessages++;
                System.out.println("Message stored.");
            } else {
                msg.setFlag("Disregard");
                System.out.println("Message disregarded.");
            }
        }
        System.out.println("\nTotal messages processed: " + totalMessages);
    }
}