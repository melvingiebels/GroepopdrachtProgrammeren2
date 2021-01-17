package Logic.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import Logic.InputValidation;


public class PercentageValidationTest {
    
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
}
