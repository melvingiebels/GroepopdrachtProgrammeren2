package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Logic.Validation.PercentageValidation;


public class PercentageValidationTest {
    
    @Test
    public void testValidatePercentageIsLessThen0EnsuresFalse(){
        // Arrange
        int percentage = -1;

        // Act
        Boolean result = PercentageValidation.validatePercentage(percentage);

        // Assert
        assertFalse("percentage is -1", result);
    }

    @Test
    public void testValidatePercentage0EnsuresTrue(){
        // Arrange
        int percentage = 0;

        // Act
        Boolean result = PercentageValidation.validatePercentage(percentage);

        // Assert
        assertTrue("Percentage is 0", result);
    }

    @Test
    public void testValidatePercentage50EnsuresTrue(){
        // Arrange
        int percentage = 50;

        // Act
        Boolean result = PercentageValidation.validatePercentage(percentage);

        // Assert
        assertTrue("Percentage is 50", result);
    }

    @Test
    public void testValidatePercentage100EnsuresTrue(){
        // Arrange
        int percentage = 100;

        // Act
        Boolean result = PercentageValidation.validatePercentage(percentage);

        // Assert
        assertTrue("Percentage is 100", result);
    }

    @Test
    public void testValidatePercentageIsGreaterThen100EnsuresFalse(){
        // Arrange
        int percentage = 101;

        // Act
        Boolean result = PercentageValidation.validatePercentage(percentage);

        // Assert
        assertFalse("percentage is 101", result);
    }
}
