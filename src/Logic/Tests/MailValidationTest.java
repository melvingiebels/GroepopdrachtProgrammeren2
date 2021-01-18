package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Logic.Validation.MailValidation;


public class MailValidationTest {
    
    // No mailbox part
    @Test
    public void testMailValidationRequiresNoMailboxPartEnsuresFalse() {
        // Arrange
        String mailAddress = "@gmail.com";

        // Act
        Boolean result = MailValidation.validateMailAddress(mailAddress);

        // Assert
        assertFalse("Test contains mailbox part", result);
    }

    // With mailbox part
    @Test
    public void testMailValidationRequiresMailboxPartEnsuresTrue() {
        // Arrange
        String mailAddress = "email@gmail.com";

        // Act
        Boolean result = MailValidation.validateMailAddress(mailAddress);

        // Assert
        assertTrue("Test contains no mailbox part", result);
    }
    
    // without @
    @Test
    public void testMailValidationRequiresNoAtPartEnsuresFalse() {
        // Arrange
        String mailAddress = "emailgmail.com";
        
        // Act
        Boolean result = MailValidation.validateMailAddress(mailAddress);

        // Assert
        assertFalse("Test contained an @", result);
    }

    // with @
    @Test
    public void testMailValidationRequiresAtPartEnsuresTrue() {
        // Arrange
        String mailAddress = "email@gmail.com";
        
        // Act
        Boolean result = MailValidation.validateMailAddress(mailAddress);

        // Assert
        assertTrue("Test contained no @", result);
    }


    //subdomain-tld delimiter
    @Test
    public void testMailValidationRequiresSubdomainTldEnsuresFalse() {
        // Arrange
        String mailAddress = "test@student.avans.nl";

        // Act
        Boolean result = MailValidation.validateMailAddress(mailAddress);

        // Assert
        assertFalse("Test did not contain subdomains", result);
    }

    //subdomain-tld delimiter
    @Test
    public void testMailValidationRequiresNoSubdomainTldEnsuresTrue() {
        // Arrange
        String mailAddress = "test@avans.nl";

        // Act
        Boolean result = MailValidation.validateMailAddress(mailAddress);

        // Assert
        assertTrue("Email has too many subdomains", result);
    }
    

    // no subdomain part 
    @Test
    public void testMailValidationRequiresNoSubdomainPartEnsuresFalse() {
         // Arrange
         String mailAddress = "test@.nl";

         // Act
         Boolean result = MailValidation.validateMailAddress(mailAddress);
 
         // Assert
         assertFalse("Email has a subdomainpart", result);
    }

    
    // No tld part
    @Test
    public void testMailValidationRequiresNoTdlPartEnsuresFalse() {
         // Arrange
         String mailAddress = "test@student.";

         // Act
         Boolean result = MailValidation.validateMailAddress(mailAddress);
 
         // Assert
         assertFalse("Email did contain tld part", result);
    }

    // With tld part
    @Test
    public void testMailValidationRequiresNoTdlPartEnsuresTrue() {
         // Arrange
         String mailAddress = "test@student.nl";

         // Act
         Boolean result = MailValidation.validateMailAddress(mailAddress);
 
         // Assert
         assertTrue("Email did not contain tld part", result);
    }
}
