package com.mycompany.quickchat.part1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    
    Login login = new Login("Kyle","Smith");
    
    @Test public void testUserNameCorrect() { 
        assertTrue(login.checkUserName("kyl_1")); 
    }
    
    @Test public void testUserNameIncorrect() { 
        assertFalse(login.checkUserName("kyle!!!!!!!")); 
    }
    
    @Test public void testUserNameIncorrectMessage() {
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
        login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!"));
    }

    @Test public void testPasswordCorrect() { 
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!")); 
    }
   
    @Test public void testPasswordIncorrect() { 
        assertFalse(login.checkPasswordComplexity("password")); 
    }
   
    @Test public void testPasswordCorrectMessage() {
        assertEquals("Password successfully captured.",
        login.registerUser("kyl_1", "Ch&&sec@ke99!"));
    }
   
    @Test public void testPasswordIncorrectMessage() {
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
        login.registerUser("kyl_1", "password"));
    }
    @Test public void testCellCorrect() { 
        assertTrue(login.checkCellPhoneNumber("+27838968976")); 
    }
    
    @Test public void testCellIncorrect() { 
        assertFalse(login.checkCellPhoneNumber("08966553")); 
    }
   
    @Test public void testCellCorrectMessage() {
        assertEquals("Cell number successfully captured.",
        login.registerUserCell("+27838968976"));
    }
    
    @Test public void testCellIncorrectMessage() {
        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
        login.registerUserCell("08966553"));
    }

    @Test public void testLoginSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    
    @Test public void testLoginFailed() {
        assertFalse(login.loginUser("kyl_1", "wrongpass"));
    }
    @Test public void testUsernameCorrectlyFormattedTrue() {
        assertTrue(login.checkUserName("kyl_1"));
    }
}

