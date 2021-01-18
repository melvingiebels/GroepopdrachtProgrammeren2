package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Logic.InputValidation;

public class ZipCodeValidationTest {

    // * @subcontract null postalCode {
    // * @requires postalCode == null;
    // * @signals (NullPointerException) postalCode == null;
    
    @Test(expected = NullPointerException.class)
    public void testValidatePostalCodeRequiresNullSignalsNullPointerException() {
        // Arrange
        String PostalCode = null;
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
    }

    // @subcontract valid postalCode
    @Test
    public void testValidatePostalCodeRequires1000AAEnsuresTrue() {
        // Arrange
        String PostalCode = "1000 AA";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires9999ZZEnsuresTrue() {
        // Arrange
        String PostalCode = "9999ZZ";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires5000LLEnsuresTrue() {
        // Arrange
        String PostalCode = "5000 LL";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    // @subcontract invalid postalCode
    @Test
    public void testValidatePostalCodeRequires999AAEnsuresFalse() {
        // Arrange
        String PostalCode = "999 AA";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires10000AAEnsuresFalse() {
        // Arrange
        String PostalCode = "10000 AA";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires333LLEnsuresFalse() {
        // Arrange
        String PostalCode = "333 LL";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires1000AEnsuresFalse() {
        // Arrange
        String PostalCode = "1000 A";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires1000spaceAEnsuresFalse() {
        // Arrange
        String PostalCode = "1000  A";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    // This test is not working
    @Test
    public void testValidatePostalCodeRequires1000aaEnsuresTrue() {
        // Arrange
        String PostalCode = "1000 aa";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    @Test
    public void testValidatePostalCodeRequires1000SpecialcharEnsuresFalse() {
        // Arrange
        String PostalCode = "1000 %#";
        // Act
        Boolean result = InputValidation.validatePostalCode(PostalCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    // @Test
    // public void
    // testValidatePostalRequiresCodeCanBeShorterThen6CharactersEnsuresFalse() {
    // // Arrange
    // String PostalCode = "1234A";

    // // Act
    // Boolean result = InputValidation.validatePostalCode(PostalCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testValidatePostalCodeCanBeLongerThen6CharactersEnsuresFalse() {
    // // Arrange
    // String PostalCode = "1234AAA";

    // // Act
    // Boolean result = InputValidation.validatePostalCode(PostalCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testValidatePostalCodeStartsWith0EnsuresFalse() {
    // // Arrange
    // String PostalCode = "0123AB";

    // // Act
    // Boolean result = InputValidation.validatePostalCode(PostalCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testValidatePostalCodeDoesNotStartWith4DigitsEnsuresFalse() {
    // // Arrange
    // String PostalCode = "123ABC";

    // // Act
    // Boolean result = InputValidation.validatePostalCode(PostalCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testValidatePostalCodeDoesNotEndWith2LettersEnsuresFalse() {
    // // Arrange
    // String PostalCode = "12345A";

    // // Act
    // Boolean result = InputValidation.validatePostalCode(PostalCode);

    // // Assert
    // assertEquals(false, result);
    // }
}
