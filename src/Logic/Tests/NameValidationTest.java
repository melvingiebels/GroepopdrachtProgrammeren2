package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Logic.Validation.NameValidation;

public class NameValidationTest {
    // Valid name
    @Test
    public void testNameValidationRequiresArnoSpaceBroedersEnsuresTrue() {
        // Arrange
        String name = "Arno Broeders";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertTrue("False name", result);
    }

    // No lastname
    @Test
    public void testNameValidationRequiresArnoEnsuresFalse() {
        // Arrange
        String name = "Arno";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name has a lastname", result);
    }

    // Numbers in name
    @Test
    public void testNameValidationRequiresArno1SpaceBroedersEnsuresFalse() {
        // Arrange
        String name = "Arno1 Broeders";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name does not contain numbers", result);
    }

    // Spaces at beginning and end
    @Test
    public void testNameValidationRequiresSpaceArnoSpaceBroedersSpaceEnsuresFalse() {
        // Arrange
        String name = " Arno Broeders ";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name does not contain spaces at the end/beginning", result);
    }

    // Too many spaces between the firstName and LastName
    @Test
    public void testNameValidationRequiresArno3SpaceBroedersEnsuresFalse() {
        // Arrange
        String name = "Arno   Broeders";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name does not have more than 1 space in the middle", result);
    }

    // No spaces in the middle
    @Test
    public void testNameValidationRequiresArnoBroedersEnsuresFalse() {
        // Arrange
        String name = "ArnoBroeders";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name has a space in between", result);
    }

    // Name longer than 50 characters
    @Test
    public void testNameValidationRequiresAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarnoooSpaceBroooooooooedersEnsuresFalse() {
        // Arrange
        String name = "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarnooo Broooooooooeders";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name is smaller then 50 characters", result);
    }

    // Name is empty
    @Test
    public void testNameValidationRequiresEmptyEnsuresFalse() {
        // Arrange
        String name = "";

        // Act
        boolean result = NameValidation.validateName(name);

        // Assert
        assertFalse("Name is not empty", result);
    }
}
