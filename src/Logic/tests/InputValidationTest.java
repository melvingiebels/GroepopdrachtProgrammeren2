package Logic.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import Logic.InputValidation;

public class InputValidationTest {
    
    // Mail validation tests ------------------------------------------------------------
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


    // URL validation Tests ----------------------------------------------------------------
    @Test
    public void testValidateUrlStartsWithoutProtocolEnsuresFalse() {
        // Arrange
        String url = "www.website.com";

        // Act
        Boolean result = InputValidation.validateUrl(url);

        // Assert
        assertEquals(false, result);
   }

    @Test
    public void testValidateUrlRequiresNoSubdomainEnsuresFalse(){
        // Arrange
        String url = "https://.website.com";

        // Act
        Boolean result = InputValidation.validateUrl(url);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidateUrlRequiresNoDomainNameEnsuresFalse(){
        // Arrange
        String url = "http://www..com";

        // Act
        Boolean result = InputValidation.validateUrl(url);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidateUrlRequiresNoTopLevelDomainEnsuresFalse(){
        // Arrange
        String url = "https://www.website.";

        // Act
        Boolean result = InputValidation.validateUrl(url);

        // Assert
        assertEquals(false, result);
    }

    
    // Percentage validation Tests --------------------------------------------------------------
    @Test
    public void testValidatePercentageIsLessThen0EnsuresFalse(){
        // Arrange
        int percentage = -1;

        // Act
        Boolean result = InputValidation.validatePercentage(percentage);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidatePercentageIsGreaterThen100EnsuresFalse(){
        // Arrange
        int percentage = 101;

        // Act
        Boolean result = InputValidation.validatePercentage(percentage);

        // Assert
        assertEquals(false, result);
    }


    // Date validation Tests --------------------------------------------------------------
    

    // CertificateGrade validation Tests --------------------------------------------------------------
    @Test
    public void testValidateGradeisLessThen1EnsuresFalse(){
        // Arrange
        double grade = 0;

        // Act
        Boolean result = InputValidation.validateGrade(grade);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidateGradeIsGreaterThen10EnsuresFalse(){
        // Arrange
        double grade = 11;

        // Act
        Boolean result = InputValidation.validateGrade(grade);

        // Assert
        assertEquals(false, result);
    }


    // PostalCode validation Tests --------------------------------------------------------------    
    @Test
    public void testValidatePostalCodeCanBeShorterThen6CharactersEnsuresFalse(){
        // Arrange
        String PostalCode = "1234A";

        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidatePostalCodeCanBeLongerThen6CharactersEnsuresFalse(){
        // Arrange
        String PostalCode = "1234AAA";

        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);

        // Assert
        assertEquals(false, result);
    }


    @Test
    public void testValidatePostalCodeStartsWith0EnsuresFalse(){
        // Arrange
        String PostalCode = "0123AB";

        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidatePostalCodeDoesNotStartWith4DigitsEnsuresFalse(){
        // Arrange
        String PostalCode = "123ABC";

        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidatePostalCodeDoesNotEndWith2LettersEnsuresFalse(){
        // Arrange
        String PostalCode = "12345A";

        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);

        // Assert
        assertEquals(false, result);
    }


}
