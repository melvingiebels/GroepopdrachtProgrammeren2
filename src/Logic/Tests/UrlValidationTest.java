package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Logic.Validation.UrlValidation;


public class UrlValidationTest {
    
    @Test
    public void testValidateUrlStartsWithoutProtocolEnsuresFalse() {
        // Arrange
        String url = "www.website.com";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("Url contains no protocol", result);
    }

    @Test
    public void testValidateUrlStartsWithProtocolEnsurestrue() {
        // Arrange
        String url = "http://www.website.com";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("Url contains protocol", result);
    }

    @Test
    public void testValidateUrlRequiresNoSubdomainEnsuresFalse(){
        // Arrange
        String url = "https://.website.com";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("Url contains no subdomain", result);
    }

    @Test
    public void testValidateUrlRequiresSubdomainEnsuresTrue(){
        // Arrange
        String url = "https://www.website.com";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("Url contains subdomain", result);
    }

    @Test
    public void testValidateUrlRequiresNoDomainNameEnsuresFalse(){
        // Arrange
        String url = "http://www..com";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("Url contains no domain name", result);
    }

    @Test
    public void testValidateUrlRequiresDomainNameEnsuresTrue(){
        // Arrange
        String url = "http://www..com";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("Url contains domain name", result);
    }

    @Test
    public void testValidateUrlRequiresNoTopLevelDomainEnsuresFalse(){
        // Arrange
        String url = "https://www.website.";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("Url contains no top level domain", result);
    }

    @Test
    public void testValidateUrlRequiresTopLevelDomainEnsuresTrue(){
        // Arrange
        String url = "https://www.website.";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("Url contains top level domain", result);
    }
}
