package Logic.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import Logic.InputValidation;


public class PostalCodeValidationTest {

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
