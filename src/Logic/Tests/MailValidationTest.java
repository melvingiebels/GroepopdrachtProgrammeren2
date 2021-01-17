package Logic.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import Logic.InputValidation;


public class MailValidationTest {
    
    @Test
    public void testMailToolsRequiresNoMailboxPartEnsuresFalse() {
        // Arrange
        String mailAddress = "@gmail.com";

        // Act
        Boolean result = InputValidation.validateMailAddress(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testMailToolsRequiresSubdomainTldEnsuresFalse() {
        // Arrange
        String mailAddress = "test@student.avans.nl";

        // Act
        Boolean result = InputValidation.validateMailAddress(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testMailToolsRequiresNoSubdomainPartEnsuresFalse() {
         // Arrange
         String mailAddress = "test@.nl";

         // Act
         Boolean result = InputValidation.validateMailAddress(mailAddress);
 
         // Assert
         assertEquals(false, result);
    }

    @Test
    public void testMailToolsRequiresNoTdlPartEnsuresFalse() {
         // Arrange
         String mailAddress = "test@student.";

         // Act
         Boolean result = InputValidation.validateMailAddress(mailAddress);
 
         // Assert
         assertEquals(false, result);
    }
}
