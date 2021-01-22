package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Logic.Validation.ZipCodeValidation;

public class ZipCodeValidationTest {

    // * @subcontract null ZipCode {
    // * @requires ZipCode == null;
    // * @signals (NullPointerException) ZipCode == null;
    
    @Test(expected = NullPointerException.class)
    public void testvalidateZipCodeRequiresNullSignalsNullPointerException() {
        // Arrange
        String ZipCode = null;
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
    }

    // @subcontract valid ZipCode
    @Test
    public void testvalidateZipCodeRequires1000aAEnsures1000_AA() {
        // Arrange
        String ZipCode = "1000aA";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires9999ZZEnsuresTrue() {
        // Arrange
        String ZipCode = "9999ZZ";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires5000LLEnsuresTrue() {
        // Arrange
        String ZipCode = "5000 LL";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    // @subcontract invalid ZipCode
    @Test
    public void testvalidateZipCodeRequires999AAEnsuresFalse() {
        // Arrange
        String ZipCode = "999 AA";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires10000AAEnsuresFalse() {
        // Arrange
        String ZipCode = "10000 AA";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires333LLEnsuresFalse() {
        // Arrange
        String ZipCode = "333 LL";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires1000AEnsuresFalse() {
        // Arrange
        String ZipCode = "1000 A";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires1000spaceAEnsuresFalse() {
        // Arrange
        String ZipCode = "1000  A";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    // This test is not working
    @Test
    public void testvalidateZipCodeRequires1000aaEnsuresTrue() {
        // Arrange
        String ZipCode = "1000 aa";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertTrue("This is a valid zipcode", result);
    }

    @Test
    public void testvalidateZipCodeRequires1000SpecialcharEnsuresFalse() {
        // Arrange
        String ZipCode = "1000 %#";
        // Act
        Boolean result = ZipCodeValidation.validateZipCode(ZipCode);
        // Assert
        assertFalse("This is a invalid zipcode", result);
    }

    // @Test
    // public void
    // testValidatePostalRequiresCodeCanBeShorterThen6CharactersEnsuresFalse() {
    // // Arrange
    // String ZipCode = "1234A";

    // // Act
    // Boolean result = ZipCodeValidation.validateZipCode(ZipCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testvalidateZipCodeCanBeLongerThen6CharactersEnsuresFalse() {
    // // Arrange
    // String ZipCode = "1234AAA";

    // // Act
    // Boolean result = ZipCodeValidation.validateZipCode(ZipCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testvalidateZipCodeStartsWith0EnsuresFalse() {
    // // Arrange
    // String ZipCode = "0123AB";

    // // Act
    // Boolean result = ZipCodeValidation.validateZipCode(ZipCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testvalidateZipCodeDoesNotStartWith4DigitsEnsuresFalse() {
    // // Arrange
    // String ZipCode = "123ABC";

    // // Act
    // Boolean result = ZipCodeValidation.validateZipCode(ZipCode);

    // // Assert
    // assertEquals(false, result);
    // }

    // @Test
    // public void testvalidateZipCodeDoesNotEndWith2LettersEnsuresFalse() {
    // // Arrange
    // String ZipCode = "12345A";

    // // Act
    // Boolean result = ZipCodeValidation.validateZipCode(ZipCode);

    // // Assert
    // assertEquals(false, result);
    // }
}
