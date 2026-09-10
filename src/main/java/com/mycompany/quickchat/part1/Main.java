package com.mycompany.quickchat.part1;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter First Name: ");
        String fn = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String ln = sc.nextLine();
        
        Login login = new Login(fn, ln);
        
        String username, password, cell;
     
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
        
        System.out.print("Enter Username to LOGIN: ");
        String uLogin = sc.nextLine();
        System.out.print("Enter Password to LOGIN: ");
        String pLogin = sc.nextLine();
        
        System.out.println(login.returnLoginStatus(uLogin, pLogin));
    }
}
