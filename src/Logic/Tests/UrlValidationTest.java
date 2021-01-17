package Logic.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import Logic.InputValidation;


public class UrlValidationTest {
    
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
}
