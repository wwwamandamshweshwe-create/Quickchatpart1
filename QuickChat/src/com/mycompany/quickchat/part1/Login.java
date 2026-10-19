package com.mycompany.quickchat.part1;

public class Login {
    // Variables to store user details in memory
    private String firstName, lastName, username, password, cellNumber;

    // Constructor sets the user's name and default test credentials
    public Login(String fn, String ln){ 
        firstName=fn; 
        lastName=ln; 
        this.username = "kyl_1";
        this.password = "Ch&&sec@ke99!";
    }

    // Rubric Rule: Username must contain '_' and be <= 5 characters
    public boolean checkUserName(String u){ 
        return u.contains("_") && u.length()<=5; 
    }

    // Rubric Rule: Password must be >= 8 chars, have an uppercase, a number, and a special character
    public boolean checkPasswordComplexity(String p){ 
        return p.length()>=8 && p.matches(".*[A-Z].*") 
            && p.matches(".*[0-9].*") 
            && p.matches(".*[!@#$%^&*()_+=`~\\-].*"); 
    }

    // Rubric Rule: Cellphone must start with +27 and have 9 digits
    public boolean checkCellPhoneNumber(String c){ 
        return c.matches("^\\+27[0-9]{9}$"); 
    }

    // Registers username and password, returns success or error message
    public String registerUser(String u,String p){
        if(!checkUserName(u)) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if(!checkPasswordComplexity(p)) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        username=u; 
        password=p;
        return "Password successfully captured.";
    }

    // Overloaded method to also register the cellphone number
    public String registerUser(String u,String p,String c){
        String result = registerUser(u,p);
        if(!result.equals("Password successfully captured.")) return result;
        if(!checkCellPhoneNumber(c)) return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        cellNumber=c;
        return "Cell number successfully captured.";
    }

    // Separate check just for the cellphone message
    public String registerUserCell(String c){
        if(checkCellPhoneNumber(c)) return "Cell number successfully captured.";
        else return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
    }

    // Verifies if entered login details match the stored ones
    public boolean loginUser(String u,String p){ 
        return username.equals(u) && password.equals(p); 
    }

    // Returns welcome message if login is successful, or error if failed
    public String returnLoginStatus(String u,String p){
        if(loginUser(u,p)) return "Welcome "+firstName+", "+lastName+" it is great to see you again.";
        else return "Username or password incorrect, please try again.";
    }
}