package com.mycompany.quickchat.part1;

public class Login {
    private String firstName, lastName, username, password, cellNumber;

    public Login(String fn, String ln){ 
        firstName=fn; lastName=ln; 
        // Defaults for testing
        this.username = "kyl_1";
        this.password = "Ch&&sec@ke99!";
    }

    // Check username: contains '_' and <=5 chars
    public boolean checkUserName(String u){ 
        return u.contains("_") && u.length()<=5; 
    }

    // Check password: >=8 chars, has uppercase, number, special
    public boolean checkPasswordComplexity(String p){ 
        return p.length()>=8 && p.matches(".*[A-Z].*") 
            && p.matches(".*[0-9].*") 
            && p.matches(".*[!@#$%^&*()_+=`~\\-].*"); 
    }

    // Check cell: starts with +27 and has 9 digits
    public boolean checkCellPhoneNumber(String c){ 
        return c.matches("^\\+27[0-9]{9}$"); 
    }

    // Validate and save username/password
    public String registerUser(String u,String p){
        if(!checkUserName(u)) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if(!checkPasswordComplexity(p)) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        username=u; password=p;
        return "Password successfully captured.";
    }

    // Validate and save cell number
    public String registerUser(String u,String p,String c){
        String result = registerUser(u,p);
        if(!result.equals("Password successfully captured.")) return result;
        if(!checkCellPhoneNumber(c)) return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        cellNumber=c;
        return "Cell number successfully captured.";
    }

    // Get cell validation message
    public String registerUserCell(String c){
        if(checkCellPhoneNumber(c)) return "Cell number successfully captured.";
        else return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
    }

    // Verify login
    public boolean loginUser(String u,String p){ 
        return username.equals(u) && password.equals(p); 
    }

    // Return login status message
    public String returnLoginStatus(String u,String p){
        if(loginUser(u,p)) return "Welcome "+firstName+", "+lastName+" it is great to see you again.";
        else return "Username or password incorrect, please try again.";
    }
}